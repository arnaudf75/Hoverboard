package windows;

import hoverboard.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.HashMap;

/**
 * Login est la fenêtre par laquelle l'utilisateur se connecte à son compte.
 * @author Arnaud
 */

public class Login extends JFrame implements ActionListener {
    
    private final JButton validation = new JButton ("Valider");
    private final JButton reset = new JButton ("Annuler");
    private final JButton password_lost = new JButton("I lost my password");
    private final JButton register = new JButton ("I don't have an account");
    private final JCheckBox check_cookie = new JCheckBox ();
    
    private final JLabel logo = new JLabel (new ImageIcon("src/ressources/logo.png"));
    private final JLabel check_label = new JLabel ("Remember me ?");
    private final JLabel login_label = new JLabel("Enter your login :");
    private final JLabel password_label = new JLabel("Enter your password :");
    
    
    private final JPanel main_container = new JPanel();
    private final JPanel center_container = new JPanel();
    private final JPanel bottom_container = new JPanel();
    private final JPasswordField password_field = new JPasswordField();
    private final JTextField login_field = new JTextField();

    
    public Login() {
        this.setTitle("Fenêtre de connexion");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        
        validation.addActionListener(this);
        reset.addActionListener(this);
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
        center_container.add(reset);

        bottom_container.add(register, BorderLayout.WEST);
        bottom_container.add(password_lost, BorderLayout.EAST);

        main_container.add(logo, BorderLayout.NORTH);
        main_container.add(center_container, BorderLayout.CENTER);
        main_container.add(bottom_container, BorderLayout.SOUTH);
        
        this.setContentPane(main_container);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Affiche la page d'accueil de l'utilisateur si le login et le mot de passe sont valides, ou affiche une fenêtre d'enregistrement ou de mot de passe perdu.
     * @param event 
     * L'action qui vient de se produire (bouton cliqué).
     */
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == validation) {
            String login = login_field.getText();
            String password = password_field.getText();
            if (login.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "You must enter your login and your password to continue !" , "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            }
            else {
                ParserXml xmlParser = new ParserXml();
                HashMap data_jdbc = xmlParser.getDataJDBC(xmlParser.getSax());        
                BDD connexion = new BDD(data_jdbc.get("dbUrl").toString(), data_jdbc.get("driver").toString(), data_jdbc.get("login").toString(), data_jdbc.get("password").toString());
                if (connexion.connect_user(login, password)) {
                    if (check_cookie.isSelected()) {
                        ParserXml parser = new ParserXml();
                        parser.creerCookie(login,password);
                    }
                    this.setVisible(false);
                    Home home_window = new Home();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Nobody in the database with this login and password !" , "ERROR",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if(source == reset) {

        }
        else if (source == password_lost) {
            Forgot_Password forgot_psw = new Forgot_Password();
        }
        else if (source == register) {
            Register reg = new Register();
        }
    }
}