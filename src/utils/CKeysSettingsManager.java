package utils;

import exceptions.CKeysSettingsXMLFileNameException;
import gui.dialogs.CKeysSettingsDialogGui;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;

/**
 * Classe permettant de gérer les affectations des touches.
 * 
 * @author dmilliner
 */
public class CKeysSettingsManager
{
	private static final String DEFAULT_KEYS_SETTINGS_XML_FILENAME = "keysSettings.xml";
	
	private static final String DEFAULT_MOVE_FORWARD_KEY      = "z";
	private static final String DEFAULT_MOVE_BACKWARDWARD_KEY = "s";
	private static final String DEFAULT_MOVE_RIGHT_KEY        = "d";
	private static final String DEFAULT_MOVE_LEFT_KEY         = "q";
	//private static final String DEFAULT_MOVE_NEXT_WEAPON_KEY  = "f";
	//private static final String DEFAULT_PREVIOUS_WEAPON_KEY   = "c";
	private static final String DEFAULT_RELOAD_KEY            = "r";
	private static final String DEFAULT_LIGHTSWITCH_KEY       = "g";
	
	private static CKeysSettingsManager sInstance = null;

	private File            mXmlFile;
	private DocumentBuilder mDBuilder;
	private Document        mDocument;
	
	public String           mKeysSettingsXMLFileName;
	
	public String           mMoveForwardKey;
	public String           mMoveBackwardKey;
	public String           mMoveRightKey;
	public String           mMoveLeftKey;
	public String           mPreviousWeaponKey;
	public String           mNextWeaponKey;
	public String           mReloadKey;
	public String			mLightSwitchKey;


	/**
	 * 
	 * @return
	 */
	public static CKeysSettingsManager getInstance()
	{
		if (sInstance == null)
		{
			sInstance = new CKeysSettingsManager();
		}
		return sInstance;
	}

	/**
	 * 
	 */
	private CKeysSettingsManager()
	{
		mKeysSettingsXMLFileName  = DEFAULT_KEYS_SETTINGS_XML_FILENAME;
		
		mMoveForwardKey           = DEFAULT_MOVE_FORWARD_KEY;
		mMoveBackwardKey          = DEFAULT_MOVE_BACKWARDWARD_KEY;
		mMoveRightKey             = DEFAULT_MOVE_RIGHT_KEY;
		mMoveLeftKey              = DEFAULT_MOVE_LEFT_KEY;
		//mNextWeaponKey            = DEFAULT_MOVE_NEXT_WEAPON_KEY;
		//mPreviousWeaponKey        = DEFAULT_PREVIOUS_WEAPON_KEY;
		mReloadKey                = DEFAULT_RELOAD_KEY;
		mLightSwitchKey           = DEFAULT_LIGHTSWITCH_KEY;
	}


	/**
	 * 
	 * @throws CKeysSettingsXMLFileNameException
	 */
	public void readKeysSettingsXMLFile() throws CKeysSettingsXMLFileNameException
	{
		if ( (mKeysSettingsXMLFileName == null) || (mKeysSettingsXMLFileName.equals("")) )
		{
			throw new CKeysSettingsXMLFileNameException();
		}
		else
		{
			readKeysSettingsXMLFile(mKeysSettingsXMLFileName);
		}
	}
	
	/**
	 * 
	 */
	public void readKeysSettingsXMLFile(String pKeysSettingsXMLFileName)
	{
		mKeysSettingsXMLFileName = pKeysSettingsXMLFileName;
		
		
	    try
	    {
	    	NodeList lNodesList = null;


	    	mXmlFile  = new File(mKeysSettingsXMLFileName);
	    	mDBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    	mDocument = mDBuilder.parse(mXmlFile);
	    	
	    	mDocument.getDocumentElement().normalize();
	    	lNodesList = mDocument.getElementsByTagName("action");
	    	
	    	for (int lTemp = 0; lTemp < lNodesList.getLength(); lTemp++)
	    	{
	    		Node lNode = lNodesList.item(lTemp);
	    		
	     
	    		if (lNode.getNodeType() == Node.ELEMENT_NODE)
	    		{
	    			Element lElement = (Element)lNode;
	     
	    			
	    			switch (lElement.getAttribute("id"))
	    			{
	    			    case "moveForward" :
	    			    	mMoveForwardKey = lElement.
	    			    	        getElementsByTagName("key").item(0).
	    			    	                getTextContent();
	    			    	break;
	    			    	
	    			    case "moveBackward" :
	    			    	mMoveBackwardKey = lElement.
	    			    	        getElementsByTagName("key").item(0).
	    			    	                getTextContent();
	    			    	break;
	    			    	
	    			    case "moveRigt" :
	    			    	mMoveRightKey = lElement.
	    			    	        getElementsByTagName("key").item(0).
	    			    	                getTextContent();
	    			    	break;
	    			    	
	    			    case "moveLeft" :
	    			    	mMoveLeftKey = lElement.
	    			    	        getElementsByTagName("key").item(0).
	    			    	                getTextContent();
	    			    	break;
	    			/*    	
	    			    case "NextWeapon" :
	    			    	mNextWeaponKey = lElement.
	    			    	        getElementsByTagName("key").item(0).
	    			    	                getTextContent();
	    			    	break;
	    			    	
	    			    case "PreviousWeapon" :
	    			    	mPreviousWeaponKey = lElement.
	    			    	        getElementsByTagName("key").item(0).
	    			    	                getTextContent();
	    			    	break;
	    			  */  	
	    			    case "Reload" :
	    			    	mReloadKey = lElement.
	    			    	        getElementsByTagName("key").item(0).
	    			    	                getTextContent();
	    			    	break;
	    			    	
	    			    case "LightSwitch" :
	    			    	mLightSwitchKey = lElement.
	    			    	        getElementsByTagName("key").item(0).
	    			    	                getTextContent();
	    			    	break;
	    			}
	    		}
	    	}
        }
	    catch (Exception lException)
	    {
	    	lException.printStackTrace();
	    }
	}
	
