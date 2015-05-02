package com.hoverboard.windows.widgets;

import com.hoverboard.BDD;
import com.hoverboard.windows.dashboards.Dashboard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/**
 *
 * @author Cavoleau
 */
public class ToDoList extends Widget {
    
    JButton newTask = new JButton("Ajouter tâche");
    JPanel taskList = new JPanel();
    JPanel bottom_container = new JPanel();
    
    /**
     * Constructeur de la classe ToDoList
     * @param idDashboard Dashboard dans lequel le ToDoList sera ajouté
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public ToDoList(int idDashboard)
    {
        super();
        
        this.height=300;
        this.width=250;
        this.setBounds(positionX, positionY, this.width, this.height);
        
        taskList.setLayout(new BoxLayout(taskList, BoxLayout.PAGE_AXIS));
        content.setLayout(new BorderLayout());
        bottom_container.setLayout(new BorderLayout());
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
        this.idDashboard = idDashboard;
        this.idWidget = BDD.ajouteWidget(this.positionX, this.positionY, this.height, this.width, this.idDashboard, "TODOLIST");
        Dashboard.listWidgets.add(this);
        this.revalidate();
    }
    
    /**
     * Constructeur de la classe ToDoList
     * @param idWidget ID du widget qui va être crée
     * @param contenuWidget Contenu du widget en XML
     * @param positionX Position horizontale en pixel
     * @param positionY Position verticale en pixel
     * @param height hauteur en pixel
     * @param width largeur en pixel
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public ToDoList(int idWidget, String contenuWidget, int positionX, int positionY, int height, int width)
    {
        super(idWidget, positionX, positionY, height, width);
        
        taskList.setLayout(new BoxLayout(taskList, BoxLayout.PAGE_AXIS));
        content.setLayout(new BorderLayout());
        bottom_container.setLayout(new BorderLayout());
        content.add(taskList, BorderLayout.CENTER);
        content.add(bottom_container, BorderLayout.SOUTH);
        bottom_container.add(newTask, BorderLayout.CENTER);
        newTask.addActionListener(this);
        //ajout du scroll
        JScrollPane scrollPane = new JScrollPane(taskList,
                                                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        content.add(scrollPane);
        //Creation en fonction du XML du contenu du widget
        org.jdom2.input.SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Document doc = saxBuilder.build(new StringReader(contenuWidget));
            List<Element> tasklist = doc.getRootElement().getChildren();
            for(int cpt=0;cpt<tasklist.size();cpt++){
                this.taskList.add(new Task(tasklist.get(cpt).getChild("name").getText(),Boolean.valueOf(tasklist.get(cpt).getAttributeValue("done"))));
            }
        } 
        catch (JDOMException e) {
            // handle JDOMException
        } 
        catch (IOException e) {
            // handle IOException
        }
        Dashboard.listWidgets.add(this);
        this.revalidate();
    }
    
    /**
     * Action effectués lors d'un clic sur un bouton
     * @param event evenement du clic
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        //Ajout d'une tache a la liste
        if (source == newTask){
            this.taskList.add(new Task());
            this.taskList.revalidate();//pour qu'elle s'affiche bien sans avoir a deplacer le widget
        }
        //
        else if (source == save){
            this.save();
        }
        //Recherche la derniere version sur la BDD
        else if (source == refresh) {
            this.refresh();
            this.revalidate();
        }
        //Supprime le widget
        else if (source == del){
            int option = JOptionPane.showConfirmDialog(null, "Êtes vous sûr de bien vouloir supprimer cette liste de tâches ?",
            "Confirmez la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                Dashboard.listWidgets.remove(this);
                this.dispose();
                BDD.deleteWidget(this.idWidget);
            }
        }
    }
    
    public void refresh(){
        super.refresh();
        String contenuWidget = BDD.getContentWidget(idWidget);
        this.taskList.removeAll();
        org.jdom2.input.SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Document doc = saxBuilder.build(new StringReader(contenuWidget));
            List<Element> tasklist = doc.getRootElement().getChildren();
            for(int cpt=0;cpt<tasklist.size();cpt++){
                this.taskList.add(new Task(tasklist.get(cpt).getChild("name").getText(),Boolean.valueOf(tasklist.get(cpt).getAttributeValue("done"))));
            }
        } 
        catch (IOException | JDOMException e) {
            // handle JDOMException
        }
    }
    public void save(){
        super.save();
        Component[] comps = this.taskList.getComponents();
        String content = "";
        content=content.concat("<tasklist>");
        for(int cpt=0;cpt<comps.length;cpt++){
            content=content.concat("<task done=\""+((Task)comps[cpt]).done.isSelected()+"\">");
            content=content.concat("<name>"+((Task)comps[cpt]).taskName.label.getText()+"</name>");
            content=content.concat("</task>");
        }
        content=content.concat("</tasklist>");
        BDD.updateWidget(idWidget, name.label.getText(), content,positionX,positionY,height,width);
    }
}