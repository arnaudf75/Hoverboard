package windows;

import hoverboard.BDD;
import hoverboard.SendMail;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private final JLabel logo = new JLabel (new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/logo.png")));
    private final JLabel email_label = new JLabel ("Adresse email");
    private final JLabel firstName_label = new JLabel("Prénom :");
    private final JLabel lastName_label = new JLabel("Nom de famille :");
    private final JLabel login_label = new JLabel("Login :");
    private final JLabel password_label = new JLabel("Mot de passe :");
    
    private final JPanel bottom_container = new JPanel();
    private final JPanel center_container = new JPanel();
    private final JPanel main_container = new JPanel();
    
    private final JPasswordField password_field = new JPasswordField ();
    private final JTextField email_field = new JTextField();
    private final JTextField firstName_field = new JTextField();
    private final JTextField lastName_field = new JTextField();
    private final JTextField login_field = new JTextField();
    
    /**
     * Crée une fenêtre permettant à l'utilisateur de se créer un compte sur l'application
     * avec ses informations (login, mot de passe, nom, prénom et email).
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Register() {

        validation.addActionListener(this);

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

        main_container.add(logo, BorderLayout.NORTH);
        main_container.add(center_container, BorderLayout.CENTER);
        main_container.add(bottom_container, BorderLayout.SOUTH);
        
        this.setTitle("Inscription");
        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/icone.png")).getImage());
        this.setContentPane(main_container);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
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
                JOptionPane.showMessageDialog(null, "Vous devez remplir tous les champs pour continuer !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                BDD connexion = new BDD();
                if (connexion.registerUser(firstName, lastName, email, login, password)) {
                    JOptionPane.showMessageDialog(null, "Votre compte a été crée, vérifiez votre boîte mail pour l'activer.", "Compte créé !", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        SendMail send = new SendMail();
                        send.sendRegistrationEmail(email);
                    }
                    catch (MessagingException error) {
                        JOptionPane.showMessageDialog(null, "Impossible d'envoyer le mail d'inscription ! " + error, "erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Ce login ou cet email est déjà pris.", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }    
}