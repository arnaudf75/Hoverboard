package windows;

import hoverboard.BDD;
import hoverboard.ParserXml;
import hoverboard.SendMail;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Hashtable;
import javax.mail.MessagingException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register_window extends JFrame implements ActionListener {

    private final JButton validation = new JButton ("Valider");
    private final JButton reset = new JButton ("Annuler");
    private final JLabel logo = new JLabel (new ImageIcon("src/ressources/logo.png"));
    private final JLabel email_label = new JLabel ("Enter your email adress");
    private final JLabel firstName_label = new JLabel("Enter your first name :");
    private final JLabel lastName_label = new JLabel("Enter your last name :");
    private final JLabel login_label = new JLabel("Enter your login :");
    private final JLabel password_label = new JLabel("Enter your password :");
    
    private final JPanel bottom_container = new JPanel();
    private final JPanel center_container = new JPanel();
    private final JPanel main_container = new JPanel();
    
    private final JPasswordField password_field = new JPasswordField ();
    private final JTextField email_field = new JTextField();
    private final JTextField firstName_field = new JTextField();
    private final JTextField lastName_field = new JTextField();
    private final JTextField login_field = new JTextField();
    
    public Register_window() {
        this.setTitle("Registration window");
        this.setSize(400, 400);
        this.setIconImage(new ImageIcon(this.getClass().getResource("logo.png")).getImage());

        validation.addActionListener(this);
        reset.addActionListener(this);

        center_container.setLayout(new GridLayout(6, 6));

        center_container.add(firstName_label);
        center_container.add(firstName_field);
        center_container.add(lastName_label);
        center_container.add(lastName_field);
        center_container.add(email_label);
        center_container.add(email_field);
        center_container.add(login_label);
        center_container.add(login_field);
        center_container.add(password_label);
        center_container.add(password_field); 

        bottom_container.add(validation);
        bottom_container.add(reset);

        main_container.add(logo, BorderLayout.NORTH);
        main_container.add(center_container, BorderLayout.CENTER);
        main_container.add(bottom_container, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setContentPane(main_container);
        this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == validation) {
            String firstName = firstName_field.getText();
            String lastName = lastName_field.getText();
            String email = email_field.getText();
            String login = login_field.getText();
            String password = password_field.getText();
            if (login.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You must fill all of the fields to continue !" , "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            }
            else {
                ParserXml xmlParser = new ParserXml();
                Hashtable data_jdbc = xmlParser.getDataJDBC(xmlParser.getSax(), xmlParser.getDocument(), xmlParser.getRacine());        
                BDD connexion = new BDD(data_jdbc.get("dbUrl").toString(), data_jdbc.get("driver").toString(), data_jdbc.get("login").toString(), data_jdbc.get("password").toString());
                if (connexion.ifUserExists(login, email)) {
                    JOptionPane.showMessageDialog(null, "Your account has been created, check your email inbox to activate it." , "ERROR",
                    JOptionPane.INFORMATION_MESSAGE);
                    try {
                        SendMail send = new SendMail();
                        send.sendRegistrationEmail(email);
                    }
                    catch (MessagingException e) {

                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "This login or this email are already taken." , "ERROR",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if(source == reset) {
            System.out.println("Vous avez cliqué sur reset");
        }
    }    
}