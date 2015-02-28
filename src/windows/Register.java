package windows;

import hoverboard.BDD;
import hoverboard.SendMail;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Register est la fenêtre par laquelle l'utilisateur peut se créer un compte.
 * @author Arnaud
 */

public class Register extends JFrame implements ActionListener {

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
    
    @SuppressWarnings("LeakingThisInConstructor")
    public Register() {
        this.setTitle("Registration window");
        this.setSize(400, 400);

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
    
    /**
     * Enregistre l'utilisateur dans la base de données si celui-ci n'existe pas et lui envoie un mail d'activation.
     * @param event 
     * L'action qui vient de se produire (bouton cliqué).
     */
    
    @Override
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
                BDD connexion = new BDD();
                if (connexion.registerUser(firstName, lastName, email, login, password)) {
                    JOptionPane.showMessageDialog(null, "Your account has been created, check your email inbox to activate it." , "Success !",
                    JOptionPane.INFORMATION_MESSAGE);
                    try {
                        String smtp="",  sender="", passwordMail="";
                        SendMail send = new SendMail(smtp,sender, email, passwordMail);
                        send.sendRegistrationEmail(email);
                    }
                    catch (MessagingException e) {
                        System.out.println(e);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "This login or this email are already taken." , "ERROR",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if(source == reset) {
        }
    }    
}