/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows.widgets;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Cavoleau
 */
public class Answer extends JPanel{
    JLabel name=new JLabel();
    JLabel stat=new JLabel();
    JRadioButton select = new JRadioButton();
    
    int count;
    boolean answered;
    
    public Answer(String answer,int count, boolean answered){
        this.setLayout(new BorderLayout());
        this.name=new JLabel(answer);
        this.add(name, BorderLayout.CENTER);
        this.count=count;
        if(answered){
            this.stat.setText(" ( "+String.valueOf(count)+" )  ");
            this.add(stat, BorderLayout.WEST);
        }
        else{
            this.add(select, BorderLayout.WEST);
        
        this.name.addMouseListener(
        new MouseListener(){
            
            public void mouseClicked(MouseEvent event) {
                select.setSelected(true);
                System.out.println(count);
            }

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
        }
        );
        }
    }

}
