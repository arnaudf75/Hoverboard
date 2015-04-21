package windows.widgets;

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
    
    /**
     * Constructeur de la classe PostIt
     * @param idDashboard ID du dashboard dans lequel le postit va être ajouté
     */
    public PostIt(int idDashboard)
    {
        super();
        this.height = 200;
        this.width = 250;
        this.setBounds(this.positionX, this.positionY, this.width, this.height);
        this.content.add(postit_text);
        postit_text.setRows(9);
        postit_text.setColumns(20);
        JScrollPane scrollPane = new JScrollPane(postit_text,
                                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(new Rectangle(-4, 1, 397, 198)); 
        this.content.add(scrollPane, null);
        this.idDashboard = idDashboard;
        
        this.idWidget = this.connexion.ajouteWidget(this.positionX, this.positionY, this.height, this.width, this.idDashboard, "POSTIT");
        this.revalidate();
    }
    
    /**
     * Constructeur de la classe PostIt
     * @param idWidget ID du widge qui va être crée
     * @param text Texte dans le postit
     * @param positionX Position horizontale en pixel
     * @param positionY Position verticale en pixel
     * @param height hauteur en pixel
     * @param width largeur en pixel
     */
    public PostIt(int idWidget, String text, int positionX, int positionY, int height, int width)
    {
        super(idWidget, positionX, positionY, height, width);
        this.content.add(postit_text);
        this.postit_text.setRows(9);
        this.postit_text.setColumns(20);
        this.postit_text.setText(text);
        JScrollPane scrollPane = new JScrollPane(postit_text,
                                                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(new Rectangle(-4, 1, 397, 198)); 
        content.add(scrollPane, null);        
        this.revalidate();
    }
    
    @Override
    public void refresh(){
        super.refresh();
        this.postit_text.setText(this.connexion.getContentWidget(idWidget));
        this.revalidate();
    }
    
    @Override
    public void save(){
        this.connexion.updateWidget(idWidget, name.label.getText(), this.postit_text.getText(),positionX,positionY,height,width);
    }
    
    /**
     * Action effectués lors d'un clic sur un bouton
     * @param event evenement du clic
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        //mise a jour sur la BDD
        if (source == save) {
            this.save();
        }
        //rechercher la derniere version sur la BDD
        else if (source == refresh) {
            this.refresh();
        }
        //suppression du postit
        else if (source == del){
            int option = JOptionPane.showConfirmDialog(null, "Êtes vous sûr de bien vouloir supprimer ce post-it ?",
            "Confirmez la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (option == JOptionPane.OK_OPTION) {
                this.dispose();
                this.connexion.deleteWidget(this.idWidget);
            }
        }
    } 
}