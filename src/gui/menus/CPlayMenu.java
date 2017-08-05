package gui.menus;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

import gui.CMainFrame;
import gui.dialogs.CChangeNameDialogGui;
import gui.dialogs.CCreateNameDialogGui;
import gui.dialogs.CCreateServerDialogGui;
import gui.dialogs.CJoinServerDialogGui;
import gui.dialogs.CKeysSettingsDialogGui;
import i18n.CResourceBundle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class CPlayMenu extends JMenu
{
	private static CPlayMenu sInstance = null;
	
	private JMenuItem mCreateNameMenuItem   = null;
	private JMenuItem mChangeNameMenuItem   = null;
	private JMenuItem mCreateServerMenuItem = null;
	private JMenuItem mJoinServerMenuItem   = null;	
	private JMenuItem mResumeGameMenuItem   = null;	
	private JMenuItem mQuitMenuItem         = null;
	
	/**
	 * 
	 * @return
	 */
	public static CPlayMenu getInstance()
	{
		if (sInstance == null)
		{
			sInstance = new CPlayMenu();
		}
		return sInstance;
	} // getInstance
	
	/**
	 * 
	 */
	private CPlayMenu()
	{
		super(CResourceBundle.getInstance().getString(
				"menus.playMenu.title"));
		setToolTipText(
				CResourceBundle.getInstance().getString(
						"menus.playMenu.tooltip"));
		
		add(getCreateNameMenuItem());
		add(getChangeNameMenuItem());
		
		addSeparator();
		
		add(getCreateServerMenuItem());
		add(getJoinServerMenuItem());
		
		addSeparator();
		
		add(getResumeGameMenuItem());
		
		addSeparator();
		
		add(getQuitMenuItem());
	} //CPlayerMenu
	
	/**
	 * 
	 * @return
	 */
	private JMenuItem getCreateServerMenuItem()
	{
		if (mCreateServerMenuItem == null)
		{
			mCreateServerMenuItem = new JMenuItem(
					CResourceBundle.getInstance().getString(
							"menus.playMenu.createServer.title"));
		
			mCreateServerMenuItem.setToolTipText(
			        CResourceBundle.getInstance().getString(
			        		"menus.playMenu.createServer.tooltip"));
			
			
			mCreateServerMenuItem.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent pEvent)
						{
							if (JOptionPane.showConfirmDialog(
									CMainFrame.getInstance(),
									CResourceBundle.getInstance().getString(
											"menus.playMenu.confirmkeys.message"),
									CResourceBundle.getInstance().getString(
											"menus.playMenu.confirmkeys.windowtitle"),
									JOptionPane.YES_NO_CANCEL_OPTION
								)== JOptionPane.YES_OPTION)
							{
								CCreateServerDialogGui.getInstance().init();
								CCreateServerDialogGui.getInstance().setVisible(true);
							}
							else if (JOptionPane.showConfirmDialog(
									CMainFrame.getInstance(),
									CResourceBundle.getInstance().getString(
											"menus.playMenu.confirmkeys.message"),
									CResourceBundle.getInstance().getString(
											"menus.playMenu.confirmkeys.windowtitle"),
									JOptionPane.YES_NO_CANCEL_OPTION
								)== JOptionPane.NO_OPTION)
							{
								CKeysSettingsDialogGui.getInstance().init();
								CKeysSettingsDialogGui.getInstance().setVisible(true);
							}
							else
							{
								CKeysSettingsDialogGui.getInstance().setVisible(false);
								CCreateServerDialogGui.getInstance().setVisible(false);
							}
						}
					});
		}
		return mCreateServerMenuItem;
	} // getCreateNameMenuItem
	
	/**
	 * 
	 * @return
	 */
	private JMenuItem getJoinServerMenuItem()
	{
		if (mJoinServerMenuItem == null)
		{
			mJoinServerMenuItem = new JMenuItem(
					CResourceBundle.getInstance().getString(
							"menus.playMenu.joinServer.title"));
			mJoinServerMenuItem.setToolTipText(
			        CResourceBundle.getInstance().getString(
			        		"menus.playMenu.joinServer.tooltip"));
			mJoinServerMenuItem.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent pEvent)
						{
							if (JOptionPane.showConfirmDialog(
									CMainFrame.getInstance(),
									CResourceBundle.getInstance().getString(
											"menus.playMenu.confirmkeys.message"),
									CResourceBundle.getInstance().getString(
											"menus.playMenu.confirmkeys.windowtitle"),
									JOptionPane.YES_NO_CANCEL_OPTION
								)== JOptionPane.YES_OPTION)
							{
								CJoinServerDialogGui.getInstance().init();
								CJoinServerDialogGui.getInstance().setVisible(true);
							}
							else if (JOptionPane.showConfirmDialog(
									CMainFrame.getInstance(),
									CResourceBundle.getInstance().getString(
											"menus.playMenu.confirmkeys.message"),
									CResourceBundle.getInstance().getString(
											"menus.playMenu.confirmkeys.windowtitle"),
									JOptionPane.YES_NO_CANCEL_OPTION
								)== JOptionPane.NO_OPTION)
							{
								CKeysSettingsDialogGui.getInstance().init();
								CKeysSettingsDialogGui.getInstance().setVisible(true);
							}
							else
							{
								CKeysSettingsDialogGui.getInstance().setVisible(false);
								CJoinServerDialogGui.getInstance().setVisible(false);
							}
						}
					});
		}
		return mJoinServerMenuItem;
	} // getModifyNameMenuItem
	
	private JMenuItem getCreateNameMenuItem()
	{
		if (mCreateNameMenuItem == null)
		{
			mCreateNameMenuItem = new JMenuItem(
					CResourceBundle.getInstance().getString(
							"menus.playMenu.createName.title"));
			
			mCreateNameMenuItem.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent pEvent)
						{
							CCreateNameDialogGui.getInstance().init();
							CCreateNameDialogGui.getInstance().setVisible(true);
						}
					});
		}
		return mCreateNameMenuItem;
	} // getModifyNameMenuItem
	
	private JMenuItem getChangeNameMenuItem()
	{
		if (mChangeNameMenuItem == null)
		{
			mChangeNameMenuItem = new JMenuItem(
					CResourceBundle.getInstance().getString(
							"menus.playMenu.changeName.title"));
			mChangeNameMenuItem.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent pEvent)
						{
							CChangeNameDialogGui.getInstance().init();
							CChangeNameDialogGui.getInstance().setVisible(true);
						}
					});
		}
		return mChangeNameMenuItem;
	} // getModifyNameMenuItem
	
	
	private JMenuItem getResumeGameMenuItem()
	{
		if (mResumeGameMenuItem == null)
		{
			mResumeGameMenuItem = new JMenuItem(
					CResourceBundle.getInstance().getString(
							"menus.playMenu.resumeGame.title"));
			mResumeGameMenuItem.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent pEvent)
						{
					    	//CMainFrame.getInstance().mCanvas3D.setVisible(true);
							//CModule3D.getInstance().gainFocus();
							//CModule3D.getInstance().restart();
							
						}
					});
		}
		return mResumeGameMenuItem;
	} // getResumeGameMenuItem
	
	private JMenuItem getQuitMenuItem()
	{
		if (mQuitMenuItem == null)
		{
			mQuitMenuItem = new JMenuItem(
					CResourceBundle.getInstance().getString(
							"menus.playMenu.quit.title"));
			mQuitMenuItem.setToolTipText(
			        CResourceBundle.getInstance().getString(
			        		"menus.playMenu.quit.tooltip"));
			
			mQuitMenuItem.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent pEvent)
						{
							if (JOptionPane.showConfirmDialog(
									CMainFrame.getInstance(),
									CResourceBundle.getInstance().getString(
											"menus.playMenu.quit.message"),
									CResourceBundle.getInstance().getString(
											"menus.playMenu.quit.windowtitle"),
									JOptionPane.YES_NO_OPTION
								)== JOptionPane.YES_OPTION)
							{
							    System.exit(0);
							}
						}
					}
					
				);
		}
		return mQuitMenuItem;
	} // getModifyNameMenuItem
} // CPlayerMenu
