package gui.dialogs;

import gui.CMainFrame;
import i18n.CResourceBundle;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
//import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import network.CNetwork;
import network.client.CClient;
import network.server.CServer;
//import javax.swing.JPasswordField;


@SuppressWarnings("serial")
public class CCreateServerDialogGui extends CDialogGui
{
	private static final String DEFAULT_SERVER_NAME = CResourceBundle.getInstance().getString(
			"dialogs.createserver.defaultname");
	private static final String DEFAULT_SERVER_PORT = "8087";
	
    private static CCreateServerDialogGui sInstance = null;

    private JTextField     mNameTextField     = null;
    private JTextField     mPortTextField     = null;
    //private JPasswordField mPasswordTextField = null;
    private JButton        mOkButton          = null;
    private JButton        mCancelButton      = null;
    private JLabel         mIpAddrLabel       = null;
    
    
	public static CCreateServerDialogGui getInstance()
	{
		if (sInstance == null)
		{
			sInstance = new CCreateServerDialogGui();
			sInstance.getNameTextField().requestFocus();
		}
		sInstance.setLocationRelativeTo(sInstance.getParent());
		return sInstance;
	}
	
	private CCreateServerDialogGui() 
	{
		super();
		buildGui();
	}
	
	public void init()
	{
		getNameTextField().setText(DEFAULT_SERVER_NAME);
		getPortTextField().setText(DEFAULT_SERVER_PORT);
	//	getPasswordServerTextField().setText("");
		getNameTextField().requestFocus();
	}
	
	public void buildGui()
	{
		GridLayout lLayout = new GridLayout(4, 2);
		
		
		setTitle(CResourceBundle.getInstance().getString(
				"dialogs.createserver.title"));
		setResizable(false);

		lLayout.setHgap(4);
		lLayout.setVgap(4);
		getContentPane().setLayout(lLayout);
		
		getContentPane().add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.createserver.name")));
		getContentPane().add(getNameTextField());

		getContentPane().add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.createserver.port")));
		getContentPane().add(getPortTextField());
/*
		getContentPane().add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.createserver.password")));
		getContentPane().add(getPasswordServerTextField());
*/
		getContentPane().add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.createserver.ipaddr")));
		getContentPane().add(getIpAddrLabel());
		
		getContentPane().add(getOkButton());
		getContentPane().add(getCancelButton());
		
		pack();
		setLocationRelativeTo(getParent());
	}

	/**
	 * Accesseur du champ de texte "Nom".
	 * 
	 * @return le champ de texte "Nom".
	 * */
	private JTextField getNameTextField()
	{
		if (mNameTextField == null)
		{
			mNameTextField = new JTextField(10);
		}
		return mNameTextField;
	}
	
	/**
	 * Accesseur du champ de texte "Port".
	 * 
	 * @return le champ de texte "Port".
	 * */
	public JTextField getPortTextField()
	{
		if (mPortTextField == null)
		{
			mPortTextField = new JTextField(10);
		}
		return mPortTextField;
	}

	/**
	 * Accesseur du champ de texte "Mot de passe".
	 * 
	 * @return le champ de texte "Mot de passe".
	 * *//*
	public JPasswordField getPasswordServerTextField()
	{
		if (mPasswordTextField == null)
		{
			mPasswordTextField = new JPasswordField(10);
		}
		return mPasswordTextField;
	}
	*/
	/**
	 * Accesseur du champ de texte "Adresse IP".
	 * 
	 * @return le champ de texte "Adresse IP".
	 */
	public JLabel getIpAddrLabel()
	{
		if (mIpAddrLabel == null)
		{
			String lIpAddrAsString = "";
			

			try
			{
			     lIpAddrAsString = (InetAddress.getLocalHost()).getHostAddress().toString();
			}
			catch (Exception lException)
			{
				lException.printStackTrace();
			    lIpAddrAsString = CNetwork.sLocalhost;
				
			}
			mIpAddrLabel = new JLabel(lIpAddrAsString);
		}
		return mIpAddrLabel;
	}
	
