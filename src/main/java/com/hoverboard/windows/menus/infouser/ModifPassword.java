package com.hoverboard.windows.menus.infouser;

import com.hoverboard.BDD;
import com.hoverboard.windows.menus.themes.Theme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;

/**
 * ModifPassword est la classe permettant à un utilisateur de changer son mot de passe.
 * @author Arnaud
 */
public class ModifPassword extends JFrame implements ActionListener {
    private int idUser = -1;
    private final JButton validate = new JButton("Valider");
    private final JLabel old_password_label = new JLabel("Votre mot de passe actuel");
    private final JLabel new_password_label = new JLabel("Votre nouveau mot de passe");
    private final JLabel confirm_password_label = new JLabel("Confirmez votre nouveau mot de passe");
    private final JPasswordField new_password_field = new JPasswordField();
    private final JPasswordField confirm_password_field = new JPasswordField();
    private final JPasswordField old_password_field = new JPasswordField();
    private final JPanel main_container = new JPanel();
    
    /**
     * Affiche une fenêtre demandant à l'utilisateur de renseigner son ancien mot de passe et le nouveau mot de passe désiré.
     * @param idUser L'id de l'utilisateur connecté.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public ModifPassword(int idUser) {

        this.idUser = idUser;
        this.validate.addActionListener(this);
        this.main_container.setLayout(new GridLayout(4,2));
        this.main_container.add(old_password_label);
        this.main_container.add(old_password_field);
        this.main_container.add(new_password_label);
        this.main_container.add(new_password_field);
        this.main_container.add(confirm_password_label);
        this.main_container.add(confirm_password_field);
        
        this.main_container.add(validate);
        
        this.setTitle("Vos informations");
        this.setIconImage(Theme.logo.getImage());
        this.setContentPane(main_container);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * Change le mot de passe de la base de données par celui saisi.
     * @param event L'action qui vient de se produire (bouton cliqué). 
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == validate) {
            if (!new_password_field.getText().equals(confirm_password_field.getText())) {
                JOptionPane.showMessageDialog(null, "Les deux mots de passe saisis sont différents !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                BDD.setNewPassword(this.idUser, old_password_field.getText(), new_password_field.getText());
                File cookie = new File("userData/cookie_login.xml");
                cookie.delete();
            }
        }
    }
}
