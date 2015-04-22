package hoverboard;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Arnaud
 */
public class AppProperties {
    public static Properties properties = new Properties();
    public static String themeSelected = "DEFAULT";
    
    public static void getProperties() {
        try {
            InputStream propertiesFile = AppProperties.class.getClassLoader().getResourceAsStream("ressources/app.properties");
            AppProperties.properties.load(propertiesFile);
            AppProperties.themeSelected = properties.get("theme").toString();
        }
        catch (IOException error) {
            JOptionPane.showMessageDialog(null, "Impossible d'accèder au fichier de configuration !", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void storeProperties() {
        try {
            FileOutputStream propertiesOut = new FileOutputStream(AppProperties.class.getClassLoader().getResource("ressources/app.properties").toString());
            AppProperties.properties.store(propertiesOut, "Fichier de configuration"); 
        }
        catch (IOException error) {
            JOptionPane.showMessageDialog(null, "Impossible d'enregistrer fichier de configuration !", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
