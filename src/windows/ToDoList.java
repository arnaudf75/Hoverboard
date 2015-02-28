package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Cavoleau
 */

public class ToDoList extends Widget {
    
    JButton newTask = new JButton("New Task");
    JPanel taskList = new JPanel();
    JPanel bottom_container = new JPanel();
    
    @SuppressWarnings("LeakingThisInConstructor")
    public ToDoList()
    {
        super();
        this.height=250;
        this.width=300;
        taskList.setLayout(new BoxLayout(taskList, BoxLayout.PAGE_AXIS));
        content.setLayout(new BorderLayout());
        bottom_container.setLayout(new BorderLayout());
        this.setBounds(0, 0, width, height);
        taskList.add(new Task());
        content.add(taskList, BorderLayout.CENTER);
        content.add(bottom_container, BorderLayout.SOUTH);
        bottom_container.add(newTask, BorderLayout.CENTER);
        newTask.addActionListener(this);
        //ajout du scroll
        JScrollPane scrollPane = new JScrollPane(taskList,
                                                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        content.add(scrollPane);
        this.idDashboard = 3; // VARIABLE RENTREE EN DUR !!!!!!!!!!
        this.connexion.ajouteWidget(this.positionX, this.positionY, this.height, this.width, this.idDashboard, 1);
    }
    
    public ToDoList(int idWidget, String contenuWidget)
    {
        super();
        this.height=250;
        this.width=300;
        taskList.setLayout(new BoxLayout(taskList, BoxLayout.PAGE_AXIS));
        content.setLayout(new BorderLayout());
        bottom_container.setLayout(new BorderLayout());
        this.setBounds(0, 0, width, height);
        taskList.add(new Task());
        content.add(taskList, BorderLayout.CENTER);
        content.add(bottom_container, BorderLayout.SOUTH);
        bottom_container.add(newTask, BorderLayout.CENTER);
        newTask.addActionListener(this);
        //ajout du scroll
        JScrollPane scrollPane = new JScrollPane(taskList,
                                                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        content.add(scrollPane);
        this.idDashboard = 3; // VARIABLE RENTREE EN DUR !!!!!!!!!!
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == del){
            this.dispose();
        }
        if (source == newTask){
            taskList.add(new Task());
            taskList.revalidate();//pour qu'elle s'affiche bien sans avoir a deplacer le widget
        }
    }
}

class Task extends JPanel implements ActionListener{
    JCheckBox done;
    EditableText taskName;
    JButton delTask;
    Task()
    {
        done=new JCheckBox();
        this.add(done);
        
        taskName = new EditableText("Nouvelle tache");
        this.add(taskName); 
        
        delTask = new JButton("Delete");
        this.add(delTask);
        delTask.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == delTask){
            Container parent =this.getParent();
            parent.remove(this);//retire la tache de la lsite de tache
            parent.revalidate();
        }
    }
}