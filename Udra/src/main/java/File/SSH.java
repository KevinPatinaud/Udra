package File;


import java.awt.*;

import javax.swing.*;

import com.jcraft.jsch.*;

import udra.Udra;

import java.io.*;

public class SSH {

	public static final String col_FullPath = "FullPath";
	public static final String col_Path = "Path";
	public static final String col_FileName = "FileName";
	public static final String col_FileType = "FileType";
	
	private Channel channel;
	private Session session;
	
	public SSH(String user, String passWord, String host) throws JSchException
	{
		JSch jsch = new JSch();

		session = jsch.getSession(user, host, 22);
		UserInfo ui = new MyUserInfoSilence(passWord);
		session.setUserInfo(ui);
		session.connect();

	}
	
	
	
	public int getFileSize(String fullPath) throws Exception
	{
		String fileSize = requeteSSH( "ls -s '" + fullPath + "'  | cut -d' ' -f1");
		return Integer.valueOf(fileSize.replaceAll("\\s", ""));
	}
	
	
	
	public Udra getContentServerDirectory(String fichierDistant) throws Exception
	{
		Udra listFile = new Udra(col_FullPath, col_Path , col_FileName, col_FileType);
		
		//test si le fichier passé est un repertoir ou un fichier simple
		String typeFichier = requeteSSH( "file " + fichierDistant + " | cut -d':' -f2");

		//System.out.println(typeFichier);
		
		//si c'est un repertoire
		if ( typeFichier.contains(" directory"))
		{
			
			//liste le contenu
			String[] listeFichier = requeteSSH( "ls " + fichierDistant).split("\n");
			
			if (! listeFichier[0].trim().equalsIgnoreCase("")) //control que au moins un élément soit présent dans le repertoire
			for (int i = 0 ; i < listeFichier.length; i++)
			{
				listFile.add( getContentServerDirectory( fichierDistant + "/" + listeFichier[i]) );
			}
		}
		//si c'est un fichier, on enregistre ses informations
		else
		{
			fichierDistant = fichierDistant.replaceAll("//", "/");
			String[] spltFileName = fichierDistant.split("/");
			String fileName = spltFileName[spltFileName.length - 1];
			listFile.insertALine(fichierDistant ,  fichierDistant.subSequence(0, fichierDistant.length() - fileName.length()) , fileName , typeFichier);
		}
		
		return listFile;
	}
	
		
	
	
	
	
	public void getFile(String user, String passWord, String host, String fichierDistant, String fichierLocal) throws Exception
	{
		FileOutputStream fos = null;
		String prefix = null; // Le prefixe permet de gérer le cas ou fichierLocal correspond à un répertoire. Dans ce cas le nom du fichier téléchargé sera telecharger identique au nom d serveur

		fichierLocal = fichierLocal.replaceAll("\\\\", "/");
		if (new File(fichierLocal).isDirectory()) {
			prefix = fichierLocal + File.separator;
		}
		else //si le fichier n'est pas un repertoire ou qu'il n'existe pas 
		{
			if ( ! (new File(fichierLocal).exists())) //si le fichier n'existe pas (l'arborescence correspond donc forcement à un fichier)
			{
				//on s'assure que son arborescence est créée
				
				String[] spltPathFichierLocal = fichierLocal.split("/");
				String recompo = "";
				for (int i = 0 ; i < spltPathFichierLocal.length - 1 ; i++)
				{
					recompo = recompo + "/" + spltPathFichierLocal[i];
					if ( ! (new File(recompo).exists())) // si le repertoire de l'arborescence n'esite pas on le cré
					{
						(new File(recompo)).mkdir();
					}
				}
				
			}
		}
		


		// exec 'scp -f rfile' remotely

		fichierDistant = fichierDistant.replace("'", "'\"'\"'");
		fichierDistant = "'" + fichierDistant + "'";
		String command = "scp -f " + fichierDistant;
		Channel channel = session.openChannel("exec");
		((ChannelExec) channel).setCommand(command);

		// get I/O streams for remote scp

		OutputStream out = channel.getOutputStream();
		InputStream in = channel.getInputStream();
		channel.connect();

		byte[] buf = new byte[1024];

		buf[0] = 0;
		out.write(buf, 0, 1);
		out.flush();

		while (true) {
			int c = checkAck(in);
			if (c != 'C') {
				break;
			}

			// read '0644 '
			in.read(buf, 0, 5);
			long filesize = 0L;
			while (true) {
				if (in.read(buf, 0, 1) < 0) {
					break;
				}

				if (buf[0] == ' ')
					break;
				filesize = filesize * 10L + (long) (buf[0] - '0');
			}

			String file = null;

			for (int i = 0;; i++) {
				in.read(buf, i, 1);
				if (buf[i] == (byte) 0x0a) {
					file = new String(buf, 0, i);
					break;
				}
			}

			// System.out.println("filesize="+filesize+", file="+file);
			// send '\0'
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();

			// read a content of lfile
			fos = new FileOutputStream(prefix == null ? fichierLocal : prefix + file);
			int foo;

			while (true) {

				if (buf.length < filesize)
					foo = buf.length;
				else
					foo = (int) filesize;

				foo = in.read(buf, 0, foo);

				if (foo < 0) {
					break;
				}

				fos.write(buf, 0, foo);

				filesize -= foo;

				if (filesize == 0L)

					break;

			}

			fos.close();

			fos = null;

			if (checkAck(in) != 0) {

				System.exit(0);

			}

			// send '\0'

			buf[0] = 0;

			out.write(buf, 0, 1);

			out.flush();

		}

		session.disconnect();

	}
	
	
	
	
	

