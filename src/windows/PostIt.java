package windows;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Cavoleau
 */
public class PostIt extends Widget {
    
    JTextArea postit_text = new JTextArea();
    
    public PostIt(int idDashboard)
    {
        super();
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
        this.idDashboard = idDashboard;
        
        this.idWidget = this.connexion.ajouteWidget(this.positionX, this.positionY, this.height, this.width, this.idDashboard, 2);
    }
    
    public PostIt(int idWidget, String text, int positionX, int positionY, int height, int width)
    {
        super();
        this.idWidget = idWidget;
        this.height=250;
        this.width=200;
        content.setBackground(Color.YELLOW);
        this.setBounds(0, 0, height, width);
        this.content.add(postit_text);
        this.postit_text.setRows(9);
        this.postit_text.setColumns(20);
        this.postit_text.setText(text);
        JScrollPane scrollPane = new JScrollPane(postit_text,
                                                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(new Rectangle(-4, 1, 397, 198)); 
        content.add(scrollPane, null);        
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == save) {
            String contentPostIt = this.postit_text.getText();
            this.connexion.updateWidget(idWidget, contentPostIt);
        }
        else if (source == refresh) {
            String contentWidget = this.connexion.getContentWidget(idWidget);
            this.postit_text.setText(contentWidget);
        }
        else if (source == del){
            int option = JOptionPane.showConfirmDialog(null, "Êtes vous sûr de bien vouloir supprimer ce post-it ?",
            "Confirmez la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (option == JOptionPane.OK_OPTION) {
                this.dispose();
                this.connexion.deleteWidget(this.idWidget);
            }
        }
    }
    
    
    
    
    /**
     * PAS ENCORE UTILISEE, A IMPLEMENTER QUAND LA PARTIE EN LIGNE DE L'APPLICATION SERA TERMINEE
     * USAGE LOCAL : Crée un fichier .xml contenant les données du post-it.
     * @param idDashboard
     * @param height
     * @param width
     * @param positionX
     * @param positionY 
     */
    /*public void creePostIt(int idDashboard, int height, int width, int positionX, int positionY) {
        try {
            Element postIt = new Element("postIt");
            this.document = new Document();
            this.document.setRootElement(postIt);
            this.document.getRootElement().addContent(new Element("height").addContent(Integer.toString(height)));
            this.document.getRootElement().addContent(new Element("width").addContent(Integer.toString(width)));
            this.document.getRootElement().addContent(new Element("positionX").addContent(Integer.toString(positionX)));
            this.document.getRootElement().addContent(new Element("positionY").addContent(Integer.toString(positionY)));
            this.document.getRootElement().addContent(new Element("content"));
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            File directory = new File ("src/ressources/dashboard_"+idDashboard+"/");
            xmlOutput.output(this.document, new FileWriter(directory+"/"+Integer.toString(directory.listFiles().length)+".xml"));
        }
        catch (IOException error) {
            System.out.println("Erreur : Impossible de créer le post-it "+error);
        }
    }*/

    /**
     * PAS ENCORE UTILISEE, A IMPLEMENTER QUAND LA PARTIE EN LIGNE DE L'APPLICATION SERA TERMINEE
     * USAGE LOCAL : Récupère les données des fichiers de post-it au format .xml pour les afficher.
     * @param postIt
     * Un fichier .xml contenant les données d'un post-it.
     * @return 
     * Dictionnaire contenant les données d'un post-it.
     */
    /*public HashMap getDataPost(String postIt) {
        try {
            this.document = sax.build(new File(postIt));
        }
        catch(IOException | JDOMException error) {
            System.out.println(error);
        }
        Element racine = document.getRootElement();
        HashMap dicto = new HashMap();
        dicto.put("content", racine.getChild("content").getText());
        return (dicto);
    }*/
    
}