	/**
	 * 
	 */
	public void writeKeysSettingsXMLFile()
	{
    	NodeList lNodesList = mDocument.getElementsByTagName("action");
    	
    	
   	    try
	    {
	    	for (int lTemp = 0; lTemp < lNodesList.getLength(); lTemp++)
	    	{
	    		Node lNode = lNodesList.item(lTemp);
	    		
	     
	    		if (lNode.getNodeType() == Node.ELEMENT_NODE)
	    		{
	    			Element lElement = (Element)lNode;
	    			NodeList lNodesList2 = lElement.getElementsByTagName("key");
	     
	    			
	    			switch (lElement.getAttribute("id"))
	    			{
	    			    case "moveForward" :
	    			    	((Element)lNodesList2.item(0)).setTextContent(
	    			    	        CKeysSettingsDialogGui.getInstance().
	    			    	        getMoveForwardTextField().getText());
	    			    	break;
	    			    	
	    			    case "moveBackward" :
	    			    	((Element)lNodesList2.item(0)).setTextContent(
	    			    	        CKeysSettingsDialogGui.getInstance().
	    			    	        getMoveBackwardTextField().getText());
	    			    	break;
	    			    	
	    			    case "moveRight" :
	    			    	((Element)lNodesList2.item(0)).setTextContent(
	    			    	        CKeysSettingsDialogGui.getInstance().
	    			    	        getMoveRightTextField().getText());
	    			    	break;
	    			    	
	    			    case "moveLeft" :
	    			    	((Element)lNodesList2.item(0)).setTextContent(
	    			    	        CKeysSettingsDialogGui.getInstance().
	    			    	        getMoveLeftTextField().getText());
	    			    	break;
	    			    	
	    			    case "NextWeapon" :
	    			    	((Element)lNodesList2.item(0)).setTextContent(
	    			    	        CKeysSettingsDialogGui.getInstance().
	    			    	        getNextWeaponTextField().getText());
	    			    	break;
	    			    	
	    			    case "PreviousWeapon" :
	    			    	((Element)lNodesList2.item(0)).setTextContent(
	    			    	        CKeysSettingsDialogGui.getInstance().
	    			    	        getPreviousWeaponTextField().getText());
	    			    	break;
	    			    	
	    			    case "Reload" :
	    			    	((Element)lNodesList2.item(0)).setTextContent(
	    			    	        CKeysSettingsDialogGui.getInstance().
	    			    	        getReloadTextField().getText());
	    			    	break;
	    			    	
	    			    case "LightSwitch" :
	    			    	((Element)lNodesList2.item(0)).setTextContent(
	    			    	        CKeysSettingsDialogGui.getInstance().
	    			    	        getLightSwitchTextField().getText());
	    			    	break;
	    			}
	    		}
	    	}
        }
	    catch (Exception lException)
	    {
	    	lException.printStackTrace();
	    }
	    
	    // Ecriture du fichier sur le disque.
        try
        {
            // Préparation du document pour l'écriture sur le disque.
            Source lSource = new DOMSource(mDocument);
            Result lResult = new StreamResult(new File(mKeysSettingsXMLFileName));
            Transformer lXFormer = TransformerFactory.newInstance().newTransformer();
            
            
            
            lXFormer.transform(lSource, lResult);
            
            
        }
        catch (TransformerException lException)
        {
        	lException.printStackTrace();
        }
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMoveForwardKey()
	{
		return mMoveForwardKey;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMoveBackwardKey()
	{
		return mMoveBackwardKey;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMoveRightKey()
	{
		return mMoveRightKey;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMoveLeftKey()
	{
		return mMoveLeftKey;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNextWeaponKey()
	{
		return mNextWeaponKey;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPreviousWeaponKey()
	{
		return mPreviousWeaponKey;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getReloadKey()
	{
		return mReloadKey;
	}
	
	public String getLightSwitchKey()
	{
		return mLightSwitchKey;
	}
	
	/**
	 * 
	 * @return
	 */
	public KeyTrigger getMoveForwardKeyTrigger()
	{
		KeyTrigger lResult = getKeyTrigger(mMoveForwardKey);
		return lResult;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public KeyTrigger getMoveBackwardKeyTrigger()
	{
		KeyTrigger lResult = getKeyTrigger(mMoveBackwardKey);
		return lResult;
	}
	
	/**
	 * 
	 * @return
	 */
	public KeyTrigger getMoveRightKeyTrigger()
	{
		KeyTrigger lResult = getKeyTrigger(mMoveRightKey);
		return lResult;
	}
	
	/**
	 * 
	 * @return
	 */
	public KeyTrigger getMoveLeftKeyTrigger()
	{
		KeyTrigger lResult = getKeyTrigger(mMoveLeftKey);
		return lResult;
	}
	
	/**
	 * 
	 * @return
	 */
	public KeyTrigger getNextWeaponKeyTrigger()
	{
		KeyTrigger lResult = getKeyTrigger(mNextWeaponKey);
		return lResult;
	}
	
	/**
	 * 
	 * @return
	 */
	public KeyTrigger getPreviousWeaponKeyTrigger()
	{
		KeyTrigger lResult = getKeyTrigger(mPreviousWeaponKey);
		return lResult;
	}
	
	/**
	 * 
	 * @return
	 */
	public KeyTrigger getReloadKeyTrigger()
	{
		KeyTrigger lResult = getKeyTrigger(mReloadKey);
		return lResult;
	}
	
	public KeyTrigger getLightSwitchKeyTrigger()
	{
		KeyTrigger lResult = getKeyTrigger(mLightSwitchKey);
		return lResult;
	}
	/**
	 * 
	 * @param pKey
	 * @return
	 */
	private KeyTrigger getKeyTrigger(String pKey)
	{
		KeyTrigger lResult = null;
		
		/*Class<?> lResult = null;

		try {
			lResult = Class.forName("KeyInput.KEY_" + pKey.toUpperCase());
		}
		catch (ClassNotFoundException lException)
		{
			lException.printStackTrace();
		}*/
		
		
		switch (pKey.toUpperCase())
		{
		   case "A" : lResult = new KeyTrigger(KeyInput.KEY_A); break;
		   case "B" : lResult = new KeyTrigger(KeyInput.KEY_B); break;
		   case "C" : lResult = new KeyTrigger(KeyInput.KEY_C); break;
		   case "D" : lResult = new KeyTrigger(KeyInput.KEY_D); break;
		   case "E" : lResult = new KeyTrigger(KeyInput.KEY_E); break;
		   case "F" : lResult = new KeyTrigger(KeyInput.KEY_F); break;
		   case "G" : lResult = new KeyTrigger(KeyInput.KEY_G); break;
		   case "H" : lResult = new KeyTrigger(KeyInput.KEY_H); break;
		   case "I" : lResult = new KeyTrigger(KeyInput.KEY_I); break;
		   case "J" : lResult = new KeyTrigger(KeyInput.KEY_J); break;
		   case "K" : lResult = new KeyTrigger(KeyInput.KEY_K); break;
		   case "L" : lResult = new KeyTrigger(KeyInput.KEY_L); break;
		   case "M" : lResult = new KeyTrigger(KeyInput.KEY_M); break;
		   case "N" : lResult = new KeyTrigger(KeyInput.KEY_N); break;
		   case "O" : lResult = new KeyTrigger(KeyInput.KEY_O); break;
		   case "P" : lResult = new KeyTrigger(KeyInput.KEY_P); break;
		   case "Q" : lResult = new KeyTrigger(KeyInput.KEY_Q); break;
		   case "R" : lResult = new KeyTrigger(KeyInput.KEY_R); break;
		   case "S" : lResult = new KeyTrigger(KeyInput.KEY_S); break;
		   case "T" : lResult = new KeyTrigger(KeyInput.KEY_T); break;
		   case "U" : lResult = new KeyTrigger(KeyInput.KEY_U); break;
		   case "V" : lResult = new KeyTrigger(KeyInput.KEY_V); break;
		   case "W" : lResult = new KeyTrigger(KeyInput.KEY_W); break;
		   case "X" : lResult = new KeyTrigger(KeyInput.KEY_X); break;
		   case "Y" : lResult = new KeyTrigger(KeyInput.KEY_Y); break;
		   case "Z" : lResult = new KeyTrigger(KeyInput.KEY_Z); break;
		   
		}
		return lResult;
	}
}
