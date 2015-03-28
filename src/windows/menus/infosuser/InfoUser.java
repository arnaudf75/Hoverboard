package windows.menus.infosuser;

import hoverboard.User;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Arnaud
 */
public class InfoUser extends JFrame {
    private final JButton Validate = new JButton("Valider");
    private final JLabel firstNameLabel = new JLabel("Prénom");
    private final JLabel lastNameLabel = new JLabel("Nom");
    private final JLabel emailLabel = new JLabel("Email");
    private final JLabel isAdminLabel = new JLabel("Droits d'administrateur");
    private final JPanel main_container = new JPanel();
    private final JTextField firstNameField = new JTextField("");
    private final JTextField lastNameField = new JTextField("");
    private final JTextField emailField = new JTextField("");
    protected User utilisateur = null;
    
    public InfoUser(User utilisateur) {
        this.setTitle("Vos informations");
        this.setSize(500, 200);
        this.setLocationRelativeTo(null);
        this.utilisateur = utilisateur;
        this.firstNameField.setText(this.utilisateur.getFirstName());
        this.lastNameField.setText(this.utilisateur.getLastName());
        this.emailField.setText(this.utilisateur.getEmail());
        
        this.main_container.setLayout(new GridLayout(6,2));
        
        this.main_container.add(firstNameLabel);
        this.main_container.add(firstNameField);
        this.main_container.add(lastNameLabel);
        this.main_container.add(lastNameField);
        this.main_container.add(emailLabel);
        this.main_container.add(emailField);
        this.main_container.add(isAdminLabel);
        if (this.utilisateur.getAdminRights() == true) {
            this.main_container.add(new JLabel("Oui"));
        }
        else {
            this.main_container.add(new JLabel("Non"));
        }
        this.main_container.add(Validate);
        
        this.setContentPane(main_container);
        this.setVisible(true);
    }
}
