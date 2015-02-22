package windows;

import hoverboard.BDD;
import hoverboard.ParserXml;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.util.HashMap;

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
        new_poll.addActionListener(this);
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
        
        int idDashboard = 3;
        ParserXml myParser = new ParserXml();
        HashMap dicto = new HashMap();
        File directory = new File("src/ressources/dashboard_"+idDashboard);
        // Avec l'id du dashboard, je vais voir en local pour récupérer les fichiers post it
        String [] listeFichiers = directory.list();
        // Pour chaque post it récupéré (au format Hashmap), je crée un nouveau post it (new PostIt) et à la fin je fais un revalidate()
        for (int i=0; i<directory.listFiles().length; i++) {
            dicto = myParser.getDataPost(myParser.getSax(),"src/ressources/dashboard_"+idDashboard+"/"+listeFichiers[i]);
            this.main_container.add(new PostIt(dicto.get("content").toString()));
        }
        this.main_container.revalidate();
        
        this.setContentPane(main_container);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Synchronise les widgets stockés en local avec ceux stockés dans la base de données.
     * @param idDashboard 
     */
    
    public void refreshWidgets(int idDashboard) {
        // Todo -> Avec l'id du dashboard, je fais une requête vers la BDD pour voir si jamais un widget aurait été rajouté
        ParserXml myParser = new ParserXml();
        HashMap data_jdbc = myParser.getDataJDBC(myParser.getSax());        
        BDD connexion = new BDD(data_jdbc.get("dbUrl").toString(), data_jdbc.get("driver").toString(), data_jdbc.get("login").toString(), data_jdbc.get("password").toString());
        HashMap dicto = new HashMap();
        ResultSet resultWidgets = connexion.getNewWidgets(idDashboard);
        // ET enregistrer dans la BDD les nouveaux widgets ajoutés en local
        File directory = new File("src/ressources/dashboard_"+idDashboard);
        // Avec l'id du dashboard, je vais voir en local pour récupérer les fichiers post it
        String [] listeFichiers = directory.list();
        // Pour chaque post it récupéré (au format Hashmap), je crée un nouveau post it (new PostIt) et à la fin je fais un revalidate()
        for (int i=0; i<directory.listFiles().length; i++) {
            dicto = myParser.getDataPost(myParser.getSax(),"src/ressources/dashboard_"+idDashboard+"/"+listeFichiers[i]);
            this.main_container.add(new PostIt(dicto.get("content").toString()));
        }
        this.main_container.revalidate();
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
        /*
        if (source==new_tasklist) {
            main_container.add(new ToDoList());
            main_container.revalidate();
        }*/
        else if (source==menuDisconnect) {
            File cookie = new File("src/ressources/cookie_login.xml");
            if(cookie.delete()){
                System.out.println("Cookie supprimé");
            }
            this.dispose();
            Login login = new Login();
        }
        else if (source==new_poll){
            
        }
    }
}