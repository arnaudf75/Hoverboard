package com.hoverboard.windows.menus.myplugins;

import com.hoverboard.BDD;
import com.hoverboard.User;
import com.hoverboard.windows.menus.themes.Theme;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 * ListeMyPlugins est la fenêtre affichant la liste des plugins choisis sur le site web de l'application par l'utilisateur.
 * @author Arnaud
 */
@SuppressWarnings("LeakingThisInConstructor")
public class ListeMyPlugins extends JFrame implements ActionListener {
    private int idUser = -1;
    private int idPlugin = -1;
    private String nomPlugin = "";
    private String descriptionPlugin = "";
    private final JButton updatePlugins = new JButton("Rechercher des mises à jour");
    private final JPanel tab_header = new JPanel();
    private final JPanel main_container = new JPanel();
    
    /**
     * Affiche la liste des plugins de l'utilisateur à partir de son id.
     * @param idUser L'id de l'utilisateur connecté.
     */
    public ListeMyPlugins(int idUser) {
        this.setSize(1100, 200);
        this.setIconImage(Theme.logo.getImage());
        this.idUser = idUser;
        
        this.updatePlugins.addActionListener(this);
        
        this.main_container.setLayout(new GridLayout(3,6));
        
        this.tab_header.setLayout(new GridLayout(1,3));
        
        this.tab_header.add(new JLabel("Nom du plugin"));
        this.tab_header.add(new JLabel("Description"));
        this.tab_header.add(updatePlugins);
        
        this.main_container.add(tab_header);
        
        ResultSet listePlugins = BDD.getMyPlugins(this.idUser);
        try {
            while (listePlugins.next()) {
                this.nomPlugin = listePlugins.getString("namePlugin");
                this.descriptionPlugin = listePlugins.getString("descriptionPlugin");
                this.main_container.add(new Plugins(this.idPlugin, this.nomPlugin, this.descriptionPlugin));
            }
        }
        catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Impossible d'afficher la liste de vos plugins !", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
        
        this.setTitle("Mes plugins installés");
        this.setIconImage(Theme.icone.getImage());
        this.setContentPane(main_container);
        this.pack();
        this.setSize(this.getWidth(), this.getHeight() + 100);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * Active ou désactive le plugin sélectionné.
     * @param event L'action qui vient de se produire (bouton cliqué).
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == updatePlugins) {
            this.installPlugins();
        }
    }
    
    /**
     * Télécharge les plugins dans leur version la plus récente depuis leurs dépôts.
     */
    public void installPlugins() {
        ResultSet myPlugins = BDD.getMyPluginsVersion(User.getIdUser());
        try {
            while (myPlugins.next()) {
                try {
                    new File("plugins").mkdirs();
                    Files.copy(new URL(myPlugins.getString("pathToVersion")).openStream(), new File("plugins/"+myPlugins.getString("namePlugin")+myPlugins.getString("numVersion")+".jar").toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                catch (IOException error) {
                    JOptionPane.showMessageDialog(null, "Impossible d'afficher la liste de vos plugins !", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Impossible d'accèder à vos plugins !" +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
    }
}