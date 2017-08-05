package gui.menus;

import javax.swing.Box;
import javax.swing.JMenuBar;



@SuppressWarnings("serial")
public class CMenuBar extends JMenuBar
{
	private static CMenuBar sInstance = null;
	
	public static CMenuBar getInstance()
	{
		if (sInstance == null)
		{
			sInstance = new CMenuBar();
		}
		return sInstance;
	}
	
	private CMenuBar()
	{
		add(CPlayMenu.getInstance());
		add(COptionsMenu.getInstance());
		add(Box.createHorizontalGlue());
		add(CCreditsMenu.getInstance());
	}
	
	
}
