package windows;

import java.awt.Dimension;
import java.awt.Rectangle;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Cavoleau
 */
public class PostIt extends Widget{
    private JTextArea postit_text;
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
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JDesktopPane desktopPane=new JDesktopPane();
        frame.add(desktopPane);
        
        PostIt test=new PostIt("test zefvergetrh\nlolilol\n3e line OP");
        PostIt test2=new PostIt();
        desktopPane.add(test);
        desktopPane.add(test2);
    }
}