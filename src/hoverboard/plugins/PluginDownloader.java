/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoverboard.plugins;

import hoverboard.BDD;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Cette classe permet de télécharger un plugin d'un menu du programme 
 * directement sur le site. 
 * @author Amram
 */
public class PluginDownloader {
    
    private int pluginID;
    
    public PluginDownloader(int pluginID){
        // do constructor
        this.pluginID = pluginID;
    }
    
    /**
     * Permet de télécharger un fichier plugin depuis le serveur afin 
     * de le charger dans le programme. 
     * @return
     */
    public PluginFile downloadPluginFromServer() {
        // - appelle la methode qui retourne le nom du fichier du plugin avec son ID
        //      > methode a implementer dans la classe BDD
        // - utilise un protocole de telechargement java a la wget
        //      > test les trucs ici : http://stackoverflow.com/questions/921262/how-to-download-and-save-a-file-from-internet-using-java
        // - appelle la methode de creation d'un pluginFile avec l'emplacement de telechargement du fichier plugin
        //      > determiner un fichier dans lequel stocker les plugins
        // - retourner le PluginFile fabrique a partir du fichier telecharge plus haut
        //      > utiliser la methode d'en dessous qui le fabrique
        BDD db = new BDD();
        String name = db.getPluginName(pluginID);
        String host = "http://" + name + ".jar"; // concatener avec le "nom du plugin.jar"
        
        InputStream input = null;
        FileOutputStream writeFile = null;

        try
        {
            URL url = new URL(host);
            URLConnection connection = url.openConnection();
            int fileLength = connection.getContentLength();

            if (fileLength == -1)
            {
                System.out.println("Invalide URL or file.");
                return null;
            }

            input = connection.getInputStream();
            String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
            writeFile = new FileOutputStream(fileName);
            byte[] buffer = new byte[1024];
            int read;

            while ((read = input.read(buffer)) > 0)
                writeFile.write(buffer, 0, read);
            writeFile.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error while trying to download the file.");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                writeFile.close();
                input.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return new PluginFile("/" + name + ".jar");
    }
    
}
