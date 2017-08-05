package network.messagingService;

import java.io.Serializable;

import com.jme3.math.Vector3f;

public class CMessage implements Serializable
{
	private static final long serialVersionUID = 1237041981964203096L;

	protected static final String SEPARATION_SYMBOL = "|";
	
	private int typeofmessage;
	private String mPlayerName;
	private Vector3f mPosition;
	private Vector3f viewdirection;
	private int weaponid;
	

    /**
     * 
     * typeofmessage:
     * 0 = get all player
     * 1 = creation player
     * 2 = start shoot message
     * 3 = stop shoot message
     * 4 = switch light
     * 5 = position message
     */
    public CMessage(int typeofmessage,String mPlayerName,Vector3f mPosition,Vector3f viewdirection)
    {
    	this.typeofmessage=typeofmessage;
    	this.mPlayerName=mPlayerName;
    	this.mPosition=mPosition;
    	this.viewdirection=viewdirection;
    }
    
    public void setidweapon(int id)
    {
    	weaponid=id;
    }
    public int getweaponid()
    {
    	return weaponid;
    }
    public int gettypeofmessage()
    {
    	return typeofmessage;
    }
	public String getName()
	{
		return mPlayerName;
	}
	public Vector3f getPosition()
	{
		return mPosition;
	}
	public Vector3f getViewDirection()
	{
		return viewdirection;
	}
}
