package com.hoverboard.windows.menus.myplugins;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Plugins est la classe qui affiche dans un JPanel les informations sur chacun des plugins de l'utilisateur.
 * @author Arnaud
 */
public class Plugins extends JPanel implements ActionListener {
    private int idPlugin = -1;
    
    private final JButton activate = new JButton("Activer");
    private final JButton deactivate = new JButton("Désactiver");
    private final JButton delete = new JButton ("Supprimer");
    private final JLabel labelNom = new JLabel("");
    private final JTextArea areaDescription = new JTextArea("");
    
    /**
     * Crée un JPanel affichant les informations relatives à un plugin.
     * @param idPlugin L'id du plugin affiché.
     * @param namePlugin Le nom du plugin affiché.
     * @param descriptionPlugin La description du plugin affiché.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Plugins(int idPlugin, String namePlugin, String descriptionPlugin) {        
        this.idPlugin = idPlugin;
        
        this.setLayout(new GridLayout(1,3));
        
        this.labelNom.setText(namePlugin);
        this.areaDescription.setText(descriptionPlugin);
        this.areaDescription.setWrapStyleWord(true);
        this.areaDescription.setLineWrap(true);
        this.areaDescription.setEditable(false);
        this.areaDescription.setOpaque(false);
        
        this.activate.addActionListener(this);
        this.deactivate.addActionListener(this);
        this.delete.addActionListener(this);
        
        this.add(labelNom);
        this.add(areaDescription);
        this.add(delete);
    }
    
    /**
     * Active ou désactive le plugin sélectionné.
     * @param event L'action qui vient de se produire (bouton cliqué).
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        
        if (source == activate) {
            this.remove(activate);
            this.add(deactivate);
        }
        else if (source == deactivate) {
            this.remove(deactivate);
            this.add(activate);
        }
        else if (source == delete) {
            // suppression du .jar
            // suppression de la version dans la base de données
        }
        this.revalidate();
    }
}