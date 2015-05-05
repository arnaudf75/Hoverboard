/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoverboard.plugins;

/**
 * Cette interface ne doit pas être implémentée dans un plugin. 
 * Elle définit le comportement de base de toutes les autres interfaces de plugins.
 * @author Amram
 */
public interface PluginDeBase {
    /**
     * récupére le libellé à afficher dans les menus. 
     * @return le libellé sous forme de String. Créer un libellé explicite. 
     */
    public int getId();
    
    /**
     * Récupère la catégorie du plugin. 
     * permettra d'ajouter ses infos au menu une fois chargé. 
     * @return la catégorie
     */
    public int getCategorie();
    
      /**
     * Récupère la description du plugin. 
     * permettra d'ajouter ses infos au menu une fois chargé. 
     * @return la description
     */
    public String getDescription();
     
    /**
     * Récupère la version du plugin. 
     * permettra d'ajouter ses infos au menu une fois chargé. 
     * @return la version
     */
    public String getVersion();
    
    /**
     * permet a l'application de lancer le plugin
     */
    public void start();
}
