package windows;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Home est la page d'accueil de l'utilisateur, elle contient tous les widgets du dashboard.
 * @author Arnaud
 */

public class Home extends JFrame implements ActionListener {
    private final JMenuBar menu = new JMenuBar();
    private final JMenu menuDashboard = new JMenu("Dashboard");
    private final JMenu dashboard_new = new JMenu ("New");
    private final JMenuItem new_postit = new JMenuItem("Post-it");
    private final JMenuItem new_tasklist = new JMenuItem("Task list");
    private final JMenuItem new_poll = new JMenuItem("Poll");
    private final JMenu menuPlugins = new JMenu("Plugins");
    private final JMenuItem plugins_library = new JMenuItem("Go to the online plugin library");
    private final JMenu menuOptions = new JMenu("Options");
    private final JMenuItem options_infoUser = new JMenuItem("My informations");
    private final JMenu menuHelp = new JMenu("Help");
    private final JMenuItem about_doc = new JMenuItem("View Online Documentation");
    private final JMenuItem about_help = new JMenuItem("About Hoverboard");
    private final JMenuItem menuDisconnect = new JMenuItem("Disconnect");
    private final JPanel main_container = new JPanel();
    
    public Home() {
        this.setTitle("Home Page");
        this.setSize(800, 800);
        
        main_container.setLayout(new BorderLayout());
        
        new_postit.addActionListener(this);
        new_tasklist.addActionListener(this);
        menuDisconnect.addActionListener(this);
        
        dashboard_new.add(new_postit);
        dashboard_new.add(new_tasklist);
        dashboard_new.add(new_poll);
        
        menuDashboard.add(dashboard_new);
        menuPlugins.add(plugins_library);
        menuOptions.add(options_infoUser);
        menuHelp.add(about_help);
        menuHelp.add(about_doc);
        
        menu.add(menuDashboard);
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
     * Effectue une action en fonction du menu cliqué, par exemple un post-it sera ajouté, ou un les informations de l'utilisateur seront affichées.
     * @param event 
     * L'action qui vient de se produire (bouton cliqué).
     */
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source==new_postit) {
            main_container.add(new PostIt());
            main_container.revalidate();
        }
        if (source==new_tasklist) {
            main_container.add(new ToDoList());
            main_container.revalidate();
        }
        else if (source==menuDisconnect) {
            File cookie = new File("src/ressources/cookie_login.xml");
            if(cookie.delete()){
                System.out.println("Cookie supprimé");
            }
            this.dispose();
            Login login = new Login();
        }
    }
}