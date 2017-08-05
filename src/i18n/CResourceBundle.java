package i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import exceptions.CLanguageException;


/**
 * TODO
 * 
 * @author dmilliner
 * @version 0.1 du 02/11/12
 */
public class CResourceBundle
{
	private static final String I18N_RESOURCE_FILE = "i18n.fyd";
	
	// Singleton.
	private static CResourceBundle sInstance = null;
	
    private static ResourceBundle sResourceBundle = null;
	
	
	/**
	 * Constructeur sans argument.
	 * 
	 * @author dmilliner
	 * @version 0.1 du 02/11/12
	 * 
	 * @param pLanguage la langue à mettre en oeuvre.
	 */
	private  CResourceBundle(
			String pLanguage) throws CLanguageException
	{
		if ( (pLanguage            != null)
		  && (pLanguage.equals("") == false) )
		{
			Locale lLocale = new Locale(pLanguage);
			
			
			sResourceBundle = ResourceBundle.getBundle(
					I18N_RESOURCE_FILE + "_" + pLanguage,
					lLocale);
			JOptionPane.setDefaultLocale(lLocale);
		}
		else
		{
			throw new CLanguageException(); 
		}
	} //  CResourceBundle
	
	/**
	 * Méthode d'accès au singleton.
	 * 
	 * @author dmilliner
	 * @version 0.1 du 02/11/12
	 * 
	 * @param pLanguage la langue à mettre en oeuvre.
	 */
	public static  CResourceBundle getInstance(
			String pLanguage)
	{
		if (sInstance == null)
		{
			try
			{
			    sInstance = new CResourceBundle(pLanguage);
			}
			catch (CLanguageException lException)
			{
				lException.printStackTrace();
			}
		}
		return sInstance;
	} // getInstance

	/**
	 * Méthode d'accès au singleton.
	 * 
	 * @author dmilliner
	 * @version 0.1 du 02/11/12
	 */
	public static  CResourceBundle getInstance()
	{
		return sInstance;
	} // getInstance
	
	/**
	 * TODO
	 * 
     * @author dmilliner
     * @version 0.1 du 02/11/12
	 * 
	 * @param pKey clé de la chaîne de caractères à lire dans le fichier de
	 * ressources.
	 * 
	 * @return TODO
	 */
	public String getString(String pKey)
	{
		return sResourceBundle.getString(pKey);
	}
}