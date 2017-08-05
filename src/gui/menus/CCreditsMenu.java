package gui.menus;
import gui.CMainFrame;
import i18n.CResourceBundle;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class CCreditsMenu extends JMenu implements MouseListener
{
	private static CCreditsMenu sInstance = null;
	
	/**
	 * 
	 * @return
	 */
	public static CCreditsMenu getInstance()
	{
		if (sInstance == null)
		{
			sInstance = new CCreditsMenu();
		}
		return sInstance;
	} // getInstance
	
	/**
	 * 
	 */
	private CCreditsMenu()
	{
		super(CResourceBundle.getInstance().getString(
				"menus.creditsMenu.title"));
		setToolTipText(
				CResourceBundle.getInstance().getString(
						"menus.creditsMenu.tooltip"));
		
		addMouseListener(this);
		
		
		/*
		 * this.getActionForKeyStroke(aKeyStroke)
		 * peut être utile pour echap
		 */
		
	} // CCreditsMenu

	@Override
	public void mouseClicked(MouseEvent pEvent) 
	{
		Image mIcon         = Toolkit.getDefaultToolkit().getImage(
				getClass().getClassLoader().getResource("gui/img/FYD_credits.gif"));
		
		ImageIcon myIcon = new ImageIcon(mIcon);
		
		JOptionPane.showMessageDialog(
				CMainFrame.getInstance(),
				CResourceBundle.getInstance().getString("credits.content"),
				"Credits",
				JOptionPane.INFORMATION_MESSAGE,
				myIcon);
		

		}

	@Override
	public void mouseEntered(MouseEvent pEvent)
	{
	}

	@Override
	public void mouseExited(MouseEvent pEvent)
	{
	}

	@Override
	public void mousePressed(MouseEvent pEvent)
	{
	}

	@Override
	public void mouseReleased(MouseEvent pEvent)
	{
	}
} // CCreditsMenu
