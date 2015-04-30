package com.hoverboard.windows.widgets;
import com.hoverboard.BDD;
import com.hoverboard.windows.dashboards.Dashboard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
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
public class Poll extends Widget{
    JButton sendAnswers = new JButton("Repondre au Sondage");
    int published;
    List<Integer> contribList = new ArrayList();
    int user;
    
    JPanel questionList = new JPanel();
    JPanel bottom_container = new JPanel();
    JPanel button_container = new JPanel();
    
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
    public Poll(int idWidget, String contenuWidget, int positionX, int positionY, int height, int width,int idUser)
    {
        super(idWidget, positionX, positionY, height, width);
        
        user=idUser;
        
        questionList.setLayout(new BoxLayout(questionList, BoxLayout.PAGE_AXIS));
        content.setLayout(new BorderLayout());
        bottom_container.setLayout(new BorderLayout());
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
            this.published=1;
            List<Element> contriblist = poll.getChild("contriblist").getChildren();
            for(int cpt=0;cpt<contriblist.size();cpt++){
                this.contribList.add(Integer.valueOf(contriblist.get(cpt).getText()));
            }
            //On verifie si l'utilisateur a répondu au questionnaire
            for(int cpt=0;cpt<contribList.size();cpt++){
                if(this.user==this.contribList.get(cpt)){
                    this.published=2;
                }
            }
            List<Element> questionlist = poll.getChild("questionlist").getChildren();
            for(int cpt=0;cpt<questionlist.size();cpt++){
                Question newone =new Question(questionlist.get(cpt).getAttributeValue("name"));
                this.questionList.add(newone);
                List<Element> answerlist = questionlist.get(cpt).getChild("answerlist").getChildren();
                ButtonGroup group = new ButtonGroup();
                for(int cpt2=0;cpt2<answerlist.size();cpt2++){
                    Answer newAnswer;
                    if(published==1){
                        newAnswer = new Answer(answerlist.get(cpt2).getText(),Integer.valueOf(answerlist.get(cpt2).getAttributeValue("count")),false);
                    }
                    else{
                        newAnswer = new Answer(answerlist.get(cpt2).getText(),Integer.valueOf(answerlist.get(cpt2).getAttributeValue("count")),true);
                    }

                    newone.answerList.add(newAnswer);
                    group.add(newAnswer.select);
                    newAnswer.select.setSelected(true);
                    newAnswer.setMaximumSize( newAnswer.getPreferredSize() );
                }
                newone.setMaximumSize( newone.getPreferredSize() );
            }
            bottom_container.add(sendAnswers, BorderLayout.CENTER);
            sendAnswers.addActionListener(this);                
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
        //Envoie les réponse au sondage
        if(source == sendAnswers){
            this.send();
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
                BDD.deleteWidget(this.idWidget);
            }
        }
    }
    
    public void send(){
        Component[] compsQuestion = this.questionList.getComponents();
        Component[] compsAnswer;
        for(int cpt=0;cpt<compsQuestion.length;cpt++){
            compsAnswer = ((Question)compsQuestion[cpt]).answerList.getComponents();
            for(int cpt2=0;cpt2<compsAnswer.length;cpt2++){
                if(((Answer)compsAnswer[cpt2]).select.isSelected())
                {
                    ((Answer)compsAnswer[cpt2]).count++;
                }
            }
        }
        this.contribList.add(this.user);
        this.save();
    }
    @Override
    public void refresh(){
        super.refresh();
            //accès à la BDD
            String contenuWidget = BDD.getContentWidget(idWidget);
            //Clean du widget avant réecriture
            this.questionList.removeAll();
            this.contribList=new ArrayList();
            //réecriture
            org.jdom2.input.SAXBuilder saxBuilder = new SAXBuilder();
            try {
                Document doc = saxBuilder.build(new StringReader(contenuWidget));
                Element poll = doc.getRootElement();
                this.published=1;
                List<Element> contriblist = poll.getChild("contriblist").getChildren();
                for(int cpt=0;cpt<contriblist.size();cpt++){
                    this.contribList.add(Integer.valueOf(contriblist.get(cpt).getText()));
                }
                //On verifie si l'utilisateur a répondu au questionnaire
                for(int cpt=0;cpt<contribList.size();cpt++){
                    if(this.user==this.contribList.get(cpt)){
                        this.published=2;
                    }
                }
                List<Element> questionlist = poll.getChild("questionlist").getChildren();
                for(int cpt=0;cpt<questionlist.size();cpt++){
                    Question newone =new Question(questionlist.get(cpt).getAttributeValue("name"));
                    this.questionList.add(newone);
                    List<Element> answerlist = questionlist.get(cpt).getChild("answerlist").getChildren();
                    ButtonGroup group = new ButtonGroup();
                    for(int cpt2=0;cpt2<answerlist.size();cpt2++){
                        Answer newAnswer;
                        if(published==1){
                            newAnswer = new Answer(answerlist.get(cpt2).getText(),Integer.valueOf(answerlist.get(cpt2).getAttributeValue("count")),false);
                        }
                        else{
                            newAnswer = new Answer(answerlist.get(cpt2).getText(),Integer.valueOf(answerlist.get(cpt2).getAttributeValue("count")),true);
                        }
                        newone.answerList.add(newAnswer);
                        group.add(newAnswer.select);
                        newAnswer.select.setSelected(true);
                        newAnswer.setMaximumSize( newAnswer.getPreferredSize() );
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
            bottom_container.add(sendAnswers, BorderLayout.CENTER);
            sendAnswers.addActionListener(this);
            this.revalidate();
    }
    @Override
    public void save(){
        super.save();
        Component[] compsQuestion = this.questionList.getComponents();
        Component[] compsAnswer;
            String content = "";
            content=content.concat("<poll published=\"true\">");
            content=content.concat("<contriblist>");
            for(int cpt=0;cpt<this.contribList.size();cpt++){
                content=content.concat("<contrib>"+contribList.get(cpt)+"</contrib>");
            }
            content=content.concat("</contriblist>");
            content=content.concat("<questionlist>");
            for(int cpt=0;cpt<compsQuestion.length;cpt++){
                content=content.concat("<question name=\""+((Question)compsQuestion[cpt]).questionName.getText()+"\">");
                compsAnswer = ((Question)compsQuestion[cpt]).answerList.getComponents();
                content=content.concat("<answerlist>");
                for(int cpt2=0;cpt2<compsAnswer.length;cpt2++){
                    content=content.concat("<answer count=\""+((Answer)compsAnswer[cpt2]).count+"\">"+((Answer)compsAnswer[cpt2]).name.getText()+"</answer>");
                }
                content=content.concat("</answerlist>");
                content=content.concat("</question>");
            }
            content=content.concat("</questionlist>");
            content=content.concat("</poll>");
            BDD.updateWidget(idWidget, name.label.getText(), content,positionX,positionY,height,width);
    }
}