	public static void sendFile(String user, String passWord, String host, String fichierLocal, String fichierDistant)	throws Exception
	{

		FileInputStream fis = null;

		JSch jsch = new JSch();

		Session session = jsch.getSession(user, host, 22);

		// username and password will be given via UserInfo interface.

		UserInfo ui = new MyUserInfoSilence(passWord);

		session.setUserInfo(ui);

		session.connect();

		boolean ptimestamp = true;

		// exec 'scp -t rfile' remotely

		fichierDistant = fichierDistant.replace("'", "'\"'\"'");

		fichierDistant = "'" + fichierDistant + "'";

		String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + fichierDistant;

		Channel channel = session.openChannel("exec");

		((ChannelExec) channel).setCommand(command);

		// get I/O streams for remote scp

		OutputStream out = channel.getOutputStream();

		InputStream in = channel.getInputStream();

		channel.connect();

		if (checkAck(in) != 0) {

			System.exit(0);

		}

		File _lfile = new File(fichierLocal);

		if (ptimestamp) {

			command = "T" + (_lfile.lastModified() / 1000) + " 0";

			// The access time should be sent here,

			// but it is not accessible with JavaAPI ;-<

			command += (" " + (_lfile.lastModified() / 1000) + " 0\n");

			out.write(command.getBytes());

			out.flush();

			if (checkAck(in) != 0) {

				System.exit(0);

			}

		}

		// send "C0644 filesize filename", where filename should not include '/'

		long filesize = _lfile.length();

		command = "C0644 " + filesize + " ";

		if (fichierLocal.lastIndexOf('/') > 0) {

			command += fichierLocal.substring(fichierLocal.lastIndexOf('/') + 1);

		} else {

			command += fichierLocal;

		}

		command += "\n";

		out.write(command.getBytes());

		out.flush();

		if (checkAck(in) != 0) {

			System.exit(0);

		}

		// send a content of lfile

		fis = new FileInputStream(fichierLocal);

		byte[] buf = new byte[1024];

		while (true) {

			int len = fis.read(buf, 0, buf.length);

			if (len <= 0)

				break;

			out.write(buf, 0, len); // out.flush();

		}

		fis.close();

		fis = null;

		// send '\0'

		buf[0] = 0;

		out.write(buf, 0, 1);

		out.flush();

		if (checkAck(in) != 0) {

			System.exit(0);

		}

		out.close();

		channel.disconnect();

		session.disconnect();

	}

	public String requeteSSH(String requete) throws Exception
	{
		channel = session.openChannel("exec");
		channel.setInputStream(null);
		((ChannelExec) channel).setErrStream(System.err);
		((ChannelExec) channel).setCommand(requete);
		InputStream in = channel.getInputStream();

		channel.connect();
		String result = "";

		byte[] tmp = new byte[1024];
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0)
					break;
				result = result + (new String(tmp, 0, i));
			}

			if (channel.isClosed()) {
				if (in.available() > 0)
					continue;
				break;
			}
		}
		return result;
	}
	
	

	public static class MyUserInfoSilence implements UserInfo {
		String passwd;
		public MyUserInfoSilence(String passWord) {	passwd = passWord;	}
		public String getPassword() {	return passwd;	}
		public boolean promptYesNo(String str) {	return true;	}
		public String getPassphrase() {		return null;		}
		public boolean promptPassphrase(String message) {	return true;		}
		public boolean promptPassword(String message) {		return true;	}
		public void showMessage(String message) {	}
	}

	
	
	static int checkAck(InputStream in) throws IOException {

		int b = in.read();

		// b may be 0 for success,

		// 1 for error,

		// 2 for fatal error,

		// -1

		if (b == 0)

			return b;

		if (b == -1)

			return b;

		if (b == 1 || b == 2) {

			StringBuffer sb = new StringBuffer();

			int c;

			do {

				c = in.read();

				sb.append((char) c);

			} while (c != '\n');

			if (b == 1) { // error

				System.out.print(sb.toString());

			}

			if (b == 2) { // fatal error
				System.out.print(sb.toString());

			}

		}

		return b;

	}

}
