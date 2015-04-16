package windows.menus.newdashboard;

import hoverboard.BDD;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * La classe CreateDashboard affiche une fenêtre permettant à un utilisateur
 * @author Arnaud
 */
public class CreateDashboard extends JFrame implements ActionListener {
    
    private int idUser = -1;
    private String titreDashboard = "";
    private String descriptionDashboard = "";
    protected BDD connexion = new BDD();
    private final JButton validate = new JButton("Valider");
    private final JLabel titreLabel = new JLabel("Titre du dashboard");
    private final JLabel descriptionLabel = new JLabel("Description du dashboard");
    private final JPanel center_container = new JPanel();
    private final JPanel bottom_container = new JPanel();
    private final JPanel main_container = new JPanel();
    private final JTextField titreField = new JTextField("");
    private final JTextArea descriptionArea = new JTextArea("");
    
    @SuppressWarnings("LeakingThisInConstructor")
    public CreateDashboard(int idUser) {
        
        this.idUser = idUser;
        
        this.center_container.setLayout(new GridLayout(2,2));
        this.bottom_container.setLayout(new BorderLayout());
        this.main_container.setLayout(new BorderLayout());
        
        this.validate.addActionListener(this);

        this.descriptionArea.setRows(5);
        
        this.center_container.add(titreLabel);        
        this.center_container.add(titreField);
        this.center_container.add(descriptionLabel);
        this.center_container.add(descriptionArea);

        this.bottom_container.add(validate, BorderLayout.WEST);

        this.main_container.add(center_container, BorderLayout.CENTER);
        this.main_container.add(bottom_container, BorderLayout.SOUTH);
        
        this.setTitle("Création d'un nouveau dashboard");
        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/icone.png")).getImage());
        this.setContentPane(main_container);
        this.pack();
        this.setSize(400,this.getHeight());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * Crée un dashboard à partir des informations saisies par l'utilisateur.
     * @param event L'action qui vient de se produire en l'occurence un clic sur le bouton "Valider".
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == validate) {
            if (titreField.getText().isEmpty() || descriptionArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vous devez donner un titre et une description à votre dashboard pour continuer !" , "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
            else {
                titreDashboard = titreField.getText();
                descriptionDashboard = descriptionArea.getText();
                this.dispose();
                int idDashboard = this.connexion.ajouteDashboard(this.idUser, this.titreDashboard, this.descriptionDashboard);
                AddMates addUsersToDashboard = new AddMates(idDashboard);
            }
        }
    }
}
