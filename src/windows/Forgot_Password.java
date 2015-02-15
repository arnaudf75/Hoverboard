/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JInternalFrame;
/**
 *
 * @author Cavoleau
 */

public class Forgot_Password extends JFrame implements ActionListener{
    private final JPanel grid_container= new JPanel();
    private final JPanel main_container= new JPanel();
    
    private final JLabel logo = new JLabel (new ImageIcon("src/ressources/logo.png"));
            
    private final JLabel use_login = new JLabel("Utilisez votre login :");
    private final JLabel use_mail = new JLabel("Utilisez votre mail :");

    private final JTextField login_field = new JTextField();
    private final JTextField mail_field = new JTextField();

    private final JButton valider_login = new JButton ("Valider login");
    private final JButton valider_mail = new JButton ("Valider mail");
	public Forgot_Password(){
            
            this.setTitle("Forgot Password");
            this.setSize(450,180);
            this.setVisible(true);
            this.setLocationRelativeTo(null);

            grid_container.setLayout(new GridLayout(4,2));
            grid_container.add(use_login);
            grid_container.add(login_field);
            grid_container.add(new JLabel(""));
            grid_container.add(valider_login);
            grid_container.add(use_mail);
            grid_container.add(mail_field);
            grid_container.add(new JLabel(""));
            grid_container.add(valider_mail);

            main_container.add(logo);
            main_container.add(grid_container);

            this.add(main_container);
	}

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == valider_login){
            
        }
        else if (source == valider_mail){
            //
        }
    }
}
