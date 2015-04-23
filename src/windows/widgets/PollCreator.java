/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows.widgets;

import windows.dashboards.Dashboard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
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
public class PollCreator extends Widget{
    JButton newQuestion = new JButton("Ajouter Question");
    JButton publishPoll = new JButton("Publier Sondage");
    
    int published;
    int user;
    
    JPanel questionList = new JPanel();
    JPanel bottom_container = new JPanel();
    JPanel button_container = new JPanel();
    
    /**
     * Constructeur de la classe Poll
     * @param idDashboard Dashboard dans lequel le Poll sera ajouté
     */
    public PollCreator(int idDashboard, int idUser)
    {
        super();
        user=idUser;
        published=0;
        
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
        publishPoll.addActionListener(this);
        //ajout du scroll
        JScrollPane scrollPane = new JScrollPane(questionList,
                                                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        content.add(scrollPane);
        this.idDashboard = idDashboard;
        this.idWidget=this.connexion.ajouteWidget(this.positionX, this.positionY, this.height, this.width, this.idDashboard, "POLL");
        Dashboard.listWidgets.add(this);
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
     * @param idUser utilisateur actuel
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public PollCreator(int idWidget, String contenuWidget, int positionX, int positionY, int height, int width, int idUser)
    {
        super(idWidget, positionX, positionY, height, width);
        user=idUser;
        
        questionList.setLayout(new BoxLayout(questionList, BoxLayout.PAGE_AXIS));
        content.setLayout(new BorderLayout());
        bottom_container.setLayout(new BorderLayout());
        button_container.setLayout(new BorderLayout());
        content.add(questionList, BorderLayout.CENTER);
        content.add(bottom_container, BorderLayout.SOUTH);
        //ajout du scroll
        JScrollPane scrollPane = new JScrollPane(questionList,
                                                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        content.add(scrollPane);
        //Creation en fonction du XML du contenu du widget
        org.jdom2.input.SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Document doc = saxBuilder.build(new StringReader(contenuWidget));
            Element poll = doc.getRootElement();
            this.published=0;
            List<Element> questionlist = poll.getChild("questionlist").getChildren();
            for(int cpt=0;cpt<questionlist.size();cpt++){
                QuestionCreator newone =new QuestionCreator(questionlist.get(cpt).getAttributeValue("name"));
                this.questionList.add(newone);
                List<Element> answerlist = questionlist.get(cpt).getChild("answerlist").getChildren();
                for(int cpt2=0;cpt2<answerlist.size();cpt2++){
                    newone.answerList.add(new AnswerCreator(answerlist.get(cpt2).getText()));
                }
                newone.setMaximumSize( newone.getPreferredSize() );
            }
            bottom_container.add(button_container, BorderLayout.CENTER);
            button_container.add(newQuestion, BorderLayout.NORTH);
            button_container.add(publishPoll, BorderLayout.SOUTH);
            newQuestion.addActionListener(this);
            publishPoll.addActionListener(this);               
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
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        //Ajout d'une tache a la liste
        if (source == newQuestion){
            this.questionList.add(new QuestionCreator());
            this.questionList.revalidate();//pour qu'elle s'affiche bien sans avoir a deplacer le widget
        }
        //Ferme les modification des questions/réponse et perme aux utilisateurs de repondre au sondage
        if(source == publishPoll){
            this.publish();
            Poll pollPublished = new Poll(this.idWidget, "", 0,0,0,0,user);
            pollPublished.refresh();
            this.getParent().add(pollPublished);
            this.dispose();
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
    
    public void refresh(){
        super.refresh();
            //accès à la BDD
            String contenuWidget = this.connexion.getContentWidget(idWidget);
            //Clean du widget avant réecriture
            this.questionList.removeAll();
            //réecriture
            org.jdom2.input.SAXBuilder saxBuilder = new SAXBuilder();
            try {
                Document doc = saxBuilder.build(new StringReader(contenuWidget));
                Element poll = doc.getRootElement();
                this.published=0;
                List<Element> questionlist = poll.getChild("questionlist").getChildren();
                for(int cpt=0;cpt<questionlist.size();cpt++){
                    QuestionCreator newone =new QuestionCreator(questionlist.get(cpt).getAttributeValue("name"));
                    this.questionList.add(newone);
                    List<Element> answerlist = questionlist.get(cpt).getChild("answerlist").getChildren();
                    for(int cpt2=0;cpt2<answerlist.size();cpt2++){
                        newone.answerList.add(new AnswerCreator(answerlist.get(cpt2).getText()));
                    }
                    newone.setMaximumSize( newone.getPreferredSize() );
                }
            } 
            catch (JDOMException e) {
                System.out.println("handle JDOMException"+e);
            } 
            catch (IOException e) {
                System.out.println("andle IOException"+e);
            }
            this.bottom_container.removeAll();
            bottom_container.add(newQuestion, BorderLayout.CENTER);
            newQuestion.addActionListener(this);
            bottom_container.add(publishPoll, BorderLayout.CENTER);
            publishPoll.addActionListener(this);
            this.revalidate();
    }
    
    @Override
    public void save(){
        super.save();
        Component[] compsQuestion = this.questionList.getComponents();
        Component[] compsAnswer;
            String content = "";
            content=content.concat("<poll published=\"false\">");
            content=content.concat("<questionlist>");
            for(int cpt=0;cpt<compsQuestion.length;cpt++){
                content=content.concat("<question name=\""+((QuestionCreator)compsQuestion[cpt]).questionName.label.getText()+"\">");
                compsAnswer = ((QuestionCreator)compsQuestion[cpt]).answerList.getComponents();
                content=content.concat("<answerlist>");
                for(int cpt2=0;cpt2<compsAnswer.length;cpt2++){
                    content=content.concat("<answer>"+((AnswerCreator)compsAnswer[cpt2]).name.label.getText()+"</answer>");
                }
                content=content.concat("</answerlist>");
                content=content.concat("</question>");
            }
            content=content.concat("</questionlist>");
            content=content.concat("</poll>");
            this.connexion.updateWidget(idWidget, name.label.getText(), content,positionX,positionY,height,width);
    }
    
    public void publish(){
        Component[] compsQuestion = this.questionList.getComponents();
        Component[] compsAnswer;
        String content = "";
        content=content.concat("<poll published=\"true\">");
        content=content.concat("<contriblist></contriblist>");
        content=content.concat("<questionlist>");
        for(int cpt=0;cpt<compsQuestion.length;cpt++){
            content=content.concat("<question name=\""+((QuestionCreator)compsQuestion[cpt]).questionName.label.getText()+"\">");
            compsAnswer = ((QuestionCreator)compsQuestion[cpt]).answerList.getComponents();
            content=content.concat("<answerlist>");
            for(int cpt2=0;cpt2<compsAnswer.length;cpt2++){
                content=content.concat("<answer count=\"0\">"+((AnswerCreator)compsAnswer[cpt2]).name.label.getText()+"</answer>");
            }
            content=content.concat("</answerlist>");
            content=content.concat("</question>");
        }
        content=content.concat("</questionlist>");
        content=content.concat("</poll>");
        this.connexion.updateWidget(idWidget, name.label.getText(), content,positionX,positionY,height,width);
    }
    
}