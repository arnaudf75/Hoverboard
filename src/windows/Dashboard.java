package windows;

import hoverboard.BDD;
import windows.newdashboard.AddMates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

/**
 * Dashboard est la classe qui permet d'afficher et de créer les widgets.
 * @author Arnaud
 */

public class Dashboard extends Home implements ActionListener {
    private int idDashboard = -1;
    private final JButton homeButton = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/home.png")));
    private final JButton new_postit = new JButton("Nouveau Post-it");
    private final JButton new_tasklist = new JButton("Nouvelle liste de tâches");
    private final JButton new_poll = new JButton("Nouveau sondage");
    private final JButton add_users = new JButton("Ajouter des utilisateurs à ce dashboard");
    private final JPanel topRightSide_container = new JPanel();
    private final JPanel top_container = new JPanel();
    private final JDesktopPane widget_container = new JDesktopPane();
        
    /**
     * Crée le dashboard complet avec les widgets qui y sont associés.
     * @param idDashboard L'id du dashboard choisi dans la liste des dashboards de la page d'accueil.
     * @param idUser L'id de l'utilisateur connecté.
     * @param titreDashboard Le titre affiché en haut de la fenêtre.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Dashboard(int idDashboard, int idUser, String titreDashboard) {
        this.idUser  = idUser;
        this.idDashboard = idDashboard;
        this.setTitle(titreDashboard);
        this.setLayout(new BorderLayout());
        this.top_container.setLayout(new BorderLayout());
        
        homeButton.addActionListener(this);
        add_users.addActionListener(this);
        new_postit.addActionListener(this);
        new_tasklist.addActionListener(this);
        new_poll.addActionListener(this);
        
        this.topRightSide_container.add(new_postit);
        this.topRightSide_container.add(new_tasklist);
        this.topRightSide_container.add(new_poll);
        this.topRightSide_container.add(add_users);
        this.top_container.add(topRightSide_container, BorderLayout.EAST);
        this.add(widget_container, BorderLayout.CENTER);
        this.add(homeButton, BorderLayout.SOUTH);
        
        ResultSet listeWidgets = this.connexion.getWidgets(idDashboard);
        try {
            //Recupere tout les widget du dashboard dans la BDD pour les afficher
            while (listeWidgets.next()){
                switch (listeWidgets.getInt("idTypeWidget")) {
                    case 1 : {
                        this.widget_container.add(new ToDoList(listeWidgets.getInt("idWidget"), listeWidgets.getString("contentWidget"), listeWidgets.getInt("positionX"),
                                            listeWidgets.getInt("positionY"), listeWidgets.getInt("longueur"), listeWidgets.getInt("largeur")));
                        System.out.println("todo trouve");
                        break;
                    }
                    case 2 : {
                        this.widget_container.add(new PostIt(listeWidgets.getInt("idWidget"), listeWidgets.getString("contentWidget"), listeWidgets.getInt("positionX"),
                                listeWidgets.getInt("positionY"), listeWidgets.getInt("longueur"), listeWidgets.getInt("largeur")));
                        System.out.println("post trouve");
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
            System.out.println("Impossible d'afficher les widgets ! "+error);
        }
        
        this.add(top_container, BorderLayout.NORTH);
    }
    
    /**
     * En fonction de l'action, peut créer un widget ou revenir à la page d'accueil.
     * @param event L'action qui vient de se produire (bouton cliqué)
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == homeButton) {
            this.dispose();
            ListeDashboard myDashboards = new ListeDashboard(this.idUser);
        }
        else if (source == add_users) {
            AddMates addUsersToDashboard = new AddMates(this.idDashboard);
        }
        else if (source == new_postit) {
            this.widget_container.add(new PostIt(this.idDashboard));
            this.revalidate();
        }
        else if (source == new_tasklist) {
            this.widget_container.add(new ToDoList(this.idDashboard));
            this.revalidate();
        }
        else if (source == new_poll){
            this.widget_container.add(new Poll(this.idDashboard));
            this.revalidate();
        }
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