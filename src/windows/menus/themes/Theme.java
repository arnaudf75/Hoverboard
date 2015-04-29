package windows.menus.themes;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.plaf.FontUIResource;
import javax.swing.UIManager;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Arnaud
 */
public class Theme {
    public static String nomTheme = "Thème de base Hoverboard";
    public static String nomFont = "Arial";
    public static int colorR = 0;
    public static int colorG = 0;
    public static int colorB = 0;
    public static ImageIcon logo = new ImageIcon(Theme.class.getClassLoader().getResource("ressources/images/icone.png"));
    public static ImageIcon background = new ImageIcon(Theme.class.getClassLoader().getResource("ressources/images/background.png"));
    public static int fontSize = 16;
    
    public static HashMap getDataTheme(File themeFile) {
        HashMap dataTheme = new HashMap();
        try {
            Document theme = new SAXBuilder().build(themeFile);
            Element racine = theme.getRootElement();
            dataTheme.put("nom",racine.getChild("nom").getText());
            dataTheme.put("font",racine.getChild("font").getText());
            dataTheme.put("colorR",racine.getChild("colorText").getChild("colorR").getText());
            dataTheme.put("colorG",racine.getChild("colorText").getChild("colorG").getText());
            dataTheme.put("colorB",racine.getChild("colorText").getChild("colorB").getText());
            dataTheme.put("fontSize",racine.getChild("fontSize").getText());
        }
        catch (IOException | JDOMException error) {
            JOptionPane.showMessageDialog(null, "Impossible de récupérer les données du thème ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
        return (dataTheme);
    }
    
    public static void setDataTheme(File modifiedTheme) {
        
    }
    
    public static void setTheme(File themeFile) {
        
    }
    
    public static void setUIFont(FontUIResource fontProperties) {
        java.util.Enumeration listUIElements = UIManager.getDefaults().keys();
        while (listUIElements.hasMoreElements()) {
        Object property = listUIElements.nextElement();
        Object element = UIManager.get(property);
        if (element instanceof javax.swing.plaf.FontUIResource)
            UIManager.put(property, fontProperties);
        }
    }
}