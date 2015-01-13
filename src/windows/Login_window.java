package windows;
import Hoverboard.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Login_window extends JFrame implements ActionListener {

    private JCheckBox CHECK_cookie = new JCheckBox ("Enregistrer mes informations de connexion");
    public JTextField login_field = new JTextField ("Login");
    private JTextField password_field = new JTextField ("Mot de passe");
    private JButton validation = new JButton ("Valider");
    private JButton reset = new JButton ("Annuler");
    private JOptionPane error_fields = new JOptionPane();
    
    public Login_window() {
        this.setTitle("Fenêtre de connexion");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        validation.addActionListener(this);
        reset.addActionListener(this);
        JPanel panel = new JPanel();
        panel.add(login_field);
        panel.add(password_field);
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

        if (source == validation) {
            String login = login_field.getText();
            String password = password_field.getText();
            if (login.isEmpty() || password.isEmpty()) {
                error_fields.showMessageDialog(null, "You must enter your login and your password to continue !" , "ERROR",
			JOptionPane.ERROR_MESSAGE);
            }
            else {
                //BDD.connect_user(login, password);
            }
        }
        if (source == reset) {
            
        }
    }
}