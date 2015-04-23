package windows.widgets;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
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
    
    @SuppressWarnings("LeakingThisInConstructor")
    public Task()
    {
        this.add(done);
        this.add(taskName);    
        this.add(delTask);
        done.addActionListener(this);
        delTask.addActionListener(this);
    }
    
    public Task(String name,boolean checked)
    {
        this.add(done);
        this.add(taskName);    
        this.add(delTask);
        done.setSelected(checked);
        taskName.label.setText(name);
        done.addActionListener(this);
        delTask.addActionListener(this);
        if(checked == true)
            taskName.label.setForeground(Color.LIGHT_GRAY);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == done) {
            if(done.isSelected())
            {
                taskName.label.setForeground(Color.LIGHT_GRAY);
            }
            else
            {
                taskName.label.setForeground(Color.BLACK);
            }
        }
        else if (source == delTask){
            Container parent =this.getParent();
            parent.remove(this);//retire la tache de la lsite de tache
            parent.revalidate();
        }
    }
}