package windows.dashboards;

import hoverboard.BDD;
import hoverboard.User;
import windows.Login;
import windows.menus.infouser.InfoUser;
import windows.menus.myplugins.MyPlugins;
import windows.menus.newdashboard.CreateDashboard;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Home est la page d'accueil de l'utilisateur, elle contient tous les widgets du dashboard.
 * @author Arnaud
 */
public abstract class Home extends JFrame implements ActionListener {
    protected BDD connexion = new BDD();
    protected User utilisateur = null;
    protected int idUser = -1;
    protected final JMenuBar menu = new JMenuBar();
    protected final JMenu new_item = new JMenu("Nouveau");
    protected final JMenuItem newDashboard = new JMenuItem("Dashboard");
    protected final JMenu menuPlugins = new JMenu("Plugins");
    protected final JMenuItem menu_myPlugins = new JMenuItem("Afficher mes plugins");
    protected final JMenuItem plugins_library = new JMenuItem("Aller à la bibliothèque de plugins en ligne");
    protected final JMenu menuOptions = new JMenu("Options");
    protected final JMenuItem options_preferences = new JMenuItem("Préférences");
    protected final JMenuItem options_infoUser = new JMenuItem("Mes informations");
    protected final JMenu menuHelp = new JMenu("Aide");
    protected final JMenuItem about_help = new JMenuItem("A propos d'Hoverboard");
    protected final JMenuItem about_support = new JMenuItem("Accèder au support en ligne");
    protected final JMenuItem menuDisconnect = new JMenuItem("Se déconnecter");
    protected final JPanel main_container = new JPanel();
    
    /**
     * Crée la fenêtre d'accueil de l'utilisateur, comportant les menus lui permettant d'accéder à ses options, etc.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Home() {
        this.setSize(1200,700);
        main_container.setLayout(new BorderLayout());
        
        newDashboard.addActionListener(this);
        menu_myPlugins.addActionListener(this);
        plugins_library.addActionListener(this);
        options_preferences.addActionListener(this);
        options_infoUser.addActionListener(this);
        about_help.addActionListener(this);
        about_support.addActionListener(this);
        menuDisconnect.addActionListener(this);

        new_item.add(newDashboard);
        menuPlugins.add(menu_myPlugins);
        menuPlugins.add(plugins_library);
        menuOptions.add(options_preferences);
        menuOptions.add(options_infoUser);
        menuHelp.add(about_help);
        menuHelp.add(about_support);

        menu.add(new_item);
        menu.add(menuPlugins);
        menu.add(menuOptions);
        menu.add(menuHelp);
        menu.add(menuDisconnect);
        
        setJMenuBar(menu);  
        
        this.setContentPane(main_container);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Effectue une action en fonction du menu cliqué, par exemple afficher les informations de l'utilisateur.
     * @param event L'action qui vient de se produire (bouton cliqué).
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == newDashboard) {
            CreateDashboard createDash = new CreateDashboard(this.utilisateur.getIdUser());
        }
        
        else if (source == menu_myPlugins) {
            MyPlugins myPlugins = new MyPlugins(this.utilisateur.getIdUser());
            
        }
        
        else if (source == plugins_library) {
            if (Desktop.isDesktopSupported() ) {
                Desktop navigateurWeb = Desktop.getDesktop();
                if (navigateurWeb.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        navigateurWeb.browse(new URI("http://hoverboard.livehost.fr/"));
                    }
                    catch (IOException | URISyntaxException error) {
                        JOptionPane.showMessageDialog(null, "Impossible d'accèder au site web de l'application ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
                    } 
                }
            }
        }
        
        else if (source == options_preferences) {
            
        }
        
        else if (source == options_infoUser) {
            InfoUser myInfos = new InfoUser(this.utilisateur);
        }
        
        else if (source == about_help) {
            JOptionPane.showMessageDialog(null, "Hoverboard est une application réalisée dans le cadre du projet annuel"
                    + "de troisième année d'Architecture des Logiciels de l'école ESGI (École Supérieure du Génie Informatique).\n"
                    + "Elle permettra à ses utilisateurs de créer des panneaux sur lesquels seront stockées des informations écrites sur des post-its,\n"
                    + "des listes de tâches ou encore des sondages auxquels d'autres utilisateurs pourront répondre.\n Cette application doit être codée en Java et sera accompagnée"
                    + " d'un site web avec lequel elle interagira par le biais d'une base de données.", "A propos", JOptionPane.INFORMATION_MESSAGE);
        }
        
        else if (source == about_support) {
            if (Desktop.isDesktopSupported() ) {
                Desktop navigateurWeb = Desktop.getDesktop();
                if (navigateurWeb.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        navigateurWeb.browse(new URI("http://hoverboard.livehost.fr/"));
                    }
                    catch (IOException | URISyntaxException error) {
                        JOptionPane.showMessageDialog(null, "Impossible d'accèder au site web de l'application ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
                    } 
                }
            }
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
    /*
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
        /*this.main_container.revalidate();
    }*/
}