/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoverboard.plugins;

/**
 * Interface définissant les méthodes de manipulation de String 
 * ajoutée par le plugin l'implémentant.
 * @author Amram
 * 
 * Interface destinée à être utilisée par des plugins. 
 */
public interface PluginString extends PluginDeBase{
    /**
     * Fonction de traitement de manipulation de String
     * @param str la chaîne à traiter.
     * @return La chaine traitée.
     */
    public String actionOnString(String str);
}
