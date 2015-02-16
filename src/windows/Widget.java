/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
<<<<<<< HEAD
import java.awt.event.MouseListener;
import javafx.scene.layout.Border;
=======

import java.awt.event.MouseMotionListener;
>>>>>>> f5492beea74ee1b84b741a502ee7d2cc9a47b593
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Cavoleau
 */
public abstract class Widget extends JInternalFrame implements ActionListener,MouseMotionListener {
    protected int positionX;
    protected int positionY;
    protected int oldX;
    protected int oldY;
    protected int height;
    protected int width;
    
<<<<<<< HEAD
    protected JPanel settings;
    protected JPanel content;
    protected JButton del;
    
    public Widget(){
        super();
        settings=new JPanel();
        settings.setBackground(new Color(200,200,200));
        content=new JPanel();
        del=new JButton("delete");
        
=======
    protected JPanel settings=new JPanel();
    protected JPanel content=new JPanel();
    JButton del = new JButton("x");
    
    public Widget(){
        super();
        settings.setLayout(new BorderLayout());
>>>>>>> f5492beea74ee1b84b741a502ee7d2cc9a47b593
        this.add(settings, BorderLayout.NORTH);
        this.add(content, BorderLayout.CENTER);
        
        del.addActionListener(this);
        settings.add(del,BorderLayout.EAST);
        
        this.setVisible(true);
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        
        settings.addMouseMotionListener(this);
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
       // if (contains(e.getX(),e.getY())) { A utiliser pour que le post it reste dans le cadre
            positionX += e.getX();
            positionY += e.getY();
            setBounds(positionX,positionY,height,width);
       //} 
    }
    @Override
    public void mouseMoved(MouseEvent e) {
	 

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == del){
            this.dispose();
        }
    }
}
