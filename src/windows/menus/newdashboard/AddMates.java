package windows.menus.newdashboard;

import hoverboard.BDD;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * AddMates est une fenêtre appellée lors de la création d'un dashboard partagé permettant d'ajouter d'autres utilisateurs à un dashboard.
 * @author Arnaud
 */
public class AddMates extends JFrame implements ActionListener {
    private int idDashboard = -1;
    private String pseudoUser = "";
    private final BDD connexion = new BDD();
    private final JButton validate = new JButton ("Valider");
    private final JLabel ajouteUserLabel = new JLabel("Saisissez le pseudo de l'utilisateur");
    private final JTextField ajouteUserField = new JTextField();
    private final JPanel main_container = new JPanel();
    
    /**
     * Affiche une fenêtre contenant l'id du dashboard pour y ajouter un utilisateur.
     * @param idDashboard L'id du dashboard auquel on souhaite ajouter un utilisateur.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public AddMates(int idDashboard) { 
        this.idDashboard = idDashboard;
        this.ajouteUserField.setPreferredSize(new Dimension (450,30));
        this.validate.addActionListener(this);
        this.main_container.add(ajouteUserLabel);
        this.main_container.add(ajouteUserField);
        this.main_container.add(validate);
        
        this.setTitle("Ajouter des utilisateurs à ce dashboard");
        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/icone.png")).getImage());
        this.setContentPane(main_container);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * Récupère le pseudo de l'utilisateur saisi dans le formulaire et va interroger
     * la base de données pour savoir si un utilisateur existe avec ce pseudo.
     * @param event L'action qui vient de se produire, en l'occurence un clic sur le bouton "Valider".
     */
    @Override
    public void actionPerformed (ActionEvent event) {
        Object source = event.getSource();
        if (source == validate) {
            pseudoUser = ajouteUserField.getText();
            if (connexion.ajouteUserToDashboard(this.idDashboard, pseudoUser)) {
                JOptionPane.showMessageDialog(null, "L'utilisateur '"+pseudoUser+"' a bien été ajouté au dashboard !", "Succès !", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
