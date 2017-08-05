package network;


import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Classe permettant au module 3D de savoir si lacommunication r�seau est
 * active.
 * 
 * @author dmilliner
 */
public class CNetwork
{
	public static String sLocalhost = "";
	
	static
	{
		try
		{
			sLocalhost = InetAddress.getLocalHost().getHostAddress();
		}
		catch (NumberFormatException | UnknownHostException lException)
		{
			lException.printStackTrace();
		}
	};
	
    public static boolean sLaunched = false;
    public static boolean s3DLoaded = false;
}
