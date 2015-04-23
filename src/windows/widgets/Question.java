/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows.widgets;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Cavoleau
 */
public class Question extends JPanel{
    JLabel questionName = new JLabel("Nouvelle Question");
    JPanel answerList = new JPanel();
    
    public Question(String name)
    {
        this.setLayout(new BorderLayout());
        this.add(questionName, BorderLayout.NORTH);  
        
        answerList.setLayout(new BoxLayout(answerList, BoxLayout.PAGE_AXIS));
        this.add(answerList, BorderLayout.CENTER);
        this.answerList.revalidate();
        
        this.questionName.setText(name);
        this.setMaximumSize( this.getPreferredSize() );
        this.setAlignmentX( Component.LEFT_ALIGNMENT );//0.0
    }
}
