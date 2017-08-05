package network.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.jme3.math.Vector3f;

import network.CNetwork;
import network.messagingService.CMessage;
import utils.CDebug;
import exceptions.network.NoClientCreatedException;
import gui.dialogs.CCreateNameDialogGui;
import gui.module3d.CModule3D;
//import network.messagingService.service.CServerPasswordRequestMessage;

public class CClient extends Thread
{
    private static CClient sInstance = null;
    
    // L'adresse du serveur.
    public String mIpAddr;
    
    // Le port du serveur.
    public int mPort;
    
	private ObjectInputStream  mIn;
	private ObjectOutputStream mOut;
	
	private String nom;
	private Vector3f prevposition;
	private Vector3f prevdirection;

	private Socket client;
	
	/**
	 * 
	 * @param pIpAddr
	 * @param pPort
	 * 
	 * @return
	 */
    public static CClient getInstance(String pIpAddr, int pPort)
    {
    	if (sInstance == null)
    	{
    		try
    		{
				sInstance = new CClient(pIpAddr, pPort);
			}
    		catch (IOException lException)
    		{
    			lException.printStackTrace();
			}
    	}
    	return sInstance;
    }
	
    /**
     * 
     * @return
     */
    public static CClient getInstance() throws NoClientCreatedException
    {
    	if (sInstance == null)
    	{
    		throw new NoClientCreatedException();
    	}
    	return sInstance;
    }
	
    /**
     * 
     * @param pIpAddr
     * @param pPort
     * 
     * @throws IOException
     */
    private CClient(String pIpAddr, int pPort) 
    		throws IOException
    {
    	client = new Socket(pIpAddr, pPort);
		mIpAddr = pIpAddr;
		mPort   = pPort;
		
		
		// Cr�ationdes flux d'E/S.
		try
		{
			mIn = new ObjectInputStream(client.getInputStream());
			
			mOut = new ObjectOutputStream(client.getOutputStream());
		}
		catch (IOException lException)
		{
			lException.printStackTrace();
		}
		
		// Notification de cr�ation du joueur dans le server
		CDebug.trace("CClient() "+nom+" : transmission d'un message de Création perso au serveur.");
		
		// Lancement du client.
		nom = CCreateNameDialogGui.getInstance().getNamePlayerTextField().getText();
		CDebug.trace("CClient() "+nom+" : lancement du client");
		
		//envoi demande de creation de lui dans les autres clients
		mOut.writeObject(new CMessage(1, nom, new Vector3f(-400,100, -100), null));
		mOut.flush();

		
		(new Thread(this)).start();
    }
    
    /**
     * 
     */
    @Override
    public void run()
    {
    	while(!CNetwork.s3DLoaded){}
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
    	
    	
		CDebug.trace("CClient() "+nom+" :client parfaitement lancé");
		
		//demande tout les personnages en jeux

			//mOut.writeObject( new CGetAllPlayerMessage(CCreateNameDialogGui.getInstance().getNamePlayerTextField().getText()));
			try {
				mOut.writeObject(new CMessage(0, nom,null, null));
				mOut.flush();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
    	// TODO : affiner la condition d'arr�t.
		
		
    	while (true)
    	{    		
    		CMessage lMessage = null;
			try {
				lMessage = (CMessage) (mIn. readObject());
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	
    		if (lMessage.gettypeofmessage()==1)
			{
    			CDebug.trace("CClient() "+nom+" : lMessage instance de CPlayerCreationMessage: name = "+lMessage.toString() +", position = "+lMessage.getPosition().toString());
    			//if(!((CPlayerCreationMessage) lMessage).getName().matches(nom))
    				CModule3D.getInstance().addPlayer(lMessage.getName(),lMessage.getPosition());
    			//CModule3D.getInstance().addPlayer(lMessage.toString(),new Vector3f(-400,100, -100));
			}
    		
    		if (lMessage.gettypeofmessage()==5)
			{
    			//CDebug.trace("CClient() "+nom+" : lMessage instance de CPlayerPositionMessage: name = "+((CPlayerPositionMessage) lMessage).getPlayerName() +", position = "+((CPlayerPositionMessage) lMessage).getPosition().toString()+", direction :"+ ((CPlayerPositionMessage) lMessage).getViewDirection());
    			//if(!(((CPlayerPositionMessage) lMessage).getPlayerName().matches(nom)))
    				CModule3D.getInstance().setPlayerAtPosition(lMessage.getName(), lMessage.getPosition(),lMessage.getViewDirection());
			}

    		if (lMessage.gettypeofmessage()==4)
			{
    			CDebug.trace("CClient() "+nom+" : lMessage instance de CPlayerSwitchLightMessage: name = "+lMessage.getName());
    			//if(!(((CPlayerPositionMessage) lMessage).getPlayerName().matches(nom)))
    				CModule3D.getInstance().setPlayerSwitchLight(lMessage.getName());
			}
    		
    		if (lMessage.gettypeofmessage()==2)
			{
    			CDebug.trace("CClient() "+nom+" : lMessage instance de CPlayerStartShootMessage: name = "+lMessage.getName());
    			//if(!(((CPlayerPositionMessage) lMessage).getPlayerName().matches(nom)))
    				CModule3D.getInstance().setPlayerShootAtPosition(lMessage.getName(),lMessage.getPosition(),lMessage.getViewDirection(),lMessage.getweaponid());
			}
    		
    		if (lMessage.gettypeofmessage()==3)
			{
    			CDebug.trace("CClient() "+nom+" : lMessage instance de CPlayerEndShootMessage: name = "+lMessage.getName());
    			//if(!(((CPlayerPositionMessage) lMessage).getPlayerName().matches(nom)))
    				CModule3D.getInstance().setPlayerEndShoot(lMessage.getName());
			}
    		
    		//CDebug.trace("CClient() "+nom+" : transmission d'un message de position au serveur.");
    		try {    				
    		if(CNetwork.s3DLoaded==true)
    		{
			//mOut.writeObject(new CPlayerPositionMessage(nom,CModule3D.getInstance().getPlayerX(),CModule3D.getInstance().getPlayerY(),CModule3D.getInstance().getPlayerZ(),CModule3D.getInstance().getPlayerviewDirection()));
    		if(prevposition!=CModule3D.getInstance().getPlayerV3f()||prevdirection!=CModule3D.getInstance().getPlayerviewDirection())
    		{
    			prevposition=CModule3D.getInstance().getPlayerV3f();
    			prevdirection=CModule3D.getInstance().getPlayerviewDirection();
    			
    			mOut.writeObject(new CMessage(5, nom,prevposition, prevdirection));
    			mOut.flush();
    		}
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    		try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

    	}
    }
    
    
    /**
     * 
     * @param pMEssage
     * @throws IOException 
     */
    public void emitSwitchLightMessage() throws IOException
    {

			mOut.writeObject(new CMessage(4, nom,null,null));
			mOut.flush();
			
    }
    public void emitShootMessage(float X,float Y,float Z,Vector3f dir,int weaponid) throws IOException
    {
    		CMessage message = new CMessage(2, nom,new Vector3f(X,Y,Z),dir);
    		message.setidweapon(weaponid);
			mOut.writeObject(message);
			mOut.flush();
    }
    
    public void emitEndShootMessage() throws IOException
    {
			mOut.writeObject(new CMessage(3, nom,null,null));
			mOut.flush();
    }
    
	/**
	 * 
	 * @param pArgs
	 */
	public static void main(String[] pArgs)
	{
		CClient.getInstance(
				"localhost",
				8087);
	}
}
