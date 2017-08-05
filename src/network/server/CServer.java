package network.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import com.jme3.math.Vector3f;

import network.messagingService.CMessage;
import utils.CDebug;
import exceptions.network.NoServerCreatedException;

public class CServer extends ServerSocket implements Runnable
{
    private static CServer sInstance = null;
    
    public static boolean sLaunched = false;
    
    private Vector<ObjectOutputStream> _tabClients = new Vector<ObjectOutputStream>();
    private Vector<String> _nameClients = new Vector<String>();// contiendra tous les flux de sortie vers les clients
    private Vector<Vector3f> _positionClients = new Vector<Vector3f>();
    private int _nbClients=0; // nombre total de clients connectés
    
	
    // Liste des clients connectés.
    private ArrayList<CService> mClientsList; 
    
    // Le nom du serveur.
    private String mName;
    
    
    /**
     * 
     * @return
     */
    public static CServer getInstance(
    		String pName, int pPort)
    {
    	if (sInstance == null)
    	{
    		try
    		{
				sInstance = new CServer(pName, pPort);
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
    public static CServer getInstance() throws NoServerCreatedException
    {
    	if (sInstance == null)
    	{
    		throw new NoServerCreatedException();
    	}
    	return sInstance;
    }
	
    /**
     * 
     * @param pName
     * @param pPort
     * @throws IOException
     */
    private CServer(String pName, int pPort) 
    		throws IOException
    {
    	// Création proprement dite du serveur.
		super(pPort);
		mName = pName;
		
		mClientsList = new ArrayList<>();
		
		// Lancement du serveur.
		CDebug.trace("CServer() : lancement du serveur.");
		System.out.println(">"+mName.toString() +"<");
		
		(new Thread(this)).start();
    }
    
    /**
     * 
     */
    @Override
    public void run()
    {
    	// TODO : affiner la condition d'arrêt.
    	while (true)
    	{
    		try
    		{
    			CDebug.trace("CServer() : attente d'un client.");
				Socket lSocketClient = accept();
				
				
				CDebug.trace("CServer() : lancement du service associé au client connecté.");
				mClientsList.add(new CService(lSocketClient));
			}
    		catch (IOException lException)
    		{
				lException.printStackTrace();
			}
    	}
    }
    
  //** Methode : ajoute un nouveau client dans la liste **
    synchronized public int addClient(ObjectOutputStream out,String name)
    {
      _nbClients++; // un client en plus ! ouaaaih
      _tabClients.addElement(out); // on ajoute le nouveau flux de sortie au tableau
      _nameClients.addElement(name);
      _positionClients.addElement(null);
      return _tabClients.size()-1; // on retourne le numéro du client ajouté (size-1)
    }
    
    //** Methode : détruit le client no i **
    synchronized public void delClient(int i)
    {
      _nbClients--; // un client en moins ! snif
      if (_tabClients.elementAt(i) != null) // l'élément existe ...
      {
        _tabClients.removeElementAt(i); // ... on le supprime
        _nameClients.removeElementAt(i);
      }
    }
    
    //** Methode : retourne le nombre de clients connectés **
    synchronized public int getNbClients()
    {
      return _nbClients; // retourne le nombre de clients connectés
    }
    
    synchronized public String getNameClients(int id)
    {
    	return (String)_nameClients.elementAt(id);
    }
    
    synchronized public void setPositionClient(Vector3f position,int id)
    {
    	_positionClients.setElementAt(position, id);
    }
    
    synchronized public Vector3f getPositionClient(int id)
    {
    	return (Vector3f)_positionClients.elementAt(id);
    }
    
    //** Methode : envoie le message à tous les clients **
    synchronized public void sendAll(CMessage message,int id)
    {
    	ObjectOutputStream out; // declaration d'une variable permettant l'envoi de texte vers le client
      for (int i = 0; i < _tabClients.size(); i++) // parcours de la table des connectés
      {
    	  out = (ObjectOutputStream) _tabClients.elementAt(i); // extraction de l'élément courant (type PrintWriter)
    	  
        if (out != null && id!=i) // sécurité, l'élément ne doit pas être vide
        {
        	// ecriture du texte passé en paramètre (et concaténation d'une string de fin de chaine si besoin)

          try {
			out.writeObject(message);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
        }
      }
    }
    
    
}