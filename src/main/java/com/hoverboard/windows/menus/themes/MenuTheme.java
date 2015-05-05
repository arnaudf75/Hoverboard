package com.hoverboard.windows.menus.themes;

import com.hoverboard.AppProperties;
import com.hoverboard.windows.widgets.EditableText;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Arnaud
 */
public class MenuTheme extends JFrame implements ActionListener, ItemListener {
    private final JButton applyChanges = new JButton("Sélectionner ce thème");
    private final JButton toDefault = new JButton("Rétablir l'apparence par défaut");
    private final JButton deleteTheme = new JButton("Supprimer le thème");
    private final JButton newTheme = new JButton("Nouveau thème");
    private final JButton selectBackground = new JButton("Changer le background");
    private final JButton selectColor = new JButton("Changer la couleur");
    private final JComboBox themeSelector = new JComboBox();
    private final EditableText labelNameTheme = new EditableText("");
    private final EditableText labelFont = new EditableText("");
    private final JLabel labelFontColor = new JLabel();
    private final JLabel labelBackground = new JLabel();
    private final EditableText labelFontSize = new EditableText("");
    private final JPanel main_container = new JPanel();
    
    @SuppressWarnings("LeakingThisInConstructor")
    public MenuTheme() {
        
        this.main_container.setLayout(new GridLayout(8, 3));
        
        this.labelBackground.setSize(100, 100);
        
        this.applyChanges.addActionListener(this);
        this.newTheme.addActionListener(this);
        this.deleteTheme.addActionListener(this);
        this.toDefault.addActionListener(this);
        this.selectBackground.addActionListener(this);
        this.selectColor.addActionListener(this);
        this.themeSelector.addItemListener(this);
        
        File themeFolder  = new File("themes");
        
        if (!themeFolder.exists()) {
            themeFolder.mkdirs();
        }
        
        else {
            File[] listOfFiles = new File("themes").listFiles();
            if (listOfFiles.length > 0) {
                this.labelFontColor.setText(listOfFiles[0].getName());
                this.labelFontSize.label.setText(listOfFiles[0].getName());
                this.labelFont.label.setText(listOfFiles[0].getName());
                for (File fichier : listOfFiles) {
                    if (fichier.getName().endsWith(".xml")) {
                        themeSelector.addItem(fichier.getName().substring(0, fichier.getName().indexOf('.')));
                    }
                }
                this.main_container.add(themeSelector);
                this.main_container.add(new JLabel("Informations sur le thème :"));
                this.main_container.add(new JLabel(""));
                this.main_container.add(new JLabel("Nom du thème"));
                this.main_container.add(labelNameTheme);
                this.main_container.add(new JLabel(""));
                this.main_container.add(new JLabel("Nom de la police"));
                this.main_container.add(labelFont);
                this.main_container.add(new JLabel(""));
                this.main_container.add(new JLabel("Couleur du texte"));
                this.main_container.add(labelFontColor);
                this.main_container.add(selectColor);
                this.main_container.add(new JLabel("Chemin du background"));
                this.main_container.add(labelBackground);
                this.main_container.add(selectBackground);
                this.main_container.add(new JLabel("Taille du texte"));
                this.main_container.add(labelFontSize);
                this.main_container.add(new JLabel(""));
                this.main_container.add(applyChanges);
                this.main_container.add(newTheme);
                this.main_container.add(toDefault);
                this.main_container.add(deleteTheme);
                
                this.setTitle("Apparence et thèmes");
                this.setIconImage(Theme.icone.getImage());
                this.setContentPane(main_container);
                this.setSize(800, 400);
                this.setLocationRelativeTo(null);
                this.setVisible(true);    
            }
            else {
                JOptionPane.showMessageDialog(null, "Vous n'avez aucun thème ! Pour installer un thème,"
                        + "rendez vous sur notre site à l'adresse http://hoverboard.livehost.fr/ et sélectionnez un thème dans la rubrique 'Thèmes'."
                        + "Ensuite, désippez l'archive dans le dossier 'Thèmes' de l'application.", "Aucun thème", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent eventButton) {
        Object source = eventButton.getSource();
        if (source == applyChanges) {
            AppProperties.themeSelected = themeSelector.getSelectedItem().toString();
            Theme.setTheme(new File("themes/"+AppProperties.themeSelected+".xml"));
            Theme.setUIColorAndFont(new ColorUIResource(Theme.couleurPolice), new FontUIResource(Theme.police));
        }
        else if (source == newTheme) {
            String nomTheme = JOptionPane.showInputDialog(null, "Saisissez le nom du thème :", "Choisissez un nom", JOptionPane.QUESTION_MESSAGE);
            if (!nomTheme.equals("")) {
               Theme.createTheme(nomTheme); 
            }
        }
        else if (source == deleteTheme) {
            int option = JOptionPane.showConfirmDialog(null, "Êtes vous sûr de vouloir supprimer ce thème ?",
                "Confirmez la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                File themeToDelete = new File("themes/"+ this.themeSelector.getSelectedItem().toString() +".xml");
                themeToDelete.delete();
                this.themeSelector.removeItem(this.themeSelector.getSelectedItem());
                this.revalidate();
            }
        }
        else if (source == selectBackground) {
            JFileChooser fileChooser = new JFileChooser();
            int choix = fileChooser.showOpenDialog(null);
            if (choix == JFileChooser.APPROVE_OPTION) {
                String pathBackground = fileChooser.getSelectedFile().getAbsolutePath();
               
                BufferedImage backgroundPreview = null;
                try {
                    Document themeToUpdate = new SAXBuilder().build(new File("themes/"+ this.themeSelector.getSelectedItem().toString() +".xml"));
                    themeToUpdate.getRootElement().getChild("background").setText(pathBackground);
                    XMLOutputter themeFile = new XMLOutputter();
                    themeFile.setFormat(Format.getPrettyFormat());
                    themeFile.output(themeToUpdate, new FileWriter(new File("themes/"+ this.themeSelector.getSelectedItem().toString() +".xml")));
                    backgroundPreview = ImageIO.read(new File(pathBackground));
                    backgroundPreview.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    this.labelBackground.setIcon(new ImageIcon(backgroundPreview));
                }
                catch (IOException | JDOMException error) {
                    JOptionPane.showMessageDialog(null, "Erreur dans la mise à jour du background !", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (source == selectColor) {
            Color newColor = JColorChooser.showDialog(this,"Choose Background Color", new Color(0,0,0));
            if (newColor != null) {
                try {
                    Document themeToUpdate = new SAXBuilder().build(new File("themes/"+ this.themeSelector.getSelectedItem().toString() +".xml"));
                    Element colorText = themeToUpdate.getRootElement().getChild("colorText");
                    colorText.getChild("colorR").setText(Integer.toString(newColor.getRed()));
                    colorText.getChild("colorG").setText(Integer.toString(newColor.getGreen()));
                    colorText.getChild("colorB").setText(Integer.toString(newColor.getBlue()));
                    XMLOutputter themeFile = new XMLOutputter();
                    themeFile.setFormat(Format.getPrettyFormat());
                    themeFile.output(themeToUpdate, new FileWriter(new File("themes/"+ this.themeSelector.getSelectedItem().toString() +".xml")));
               }
               catch (IOException | JDOMException error) {
                   JOptionPane.showMessageDialog(null, "Erreur dans la mise à jour du background !", "ERREUR", JOptionPane.ERROR_MESSAGE);
               }
            }
        }
        else if (source == toDefault) {
            AppProperties.themeSelected = "DEFAULT";
            Theme.couleurPolice = Theme.defaultCouleurPolice;
            Theme.police = Theme.defaultPolice;
            Theme.logo = Theme.defaultLogo;
            Theme.icone = Theme.defaultIcone;
            Theme.background = Theme.defaultBackground;
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent eventItem) {
       if (eventItem.getStateChange() == ItemEvent.SELECTED) {
          Object themeName = eventItem.getItem();
          HashMap dataTheme = Theme.getDataTheme(new File("themes/"+themeName.toString()+".xml"));
          String nameFont = dataTheme.get("font").toString();
          int colorR = Integer.parseInt(dataTheme.get("colorR").toString());
          int colorG = Integer.parseInt(dataTheme.get("colorG").toString());
          int colorB = Integer.parseInt(dataTheme.get("colorB").toString());
          int fontSize = Integer.parseInt(dataTheme.get("fontSize").toString());
          Font selectedFont = new Font(nameFont, Font.BOLD, fontSize);
          this.labelNameTheme.label.setText(dataTheme.get("nom").toString());
          this.labelNameTheme.label.setFont(selectedFont);
          this.labelFont.label.setText(dataTheme.get("font").toString());
          this.labelFont.label.setFont(selectedFont);
          this.labelFontColor.setText(colorR +", "+colorG+", "+colorB);
          this.labelFontColor.setFont(selectedFont);
          this.labelFontColor.setForeground(new Color(colorR, colorG, colorB));
          this.labelFontSize.label.setText(Integer.toString(fontSize));
          this.labelFontSize.setFont(selectedFont);
          BufferedImage backgroundPreview = null;
          try {
              backgroundPreview = ImageIO.read(new File(dataTheme.get("background").toString()));
              backgroundPreview.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
          }
          catch (IOException error) {
              JOptionPane.showMessageDialog(null, "Erreur lors de l'affichage du background !", "Erreur !", JOptionPane.ERROR_MESSAGE);
          }
          this.labelBackground.setIcon(new ImageIcon(backgroundPreview));
          this.revalidate();
       }
    }
}