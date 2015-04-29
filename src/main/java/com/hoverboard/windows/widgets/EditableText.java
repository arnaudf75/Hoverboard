package com.hoverboard.windows.widgets;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Cavoleau
 */

public class EditableText extends JPanel {

    final JLabel label;
    private final JTextField text;
    
    public EditableText(String value){
        super();
        label = new JLabel(value);
        text = new JTextField();
        
        label.addMouseListener(new MouseListener() {
        
            @Override
            public void mouseReleased(MouseEvent arg0) {}
        
            @Override
            public void mousePressed(MouseEvent arg0) {}
        
            @Override
            public void mouseExited(MouseEvent arg0) {}
        
            @Override
            public void mouseEntered(MouseEvent arg0) {}
        
            @Override
            public void mouseClicked(MouseEvent arg0) {
                toTextField();//Si on clic sur le label alors on le transforme en textfield
            }
        });       
        text.addKeyListener(new KeyListener() {
            
            @Override
            public void keyTyped(KeyEvent e) {}
        
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    toLabel();//Si on appuie sur entrée alors on le transforme en label
                }
            }
        
            @Override
            public void keyPressed(KeyEvent e) {}
        });
        
        this.add(label);
    }
    
    private void toTextField(){
        remove(label);
        text.setText(label.getText());
        text.setSize(label.getSize());//on reutilise la taille et le texte de l'ancien champ
        add(text);
        validate();//met a jour les changements de layout sinon le nouveau champs est decalé
        repaint();//reaffiche apres les changement sinon l'ancien champ reste affiché en arriere plan
    }
    
    private void toLabel(){
        remove(text);
        label.setText(text.getText());
        add(label);
        label.revalidate();//met a jour la taille en fonction du nouveau texte
        validate();//met a jour les changements de layout sinon le nouveau champs est decalé
        repaint();//reaffiche apres les changement sinon l'ancien champ reste affiché en arriere plan
        this.getParent().setMaximumSize( this.getParent().getPreferredSize() );//Met a jour la taille du parent
    }
}