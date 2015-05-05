package com.hoverboard.plugins;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

import net.xeoh.plugins.base.Plugin;

/**
 * Interface devant être implémentée par les plugins externes à l'application.
 * @author Arnaud
 */
public interface PluginInterface extends Plugin {
    
    /**
     * Retourne le bouton permettant l'affichage du widget du plugin.
     * @return Le bouton du plugin.
     */
    public JButton getPluginButton();
    
    /**
     * Retourne le widget du plugin, qui devra être intégré au dashboard.
     * @return Le widget du plugin.
     */
    public JInternalFrame getPluginWidget();
    
}
