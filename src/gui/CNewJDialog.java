package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class CNewJDialog extends javax.swing.JDialog {

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				JFrame frame = new JFrame();
				CNewJDialog inst = new CNewJDialog(frame);
				inst.setVisible(true);
			}
		});
	}
	
	public CNewJDialog(JFrame frame) 
	{
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try 
		{
			setSize(400, 300);
		} 
		
		catch (Exception lException) 
		{
			lException.printStackTrace();
		}
	}

}
