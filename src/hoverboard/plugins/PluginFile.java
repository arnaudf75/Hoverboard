/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoverboard.plugins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amram
 * 
 * Cette classe a pour but de referencer un fichier plugin dans l'application.
 * Instancier cette classe a chaque chargement de plugin :
 *      - au niveau de l'activation/desactivation/suppression
 *      - au niveau du chargement de lancement
 */
public class PluginFile {
    private String pathOnMachine;

    public PluginFile(String pathOnMachine) {
        // do constructor stuff
        this.pathOnMachine = pathOnMachine;
    }
    
    /**
     * @return the pathOnMachine
     */
    public String getPathOnMachine() {
        return pathOnMachine;
    }
    
    private static List<String> getLocalPluginList(){
        File actual = new File(".");
        List<String> v = new ArrayList<>();
        for( File f : actual.listFiles()){
            v.add(f.getName());
        }
        return v;
    }
    
    public static boolean pluginExists(String pluginName){
        List<String> list = getLocalPluginList();
        for(String s : list) {
            if (s.equals(pluginName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param pathOnMachine the pathOnMachine to set
     */
    public void setPathOnMachine(String pathOnMachine) {
        this.pathOnMachine = pathOnMachine;
    }
    
    public void deletePluginFile() {
        // traitement de la suppression d'un fichier plugin
        // Attention :
        //      - unload (depuis un objet PluginLoader)
        //      - ensuite seulement : appel de cette methode qui supprime def le fichier en local
        //          (dans le repertoire choisi)
    }
}
