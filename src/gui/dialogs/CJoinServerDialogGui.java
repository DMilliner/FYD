package gui.dialogs;

import exceptions.network.NoClientCreatedException;
import gui.CMainFrame;
import i18n.CResourceBundle;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import network.CNetwork;
import network.client.CClient;
//import java.awt.HeadlessException;
//import network.messagingService.service.CServerPasswordRequestMessage;
//import network.server.CServer;

@SuppressWarnings("serial")
public class CJoinServerDialogGui extends CDialogGui{
	
	    private static CJoinServerDialogGui sInstance = null;

	    private JTextField mIpAdressTextField = null;
	    private JTextField mPortTextField     = null;
	    private JButton    mOkButton          = null;
	    private JButton    mCancelButton      = null;
	    
	    
		public static CJoinServerDialogGui getInstance()
		{
			if (sInstance == null)
			{
				sInstance = new CJoinServerDialogGui();
			}
			sInstance.setLocationRelativeTo(sInstance.getParent());
			return sInstance;
		}
		
		private CJoinServerDialogGui() 
		{
			super();
			buildGui();
		}
		
		public void init()
		{
			getIpAdressTextField().setText("");
			getIpAdressTextField().requestFocus();
			getPortTextField().setText("");
		}
		
		public void buildGui()
		{
			GridLayout lLayout = new GridLayout(3, 2);
			setResizable(false);
			
			setTitle(CResourceBundle.getInstance().getString(
					"dialogs.joinserver.title"));
			
			lLayout.setHgap(2);
			lLayout.setVgap(2);
			getContentPane().setLayout(lLayout);
			
			add(new JLabel(CResourceBundle.getInstance().getString(
					"dialogs.joinserver.ipadress")));
			add(getIpAdressTextField());

			add(new JLabel(CResourceBundle.getInstance().getString(
					"dialogs.joinserver.port")));
			add(getPortTextField());

			add(getOkButton());
			add(getCancelButton());
			
			pack();
			setLocationRelativeTo(getParent());
		}

		/**
		 * Accesseur du champ de texte "Nom".
		 * 
		 * @return le champ de texte "Nom".
		 * */
		public JTextField getIpAdressTextField()
		{
			if (mIpAdressTextField == null)
			{
				mIpAdressTextField = new JTextField(10);
			}
			return mIpAdressTextField;
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
		 * Accesseur du bouton "Ok".
		 * 
		 * @return le bouton "Ok".
		 * */
		private JButton getOkButton()
		{
			if (mOkButton == null)
			{
				mOkButton = new JButton(CResourceBundle.getInstance().getString(
						"dialogs.joinserver.OK"));
				mOkButton.addActionListener(
						new ActionListener()
						{
							@Override
						    public void actionPerformed(ActionEvent pEvent)
						    {
								try
								{
									// Création du client.
									CClient.getInstance(
											mIpAdressTextField.getText(),
											new Integer(mPortTextField.getText()));

									if (CClient.getInstance() != null)
									{
										String lErrorMessage = isParametersOk();

										
										if (lErrorMessage.equals("") == true)
										{
											// Dissimulation de la boîte de dialogue.
									    	setVisible(false);
									    	
									    	// Flag permettant au module 3D de
									    	// savoir que la communication est
									    	// active.
									    	CNetwork.sLaunched = true;
									    	
									    	// Rajout du module 3D dans l'IHM.
									    	CMainFrame.getInstance().launchModule3D();
										}
										else
										{
											JOptionPane.showMessageDialog(
													CJoinServerDialogGui.getInstance(),
													lErrorMessage,
													CResourceBundle.getInstance().getString(
															"dialogs.joinserver.errorMessageDialogTitle"),
													JOptionPane.ERROR_MESSAGE);
											CJoinServerDialogGui.this.setVisible(true);
										}
									}
								} 
								catch (NoClientCreatedException lException)
								{
									lException.printStackTrace();
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
						"dialogs.joinserver.Cancel")); // TODO resource bundle
				mCancelButton.addActionListener(
						new ActionListener()
						{
							@Override
						    public void actionPerformed(ActionEvent pEvent)
						    {
								CJoinServerDialogGui.this.setVisible(false);
						    	// OU : CCreateServerDialogGui.getInstance().setVisible(false);
						    }
						});
			}
			return mCancelButton;
		}
	    //TODO : Verifier l'existance d'un nom de joueur
		private String isParametersOk()
		{
			String lErrorMessage   = "";
			//String lServerPassword = "";
			
		/*	
			try
			{
				lServerPassword = CClient.getInstance().getServerPassword();
			}
			catch (NoClientCreatedException lException)
			{
				lException.printStackTrace();
			}			
*/
			// Le nom du joueur doit avoir déjà été saisi.
			/*if (CJoinServerDialogGui.getInstance().getPassswordTextField().
					getText().equals(lServerPassword) == false)
			{
				lErrorMessage += CResourceBundle.getInstance().getString(
						"dialogs.joinserver.errorMessages.passwordMismatchError")
						      + "\n";
			}*/
			
			return lErrorMessage;
		}
		
}
