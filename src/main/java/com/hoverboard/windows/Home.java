package com.hoverboard.windows;

import com.hoverboard.AppProperties;
import com.hoverboard.BDD;
import com.hoverboard.User;
import com.hoverboard.windows.dashboards.Dashboard;
import static com.hoverboard.windows.dashboards.Dashboard.listWidgets;
import com.hoverboard.windows.dashboards.DashboardPreview;
import com.hoverboard.windows.menus.infouser.InfoUser;
import com.hoverboard.windows.menus.myplugins.ListeMyPlugins;
import com.hoverboard.windows.menus.newdashboard.CreateDashboard;
import com.hoverboard.windows.menus.themes.MenuTheme;
import com.hoverboard.windows.menus.themes.Theme;
import com.hoverboard.windows.widgets.Widget;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Font;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;

/**
 * Home est la page d'accueil de l'utilisateur, elle contient tous les widgets du dashboard.
 * @author Arnaud
 */
public class Home extends JFrame implements ActionListener, WindowListener {
    private final User utilisateur = null;
    protected final Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final JButton homeButton = new JButton(new ImageIcon(this.getClass().getResource("/images/home.png")));
    private final JMenuBar menu = new JMenuBar();
    private final JMenu new_item = new JMenu("Nouveau");
    private final JMenuItem newDashboard = new JMenuItem("Dashboard");
    private final JMenu menuPlugins = new JMenu("Plugins");
    private final JMenuItem menu_myPlugins = new JMenuItem("Afficher mes plugins");
    private final JMenuItem plugins_library = new JMenuItem("Aller à la bibliothèque de plugins en ligne");
    private final JMenu menuOptions = new JMenu("Options");
    private final JMenuItem options_themes = new JMenuItem("Apparence et thèmes");
    private final JMenuItem options_infoUser = new JMenuItem("Mes informations");
    private final JMenu menuHelp = new JMenu("Aide");
    private final JMenuItem about_help = new JMenuItem("A propos d'Hoverboard");
    private final JMenuItem about_support = new JMenuItem("Accèder au support en ligne");
    private final JMenu menuQuit = new JMenu("Quitter");
    private final JMenuItem menuDisconnect = new JMenuItem("Se déconnecter");
    private final JPanel center_container = new JPanel();
    private final JPanel bottom_container = new JPanel();
    private final JPanel main_container = new JPanel();
    
    /**
     * Crée la fenêtre d'accueil de l'utilisateur, comportant les menus lui permettant d'accéder à ses options, etc.
     * @param user
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Home(User user) {
        AppProperties.getProperties();
        if (AppProperties.themeSelected.equals("DEFAULT")) {
            Theme.setTheme(new File("/themes/"+AppProperties.themeSelected+".xml"));
        }
        Theme.setUIFont(new FontUIResource(Theme.nomFont, Font.BOLD, Theme.fontSize));
        main_container.setLayout(new BorderLayout());
        
        this.addWindowListener(this);
        
        homeButton.addActionListener(this);
        newDashboard.addActionListener(this);
        menu_myPlugins.addActionListener(this);
        plugins_library.addActionListener(this);
        options_themes.addActionListener(this);
        options_infoUser.addActionListener(this);
        about_help.addActionListener(this);
        about_support.addActionListener(this);
        menuDisconnect.addActionListener(this);

        new_item.add(newDashboard);
        menuPlugins.add(menu_myPlugins);
        menuPlugins.add(plugins_library);
        menuOptions.add(options_themes);
        menuOptions.add(options_infoUser);
        menuHelp.add(about_help);
        menuHelp.add(about_support);
        menuQuit.add(menuDisconnect);

        menu.add(new_item);
        menu.add(menuPlugins);
        menu.add(menuOptions);
        menu.add(menuHelp);
        menu.add(menuQuit);
        
        this.setJMenuBar(menu);
        
        this.getDashboards(User.getIdUser());

        this.bottom_container.add(homeButton);
        this.main_container.add(center_container, BorderLayout.CENTER);
        this.main_container.add(bottom_container, BorderLayout.SOUTH);
        
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

        this.setTitle("Hoverboard");
        this.setContentPane(main_container);
        this.setSize(windowSize);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setIconImage(Theme.icone.getImage());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    /**
     * Effectue une action en fonction du menu cliqué, par exemple afficher les informations de l'utilisateur.
     * @param event L'action qui vient de se produire (bouton cliqué).
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == homeButton) {
            for (Widget widget : listWidgets) {
                widget.save();
            }
            Dashboard.listWidgets.clear();
            this.center_container.removeAll();
            this.getDashboards(User.getIdUser());
            this.revalidate();
        }
        else if (source == newDashboard) {
            CreateDashboard createDash = new CreateDashboard(User.getIdUser());
        }
        else if (source == menu_myPlugins) {
            ListeMyPlugins myPlugins = new ListeMyPlugins(User.getIdUser());
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
        else if (source == options_themes) {
            MenuTheme menuTheme = new MenuTheme();
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
            for (Widget widget : listWidgets) {
                widget.save();
            }
            File cookie = new File("userData/cookie_login.xml");
            cookie.delete();
            this.dispose();
            Login login = new Login();
        }
    }
    
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        for (Widget widget : listWidgets) {
                widget.save();
        }
        this.dispose();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) { }

    @Override
    public void windowActivated(WindowEvent windowEvent) { }
    
    @Override
    public void windowDeactivated(WindowEvent windowEvent) { }
    
    @Override
    public void windowIconified(WindowEvent windowEvent) { }
    
    @Override
    public void windowDeiconified(WindowEvent windowEvent) { }
    
    @Override
    public void windowOpened(WindowEvent windowEvent) { }
    
    public void getDashboards(int idUser) {
        ResultSet listeDashboard = BDD.getDashboards(idUser);
        try {
            listeDashboard.last();
            int numberRows = listeDashboard.getRow()/5;
            listeDashboard.beforeFirst();
            this.center_container.setLayout(new GridLayout(numberRows,1));
            while (listeDashboard.next()) {
                int idDashboard = listeDashboard.getInt("idDashboard");
                int isAdmin = listeDashboard.getInt("isDashboardAdmin");
                String titleDashboard = listeDashboard.getString("titleDashboard");
                String descriptionDashboard = listeDashboard.getString("descriptionDashboard");
                this.center_container.add(new DashboardPreview(idDashboard, titleDashboard, descriptionDashboard, isAdmin));
            }
            this.center_container.revalidate();
            this.center_container.repaint();
        }
        catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Impossible d'afficher la liste des dashboards ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
    }
}