package hoverboard;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;

/**
 * SendMail est la classe permettant d'envoyer des messages instantanés aux utilisateurs de l'application grâce à l'API javax.mail.
 * @author Arnaud
 */

public class SendMail {
    BodyPart messageBodyPart = new MimeBodyPart();
    MimeBodyPart messageHtmlPart = new MimeBodyPart();
    MimeMessage message;
    Multipart fullMessage = new MimeMultipart();
    Properties propertiesList = new Properties();
    Session session;
    String smtp, sender, destinataire, password;
    Transport transport;
    
    public SendMail(String smtp, String sender, String destinataire, String password) throws MessagingException {
        this.smtp = "smtp.gmail.com";
        this.sender = "hoverboard.esgi@gmail.com";
        this.destinataire = destinataire;
        this.password = "esgi_hoverboard";
        this.propertiesList.setProperty("mail.transport.protocol", "smtp");
        this.propertiesList.put("mail.smtp.auth", "true");
        this.propertiesList.put("mail.smtp.starttls.enable", "true");
        this.session = Session.getDefaultInstance(this.propertiesList, null);
        this.message = new MimeMessage(this.session);
        this.message.setFrom(new InternetAddress("The Hoverboard Team <"+this.sender+">"));
        this.fullMessage.addBodyPart(this.messageBodyPart);
        this.fullMessage.addBodyPart(this.messageHtmlPart);
        this.message.setContent(this.fullMessage);
        this.message.addRecipients(Message.RecipientType.TO, this.destinataire);
        this.transport = this.session.getTransport();
    }
    
    /**
     * Envoie un mail de confirmation d'inscription à l'utilisateur.
     * @param mailDestinataire
     * Adresse email du destinataire.
     * @throws MessagingException 
     * si l'email n'arrive pas à être envoyé.
     */
    
    public void sendRegistrationEmail(String mailDestinataire) throws MessagingException {
        this.message.setSubject("Welcome to Hoverboard !");
        this.messageBodyPart.setText("Welcome to Hoverboard.\n"
                + "Click on the following link to activate your account:\n"
                + "http://www.lien_activation.hoverboard.com\n"
                + "If you have any questions, feel free to contact us at hoverboard.esgi@gmail.com. \n\n"
                + "Cordially,\n"
                + "The Hoverboard Team\n\n");
        this.messageHtmlPart.setContent("<i>This email has been sent to you automatically, please don't reply.</i>", "text/html");
        
        try {
            this.transport.connect(this.smtp, this.sender, this.password);
            this.transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            System.out.println("Mail Sent Successfully");
            this.transport.close();
        }
        catch (SendFailedException sfe) {
            System.out.println(sfe);
        }
    }
    
    /**
     * Envoie un mail à l'utilisateur pour qu'il change son mot de passe.
     * @param mailDestinataire
     * Adresse email du destinataire.
     * @throws MessagingException 
     * si l'email n'arrive pas à être envoyé.
     */
    
    public void sendPasswordLostEmail(String mailDestinataire) throws MessagingException {
        this.message.setSubject("How to change your password");
        this.messageBodyPart.setText("Hi,\n"
                + "You have recently asked to change your password.\n"
                + "Here's the link to change it :\n"
                + "http://reset.password.hoverboard.com/\n\n"
                + "If you didn't asked for a change, please disregard this email.\n"
                + "Cordially,\n"
                + "The Hoverboard Team\n\n");
        this.messageHtmlPart.setContent("<i>This email has been sent to you automatically, please don't reply.</i>", "text/html");
        try {
            this.transport.connect(this.smtp, this.sender, this.password);
            this.transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            System.out.println("Mail Sent Successfully");
            this.transport.close();
        }
        catch (SendFailedException sfe) {
            System.out.println(sfe);
        }
    }
}