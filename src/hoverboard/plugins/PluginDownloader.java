/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoverboard.plugins;

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
    
    // methode qui sert a apprendre au programme a debugger winodws vista
    public static PluginFile downloadPluginFromServer() {
        // - appelle la methode qui retourne le nom du fichier du plugin avec son ID
        //      > methode a implementer dans la classe BDD
        // - utilise un protocole de telechargement java a la wget
        //      > test les trucs ici : http://stackoverflow.com/questions/921262/how-to-download-and-save-a-file-from-internet-using-java
        // - appelle la methode de creation d'un pluginFile avec l'emplacement de telechargement du fichier plugin
        //      > determiner un fichier dans lequel stocker les plugins
        // - retourner le PluginFile fabrique a partir du fichier telecharge plus haut
        //      > utiliser la methode d'en dessous qui le fabrique
        return PluginDownloader.createPluginFile();
    }
    
    public static PluginFile createPluginFile(){
        // creer un nouveau puginFile avec le plugindl
        return new PluginFile("Some/path");
    }
    
}
