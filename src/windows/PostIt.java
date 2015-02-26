package windows;

import hoverboard.ParserXml;
import java.awt.Rectangle;
import java.awt.Color;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Cavoleau
 */

public class PostIt extends Widget {
    
    JTextArea postit_text = new JTextArea();
    
    public PostIt()
    {
        super();
        System.out.println("Nouveau Widget");
        this.height = 250;
        this.width = 200;
        this.positionX = 50;
        this.positionY = 50;
        
        this.setBounds(0, 0, this.height, this.width);
        this.content.setBackground(Color.YELLOW);
        this.content.add(postit_text);
        postit_text.setRows(9);
        postit_text.setColumns(20);
        JScrollPane scrollPane = new JScrollPane(postit_text,
                                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(new Rectangle(-4, 1, 397, 198)); 
        this.content.add(scrollPane, null);
        this.idDashboard = 3; // VARIABLE RENTREE EN DUR !!!!!!!!!!!!
        
        this.idWidget = this.connexion.ajouteWidget(this.positionX, this.positionY, this.height, this.width, this.idDashboard, 2);
    }
    
    public PostIt(int idWidget, String text)
    {
        super();
        this.idWidget = idWidget;
        this.height=250;
        this.width=200;
        content.setBackground(Color.YELLOW);
        this.setBounds(0, 0, height, width);
        JTextArea postit_text=new JTextArea(text);
        this.content.add(postit_text);
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