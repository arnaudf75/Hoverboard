package windows;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Home extends JFrame {
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuDashboard = new JMenu("Dashboard");
    private JMenuItem dashNew = new JMenuItem ("New");
    private JMenu menuOptions = new JMenu("Options");
    private JMenu menuHelp = new JMenu("Help");
    private JMenu menuAbout = new JMenu("About");
    private JLabel background = new JLabel (new ImageIcon("src/ressources/background.jpg"));    
    
    public Home() {
        this.setTitle("Home Page");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        JPanel topMenu = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        this.setContentPane(panel);
        menuDashboard.add(dashNew);
        menuBar.add(menuDashboard);
        menuBar.add(menuOptions);
        menuBar.add(menuHelp);
        menuBar.add(menuAbout);
        panel.add(background);
        topMenu.add(menuBar);
        panel.add(topMenu, BorderLayout.NORTH);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
