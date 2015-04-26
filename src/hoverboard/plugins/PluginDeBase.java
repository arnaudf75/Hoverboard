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
    public String getLibelle();
    
    /**
     * Récupère la catégorie du plugin. 
     * permettra d'ajouter son menu une fois chargé. 
     * @return la catégorie
     */
    public int getCategorie();
    
    
    
}
