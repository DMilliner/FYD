package utils;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Classe contenant divers outils.
 * 
 * @author dmilliner
 * @version 0.1 du 31/10/12
 */
public class CUtils
{
	// Singleton.
	private static CUtils sInstance = null;
	
	
	/**
	 * Constructeur sans argument.
	 * 
	 * @author dmilliner
	 * @version 0.1 du 31/10/12
	 */
	private CUtils()
	{
		
	} // CUtils
	
	/**
	 * Méthode d'accès au singleton.
	 * 
	 * 
	 */
	public static CUtils getInstance()
	{
		if (sInstance == null)
		{
			sInstance = new CUtils();
		}
		return sInstance;
	} // getInstance
	
	/**
	 * Permet de vérifier que lenombre d'arguments passés sur la ligne de
	 * commande est correct.
	 * 
	 * @author dmilliner
	 * @version 0.1 du 31/10/12
	 * 
	 * @param pArgs liste des arguments passés sur la ligne de commande.
	 * @param pArgumentsCount nombre attendu d'arguments.
	 * 
	 * @return true si le nombre d'arguments passés sur la ligne de commande
	 * est correct et false sinon.
	 */
	public boolean checkParameters(String[] pArgs, int pArgumentsCount)
	{
		boolean lResult = false;
		
		
		if ((pArgs != null) && (pArgs.length == pArgumentsCount))
		{
			lResult = true;
		}
		
		return lResult;
	} // checkParameters
	

	/**
	 * 
	 * @param path
	 * @param description
	 * @return
	 */
	public ImageIcon createImageIcon(String pPath,
	                                 String pDescription)
	{
	    URL lImgURL = getClass().getResource(pPath);
	    
	    
	    if (lImgURL != null)
	    {
	        return new ImageIcon(lImgURL, pDescription);
	    }
	    else
	    {
	        return null;
	    }
	}
} // CUtils
