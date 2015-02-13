package windows;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextArea;

public class PostIt extends JTextArea{
    
    private final String texte = "";
    
    public PostIt() {
        this.setSize(50,50);
        this.setLineWrap(true);
        this.setBackground(Color.YELLOW);
        this.setPreferredSize(new Dimension (50,50));
        this.setDragEnabled(true);
        this.setVisible(true);
    }
}