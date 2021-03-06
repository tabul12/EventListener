package sendMail;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.naming.factory.SendMailFactory;
 
public class SendEmail {
	public void sendMailFromE(String To,String subject, String mailtext)
	{
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("eventlistenerbytmm@gmail.com","tornikemishamamuka");
				}
			});
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("eventlistenerbytmm@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(To));
			message.setSubject(subject);
			message.setText(mailtext);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}