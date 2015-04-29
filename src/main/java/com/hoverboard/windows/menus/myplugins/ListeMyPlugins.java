package com.hoverboard.windows.menus.myplugins;

import com.hoverboard.BDD;
import com.hoverboard.windows.menus.themes.Theme;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 * ListeMyPlugins est la fenêtre affichant la liste des plugins choisis sur le site web de l'application par l'utilisateur.
 * @author Arnaud
 */
public class ListeMyPlugins extends JFrame {
    private int idUser = -1;
    private int idPlugin = -1;
    private int idVersion = -1;
    private int statutPlugin = -1;
    private String dateUpdate = "";
    private String nomPlugin = "";
    private String numeroVersion = "";
    private String descriptionPlugin = "";
    private final JButton updatePlugins = new JButton("Rechercher des mises à jour");
    private final JPanel tab_header = new JPanel();
    private final JPanel main_container = new JPanel();
    private final BDD connexion = new BDD();
    
    /**
     * Affiche la liste des plugins de l'utilisateur à partir de son id.
     * @param idUser L'id de l'utilisateur connecté.
     */
    public ListeMyPlugins(int idUser) {
        this.setSize(1100, 200);
        this.setIconImage(Theme.logo.getImage());
        this.idUser = idUser;
        this.main_container.setLayout(new GridLayout(3,6));
        
        this.tab_header.setLayout(new GridLayout(1,6));
        
        this.tab_header.add(new JLabel("Nom du plugin"));
        this.tab_header.add(new JLabel("N° de version"));
        this.tab_header.add(new JLabel("Date de la dernière version"));
        this.tab_header.add(new JLabel("Description"));
        this.tab_header.add(new JLabel("Statut"));
        this.tab_header.add(updatePlugins);
        
        this.main_container.add(tab_header);
        
        ResultSet listePlugins = connexion.getMyPlugins(this.idUser);
        try {
            while (listePlugins.next()) {
                this.idVersion = listePlugins.getInt("idVersion");
                this.nomPlugin = listePlugins.getString("namePlugin");
                this.numeroVersion = listePlugins.getString("numVersion");
                this.dateUpdate = listePlugins.getString("dateUpdate");
                this.descriptionPlugin = listePlugins.getString("descriptionPlugin");
                this.statutPlugin = listePlugins.getInt("idStatutPlugin");
                this.main_container.add(new Plugins(this.idUser, this.idPlugin, this.idVersion, this.nomPlugin, this.numeroVersion, this.dateUpdate, this.descriptionPlugin, this.statutPlugin));
            }
        }
        catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Impossible d'afficher la liste de vos plugins ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
        
        this.setTitle("Mes plugins installés");
        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/icone.png")).getImage());
        this.setContentPane(main_container);
        this.pack();
        this.setSize(this.getWidth(),this.getHeight() + 100);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}