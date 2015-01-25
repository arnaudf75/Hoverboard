package windows;

import hoverboard.*;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Login_window extends JFrame implements ActionListener {

    private JLabel login_label = new JLabel("Enter your login :");
    private JTextField login_field = new JTextField ();
    private JLabel background = new JLabel (new ImageIcon("src\\ressources\\logo.png"));
    private JLabel password_label = new JLabel("Enter your password :");
    private JPasswordField password_field = new JPasswordField ();
    private JCheckBox check_cookie = new JCheckBox ("Enregistrer mes informations de connexion");
    private JButton validation = new JButton ("Valider");
    private JButton reset = new JButton ("Annuler");
    private JOptionPane error_field_void = new JOptionPane();
    private JOptionPane error_no_user = new JOptionPane();
    private JPanel panel = new JPanel();
    private Box login_box = Box.createHorizontalBox();
    private Box password_box = Box.createHorizontalBox();
    private Box centerBox = Box.createHorizontalBox();
    private Box southBox = Box.createHorizontalBox();
    private Box container = Box.createVerticalBox();
    
    public Login_window() {
        this.setTitle("Fenêtre de connexion");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        validation.addActionListener(this);
        reset.addActionListener(this);
        panel.setLayout(new BorderLayout());
        background.setPreferredSize(new Dimension (30,30));
        
        login_box.add(login_label);
        login_field.setPreferredSize(new Dimension(100,30));
        login_box.add(login_field);
        password_box.add(password_label);
        password_field.setPreferredSize(new Dimension(100,30));
        password_box.add(password_field);
        centerBox.add(check_cookie);
        southBox.add(validation);
        southBox.add(reset);
        
        container.add(login_box);
        container.add(password_box);
        container.add(centerBox);
        
        panel.add(container);
        panel.add(southBox, BorderLayout.SOUTH);
        //panel.add(background, BorderLayout.CENTER);

        this.setContentPane(panel);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == validation) {
            String login = login_field.getText();
            String password = password_field.getText();
            if (login.isEmpty() || password.isEmpty()) {
                error_field_void.showMessageDialog(null, "You must enter your login and your password to continue !" , "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            }
            else {
                hoverboard.BDD connexion = new hoverboard.BDD("hoverboard_jx90","esgi_hoverboard");
                if (connexion.connect_user(login, password)) {
                    this.setVisible(false);
                }
                else {
                    error_no_user.showMessageDialog(null, "Nobody in the database with this login and password !" , "ERROR",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (source == reset) {
            System.out.println("Vous avez cliqué sur reset");
        }
    }
}