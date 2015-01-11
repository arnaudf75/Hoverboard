package windows;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class login extends JFrame implements ActionListener {

    private JCheckBox CHECK_cookie = new JCheckBox ("Enregistrer mes informations de connexion");
    public JTextField login = new JTextField ("Login");
    private JTextField password = new JTextField ("Mot de passe");
    private JButton validation = new JButton ("Valider");
    private JButton reset = new JButton ("Annuler");
    
    public login() {
        this.setTitle("Fenêtre de connexion");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        validation.addActionListener(this);
        reset.addActionListener(this);
        JPanel panel = new JPanel();
        panel.add(login);
        panel.add(password);
        panel.add(CHECK_cookie);
        panel.add(validation, BorderLayout.SOUTH);
        panel.add(reset, BorderLayout.SOUTH);
        panel.setBackground(Color.WHITE);

        this.setContentPane(panel);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == validation){
                System.out.println("Vous avez cliqué sur valider.");
        } else if(source == reset){
                System.out.println("Vous avez cliqué sur reset.");	
        }
    }
}