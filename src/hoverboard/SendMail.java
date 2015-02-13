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

public class SendMail {
    
    public SendMail() throws MessagingException {
        String destinataire,host,expediteur,password;
    }
    
    public void sendRegistrationEmail(String mailDestinataire) throws MessagingException {
        // A partir d'ici, le code doit être récupéré depuis la BDD
        String host = "smtp.gmail.com";
        String from = "hoverboard.esgi@gmail.com";
        String Password = "esgi_hoverboard";
        
        // Mail à passer en paramètre soit lorsque l'utilisateur s'inscrit soit si il est connecté, via une requête SQL
        // à passer au constructeur pour créer l'objet mail
        String toAddress = mailDestinataire;
        
        // Get system properties
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        // Code à mettre dans une méthode
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipients(Message.RecipientType.TO, toAddress);
        message.setSubject("Welcome to Hoverboard !");
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Welcome to Hoverboard.\n"
                + "Click on the following link to activate your account:\n"
                + "http://www.lien_activation.hoverboard.com\n"
                + "If you have any questions, feel free to contact us at hoverboard.esgi@gmail.com. \n\n"
                + "Cordially,\n"
                + "The Hoverboard Team\n\n");
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent("<i>This email has been sent to you automatically, please don't reply.</i>", "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(htmlPart);
        message.setContent(multipart);
        
        try {
            Transport tr = session.getTransport();
            tr.connect(host, from, Password);
            tr.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            System.out.println("Mail Sent Successfully");
            tr.close();
        }
        catch (SendFailedException sfe) {
            System.out.println(sfe);
        }
    }
}