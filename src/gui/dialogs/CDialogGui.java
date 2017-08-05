package gui.dialogs;

import gui.CMainFrame;

import javax.swing.JDialog;

@SuppressWarnings("serial")
public class CDialogGui extends JDialog
{
	/**
	 * 
	 */
	public CDialogGui()
	{
		super(CMainFrame.getInstance());
		//setIconImage(image);TODO
		setModal(true);
	}
}
