package windows;

import hoverboard.BDD;
import hoverboard.User;
import windows.menus.infouser.InfoUser;
import windows.menus.myplugins.ListeMyPlugins;
import windows.menus.newdashboard.CreateDashboard;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
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
    protected final Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
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
        
       /* ResultSet myPlugins = connexion.getMyPlugins(idUser);
        try {
            while (myPlugins.next()) {
                try {
                    //if(myPlugins.getInt())
                    new File("plugins").mkdirs();
                    Files.copy(new URL(myPlugins.getString("pathToVersion")).openStream(), new File("plugins/"+myPlugins.getString("namePlugin")+myPlugins.getString("numVersion")+".jar").toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                catch (IOException error) {
                    JOptionPane.showMessageDialog(null, "Impossible d'afficher la liste de vos plugins ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Impossible d'accèder à vos plugins ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
        }*/
        
        this.setContentPane(main_container);
        this.setSize(windowSize);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/icone.png")).getImage());
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
            ListeMyPlugins myPlugins = new ListeMyPlugins(this.utilisateur.getIdUser());
        }
        else if (source == plugins_library) {
            if (Desktop.isDesktopSupported() ) {
                Desktop navigateurWeb = Desktop.getDesktop();
                if (navigateurWeb.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        navigateurWeb.browse(new URI("http://hoverboard.livehost.fr/index.php?control=plugins"));
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
                        navigateurWeb.browse(new URI("http://hoverboard.livehost.fr/index.php?control=support"));
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
}