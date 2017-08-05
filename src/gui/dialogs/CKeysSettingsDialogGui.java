package gui.dialogs;

import exceptions.CKeysSettingsXMLFileNameException;
import gui.CMainFrame;
import gui.CRestartApp;
import gui.module3d.CModule3D;
import i18n.CResourceBundle;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import utils.CKeysSettingsManager;

@SuppressWarnings("serial")
public class CKeysSettingsDialogGui extends CDialogGui{
	
	private static CKeysSettingsDialogGui sInstance = null;

    private JTextField     mMoveForwardTextField    = null;
    private JTextField     mMoveBackwardTextField   = null;
    private JTextField     mMoveRightTextField      = null;
    private JTextField     mMoveLeftTextField       = null;
    private JTextField     mJumpTextField           = null;
    private JTextField	   mLightSwitchTextField    = null;
    private JTextField     mNextWeaponTextField     = null;
    private JTextField     mPreviousWeaponTextField = null;
    private JTextField     mPauseTextField          = null;
    private JTextField     mReloadTextField         = null;
    private JTextField     mLeftClickTextField      = null;
    
    
    private JButton        mOkButton                = null;
    private JButton        mCancelButton            = null;

    
    /**
     * 
     * @return
     */
    public static CKeysSettingsDialogGui getInstance()
	{
		if (sInstance == null)
		{
			sInstance = new CKeysSettingsDialogGui();
		}
		
		sInstance.setLocationRelativeTo(sInstance.getParent());
		return sInstance;
		

	}
    
    
    private CKeysSettingsDialogGui() 
		{
			super();
			buildGui();
		}
    
    /**
     */
	public void init()
	{
		setKeysSettings();
	}
    
	private void setKeysSettings()
	{
		CKeysSettingsManager lKM = CKeysSettingsManager.getInstance();
		
		
		try
		{
			lKM.readKeysSettingsXMLFile();
		}
		catch (CKeysSettingsXMLFileNameException lException)
		{
			lException.printStackTrace();
		}
		
		getMoveForwardTextField().setText(lKM.getMoveForwardKey());
		getMoveBackwardTextField().setText(lKM.getMoveBackwardKey());
		getMoveRightTextField().setText(lKM.getMoveRightKey());
		getMoveLeftTextField().setText(lKM.getMoveLeftKey());
		getReloadTextField().setText(lKM.getReloadKey());
		getLightSwitchTextField().setText(lKM.getLightSwitchKey());
		
		getNextWeaponTextField().setText("Mouse Down");
		getPreviousWeaponTextField().setText("Mouse Up");
		getJumpTextField().setText("space");
		getPauseTextField().setText("escape");
		getLeftClickTextField().setText("mouse left");
	}
	
    public void buildGui()
	{
		GridLayout lLayout = new GridLayout(12, 2);
		setResizable(false);
		
		setTitle(CResourceBundle.getInstance().getString(
				"dialogs.keysSetting.title"));

		getContentPane().setLayout(lLayout);
		
		add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.keysSetting.forward"))); 
		add(getMoveForwardTextField());

		add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.keysSetting.backward")));
		add(getMoveBackwardTextField());
		
		add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.keysSetting.rightmove")));
		add(getMoveRightTextField());
		
		add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.keysSetting.leftmove")));
		add(getMoveLeftTextField());

		add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.keysSetting.reload")));
		add(getReloadTextField());
		
		add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.keysSetting.jump")));
		add(getJumpTextField());
		
		add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.keysSetting.lightSwitch")));
		add(getLightSwitchTextField());

		add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.keysSetting.nextWeapon")));
		add(getNextWeaponTextField());
		
		add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.keysSetting.previousWeapon")));
		add(getPreviousWeaponTextField());
		
		add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.keysSetting.escape")));
		add(getPauseTextField());
		
		add(new JLabel(CResourceBundle.getInstance().getString(
				"dialogs.keysSetting.leftclick")));
		add(getLeftClickTextField());
		
		add(getOkButton());
		add(getCancelButton());
		
		pack();
		setLocationRelativeTo(getParent());
	}
    
	public JTextField getMoveForwardTextField()
	{
		if (mMoveForwardTextField == null)
		{
			mMoveForwardTextField = new JTextField(10);
		}
		return mMoveForwardTextField;
	}
	
	public JTextField getMoveBackwardTextField()
	{
		if (mMoveBackwardTextField == null)
		{
			mMoveBackwardTextField = new JTextField(10);
		}
		return mMoveBackwardTextField;
	}
	
	public JTextField getMoveRightTextField()
	{
		if (mMoveRightTextField == null)
		{
			mMoveRightTextField = new JTextField(10);
			
		}
		return mMoveRightTextField;
	}
	
	public JTextField getMoveLeftTextField()
	{
		if (mMoveLeftTextField == null)
		{
			mMoveLeftTextField = new JTextField(10);

		}
		return mMoveLeftTextField;
	}
	
	public JTextField getLightSwitchTextField()
	{
		if (mLightSwitchTextField == null)
		{
			mLightSwitchTextField = new JTextField(10);
		}
		return mLightSwitchTextField;
	}
	
	public JTextField getJumpTextField()
	{
		if (mJumpTextField == null)
		{
			mJumpTextField = new JTextField(10);
			mJumpTextField.setEnabled(false);
		}
		return mJumpTextField;
	}
	
	public JTextField getNextWeaponTextField()
	{
		if (mNextWeaponTextField == null)
		{
			mNextWeaponTextField = new JTextField(10);
			mNextWeaponTextField.setEnabled(false);
		}
		return mNextWeaponTextField;
	}
	
	public JTextField getPreviousWeaponTextField()
	{
		if (mPreviousWeaponTextField == null)
		{
			mPreviousWeaponTextField = new JTextField(10);
			mPreviousWeaponTextField.setEnabled(false);
		}
		return mPreviousWeaponTextField;
	}
	
	public JTextField getReloadTextField()
	{
		if (mReloadTextField == null)
		{
			mReloadTextField = new JTextField(10);
		}
		return mReloadTextField;
	}
	
	public JTextField getLeftClickTextField()
	{
		if (mLeftClickTextField == null)
		{
			mLeftClickTextField = new JTextField(10);
			mLeftClickTextField.setEnabled(false);
		}
		return mLeftClickTextField;
	}
	
	public JTextField getPauseTextField()
	{
		if (mPauseTextField == null)
		{
			mPauseTextField = new JTextField(10);
			mPauseTextField.setEnabled(false);
		}
		return mPauseTextField;
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
					"dialogs.keysSetting.OK")); // TODO resource bundle
			mOkButton.addActionListener(
					new ActionListener()
					{
						@Override
					    public void actionPerformed(ActionEvent pEvent)
					    {
							// Sauvegarde des nouvelles affectations.
							CKeysSettingsManager.getInstance()
							        .writeKeysSettingsXMLFile();
							
							// Etablir les affectations dans le module 3D.
							CModule3D.getInstance().setUpKeys();
							
							CKeysSettingsDialogGui.this.setVisible(false);
							//CRestartApp.getInstance().restartApplication();
							
							CMainFrame.getInstance().restartModule3D();							
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
					"dialogs.keysSetting.Cancel")); // TODO resource bundle
			mCancelButton.addActionListener(
					new ActionListener()
					{
						@Override
					    public void actionPerformed(ActionEvent pEvent)
					    {
					    	CKeysSettingsDialogGui.this.setVisible(false);
					    }
					});
		}
		return mCancelButton;
	}
}
