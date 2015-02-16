package windows;

import java.awt.Rectangle;
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
<<<<<<< HEAD
				                     JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				                     JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
        content.add(scrollPane);
=======
                                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(new Rectangle(-4, 1, 397, 198)); 
        content.add(scrollPane, null);
>>>>>>> f5492beea74ee1b84b741a502ee7d2cc9a47b593
    }
    public PostIt(String text)
    {
        super();
        height=250;
        width=200;
        this.setBounds(0, 0, width, height);
        postit_text=new JTextArea(text);
        postit_text.setRows(9);
        postit_text.setColumns(20);
        JScrollPane scrollPane = new JScrollPane(postit_text,
                                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(new Rectangle(-4, 1, 397, 198)); 
        content.add(scrollPane, null);        
    }
}
