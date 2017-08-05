package gui.dialogs;

import i18n.CResourceBundle;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CChangeNameDialogGui extends CDialogGui{
	
	    private static CChangeNameDialogGui sInstance   = null;

	    private JTextField     mOldNamePlayerTextField     = null;
	    private JTextField     mNewNamePlayerTextField     = null;
	    private JButton        mOkButton                = null;
	    private JButton        mCancelButton            = null;
	    
	    
		public static CChangeNameDialogGui getInstance()
		{
			if (sInstance == null)
			{
				sInstance = new CChangeNameDialogGui();
			}
			sInstance.setLocationRelativeTo(sInstance.getParent());
			return sInstance;
		}
		
		private CChangeNameDialogGui() 
		{
			super();
			buildGui();
		}
		
		public void init()
		{
			getOldNamePlayerTextField().setText(CCreateNameDialogGui.getInstance().getNamePlayerTextField().getText());
			getOldNamePlayerTextField().setEnabled(false);
			getNewNamePlayerTextField().setText("");
			getNewNamePlayerTextField().requestFocus();
		}
		
		public void buildGui()
		{
			GridLayout lLayout = new GridLayout(3, 2);
			setResizable(false);
			
			setTitle(CResourceBundle.getInstance().getString(
					"dialogs.changename.title")); // TODO ressource bundle
			
			lLayout.setHgap(2);
			lLayout.setVgap(2);
			getContentPane().setLayout(lLayout);
			
			add(new JLabel(CResourceBundle.getInstance().getString(
					"dialogs.changename.oldname"))); // TODO ressource bundle
			add(getOldNamePlayerTextField());

			add(new JLabel(CResourceBundle.getInstance().getString(
					"dialogs.changename.newname"))); // TODO ressource bundle
			add(getNewNamePlayerTextField());

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
		public JTextField getOldNamePlayerTextField()
		{
			if (mOldNamePlayerTextField == null)
			{
				mOldNamePlayerTextField = new JTextField(10);
			}
			return mOldNamePlayerTextField;
		}
		
		/**
		 * Accesseur du champ de texte "Nom".
		 * 
		 * @return le champ de texte "Nom".
		 * */
		public JTextField getNewNamePlayerTextField()
		{
			if (mNewNamePlayerTextField == null)
			{
				mNewNamePlayerTextField = new JTextField(10);
			}
			return mNewNamePlayerTextField;
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
						"dialogs.changename.OK")); // TODO resource bundle
				mOkButton.addActionListener(
						new ActionListener()
						{
							@Override
						    public void actionPerformed(ActionEvent pEvent)
						    {
						    	

						    	//CChangeNameDialogGui.this.setVisible(false);
						    	// OU : CChangeNameDialogGui.getInstance().setVisible(false);
						    	
						    	String lErrorMessage = isParametersOk();
								
								
								if (lErrorMessage.equals("") == true)
								{

									// Dissimulation de la boîte de dialogue.
									CChangeNameDialogGui.this.setVisible(false);
									System.out.println(
											">"                            +
											mNewNamePlayerTextField.getText() +
											"<");
							    	// OU : CChangeNameDialogGui.getInstance().setVisible(false);

								}
								else
								{
									JOptionPane.showMessageDialog(
											CChangeNameDialogGui.getInstance(),
											lErrorMessage,
											CResourceBundle.getInstance().getString(
													"dialogs.changename.errorMessageDialogTitle"),
											JOptionPane.ERROR_MESSAGE);
									CChangeNameDialogGui.this.setVisible(true);
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
						"dialogs.changename.Cancel")); // TODO resource bundle
				mCancelButton.addActionListener(
						new ActionListener()
						{
							@Override
						    public void actionPerformed(ActionEvent pEvent)
						    {
						    	CChangeNameDialogGui.this.setVisible(false);
						    	// OU : CCreateServerDialogGui.getInstance().setVisible(false);
						    }
						});
			}
			return mCancelButton;
		}
	
		private String isParametersOk()
		{
			String lErrorMessage = "";
			
			
			// Le nom du joueur doit avoir déjà été saisi.
			if (CChangeNameDialogGui.getInstance().getOldNamePlayerTextField().
					getText().equals("") == true)
			{
				lErrorMessage += CResourceBundle.getInstance().getString(
						"dialogs.changename.errorMessages.oldplayerNameError")
						      + "\n";
			}
			
			if (CChangeNameDialogGui.getInstance().getNewNamePlayerTextField().
					getText().equals("") == true)
			{
				lErrorMessage += CResourceBundle.getInstance().getString(
						"dialogs.changename.errorMessages.playerNameError")
						      + "\n";
			}
			

			
			return lErrorMessage;
		}
		
}
