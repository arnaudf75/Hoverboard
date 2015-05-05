package com.hoverboard.windows;

import com.hoverboard.BDD;
import com.hoverboard.SendMail;
import com.hoverboard.User;
import com.hoverboard.windows.menus.themes.Theme;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Login est la fenÃªtre par laquelle l'utilisateur se connecte Ã  son compte.
 * @author Arnaud
 */
public class Login extends JFrame implements ActionListener {
    private final JButton validation = new JButton ("Valider");
    private final JButton password_lost = new JButton("J'ai perdu mon mot de passe");
    private final JButton register = new JButton ("Créer un compte");
    private final JCheckBox check_cookie = new JCheckBox ();
    private final JLabel logo = new JLabel (Theme.logo);
    private final JLabel check_label = new JLabel ("Se souvenir de moi");
    private final JLabel login_label = new JLabel("Saisissez votre login :");
    private final JLabel password_label = new JLabel("Saisissez votre mot de passe :");
    private final JPanel main_container = new JPanel();
    private final JPanel center_container = new JPanel();
    private final JPanel bottom_container = new JPanel();
    private final JPasswordField password_field = new JPasswordField();
    private final JTextField login_field = new JTextField();

    /**
     * CrÃ©e une fenÃªtre de login dans laquelle l'utilisateur rentre ses identifiants
     * ou demande Ã  afficher une fenÃªtre d'inscription ou de mot de passe perdu.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Login() {
        
        validation.addActionListener(this);
        password_lost.addActionListener(this);
        register.addActionListener(this);

        main_container.setLayout(new BorderLayout());
        center_container.setLayout(new GridLayout(4,4));
        bottom_container.setLayout(new BorderLayout());

        center_container.add(login_label);
        center_container.add(login_field);
        center_container.add(password_label);
        center_container.add(password_field);
        center_container.add(check_label);
        center_container.add(check_cookie);
        center_container.add(validation);

        bottom_container.add(register, BorderLayout.WEST);
        bottom_container.add(password_lost, BorderLayout.EAST);

        main_container.add(logo, BorderLayout.NORTH);
        main_container.add(center_container, BorderLayout.CENTER);
        main_container.add(bottom_container, BorderLayout.SOUTH);
        
        this.setTitle("Fenêtre de connexion");
        this.setIconImage(Theme.icone.getImage());
        this.setContentPane(main_container);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Affiche la page d'accueil de l'utilisateur si le login et le mot de passe sont valides, ou affiche une fenÃªtre d'enregistrement ou de mot de passe perdu.
     * @param event L'action qui vient de se produire (bouton cliquÃ©).
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == validation) {
            String login = login_field.getText();
            String password = password_field.getText();
            if (login.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vous devez entrer votre login et votre mot de passe pour vous connecter !" , "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                ResultSet isUser = BDD.connect_user(login, password);
                try {
                    if (!isUser.isBeforeFirst()) {
                        JOptionPane.showMessageDialog(null, "Aucun utilisateur n'existe avec ce login et ce mot de passe !" , "ERREUR", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        isUser.next();
                        if (check_cookie.isSelected()) {
                            this.creerCookie(login,password);
                        }
                        this.dispose();
                        int idUser = isUser.getInt("idUser");
                        String firstName = isUser.getString("firstName");
                        String lastName = isUser.getString("lastName");
                        String email = isUser.getString("email");
                        int isAdmin = isUser.getInt("isAdmin");
                        Home homeWindow = new Home(new User(idUser, login, firstName, lastName, email, isAdmin));
                    }
                }
                catch (SQLException error) {
                    JOptionPane.showMessageDialog(null, "Impossible de vous connecter !", "ERREUR", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        else if (source == password_lost) {
            String nomTheme = JOptionPane.showInputDialog(null, "Saisissez votre adresse email :", "Réinitialisation du mot de passe", JOptionPane.QUESTION_MESSAGE);
            try {
                String new_password = BDD.resetPassword(nomTheme);
                if (new_password != null) {
                    SendMail send = new SendMail();
                    send.sendPasswordLostEmail(nomTheme, new_password);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Aucun utilisateur n'existe avec cet email !", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (MessagingException error) {
                JOptionPane.showMessageDialog(null, "Impossible d'envoyer un message pour changer votre mot de passe !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (source == register) {
            Register reg = new Register();
        }
    }
    
    /**
     * CrÃ©er un fichier cookie pour connecter l'utilisateur automatiquement la prochaine fois qu'il utilisera l'application.
     * Les informations saisies dans la fenÃªtre de login sont rÃ©cupÃ©rÃ©es pour les insÃ©rÃ©es dans le fichier cookie_login.xml.
     * @param loginField Le login saisi par l'utilisateur lors de la connexion
     * @param passwordField Le mot de passe saisi par l'utilisateur lors de la connexion
     */
    public void creerCookie(String loginField, String passwordField) {
        try {
            Document cookie = new Document().setRootElement(new Element("cookie"));
            cookie.getRootElement().addContent(new Element("login").addContent(loginField));
            cookie.getRootElement().addContent(new Element("password").addContent(passwordField));
            XMLOutputter cookie_login = new XMLOutputter();
            cookie_login.setFormat(Format.getPrettyFormat());
            new File("userData").mkdirs();
            cookie_login.output(cookie, new FileWriter(new File("userData/cookie_login.xml")));
        }
        catch (IOException error) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la création du cookie ! ", "ERREUR", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}