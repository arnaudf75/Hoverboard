/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoverboard.plugins;

/**
 * Cette classe permet au programme de charger le jar d'un plugin 
 * afin de l'implémenter. 
 * @author Amram
 */
public class PluginLoader {
    
    // la classe prend pour attribut un PluginFile pour garder la reference du plugin
    // de plus *captain obvious speaking*, il faut un fichier plugin pour l' activer/loader ou le desactiver/unloader
    private PluginFile file;
    
    // constructeur ne prend donc qu'un seul param : le PluginFile
    public PluginLoader(PluginFile file){
        this.file = file;
    }
    
    // captain obvious here, methode pour laoder un plugin
    public static void loadPlugin(){
        // do stuff
    }
    
    /**
     * Obviously unload da plugin. 
     */
    public static void unloadPlugin(){
        // do stuff
    }
    
}
