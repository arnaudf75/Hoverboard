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
    
    public PostIt()
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
            this.updateWidget(this.idWidget);
        }
        else if (source == refresh) {
            this.refreshWidget(this.idWidget);
        }
        else if (source == del){
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete that widget ?",
            "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (option == JOptionPane.OK_OPTION) {
                this.dispose();
                this.connexion.deleteWidget(this.idWidget);
            }
        }
    }
    
    @Override
    public void refreshWidget(int idWidget) {
        String contentWidget = this.connexion.getContentWidget(idWidget);
        this.postit_text.setText(contentWidget);
    }
    
    @Override
    public void updateWidget (int idWidget) {
        String contentPostIt = this.postit_text.getText();
        this.connexion.updateWidgetBDD(idWidget, contentPostIt);
    }
}