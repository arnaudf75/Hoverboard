/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class ToDoList extends Widget implements ActionListener{
    JButton newTask;
    JPanel taskList;

    public ToDoList()
    {
        super();
        height=250;
        width=300;
        this.setBounds(0, 0, width, height);
        content.setLayout(new BorderLayout());
        
        newTask = new JButton("New Task");
        taskList=new JPanel();
        taskList.setLayout(new BoxLayout(taskList, BoxLayout.PAGE_AXIS));
        content.add(taskList, BorderLayout.WEST);
        taskList.setBackground(Color.red);
         
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        
        newTask.addActionListener(this);
        settings.add(newTask);
        
        //ajout du scroll
        JScrollPane scrollPane = new JScrollPane(taskList,
				                     JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				                     JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
 
        content.add(scrollPane);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
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
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == delTask){
            Container parent =this.getParent();
            parent.remove(this);//retire la tache de la lsite de tache
            parent.revalidate();
        }
    }
}