package windows;

import hoverboard.BDD;
import hoverboard.ParserXml;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.Container;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Arnaud
 */

public class Dashboard extends JPanel implements ActionListener {
    
    private int idDashboard = -1;
    protected BDD connexion = new BDD();
    private String titleDashboard = "";
    private String descriptionDashboard = "";
    private final JButton validatePanel = new JButton("Enter");
    private final JLabel persoIcon = new JLabel (new ImageIcon("src/ressources/solo.png"));
    private final JLabel sharedIcon = new JLabel (new ImageIcon("src/ressources/groupofpeople.png"));
    private final JLabel adminIcon = new JLabel (new ImageIcon("src/ressources/admin.png"));
    private final JPanel main_panel = new JPanel();
    private final JPanel icon_container = new JPanel();
    private final JPanel top_container = new JPanel();
    private final JPanel middle_container = new JPanel();
    
    public Dashboard(int idDashboard, String titleDashboard, String descriptionDashboard, int isAdmin, int isShared) {
        this.idDashboard = idDashboard;
        this.titleDashboard = titleDashboard;
        this.descriptionDashboard = descriptionDashboard;
        this.persoIcon.setToolTipText("This dashboard is personnal");
        this.sharedIcon.setToolTipText("This dashboard is shared");
        this.adminIcon.setToolTipText("Your are the administrator of this dashboard");
        this.main_panel.setLayout(new BorderLayout());
        this.top_container.setLayout(new BorderLayout());
        this.validatePanel.addActionListener(this);
        if (isShared == 1) {
            this.icon_container.add(sharedIcon);
        }
        else {
            this.icon_container.add(persoIcon);
        }
        if (isAdmin == 1) {
            this.icon_container.add(adminIcon);
        }
        this.top_container.add(this.icon_container, BorderLayout.WEST);
        this.top_container.add(new JLabel(this.titleDashboard), BorderLayout.CENTER);
        this.middle_container.add(new JLabel(this.descriptionDashboard));
        this.main_panel.add(this.top_container, BorderLayout.NORTH);
        this.main_panel.add(this.middle_container, BorderLayout.CENTER);
        this.main_panel.add(this.validatePanel, BorderLayout.SOUTH);
        this.add(main_panel);
    }
    
    public Dashboard(int idDashboard)  {
        
        this.idDashboard = idDashboard;
        ResultSet listeWidgets = this.connexion.getNewWidgets(idDashboard);
        try {
            while (listeWidgets.next()){
                switch (listeWidgets.getInt("idTypeWidget")) {
                    case 1 : {
                        this.add(new ToDoList(listeWidgets.getInt("idWidget"),listeWidgets.getString("contentWidget")));
                        break;
                    }
                    case 2 : {
                        this.add(new PostIt(listeWidgets.getInt("idWidget"),listeWidgets.getString("contentWidget")));
                        break;
                    }
                    default : {
                        System.out.println("Type de widget non pris en charge");
                        break;
                    }
                }
            }
        }
        catch (SQLException error) {
            System.out.println(error);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Container parent =this.getParent();
        System.out.println(this.idDashboard);
        parent.removeAll();
        parent.add(new Dashboard(this.idDashboard));
        parent.revalidate();
    }
    
    // Code pour l'affichage de widgets depuis un dossier local avec des fichiers .xml
    /*
    
    // Je crée un nouveau fichier .xml
    ParserXml myParser = new ParserXml();
    this.idDashboard = 3; // Variable rentrée en dur, à enlever
    //myParser.creePostIt(idDashboard, this.height, this.width, this.positionX, this.positionY);
    HashMap dicto = new HashMap();
    File directory = new File("src/ressources/dashboard_"+idDashboard);        
    // Avec l'id du dashboard, je vais voir en local pour récupérer les fichiers post it
    String [] listeFichiers = directory.list();
    // Pour chaque post it récupéré (au format Hashmap), je crée un nouveau post it (new PostIt) et à la fin je fais un revalidate()
    for (int i=0; i<directory.listFiles().length; i++) {
        dicto = myParser.getDataPost("src/ressources/dashboard_"+idDashboard+"/"+listeFichiers[i]);
        this.add(new PostIt(99999,dicto.get("content").toString()));
    }
    
    */
 }