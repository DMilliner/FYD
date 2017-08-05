package network.server;

import exceptions.network.NoServerCreatedException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.jme3.math.Vector3f;

import network.messagingService.CMessage;
import utils.CDebug;

//import network.messagingService.service.CServerPasswordRequestMessage;
//import network.messagingService.player.CPlayerHealthMessage;

/**
 * Classe à charge de la communication proprement dite avec un client donné.
 */
public class CService extends Thread
{

	private Socket             mSocketClient;
	private ObjectInputStream  mIn;
	private ObjectOutputStream mOut;
	private int id = 99999;
	
	
	public CService(Socket pSocketClient)
	{
		super();
		mSocketClient = pSocketClient;
		
		// Création des flux d'E/S.
		try
		{
			mOut = new ObjectOutputStream(
					mSocketClient.getOutputStream());
			
			mIn = new ObjectInputStream(
					mSocketClient.getInputStream());
		}
		catch (IOException lException)
		{
			lException.printStackTrace();
		}
		
		// Lancement du service.
		CDebug.trace("CService() : lancement du service.");
		(new Thread(this)).start();
	}
	
	/**
	 * 
	 */
	@Override
    public void run()
    {
		CMessage lMessage;
		// TODO : affiner la condition d'arrêt !
    	while (true)
    	{
    		lMessage = null;
    		
    		try
    		{
	    		// Récupération de la requête en provenance du client.
    			CDebug.trace("CService() : attente d'un message du client.");
    			if (mIn != null)
    			{
    				
					lMessage = (CMessage)(mIn. readObject());
					

					if (lMessage.gettypeofmessage()==1)
					{
						CDebug.trace("CSevice() : lMessage instance de CPlayerCreationMessage: name = "+lMessage.toString() +", position = "+lMessage.getPosition().toString());
		    			
		    			try {
		    				if(id==99999)
		    				id=CServer.getInstance().addClient(mOut,(lMessage.getName()));
		    				
		    			} catch (NoServerCreatedException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			}
		    			
		    			CServer.getInstance().sendAll(lMessage, id);
						
					}
					
					if (lMessage.gettypeofmessage()==5)
					{
		    			//CDebug.trace("CService() : lMessage instance de CPlayerPositionMessage");
						try
						{
							
							CServer.getInstance().setPositionClient(lMessage.getPosition(), id);
							Vector3f vec = CServer.getInstance().getPositionClient(id);
							CDebug.trace(CServer.getInstance().getNameClients(id) +" : " + vec.toString());
							CServer.getInstance().sendAll(lMessage, id);
						}
						catch (NoServerCreatedException lException)
						{
							lException.printStackTrace();
						}
					}
					
					if (lMessage.gettypeofmessage()==0)
					{
						CDebug.trace("CService() : lMessage instance de CGetAllPlayerMessage");
						for(int i=0; i< CServer.getInstance().getNbClients() ; i++)
						{
							if(i!=id)
							{
							 //mOut.writeObject(new CPlayerCreationMessage(CServer.getInstance().getPositionClient(i),CServer.getInstance().getNameClients(i)));
							 mOut.writeObject(new CMessage(1,CServer.getInstance().getNameClients(i),CServer.getInstance().getPositionClient(i),null));
							 mOut.flush();
							}
						}

					}
					
					if (lMessage.gettypeofmessage()==2)
					{
		    			CDebug.trace("CService() : lMessage instance de CPlayerShootMessage");
						try
						{
							CServer.getInstance().sendAll(lMessage, id);
						}
						catch (NoServerCreatedException lException)
						{
							lException.printStackTrace();
						}
					}
					
					if (lMessage.gettypeofmessage()==3)
					{
		    			CDebug.trace("CService() : lMessage instance de CPlayerEndShootMessage");
						try
						{
							CServer.getInstance().sendAll(lMessage, id);
						}
						catch (NoServerCreatedException lException)
						{
							lException.printStackTrace();
						}
					}
					
					if (lMessage.gettypeofmessage()==4)
					{
		    			CDebug.trace("CService() : lMessage instance de CPlayerSwitchLightMessage");
						try
						{
							CServer.getInstance().sendAll(lMessage, id);
						}
						catch (NoServerCreatedException lException)
						{
							lException.printStackTrace();
						}
					}
    			}
    			
   			}
   		catch (IOException | ClassNotFoundException | NoServerCreatedException lException)
    		{
    			lException.printStackTrace();
			}
    	}
    }
	
	
}