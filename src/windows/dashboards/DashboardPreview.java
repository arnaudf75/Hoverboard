package windows.dashboards;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * DashboardPreview est la classe qui affiche dans un panneau un aperçu d'un dashboard avec des informations le concernant : si il est partagé ou non,
 * si l'utilisateur en est l'administrateur, son titre et sa description.
 * @author Arnaud
 */
public class DashboardPreview extends JPanel implements ActionListener {
    private int idDashboard = -1;
    private String titleDashboard = "";
    private String descriptionDashboard = "";
    private final JButton homeButton = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/home.png")));
    private final JButton validatePanel = new JButton("Accèder au dashboard");
    private final JLabel persoIcon = new JLabel (new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/solo.png")));
    private final JLabel sharedIcon = new JLabel (new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/groupofpeople.png")));
    private final JLabel adminIcon = new JLabel (new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/admin.png")));
    private final JPanel main_panel = new JPanel();
    private final JPanel topRightSide_container = new JPanel();
    private final JPanel top_container = new JPanel();
    private final JPanel middle_container = new JPanel();
    
     /**
     * Crée un aperçu d'un dashboard. Ce constructeur est appellé dans la classe ListeDashboard pour afficher les dashboards
     * appartenant ou auquel participe un utilisateur.
     * @param idDashboard L'id du dashboard concerné.
     * @param titleDashboard Le titre du dashboard.
     * @param descriptionDashboard La description du dashboard.
     * @param isAdmin Vaut 1 si l'utilisateur administre le dashboard, 0 sinon.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public DashboardPreview(int idDashboard, String titleDashboard, String descriptionDashboard, int isAdmin) {
        this.idDashboard = idDashboard;
        this.titleDashboard = titleDashboard;
        this.descriptionDashboard = descriptionDashboard;
        this.persoIcon.setToolTipText("Ce dashboard est personnel");
        this.sharedIcon.setToolTipText("Ce dashboard est partagé");
        this.adminIcon.setToolTipText("Vous êtes l'administrateur de ce dashboard");
        this.main_panel.setLayout(new BorderLayout());
        this.top_container.setLayout(new BorderLayout());
        this.homeButton.addActionListener(this);
        this.validatePanel.addActionListener(this);

        if (isAdmin == 1) {
            this.topRightSide_container.add(adminIcon);
        }
        this.top_container.add(this.topRightSide_container, BorderLayout.WEST);
        this.top_container.add(new JLabel(this.titleDashboard), BorderLayout.CENTER);
        this.middle_container.add(new JLabel(this.descriptionDashboard));
        this.main_panel.add(this.top_container, BorderLayout.NORTH);
        this.main_panel.add(this.middle_container, BorderLayout.CENTER);
        this.main_panel.add(this.validatePanel, BorderLayout.SOUTH);
        this.add(main_panel);
    }
    
    /**
     * Effectue une action en fonction du bouton cliqué, comme par exemple revenir à la liste des dashboards.
     * @param event L'action qui vient de se produire (bouton cliqué).
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == validatePanel) {
            Container parentContainer = this.getParent();
            parentContainer.removeAll();
            parentContainer.revalidate();
            parentContainer.add(new Dashboard(this.idDashboard, this.titleDashboard));
        }
    }
}