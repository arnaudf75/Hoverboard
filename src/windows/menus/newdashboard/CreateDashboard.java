package windows.menus.newdashboard;

import hoverboard.BDD;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * La classe CreateDashboard affiche une fenêtre permettant à un utilisateur
 * @author Arnaud
 */
public class CreateDashboard extends JFrame implements ActionListener {
    
    private int idUser = -1;
    private int isShared = 0;
    private String titreDashboard = "";
    private String descriptionDashboard = "";
    protected BDD connexion = new BDD();
    private final JButton validate = new JButton("Valider");
    private final JButton reset = new JButton("Annuler");
    private final JCheckBox sharedCheck = new JCheckBox();
    private final JLabel titreLabel = new JLabel("Titre du dashboard");
    private final JLabel descriptionLabel = new JLabel("Description du dashboard");
    private final JLabel sharedLabel = new JLabel("Ce dashboard sera-t-il un dashboard communataire ?");
    private final JPanel center_container = new JPanel();
    private final JPanel bottom_container = new JPanel();
    private final JPanel main_container = new JPanel();
    private final JTextField titreField = new JTextField("");
    private final JTextArea descriptionArea = new JTextArea("");
    
    @SuppressWarnings("LeakingThisInConstructor")
    public CreateDashboard(int idUser) {
        this.setTitle("Création d'un nouveau dashboard");
        this.setSize(500, 500);
        this.idUser = idUser;
        
        this.center_container.setLayout(new GridLayout(3,2));
        this.bottom_container.setLayout(new BorderLayout());
        this.main_container.setLayout(new BorderLayout());
        
        this.validate.addActionListener(this);
        this.reset.addActionListener(this);

        this.center_container.add(titreLabel);        
        this.center_container.add(titreField);
        this.center_container.add(descriptionLabel);
        this.center_container.add(descriptionArea);
        this.center_container.add(sharedLabel);
        this.center_container.add(sharedCheck);

        this.bottom_container.add(validate, BorderLayout.WEST);
        this.bottom_container.add(reset, BorderLayout.EAST);

        this.main_container.add(center_container, BorderLayout.CENTER);
        this.main_container.add(bottom_container, BorderLayout.SOUTH);
        
        this.setContentPane(main_container);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    /**
     * 
     * @param event L'action qui vient de se produire (bouton cliqué).
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == validate) {
            if (sharedCheck.isSelected()) {
                this.isShared = 1;
            }
            titreDashboard = titreField.getText();
            descriptionDashboard = descriptionArea.getText();
            this.dispose();
            int idDashboard = this.connexion.ajouteDashboard(this.idUser, this.titreDashboard, this.descriptionDashboard, this.isShared);
            if (sharedCheck.isSelected()) {
                AddMates addUsersToDashboard = new AddMates(idDashboard);
            }
        }
    }
}
