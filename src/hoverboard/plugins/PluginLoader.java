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
    
    public enum PluginLoaderOperation{
        LoadPlugin,
        UnloadPlugin;
    }
    
    private PluginFile file;
    private PluginLoaderOperation operation;
    
    public PluginLoader(PluginFile file, PluginLoaderOperation operation){
        this.operation = operation;
        this.file = file;
    }
    
    public void loadFileOnBoard(){
        // do stuff
    }
}
