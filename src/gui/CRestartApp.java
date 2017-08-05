package gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class CRestartApp 
{
	
	private static CRestartApp sInstance = null;
	
	
	public static CRestartApp getInstance()
	{
		if (sInstance == null)
		{
			sInstance = new CRestartApp();
		}
		return sInstance;
	}	
	
	public void restartApplication()
	{
	  final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
	  final File currentJar = new File("C://Users//dmilliner//Desktop//FYD.jar");
	  		                           //UpdateReportElements.class.getProtectionDomain().getCodeSource().getLocation().toURI());
	
	  /* is it a jar file? */
	  if(!currentJar.getName().endsWith(".jar"))
	    return;
	
	  /* Build command: java -jar application.jar */
	  final ArrayList<String> command = new ArrayList<String>();
	  command.add(javaBin);
	  command.add("-jar");
	  command.add(currentJar.getPath());
	
	  final ProcessBuilder builder = new ProcessBuilder(command);
	  try 
	  {
		builder.start();
	  }
	  catch (IOException e) 
	  {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  System.exit(0);
	}

}