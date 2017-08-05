package gui.dialogs;

//import gui.CMainFrame;
import i18n.CResourceBundle;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class CCreateNameDialogGui extends CDialogGui{
	
	    private static CCreateNameDialogGui sInstance   = null;

	    private JTextField     mNamePlayerTextField     = null;
	    private JButton        mOkButton                = null;
	    private JButton        mCancelButton            = null;
	    
	    
		public static CCreateNameDialogGui getInstance()
		{
			if (sInstance == null)
			{
				sInstance = new CCreateNameDialogGui();
			}
			sInstance.setLocationRelativeTo(sInstance.getParent());
			return sInstance;
		}
		
		private CCreateNameDialogGui() 
		{
			super();
			buildGui();
		}
		
		public void init()
		{
			getNamePlayerTextField().setText("");
			getNamePlayerTextField().requestFocus();
		}
		
		public void buildGui()
		{
			GridLayout lLayout = new GridLayout(2, 2);
			
			
			setTitle(CResourceBundle.getInstance().getString(
					"dialogs.createname.title")); // TODO ressource bundle
			setResizable(false);

			lLayout.setHgap(2);
			lLayout.setVgap(2);
			getContentPane().setLayout(lLayout);
			
			add(new JLabel(CResourceBundle.getInstance().getString(
					"dialogs.createname.name"))); // TODO ressource bundle
			add(getNamePlayerTextField());


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
		public JTextField getNamePlayerTextField()
		{
			if (mNamePlayerTextField == null)
			{
				mNamePlayerTextField = new JTextField(10);
			}
			return mNamePlayerTextField;
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
						"dialogs.createname.OK")); // TODO resource bundle
				mOkButton.addActionListener(
						new ActionListener()
						{
							@Override
						    public void actionPerformed(ActionEvent pEvent)
						    {
							    	String lErrorMessage = isParametersOk();
									
									
									if (lErrorMessage.equals("") == true)
									{

										// Dissimulation de la boîte de dialogue.
										CCreateNameDialogGui.this.setVisible(false);
										System.out.println(
												">"                            +
												mNamePlayerTextField.getText() +
												"<");
								    	// OU : CCreateServerDialogGui.getInstance().setVisible(false);

									}
									else
									{
										JOptionPane.showMessageDialog(
												CCreateNameDialogGui.getInstance(),
												lErrorMessage,
												CResourceBundle.getInstance().getString(
														"dialogs.createname.errorMessageDialogTitle"),
												JOptionPane.ERROR_MESSAGE);
										CCreateNameDialogGui.this.setVisible(true);
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
						"dialogs.createname.Cancel")); // TODO resource bundle
				mCancelButton.addActionListener(
						new ActionListener()
						{
							@Override
						    public void actionPerformed(ActionEvent pEvent)
						    {
						    	CCreateNameDialogGui.this.setVisible(false);
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
			if (CCreateNameDialogGui.getInstance().getNamePlayerTextField().
					getText().equals("") == true)
			{
				lErrorMessage += CResourceBundle.getInstance().getString(
						"dialogs.createname.errorMessages.playerNameError")
						      + "\n";
			}

			
			return lErrorMessage;
		}

}
