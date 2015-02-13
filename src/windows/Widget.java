/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Cavoleau
 */
public abstract class Widget extends JInternalFrame implements MouseListener,ActionListener {
    protected int positionX;
    protected int positionY;
    protected int oldX;
    protected int oldY;
    protected int height;
    protected int width;
    
    protected JPanel settings=new JPanel();
    protected JPanel content=new JPanel();
    JButton del = new JButton("delete");
    
    public Widget(){
        super();
        this.add(settings, BorderLayout.NORTH);
        this.add(content, BorderLayout.CENTER);
        
        del.addActionListener(this);
        settings.add(del);
        
        this.setVisible(true);
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        
        settings.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        if (source == settings){
            if(SwingUtilities.isLeftMouseButton(e))
                    {
                        oldX = e.getX();
                        oldY = e.getY();
                    }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object source = e.getSource();
        if (source == settings){
            if(SwingUtilities.isLeftMouseButton(e)){
                positionX += e.getX()-oldX;
                positionY += e.getY()-oldY;
                setBounds(positionX,positionY,height,width);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == del){
            this.dispose();
        }
    }
}
