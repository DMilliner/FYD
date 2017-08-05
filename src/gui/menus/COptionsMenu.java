package gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import gui.dialogs.CCreateServerDialogGui;
import gui.dialogs.CKeysSettingsDialogGui;
import i18n.CResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class COptionsMenu extends JMenu
{
	private static COptionsMenu sInstance = null;
	
	private JMenuItem mVideoMenuItem       = null;
	private JMenuItem mBrightnessMenuItem  = null;
	private JMenuItem mSoundMenuItem       = null;
	private JMenuItem mKeysSettingMenuItem = null;


	/**
	 * 
	 * @return
	 */
	public static COptionsMenu getInstance()
	{
		if (sInstance == null)
		{
			sInstance = new COptionsMenu();
		}
		return sInstance;
	} // getInstance
	
	/**
	 * 
	 */
	private COptionsMenu()
	{
		super(CResourceBundle.getInstance().getString(
				"menus.optionsMenu.title"));
		setToolTipText(
				CResourceBundle.getInstance().getString(
						"menus.optionsMenu.tooltip"));
		
		
		/*
		 * this.getActionForKeyStroke(aKeyStroke)
		 * peut être utile pour echap
		 */
		getVideoMenuItem().setEnabled(false);
		getBrightnessMenuItem().setEnabled(false);
		getSoundMenuItem().setEnabled(false);
		
		add(getVideoMenuItem());
		add(getBrightnessMenuItem());
		addSeparator();
		add(getSoundMenuItem());
		add(getKeysSettinMenuItem());
	} //CoptionserMenu
	
	/**
	 * 
	 * @return
	 */
	private JMenuItem getVideoMenuItem()
	{
		if (mVideoMenuItem == null)
		{
			mVideoMenuItem = new JMenuItem(
					CResourceBundle.getInstance().getString(
							"menus.optionsMenu.video.title"));
			mVideoMenuItem.setToolTipText(
			        CResourceBundle.getInstance().getString(
			        		"menus.optionsMenu.video.tooltip"));
		}
		return mVideoMenuItem;
	} // getVideoMenuItem
	
	/**
	 * 
	 * @return
	 */
	private JMenuItem getBrightnessMenuItem()
	{
		if (mBrightnessMenuItem == null)
		{
			mBrightnessMenuItem = new JMenuItem(
					CResourceBundle.getInstance().getString(
							"menus.optionsMenu.brightness.title"));
			mBrightnessMenuItem.setToolTipText(
			        CResourceBundle.getInstance().getString(
			        		"menus.optionsMenu.brightness.tooltip"));
		}
		return mBrightnessMenuItem;
	} // getBrightnessMenuItem
	
	private JMenuItem getSoundMenuItem()
	{
		if (mSoundMenuItem == null)
		{
			mSoundMenuItem = new JMenuItem(
					CResourceBundle.getInstance().getString(
							"menus.optionsMenu.sound.title"));
			mSoundMenuItem.setToolTipText(
			        CResourceBundle.getInstance().getString(
			        		"menus.optionsMenu.sound.tooltip"));
		}
		return mSoundMenuItem;
	} // getSoundMenuItem
	
	private JMenuItem getKeysSettinMenuItem()
	{
		if (mKeysSettingMenuItem == null)
		{
			mKeysSettingMenuItem = new JMenuItem(
					CResourceBundle.getInstance().getString(
							"menus.optionsMenu.keysSetting.title"));
			mKeysSettingMenuItem.setToolTipText(
			        CResourceBundle.getInstance().getString(
			        		"menus.optionsMenu.keysSetting.tooltip"));
			
			mKeysSettingMenuItem.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent pEvent)
						{
							CKeysSettingsDialogGui.getInstance().init();
							CKeysSettingsDialogGui.getInstance().setVisible(true);
						}
					});
		}
		return mKeysSettingMenuItem;
	} // getKeysSettinMenuItem
	
	
} // COptionsMenu
