package windows;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Home extends JFrame {
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
    private final JMenu menuDisconnect = new JMenu("Disconnect");
    private final JLabel background = new JLabel (new ImageIcon("src/ressources/background.png"));
    private final JPanel main_container = new JPanel();
    private final JPanel top_container = new JPanel();
    
    public Home() {
        this.setTitle("Home Page");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        main_container.setLayout(new BorderLayout());
        
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
        
        main_container.add(background);
        top_container.add(menu);
        main_container.add(top_container, BorderLayout.NORTH);
        
        this.setContentPane(main_container);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}