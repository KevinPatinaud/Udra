package Library;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import udra.Udra;

public class Udra_Lib_Mail {
	


	@Deprecated
	public static void sendMail_GMail( Udra mails,  String colEmetteur, String colPassword, String colDestinataire, String colObjet, String colMessage)
	{
		sendMail_GMail( mails, mails.get_the_index_of_title_from_his_Name( colEmetteur), mails.get_the_index_of_title_from_his_Name( colPassword), mails.get_the_index_of_title_from_his_Name( colDestinataire), mails.get_the_index_of_title_from_his_Name( colObjet), mails.get_the_index_of_title_from_his_Name( colMessage));
	}


	/**
	 * Ne fonctionne pas avec le JRE 1.9
	 * @param mails
	 * @param colDestinataire
	 * @param colObjet
	 * @param colMessage
	 */
	@Deprecated
	public static void sendMail_GMail( Udra mails,int colEmetteur, int colPassword, int colDestinataire, int colObjet, int colMessage)
	{
		for (int i = 0 ; i < mails.sizeRow(); i++)
		{
		    sendFromGMail(mails.getString(colEmetteur, i ), mails.getString(colPassword, i ), mails.getString(colDestinataire, i ), mails.getString(colObjet, i ), mails.getString(colMessage, i ));	
		}
	}
	
	
	
	
	  
    public static void sendFromGMail(String from, String pass, String to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress toAddress = new InternetAddress();

            // To get the array of addresses
                toAddress = new InternetAddress(to);

                message.addRecipient(Message.RecipientType.TO, toAddress);


            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
	
}
