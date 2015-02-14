package windows;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

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
    private final JLabel background = new JLabel (new ImageIcon("src/ressources/background.png"));
    private final JPanel main_container = new JPanel();
    private final JPanel top_container = new JPanel();
    
    public Home() {
        this.setTitle("Home Page");
        this.setSize(500, 500);
        
        this.setIconImage(new ImageIcon(this.getClass().getResource("logo.png")).getImage());
        main_container.setLayout(new BorderLayout());
        
        new_postit.addActionListener(this);
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
        
        
        top_container.add(menu);
        //main_container.add(background);
        main_container.add(top_container, BorderLayout.NORTH);
        
        this.setContentPane(main_container);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void affichePost() {
        //ParserXml parser = new ParserXml();
        //Hashtable dictoPost = parser.getDataPost(parser.getSax(), parser.getDocument(), parser.getRacine());
        //this.main_container.add(new PostIt(dictoPost.get("text").toString(),dictoPost.get("color").toString()));
        main_container.add(new PostIt());
        main_container.revalidate();
    }
    
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source==new_postit) {
            this.affichePost();
        }
        else if (source==menuDisconnect) {
            System.out.println("ici");
            File cookie = new File("src/ressources/cookie_login.xml");
            if(cookie.delete()){
                System.out.println("Cookie supprimé");
            }
            this.dispose();
            Login_window login = new Login_window();
        }
    }
}