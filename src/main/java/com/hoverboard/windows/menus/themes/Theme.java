package com.hoverboard.windows.menus.themes;

import com.hoverboard.AppProperties;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.UIManager;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Arnaud
 */
public class Theme {
    public static String defaultNomTheme = "Thème de base Hoverboard";
    public static Font defaultPolice = new Font("Arial", Font.BOLD, 12);
    public static Color defaultCouleurPolice = new Color(0, 0, 0);
    public static ImageIcon defaultIcone = new ImageIcon(Theme.class.getResource("/images/icone.png"));
    public static ImageIcon defaultLogo = new ImageIcon(Theme.class.getResource("/images/logo.png"));
    public static ImageIcon defaultBackground = new ImageIcon(Theme.class.getResource("/images/background.png"));
    
    public static String nomTheme = "Thème de base Hoverboard";
    public static Font police = new Font("Arial", Font.BOLD, 12);
    public static Color couleurPolice = new Color(0, 0, 0);
    public static ImageIcon icone = new ImageIcon(Theme.class.getResource("/images/icone.png"));
    public static ImageIcon logo = new ImageIcon(Theme.class.getResource("/images/logo.png"));
    public static ImageIcon background = new ImageIcon(Theme.class.getResource("/images/background.png"));
    
    public static void createTheme(String nomTheme) {
        try {
            Document theme = new Document().setRootElement(new Element("theme"));
            theme.getRootElement().addContent(new Element("nom").addContent(nomTheme));
            theme.getRootElement().addContent(new Element("font").addContent("Arial"));
            theme.getRootElement().addContent(new Element("background").addContent(""));
            Element colorText = new Element("colorText");
            colorText.addContent(new Element("colorR").addContent("0"));
            colorText.addContent(new Element("colorG").addContent("0"));
            colorText.addContent(new Element("colorB").addContent("0"));
            theme.getRootElement().addContent(colorText);
            theme.getRootElement().addContent(new Element("fontSize").addContent("12"));
            XMLOutputter themeFile = new XMLOutputter();
            themeFile.setFormat(Format.getPrettyFormat());
            new File("themes").mkdirs();
            themeFile.output(theme, new FileWriter(new File("themes/"+nomTheme+".xml")));
        }
        catch (IOException error) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la création du thème !", "ERREUR", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static HashMap getDataTheme(File themeFile) {
        HashMap dataTheme = new HashMap();
        try {
            Document theme = new SAXBuilder().build(themeFile);
            Element racine = theme.getRootElement();
            dataTheme.put("nom",racine.getChild("nom").getText());
            dataTheme.put("font",racine.getChild("font").getText());
            dataTheme.put("background",racine.getChild("background").getText());
            dataTheme.put("colorR",racine.getChild("colorText").getChild("colorR").getText());
            dataTheme.put("colorG",racine.getChild("colorText").getChild("colorG").getText());
            dataTheme.put("colorB",racine.getChild("colorText").getChild("colorB").getText());
            dataTheme.put("fontSize",racine.getChild("fontSize").getText());
        }
        catch (IOException | JDOMException error) {
            JOptionPane.showMessageDialog(null, "Impossible de récupérer les données du thème !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            AppProperties.themeSelected = "DEFAULT";
        }
        return (dataTheme);
    }
    
    public static void setTheme(File themeFile) {
        HashMap dataTheme = Theme.getDataTheme(themeFile);
        if (!dataTheme.isEmpty()) {
            String nameFont = dataTheme.get("font").toString();
            int colorR = Integer.parseInt(dataTheme.get("colorR").toString());
            int colorG = Integer.parseInt(dataTheme.get("colorG").toString());
            int colorB = Integer.parseInt(dataTheme.get("colorB").toString());
            int fontSize = Integer.parseInt(dataTheme.get("fontSize").toString());
            Theme.couleurPolice = new Color(colorR, colorG, colorB);
            Theme.police = new Font(nameFont, Font.BOLD, fontSize);
            Theme.background = new ImageIcon(dataTheme.get("background").toString());
        }
    }
    
    public static void setUIColorAndFont(ColorUIResource colorProperties, FontUIResource fontProperties) {
        java.util.Enumeration listUIElements = UIManager.getDefaults().keys();
        while (listUIElements.hasMoreElements()) {
            Object property = listUIElements.nextElement();
            Object element = UIManager.get(property);
            if (element instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(property, fontProperties);
            }
        }
    }
}