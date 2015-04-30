package com.hoverboard.windows.dashboards;

import com.hoverboard.BDD;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Arnaud
 */
public class DashboardUser extends JPanel implements ActionListener {
    private int idDashboard = -1;
    private int idUser = -1;
    private final JButton setAdmin = new JButton("Donner les droits d'administration");
    private final JButton notAdmin = new JButton("Enlever les droits d'administration");
    private final JButton removeUser = new JButton("Enlever l'utilisateur du dashboard");
    private final JPanel admin_container = new JPanel();
    private final JPanel remove_user_container = new JPanel();
    
    @SuppressWarnings("LeakingThisInConstructor")
    public DashboardUser(int idDashboard, int idUser, String login, int isDashboardAdmin) {
        this.setLayout(new GridLayout(1, 3));
        this.idDashboard = idDashboard;
        this.idUser = idUser;
        
        this.setAdmin.addActionListener(this);
        this.notAdmin.addActionListener(this);
        this.removeUser.addActionListener(this);
        
        this.add(new JLabel(login));
        if (isDashboardAdmin != 1) {
            this.admin_container.add(setAdmin);
        }
        else {
            this.admin_container.add(notAdmin);
        }
        this.add(admin_container);
        this.remove_user_container.add(removeUser);
        this.add(remove_user_container);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == setAdmin) {
            BDD.setDashboardRights(this.idUser, this.idDashboard, 1);
            this.admin_container.remove(setAdmin);
            this.admin_container.add(notAdmin);
        }
        else if (source == notAdmin) {
            BDD.setDashboardRights(this.idUser, this.idDashboard, 0);
            this.admin_container.remove(notAdmin);
            this.admin_container.add(setAdmin);
        }
        else if (source == removeUser) {
            BDD.removeUserFromDashboard(this.idDashboard, this.idUser);
            Container parentContainer = this.getParent();
            parentContainer.remove(this);
            parentContainer.revalidate();
            parentContainer.repaint();
        }
        this.revalidate();
        this.repaint();
    }
}