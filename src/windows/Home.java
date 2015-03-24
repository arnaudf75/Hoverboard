package windows;

import windows.newdashboard.CreateDashboard;
import hoverboard.BDD;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.util.HashMap;

/**
 * Home est la page d'accueil de l'utilisateur, elle contient tous les widgets du dashboard.
 * @author Arnaud
 */
public abstract class Home extends JFrame implements ActionListener {
    protected BDD connexion = new BDD();
    protected int idUser = -1;
    protected final JMenuBar menu = new JMenuBar();
    protected final JMenu new_item = new JMenu("Nouveau");
    protected final JMenuItem menu_newDashboard = new JMenuItem("Dashboard");
    protected final JMenu menuPlugins = new JMenu("Plugins");
    protected final JMenuItem plugins_library = new JMenuItem("Aller à la bibliothèque de plugins en ligne");
    protected final JMenu menuOptions = new JMenu("Options");
    protected final JMenuItem options_infoUser = new JMenuItem("Mes informations");
    protected final JMenu menuHelp = new JMenu("Aide");
    protected final JMenuItem about_doc = new JMenuItem("Voir la documentation en ligne");
    protected final JMenuItem about_help = new JMenuItem("A propos d'Hoverboard");
    protected final JMenuItem menuDisconnect = new JMenuItem("Se déconnecter");
    protected final JPanel main_container = new JPanel();
    
    /**
     * Crée la fenêtre d'accueil de l'utilisateur, comportant les menus lui permettant d'accéder à ses options, etc.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Home() {
        this.setSize(1200, 1000);
        main_container.setLayout(new BorderLayout());
        menu_newDashboard.addActionListener(this);
        menuDisconnect.addActionListener(this);

        new_item.add(menu_newDashboard);
        menuPlugins.add(plugins_library);
        menuOptions.add(options_infoUser);
        menuHelp.add(about_help);
        menuHelp.add(about_doc);

        menu.add(new_item);
        menu.add(menuPlugins);
        menu.add(menuOptions);
        menu.add(menuHelp);
        menu.add(menuDisconnect);
        
        setJMenuBar(menu);  
          
        this.setContentPane(main_container);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Effectue une action en fonction du menu cliqué, par exemple afficher les informations de l'utilisateur.
     * @param event L'action qui vient de se produire (bouton cliqué).
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == menu_newDashboard) {
            CreateDashboard createDash = new CreateDashboard(this.idUser);
        }
        else if (source == menuDisconnect) {
            File cookie = new File("userData/cookie_login.xml");
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
        /*File directory = new File("src/ressources/dashboard_"+idDashboard);
        // Avec l'id du dashboard, je vais voir en local pour récupérer les fichiers post it
        String [] listeFichiers = directory.list();
        // Pour chaque post it récupéré (au format Hashmap), je crée un nouveau post it (new PostIt) et à la fin je fais un revalidate()
        for (int i=0; i<directory.listFiles().length; i++) {
            //dicto = myParser.getDataPost("src/ressources/dashboard_"+idDashboard+"/"+listeFichiers[i]);
            //this.main_container.add(new PostIt(899898,dicto.get("content").toString()));
        }*/
        this.main_container.revalidate();
    }
}