	/**
	 * Accesseur du bouton "Ok".
	 * 
	 * @return le bouton "Ok".
	 * */
	private JButton getOkButton()
	{
		if (mOkButton == null)
		{
			mOkButton = new JButton(CResourceBundle.getInstance().getString(
					"dialogs.createserver.go"));
			mOkButton.addActionListener(
					new ActionListener()
					{
						@Override
					    public void actionPerformed(ActionEvent pEvent)
					    {
							String lErrorMessage = isParametersOk();
							
							
							if (lErrorMessage.equals("") == true)
							{
						    	
						    	// Rajout du module 3D dans l'IHM.
						    	CMainFrame.getInstance().launchModule3D();
						    	
								// Dissimulation de la bo�te de dialogue.
						    	dispose();
						    	
								// Lancement du serveur.
								CServer.getInstance(
										getNameTextField().getText(),
										(new Integer(getPortTextField().getText())).intValue());
								
								// Cr�ation du client.
								CClient.getInstance(
										CNetwork.sLocalhost,
										(new Integer(getPortTextField().getText())).intValue());
						    	
						    	// Flag permettant au module 3D de savoir que
						    	// la communication est active.
						    	CNetwork.sLaunched = true;
						    	
							}
							else
							{
								// TODO : afficher la bonne fen�tre d'erreur.
								JOptionPane.showMessageDialog(
										CCreateServerDialogGui.getInstance(),
										lErrorMessage,
										CResourceBundle.getInstance().getString(
												"dialogs.createserver.errorMessageDialogTitle"),
										JOptionPane.ERROR_MESSAGE);
							}
					    }
					});
		}
		return mOkButton;
	}

	/**
	 * Accesseur du bouton "Annuler".
	 * 
	 * @return le bouton "Annuler".
	 * */
	private JButton getCancelButton()
	{
		if (mCancelButton == null)
		{
			mCancelButton = new JButton(CResourceBundle.getInstance().getString(
					"dialogs.createserver.cancel")); // TODO resource bundle
			mCancelButton.addActionListener(
					new ActionListener()
					{
						@Override
					    public void actionPerformed(ActionEvent pEvent)
					    {
					    	CCreateServerDialogGui.this.setVisible(false);
					    	// OU : CCreateServerDialogGui.getInstance().setVisible(false);
					    }
					});
		}
		return mCancelButton;
	}
	
	/**
	 * Analyse les valeurs saisies dans les champs de la bo�te de dialogue.
	 * 
	 * @return
	 */
	private String isParametersOk()
	{
		String lErrorMessage = "";
		int    lPort         = -1;
		
		
		// Le nom du joueur doit avoir d�j� �t� saisi.
		if (CCreateNameDialogGui.getInstance().getNamePlayerTextField().
				getText().equals("") == true)
		{
			lErrorMessage += CResourceBundle.getInstance().getString(
					"dialogs.createserver.errorMessages.playerNameError")
					      + "\n";
		}
		
		// Analyse du nom du serveur.
		if (getNameTextField().getText().equals("") == true)
		{
			lErrorMessage += CResourceBundle.getInstance().getString(
					"dialogs.createserver.errorMessages.serverNameError")
					      + "\n";
		}
		
		// Analyse du port.
		try
		{
		    lPort = new Integer(getPortTextField().getText());
		}
		catch (Exception lException)
		{
			lErrorMessage += CResourceBundle.getInstance().getString(
					"dialogs.createserver.errorMessages.serverPortNotSpecifiedError")
		                  + "\n";
		}
		if ( (lPort <= 0) || (lPort > 65535) )
		{
			lErrorMessage += CResourceBundle.getInstance().getString(
					"dialogs.createserver.errorMessages.serverPortError")
		                  + "\n";
		}
		
		// Analyse du mot de passe.
		/*
		if (getPasswordServerTextField().getText().equals("") == true)
		{
			lErrorMessage += CResourceBundle.getInstance().getString(
					"dialogs.createserver.errorMessages.serverPasswordError");
		}
		*/
		return lErrorMessage;
	}
}
