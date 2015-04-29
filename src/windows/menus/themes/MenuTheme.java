package windows.menus.themes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.HashMap;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Arnaud
 */
public class MenuTheme extends JFrame implements ActionListener, ItemListener {
    private final JButton applyChanges = new JButton("Appliquer");
    private final JButton modifyTheme = new JButton("Modifier le thème");
    private final JButton deleteTheme = new JButton("Supprimer le thème");
    private final JButton newTheme = new JButton("Nouveau thème");
    private final JComboBox themeSelector = new JComboBox();
    private final JLabel labelNameTheme = new JLabel();
    private final JLabel labelFont = new JLabel();
    private final JLabel labelFontColor = new JLabel();
    private final JLabel labelFontSize = new JLabel();
    private final JPanel main_container = new JPanel();
    
    @SuppressWarnings("LeakingThisInConstructor")
    public MenuTheme() {
        
        this.main_container.setLayout(new GridLayout(7, 2));
        
        this.applyChanges.addActionListener(this);
        this.modifyTheme.addActionListener(this);
        this.newTheme.addActionListener(this);
        this.deleteTheme.addActionListener(this);
        this.themeSelector.addItemListener(this);
        
        File[] listOfFiles = new File("themes").listFiles();
        
        this.labelFont.setText(listOfFiles[0].getName());
        this.labelFontColor.setText(listOfFiles[0].getName());
        this.labelFontSize.setText(listOfFiles[0].getName());
        
        for (File fichier : listOfFiles) {
            if (fichier.getName().endsWith(".xml")) {
                themeSelector.addItem(fichier.getName().substring(0,fichier.getName().indexOf('.')));
            }
        }
        
        this.main_container.add(themeSelector);
        this.main_container.add(new JLabel("Informations sur le thème :"));
        this.main_container.add(new JLabel("Nom du thème"));
        this.main_container.add(labelNameTheme);
        this.main_container.add(new JLabel("Nom de la police"));
        this.main_container.add(labelFont);
        this.main_container.add(new JLabel("Couleur du texte"));
        this.main_container.add(labelFontColor);
        this.main_container.add(new JLabel("Taille du texte"));
        this.main_container.add(labelFontSize);
        this.main_container.add(newTheme);
        this.main_container.add(deleteTheme);
        this.main_container.add(applyChanges);
        this.main_container.add(modifyTheme);
        
        this.setTitle("Apparence et thèmes");
        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/icone.png")).getImage());
        this.setContentPane(main_container);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent eventButton) {
        Object source = eventButton.getSource();
        if (source == applyChanges) {
            
        }
        else if (source == newTheme) {
            
        }
        else if (source == modifyTheme) {
            
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
          this.labelNameTheme.setText(dataTheme.get("nom").toString());
          this.labelNameTheme.setFont(selectedFont);
          this.labelFont.setText(dataTheme.get("font").toString());
          this.labelFont.setFont(selectedFont);
          this.labelFontColor.setText(colorR +","+colorG+","+colorB);
          this.labelFontColor.setFont(selectedFont);
          this.labelFontColor.setForeground(new Color(colorR, colorG, colorB));
          this.labelFontSize.setText(Integer.toString(fontSize));
          this.labelFontSize.setFont(selectedFont);
          this.revalidate();
       }
    }
}