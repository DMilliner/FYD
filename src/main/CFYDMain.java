package main;


import gui.CMainFrame;
import gui.module3d.CModule3D;
import i18n.CResourceBundle;

//import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import utils.CKeysSettingsManager;
import utils.CUtils;

/**
 * Classe de lancement de l'application.
 * 
 * @author dmilliner
 * @version 0.1 du 31/10/12
 */
public class CFYDMain
{
	// Nombre d'arguments attendus sur la ligne de commande.
	private static final int ARGUMENTS_COUNT = 2;
	
	
	/**
	 * Point d'entrée de l'application.
	 * 
	 * @author dmilliner
	 * @version 0.1 du 31/10/12
	 * 
	 * @param pArgs liste des arguments passés sur la ligne de commande.
	 */
    public static void main(String[] pArgs)
    {
    	// Analyse des paramètres passés sur la ligne de commande.
    	if (CUtils.getInstance().checkParameters(pArgs, ARGUMENTS_COUNT))
    	{
    		// Récupération des arguments.
    		CResourceBundle.getInstance(pArgs[0]);
    		
    		// Lecture du fichier XML d'affectation des touches.
    		CKeysSettingsManager.getInstance().readKeysSettingsXMLFile(pArgs[1]);
    		
    		// Lancement de l'application.
    		try
    		{
    			CModule3D.getInstance();
    			
    			SwingUtilities.invokeLater(
	    					new Runnable()
	    					{
	    						@Override
	    						public void run()
	    						{
	    							CMainFrame.getInstance();
	    						}
	    					}
    					);
    		}
    		catch (Exception lException)
    		{
    			lException.printStackTrace();
    		}
    	}
    	else
    	{
    		// TODO
    	}
    } // main
} // CMain