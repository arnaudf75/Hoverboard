package windows;

import hoverboard.BDD;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.util.HashMap;

/**
 * Home est la page d'accueil de l'utilisateur, elle contient tous les widgets du dashboard.
 * @author Arnaud
 */
public class Home extends JFrame implements ActionListener {
    private final BDD connexion = new BDD();
    private int idUser = -1;
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuPlugins = new JMenu("Plugins");
    private final JMenuItem plugins_library = new JMenuItem("Aller à la bibliothèque de plugins en ligne");
    private final JMenu menuOptions = new JMenu("Options");
    private final JMenuItem options_infoUser = new JMenuItem("Mes informations");
    private final JMenu menuHelp = new JMenu("Aide");
    private final JMenuItem about_doc = new JMenuItem("Voir la documentation en ligne");
    private final JMenuItem about_help = new JMenuItem("A propos d'Hoverboard");
    private final JMenuItem menuDisconnect = new JMenuItem("Se déconnecter");
    private final JPanel main_container = new JPanel();
    
    /**
     * Crée la fenêtre d'accueil de l'utilisateur, comportant les menus lui permettant de créer des widgets, accéder à ses options, etc.
     * @param idUser
     * L'id de l'utilisateur connecté.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Home(int idUser) {
        this.setTitle("Choose a dashboard");
        this.setSize(800, 800);
        this.idUser = idUser;
        main_container.setLayout(new BorderLayout());
        
        menuDisconnect.addActionListener(this);

        menuPlugins.add(plugins_library);
        menuOptions.add(options_infoUser);
        menuHelp.add(about_help);
        menuHelp.add(about_doc);

        menu.add(menuPlugins);
        menu.add(menuOptions);
        menu.add(menuHelp);
        menu.add(menuDisconnect);
        
        setJMenuBar(menu);  
        
        
        main_container.add(new ListeDashboard(this.idUser));
        this.setContentPane(main_container);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Effectue une action en fonction du menu cliqué, par exemple afficher les informations de l'utilisateur.
     * @param event 
     * L'action qui vient de se produire (bouton cliqué).
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == menuDisconnect) {
            File cookie = new File("src/ressources/cookie_login.xml");
            cookie.delete();
            this.dispose();
            Login login = new Login();
        }
    }
    
    /**
     * FONCTION PAS ENCORE UTILISEE
     * Synchronise les widgets stockés EN LOCAL avec ceux stockés dans la base de données.
     * @param idDashboard 
     */
    
    public void refreshWidgets(int idDashboard) {
        // Todo -> Avec l'id du dashboard, je fais une requête vers la BDD pour voir si jamais un widget aurait été rajouté
        HashMap dicto = new HashMap();
        ResultSet resultWidgets = this.connexion.getWidgets(idDashboard);
        System.out.println(resultWidgets);
        // ET enregistrer dans la BDD les nouveaux widgets ajoutés en local
        File directory = new File("src/ressources/dashboard_"+idDashboard);
        // Avec l'id du dashboard, je vais voir en local pour récupérer les fichiers post it
        String [] listeFichiers = directory.list();
        // Pour chaque post it récupéré (au format Hashmap), je crée un nouveau post it (new PostIt) et à la fin je fais un revalidate()
        for (int i=0; i<directory.listFiles().length; i++) {
            //dicto = myParser.getDataPost("src/ressources/dashboard_"+idDashboard+"/"+listeFichiers[i]);
            //this.main_container.add(new PostIt(899898,dicto.get("content").toString()));
        }
        this.main_container.revalidate();
    }
}