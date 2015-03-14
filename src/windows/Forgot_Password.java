package windows;

import hoverboard.BDD;
import hoverboard.SendMail;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 
 * @author Cavoleau
 */
public class Forgot_Password extends JFrame implements ActionListener{
    private final JPanel grid_container= new JPanel();
    private final JPanel main_container= new JPanel();
    
    private final JLabel logo = new JLabel (new ImageIcon(this.getClass().getClassLoader().getResource("ressources/logo.png")));
    private final JLabel use_mail = new JLabel("Entrez votre adresse mail :");
    private final JTextField mail_field = new JTextField();
    private final JButton valider_mail = new JButton ("Valider");
    
    @SuppressWarnings("LeakingThisInConstructor")
    public Forgot_Password(){

        this.setTitle("Mot de passe perdu");
        this.setSize(600,180);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        valider_mail.addActionListener(this);

        grid_container.setLayout(new GridLayout(2,2));
        grid_container.add(use_mail);
        grid_container.add(mail_field);
        grid_container.add(new JLabel(""));
        grid_container.add(valider_mail);

        main_container.add(logo);
        main_container.add(grid_container);

        this.add(main_container);
    }

    /**
     * Envoie un message à l'utilisateur à l'adresse qu'il a renseignée.
     * @param event
     * L'action qui vient de se produire, en l'occurence un clic sur le bouton "Valider".
     */
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == valider_mail){
            try {
                BDD connexion = new BDD();
                if (connexion.resetPassword(mail_field.getText())) {
                    SendMail send = new SendMail();
                    send.sendPasswordLostEmail(mail_field.getText());
                }
            }
            catch (MessagingException e) {
                System.out.println(e);
            }
        }
    }
}
