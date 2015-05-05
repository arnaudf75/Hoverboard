package com.hoverboard;

import java.io.File;
import java.io.FileInputStream;
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
            InputStream propertiesFile = new FileInputStream("userData/app.properties");
            AppProperties.properties.load(propertiesFile);
            AppProperties.themeSelected = properties.get("theme").toString();
        }
        catch (IOException | NullPointerException errorNoFile) {
            File userData = new File("userData");
            userData.mkdirs();
            File appData = new File("userData/app.properties");
            try {
                appData.createNewFile();
            }
            catch (IOException error) { }
        }
    }
    
    public static void storeProperties() {
        try {
            FileOutputStream propertiesOut = new FileOutputStream(new File("userData/app.properties"));
            AppProperties.properties.put("theme", AppProperties.themeSelected);
            AppProperties.properties.store(propertiesOut, "Fichier de configuration"); 
        }
        catch (IOException error) {
            JOptionPane.showMessageDialog(null, "Impossible d'enregistrer le fichier de configuration !", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
