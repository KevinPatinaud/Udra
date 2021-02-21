package File;

import com.jcraft.jsch.JSchException;

import udra.Udra;

/*
	Classe principale du package : File
	Ce package est un utilitaire de gestion de fichier 
 */

public class UFile {

	public static Udra getContentServerDirectory(String user, String passWord, String host, String remoteDirectory) throws Exception
	{
		SSH ssh = new SSH(user,  passWord,  host);
		return ssh.getContentServerDirectory( remoteDirectory);
	}
	
	public static void getFileFromScpServer(String user, String passWord, String host, String remoteFile , String localFile) throws Exception
	{
		SSH ssh = new SSH(user,  passWord,  host);
		ssh.getFile(user, passWord, host, remoteFile, localFile);
	}
	
	
	public static void get_Direcory_Recursively_From_Scp_Server(String user, String passWord, String host, String remoteDirectory , String localDirectory) throws Exception
	{
		localDirectory = localDirectory.replaceAll("\\\\", "/");
		
		SSH ssh = new SSH(user,  passWord,  host);
		Udra listFile = ssh.getContentServerDirectory( remoteDirectory);
		
		for (int i = 0 ; i < listFile.sizeRow(); i++)
		{
			String remoteFile = listFile.getString(SSH.col_FullPath, i);
			// System.out.println("download : " + remoteFile);
			ssh.getFile(user, passWord, host, remoteFile, localDirectory + "/" + remoteFile.replaceAll(remoteDirectory, ""));
		}
	}
}
