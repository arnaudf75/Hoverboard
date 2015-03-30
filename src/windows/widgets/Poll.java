/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows.widgets;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Cavoleau
 */
public class Poll extends Widget{
    JButton newQuestion = new JButton("Ajouter Question");
    JButton publishPoll = new JButton("Publier Sondage");
    boolean published = false;
    
    JPanel questionList = new JPanel();
    JPanel bottom_container = new JPanel();
    JPanel button_container = new JPanel();
    
    /**
     * Constructeur de la classe Poll
     * @param idDashboard Dashboard dans lequel le Poll sera ajouté
     */
    public Poll(int idDashboard)
    {
        super();
        
        this.height=400;
        this.width=300;
        this.setBounds(positionX, positionY, width, height);
        
        questionList.setLayout(new BoxLayout(questionList, BoxLayout.PAGE_AXIS));
        content.setLayout(new BorderLayout());
        bottom_container.setLayout(new BorderLayout());
        button_container.setLayout(new BorderLayout());
        questionList.add(new QuestionCreator());
        content.add(questionList, BorderLayout.CENTER);  
        content.add(bottom_container, BorderLayout.SOUTH);
        bottom_container.add(button_container, BorderLayout.CENTER);
        
        button_container.add(newQuestion, BorderLayout.NORTH);
        button_container.add(publishPoll, BorderLayout.SOUTH);
        newQuestion.addActionListener(this);
        //ajout du scroll
        JScrollPane scrollPane = new JScrollPane(questionList,
                                                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        content.add(scrollPane);
        this.idDashboard = idDashboard;
        this.idWidget=this.connexion.ajouteWidget(this.positionX, this.positionY, this.height, this.width, this.idDashboard, 3);
        this.revalidate();
    }
    
    /**
     * Constructeur de la classe Poll
     * @param idWidget ID du widget qui va être crée
     * @param contenuWidget Contenu du widget en XML
     * @param positionX Position horizontale en pixel
     * @param positionY Position verticale en pixel
     * @param height hauteur en pixel
     * @param width largeur en pixel
     */
    public Poll(int idWidget, String contenuWidget, int positionX, int positionY, int height, int width)
    {
        super(idWidget, positionX, positionY, height, width);
        
        questionList.setLayout(new BoxLayout(questionList, BoxLayout.PAGE_AXIS));
        content.setLayout(new BorderLayout());
        bottom_container.setLayout(new BorderLayout());
        questionList.add(new QuestionCreator());
        content.add(questionList, BorderLayout.CENTER);
        content.add(bottom_container, BorderLayout.SOUTH);
        bottom_container.add(newQuestion, BorderLayout.CENTER);
        bottom_container.add(publishPoll, BorderLayout.CENTER);
        newQuestion.addActionListener(this);
        //ajout du scroll
        JScrollPane scrollPane = new JScrollPane(questionList,
                                                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        content.add(scrollPane);
        this.revalidate();
    }
    
    /**
     * Action effectués lors d'un clic sur un bouton
     * @param event evenement du clic
     */
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        //Ajout d'une tache a la liste
        if (source == newQuestion){
            this.questionList.add(new QuestionCreator());
            this.questionList.revalidate();//pour qu'elle s'affiche bien sans avoir a deplacer le widget
        }
        if(source == publishPoll){
            this.published=true;
            this.save();
            this.refresh();
        }
        //sauvegarde dans la BDD
        else if (source == save){
            this.save();
        }
        //Recherche la derniere version sur la BDD
        else if (source == refresh) {
            this.refresh();
        }
        //Supprime le widget
        else if (source == del){
            int option = JOptionPane.showConfirmDialog(null, "Êtes vous sûr de bien vouloir supprimer ce sondage ?",
            "Confirmez la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (option == JOptionPane.OK_OPTION) {
                this.dispose();
                this.connexion.deleteWidget(this.idWidget);
            }
        }
    }
    
    @Override
    public void refresh(){
        super.refresh();
        this.revalidate();
    }
    @Override
    public void save(){
        this.connexion.updateWidget(idWidget, "test",positionX,positionY,height,width);
    }
}
