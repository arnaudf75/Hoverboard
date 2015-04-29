package com.hoverboard.windows.widgets;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Cavoleau
 */
public class QuestionCreator extends JPanel implements ActionListener {
    EditableText questionName = new EditableText("Nouvelle Question");
    JButton delQuestion = new JButton("Suppr. Question");
    JButton newAnswer = new JButton("Ajouter Réponse");
    JPanel answerList = new JPanel();
    JPanel actionList = new JPanel();
    
    @SuppressWarnings("LeakingThisInConstructor")
    public QuestionCreator()
    {
        this.setLayout(new BorderLayout());
        this.add(questionName, BorderLayout.NORTH);  
        
        answerList.setLayout(new BoxLayout(answerList, BoxLayout.PAGE_AXIS));
        this.add(answerList, BorderLayout.CENTER);
        this.answerList.add(new AnswerCreator("ReponseA"));
        this.answerList.add(new AnswerCreator("ReponseB"));
        this.answerList.revalidate();
        
        actionList.setLayout(new BorderLayout());
        this.add(actionList, BorderLayout.SOUTH);
        actionList.add(newAnswer, BorderLayout.WEST);
        actionList.add(delQuestion, BorderLayout.EAST);
        delQuestion.addActionListener(this);
        newAnswer.addActionListener(this);
        this.setMaximumSize( this.getPreferredSize() );
        this.setAlignmentX( Component.LEFT_ALIGNMENT );//0.0
    }
    
    public QuestionCreator(String name)
    {
        this.setLayout(new BorderLayout());
        this.add(questionName, BorderLayout.NORTH);  
        
        answerList.setLayout(new BoxLayout(answerList, BoxLayout.PAGE_AXIS));
        this.add(answerList, BorderLayout.CENTER);
        this.answerList.revalidate();
        
        actionList.setLayout(new BorderLayout());
        this.add(actionList, BorderLayout.SOUTH);
        actionList.add(newAnswer, BorderLayout.WEST);
        actionList.add(delQuestion, BorderLayout.EAST);
        delQuestion.addActionListener(this);
        newAnswer.addActionListener(this);
        this.questionName.label.setText(name);
        this.setMaximumSize( this.getPreferredSize() );
        this.setAlignmentX( Component.LEFT_ALIGNMENT );//0.0
    }
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == delQuestion){
            Container parent =this.getParent();
            parent.remove(this);//retire la tache de la lsite de tache
            parent.revalidate();
            parent.repaint();
        }
        else if (source == newAnswer){
            this.answerList.add(new AnswerCreator("Nouvelle Réponse"));
            this.setMaximumSize( this.getPreferredSize() );
            this.answerList.revalidate();//pour qu'elle s'affiche bien sans avoir a deplacer le widget
        }
    }
}
