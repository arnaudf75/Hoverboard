package windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Cavoleau
 */
public class ToDoList extends Widget {
    
    JButton newTask = new JButton("Nouvelle tâche");
    JPanel taskList = new JPanel();
    JPanel bottom_container = new JPanel();
    
    
    @SuppressWarnings("LeakingThisInConstructor")
    /**
     * Constructeur de la classe ToDoList
     * @param idDashboard Dashboard dans lequel le ToDoList sera ajouté
     */
    public ToDoList(int idDashboard)
    {
        super();
        
        this.height=250;
        this.width=300;
        this.setBounds(positionX, positionY, height, width);
        
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
        this.idWidget=this.connexion.ajouteWidget(this.positionX, this.positionY, this.height, this.width, this.idDashboard, 1);
        this.revalidate();
    }
    
    @SuppressWarnings("LeakingThisInConstructor")
    /**
     * Constructeur de la classe ToDoList
     * @param idWidget ID du widget qui va être crée
     * @param contenuWidget Contenu du widget en XML
     * @param positionX Position horizontale en pixel
     * @param positionY Position verticale en pixel
     * @param height hauteur en pixel
     * @param width largeur en pixel
     */
    public ToDoList(int idWidget, String contenuWidget, int positionX, int positionY, int height, int width)
    {
        super(idWidget, positionX, positionY, height, width);
        
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
        this.revalidate();
    }
    
    @Override
    /**
     * Action effectués lors d'un clic sur un bouton
     * @param event evenement du clic
     */
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        //Ajout d'une tache a la liste
        if (source == newTask){
            this.taskList.add(new Task());
            this.taskList.revalidate();//pour qu'elle s'affiche bien sans avoir a deplacer le widget
        }
        //
        else if (source == save){
            this.connexion.updateWidget(idWidget, "test",positionX,positionY,height,width);
        }
        //Recherche la derniere version sur la BDD
        else if (source == refresh) {
            super.refresh();
            this.revalidate();
        }
        //Supprime le widget
        else if (source == del){
            int option = JOptionPane.showConfirmDialog(null, "Êtes vous sûr de bien vouloir supprimer cette liste de tâches ?",
            "Confirmez la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (option == JOptionPane.OK_OPTION) {
                this.dispose();
                this.connexion.deleteWidget(this.idWidget);
            }
        }
    }
}