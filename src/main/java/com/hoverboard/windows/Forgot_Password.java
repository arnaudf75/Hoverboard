package com.hoverboard.windows;

import com.hoverboard.BDD;
import com.hoverboard.SendMail;
import com.hoverboard.windows.menus.themes.Theme;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * 
 * @author Cavoleau
 */
public class Forgot_Password extends JFrame implements ActionListener{
    private final JLabel logo = new JLabel (Theme.logo);
    private final JPanel grid_container= new JPanel();
    private final JPanel main_container= new JPanel();
    private final JLabel use_mail = new JLabel("Entrez votre adresse mail :");
    private final JTextField mail_field = new JTextField();
    private final JButton valider_mail = new JButton ("Valider");
    
    @SuppressWarnings("LeakingThisInConstructor")
    public Forgot_Password(){

        valider_mail.addActionListener(this);

        grid_container.setLayout(new GridLayout(2,2));
        grid_container.add(use_mail);
        grid_container.add(mail_field);
        grid_container.add(new JLabel(""));
        grid_container.add(valider_mail);

        main_container.add(logo);
        main_container.add(grid_container);
        
        this.setTitle("Mot de passe perdu");
        this.setIconImage(Theme.icone.getImage());
        this.add(main_container);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Envoie un message à l'utilisateur pour changer son mot de passe à l'adresse qu'il a renseignée.
     * @param event L'action qui vient de se produire, en l'occurence un clic sur le bouton "Valider".
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == valider_mail){
            try {
                String new_password = BDD.resetPassword(mail_field.getText());
                if (new_password != null) {
                    SendMail send = new SendMail();
                    send.sendPasswordLostEmail(mail_field.getText(), new_password);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Aucun utilisateur n'existe avec cet email !", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (MessagingException error) {
                JOptionPane.showMessageDialog(null, "Impossible d'envoyer un message pour changer votre mot de passe !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}