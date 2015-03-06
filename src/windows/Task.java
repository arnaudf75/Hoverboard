package windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Cavoleau
 */

class Task extends JPanel implements ActionListener{
    JCheckBox done = new JCheckBox();
    EditableText taskName = new EditableText("Nouvelle tache");
    JButton delTask = new JButton("Delete");
    AttributedString tache = new AttributedString("test");
    Task()
    {
        this.add(done);
        this.add(taskName);    
        this.add(delTask);
        done.addActionListener(this);
        delTask.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == done) {
            tache.addAttribute(TextAttribute.STRIKETHROUGH,
        TextAttribute.STRIKETHROUGH_ON, 18, 25);
        }
        else if (source == delTask){
            Container parent =this.getParent();
            parent.remove(this);//retire la tache de la lsite de tache
            parent.revalidate();
        }
    }
}
