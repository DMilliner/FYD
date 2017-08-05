package gui;

import gui.menus.CMenuBar;
import gui.menus.CPlayMenu;
import gui.module3d.CModule3D;
import i18n.CResourceBundle;

import java.awt.Canvas;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;

import com.jme3.system.JmeCanvasContext;
//import java.awt.Color;

//import de.lessvoid.nifty.tools.Color;

/**
 * 
 * @author dmilliner
 *
 */
@SuppressWarnings("serial")
public class CMainFrame extends JFrame
{

	private static CMainFrame sInstance = null;

	private Canvas mCanvas3D;

	
	/**
	 * 
	 * @return
	 */
	public static CMainFrame getInstance()
	{
		if (sInstance == null)
		{
			sInstance = new CMainFrame();
		}
		return sInstance;
	}
	
	/*
	 * 
	 */
	private CMainFrame()
	{
		buildGui();
		addListeners();
	} // CMainFrame

	/**
	 * 
	 */
	private void buildGui()
	{
		/*
		Image lBackgroundImage   = Toolkit.getDefaultToolkit().getImage(
				getClass().getClassLoader().getResource("gui/img/background.gif"));
		
		 */
		Image lImageIcon         = Toolkit.getDefaultToolkit().getImage(
				getClass().getClassLoader().getResource("gui/img/mainIcon.gif"));
		
		
		setTitle(
				CResourceBundle.getInstance().getString("title")
			  + " "
			  + CResourceBundle.getInstance().getString("version"));
		
		
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
				
		setJMenuBar(CMenuBar.getInstance());
		
		CPlayMenu.getInstance().setFocusable(false);
		
		mCanvas3D = ((JmeCanvasContext)CModule3D.getInstance().getContext()).getCanvas();
		mCanvas3D.addFocusListener(new FocusListener()
				{
					@Override
					public void focusGained(FocusEvent pEvent)
					{
						
					}

					@Override
					public void focusLost(FocusEvent pEvent)
					{
						//CMenuBar.getInstance().requestFocus();
						mCanvas3D.transferFocusBackward();
					}
				});
		getContentPane().add(mCanvas3D);
		
		//getContentPane().add(CBackgroundPanel.getInstance(lBackgroundImage));
		
		// Spécification de l'Icone.		
		if (lImageIcon != null)
		{
		    setIconImage(lImageIcon);
		}
		

		// Spécification du Look & Feel.
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
		    {
		        if ("Nimbus".equals(info.getName()))
		        {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		}
		catch (Exception lException)
		{
			lException.printStackTrace();
		}
		
		setUndecorated(false);
        setResizable(false);
		setVisible(true);	
		mCanvas3D.setVisible(false);
		

	} // buildGui
	
	/**
	 * 
	 */
	private void addListeners()
	{
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
				{
					@Override
					public void windowClosing(WindowEvent pEvent)
					{
						if (JOptionPane.showConfirmDialog(
								CMainFrame.getInstance(),
								CResourceBundle.getInstance().getString(
										"menus.playMenu.quit.message"),
								CResourceBundle.getInstance().getString(
										"menus.playMenu.quit.windowtitle"),
								JOptionPane.YES_NO_OPTION
							) == JOptionPane.YES_OPTION)
						{
							CMainFrame.getInstance().dispose();
						    System.exit(0);
						}
					}
				});
	} // addListeners

	/**
	 * 
	 */
	public void launchModule3D()
	{	
		mCanvas3D.setVisible(true);
		mCanvas3D.requestFocus();
		repaint();
	}
	
	/**
	 * 
	 */
	public void restartModule3D()
	{
		if (mCanvas3D != null)
		{
			getContentPane().remove(mCanvas3D);
			mCanvas3D = ((JmeCanvasContext)CModule3D.getInstance().getContext()).getCanvas();
			getContentPane().add(mCanvas3D);
		}
	}
} // CMainFrame