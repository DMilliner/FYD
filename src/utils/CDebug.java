package utils;

public class CDebug
{
	// Flag permettant d'activer ou pas le mode debug.
	public static boolean sDebugMode = true;

	
	/**
	 * 
	 */
	private CDebug()
	{
		
	}
	
	/**
	 * 
	 * @param pMessage
	 */
	public static void trace(String pMessage)
	{
		if (sDebugMode == true)
		{
			System.out.println(pMessage);
		}
	}
}
