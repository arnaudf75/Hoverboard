package windows;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.JPanel;

public class home extends JFrame {

    private JLabel background = new JLabel (new ImageIcon("D:\\Téléchargements\\background.jpg"));
    public home() {
        this.setTitle("Home Page");
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        this.setContentPane(panel);this.add(background);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}