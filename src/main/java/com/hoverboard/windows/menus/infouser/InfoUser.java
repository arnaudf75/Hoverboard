package com.hoverboard.windows.menus.infouser;

import com.hoverboard.BDD;
import com.hoverboard.User;
import com.hoverboard.windows.menus.themes.Theme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * InfoUser est la fenêtre contenant les informations de l'utilisateur connecté et lui permettant de les modifier.
 * @author Arnaud
 */
public class InfoUser extends JFrame implements ActionListener {
    private final JButton changePassword = new JButton("Changer mon mot de passe");
    private final JButton validate = new JButton("Valider");
    private final JButton deleteAccount = new JButton("Supprimer mon compte");
    private final JLabel firstNameLabel = new JLabel("Prénom");
    private final JLabel lastNameLabel = new JLabel("Nom");
    private final JLabel emailLabel = new JLabel("Email");
    private final JLabel loginLabel = new JLabel("Votre login : ");
    private final JLabel isAdminLabel = new JLabel("Droits d'administrateur");
    private final JPanel main_container = new JPanel();
    private final JTextField firstNameField = new JTextField("");
    private final JTextField lastNameField = new JTextField("");
    private final JTextField emailField = new JTextField("");
    protected User utilisateur = null;
    
    /**
     * Crée la fenêtre à partir des informations contenues dans l'objet utilisateur de la classe User.
     * @param utilisateur L'objet de la classe User contenant le nom, prénom, login, email et les droits d'administration (True ou False) de l'utilisateur connecté.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public InfoUser(User utilisateur) {
        this.validate.addActionListener(this);
        this.changePassword.addActionListener(this);
        this.deleteAccount.addActionListener(this);
        
        this.utilisateur = utilisateur;
        this.firstNameField.setText(User.getFirstName());
        this.lastNameField.setText(User.getLastName());
        this.emailField.setText(User.getEmail());
        this.loginLabel.setText(loginLabel.getText()+User.getLogin());
        this.main_container.setLayout(new GridLayout(6,2));
        
        this.main_container.add(firstNameLabel);
        this.main_container.add(firstNameField);
        this.main_container.add(lastNameLabel);
        this.main_container.add(lastNameField);
        this.main_container.add(emailLabel);
        this.main_container.add(emailField);
        this.main_container.add(isAdminLabel);
        
        if (User.getAdminRights() == true) {
            this.main_container.add(new JLabel("Oui"));
        }
        else {
            this.main_container.add(new JLabel("Non"));
        }
        this.main_container.add(loginLabel);
        this.main_container.add(changePassword);
        this.main_container.add(validate);
        this.main_container.add(deleteAccount);
        
        this.setTitle("Vos informations");
        this.setIconImage(Theme.icone.getImage());
        this.setContentPane(main_container);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * Modifie les changements, ouvre une fenêtre de modification de mot de passe ou demande une suppression de compte.
     * @param event L'action qui vient de se produire (bouton cliqué). 
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == validate) {
            if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || emailField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                BDD.setNewPrivateInformations(User.getIdUser(), firstNameField.getText(), lastNameField.getText(), emailField.getText());
                User.setFirstName(firstNameField.getText());
                User.setLastName(lastNameField.getText());
                User.setEmail(emailField.getText());
            }
        }
        else if (source == changePassword) {
            ModifPassword modifPassword = new ModifPassword(User.getIdUser());
        }
        else if (source == deleteAccount) {
            int choix = JOptionPane.showConfirmDialog(null, "Êtes vous sûr de bien vouloir supprimer votre compte ?", "Message de confirmation", JOptionPane.OK_CANCEL_OPTION);
            if (choix == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(null, "Un message vous a été envoyé sur votre adresse email pour supprimer votre compte.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}