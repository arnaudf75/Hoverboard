package windows;

import hoverboard.BDD;
import hoverboard.ParserXml;
import java.awt.Rectangle;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.HashMap;
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Cavoleau
 */

public class PostIt extends Widget {
    private final JTextArea postit_text;
    public PostIt()
    {
        super();
        height=250;
        width=200;
        this.setBounds(0, 0, height, width);
        postit_text=new JTextArea();
        postit_text.setRows(9);
        postit_text.setColumns(20);
        JScrollPane scrollPane = new JScrollPane(postit_text,
                                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(new Rectangle(-4, 1, 397, 198)); 
        content.add(scrollPane, null);
        
        // Je crée un nouveau fichier .xml
        ParserXml xmlParser = new ParserXml();
        int idDashboard = 3; // Variable rentrée en dur, à enlever
        xmlParser.creePostIt(this.height, this.width, this.positionX, this.positionY);
        // Et je l'ajoute à la base de données
        HashMap data_jdbc = xmlParser.getDataJDBC(xmlParser.getSax());        
        BDD connexion = new BDD(data_jdbc.get("dbUrl").toString(), data_jdbc.get("driver").toString(), data_jdbc.get("login").toString(), data_jdbc.get("password").toString());
        connexion.ajouteWidget(this.height, this.width, this.positionX, this.positionY, idDashboard);
        
    }
    
    public PostIt(String text)
    {
        super();
        height=250;
        width=200;
        this.setBounds(0, 0, height, width);
        postit_text=new JTextArea(text);
        postit_text.setRows(9);
        postit_text.setColumns(20);
        JScrollPane scrollPane = new JScrollPane(postit_text,
                                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(new Rectangle(-4, 1, 397, 198)); 
        content.add(scrollPane, null);        
    }
    
    
    public void updatePostIt(String content) {
        // Je récupère le nouveau contenu du JTextField, je l'envoie au fichier .xml correspondant
        // Ensuite je fais un Update BDD SET content = content
    }
}