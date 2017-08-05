
package gui.module3d.players;

import gui.module3d.weapons.CAK47;
import gui.module3d.weapons.CBulletControl;
import gui.module3d.weapons.CLightGun;
import gui.module3d.weapons.CRocketLauncher;
import gui.module3d.weapons.CSW;
import gui.module3d.weapons.CWeapon;

import java.util.ArrayList;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;

/**
 *
 * @author r1z3r
 */

public class CCharacter implements AnimEventListener{
    
    private String name;
    private CharacterControl charac;
    private ArrayList<CWeapon> weapons;
    private int currentWeapon;
    private int life;
    private Node rootNode;
    private BulletAppState bulletAppState;
    private AssetManager assetManager;
    //Weapons
    public boolean isShoot=false;
    SphereCollisionShape normalbulletCollisionShape;
    private Material matnormalBullet;
    private Sphere normalbullet;
    private Geometry normalbulletgeo;
    SphereCollisionShape rocketbulletCollisionShape;
    private Material matrocketBullet;
    private Sphere rocketbullet;
    private Geometry rocketbulletgeo;
    private long lastshoottime;
    // perso annimations
    private AnimChannel channel;
    private AnimControl control;
    
    public CCharacter(CharacterControl charac,String name) 
    {
        InitWeapons();
        life = 1000;
        
        this.charac=charac;
        this.name=name;

        
    }
    public CCharacter(Node rootNode,AssetManager assetManager,BulletAppState bulletAppState,Vector3f position,String name)
    {
        this.name=name;
        this.rootNode=rootNode;
        this.bulletAppState=bulletAppState;
        this.assetManager=assetManager;
        
        InitWeapons();
        prepareBullet(assetManager);
        life = 1000;
        
        Node myCharacter = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        //init des animations
        control = myCharacter.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("Walk");

        myCharacter.setName(name);
        myCharacter.setShadowMode(RenderQueue.ShadowMode.Cast);
        CapsuleCollisionShape characshape = new CapsuleCollisionShape(3f, 4f);
        
        charac = new CCharacControl(characshape, 0.05f,name,life);
        charac.setJumpSpeed(30);
        charac.setFallSpeed(40);
        charac.setGravity(70);
        charac.setMaxSlope(0.3f);
        myCharacter.addControl(charac);
        charac.setPhysicsLocation(position);
        rootNode.attachChild(myCharacter);
        bulletAppState.getPhysicsSpace().add(charac);
       
        //bulletAppState.getPhysicsSpace().enableDebug(assetManager);
    }
    
    private void InitWeapons()
    {
        currentWeapon = 0;
        
        weapons= new ArrayList<CWeapon>();
        weapons.add(new CAK47());
        weapons.add(new CSW());
        weapons.add(new CRocketLauncher());
        weapons.add(new CLightGun());
    }
    
    private void prepareBullet(AssetManager assetManager) {
        
        //balles normales
        normalbullet = new Sphere(32, 32, 0.1f, true, false);
        normalbullet.setTextureMode(TextureMode.Projected);
        normalbulletCollisionShape = new SphereCollisionShape(0.2f);
        matnormalBullet = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matnormalBullet.setColor("Color", ColorRGBA.DarkGray);
        matnormalBullet.setColor("GlowColor", ColorRGBA.DarkGray);
        normalbulletgeo = new Geometry("bullet", normalbullet);
        normalbulletgeo.setMaterial(matnormalBullet);
        normalbulletgeo.setShadowMode(ShadowMode.CastAndReceive);
        //rocket
        rocketbullet = new Sphere(32, 32, 0.7f, true, false);
        rocketbullet.setTextureMode(TextureMode.Projected);
        rocketbulletCollisionShape = new SphereCollisionShape(0.7f);
        matrocketBullet = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matrocketBullet.setColor("Color", ColorRGBA.DarkGray);
        matrocketBullet.setColor("GlowColor", ColorRGBA.DarkGray);
        rocketbulletgeo = new Geometry("rocket", rocketbullet);
        rocketbulletgeo.setMaterial(matrocketBullet);
        rocketbulletgeo.setShadowMode(ShadowMode.CastAndReceive);

      }
    
    public Vector3f getPosition()
    {
		return charac.getPhysicsLocation();
    	
    }
    
    public String getName()
    {
		return name;
    	
    }
    
    public int getLife()
    {
        return life;
    }
    
    public void putDamage(int damage)
    {
        life=life-damage;
    }
    public ArrayList<CWeapon> getWeaponList()
    {
        return weapons;
    }
    
    public CWeapon getCurrentWeapon()
    {
        return (CWeapon)weapons.get(currentWeapon);
    }
    public boolean nextweapon()
    {
        if((currentWeapon+1)<weapons.size())
        {
            currentWeapon++;
            return true;
        }else
        {
            return false;
        } 
    }
    public boolean prevweapon()
    {
        if(currentWeapon>0)
        {
            currentWeapon--;
            return true;
        }else
        {
            return false;
        }
    }
    
    public boolean setWeaponNumber(int numb)
    {
    	if(numb<weapons.size())
    	{
    		currentWeapon=numb;
    		return true;
    	}else
    	{
    		return false;
    	}
    }
    
    public int getcurrentweapon()
    {
    	return currentWeapon;
    }
    
    public void setLocalTranslation(float pX,float pY,float pZ)
    {
    	charac.setPhysicsLocation(charac.getPhysicsLocation().add(new Vector3f(pX,pY,pZ).mult(0.05f)));
    }
    
    public void setLocalTranslation(Vector3f translation)
    {
    	charac.setPhysicsLocation(charac.getPhysicsLocation().add(translation).mult(0.05f));
    }
    
    public void setLocalPosition(Vector3f pos)
    {
    	charac.setPhysicsLocation(pos);
    }
    
    public void setViewDirection(Vector3f vec)
    {
    	charac.setViewDirection(vec);
    }
    public void setLife(int life)
    {
    	this.life=life;
    }
    
    public void SwitchLight()
    {
    	if(charac instanceof CCharacControl)
    	((CCharacControl) charac).switchLight();
    }
    
    public void Shoot()
    {

        if ((System.currentTimeMillis()-lastshoottime>getCurrentWeapon().GetBulletFlow()) && life>0 && isShoot==true)
        {
        	channel.setAnim("push");
    	    switch (this.getCurrentWeapon().WeaponType())
    	    {
    	    	case CWeapon.NORMAL_WEAPON_TYPE :
    	    	{
    		        Geometry normalb= normalbulletgeo.clone();
    		        normalb.setLocalTranslation(charac.getPhysicsLocation().add(charac.getViewDirection().mult(5)));
    		        CBulletControl bulletControl = new CBulletControl(normalbulletCollisionShape, 1,1f);
    		        bulletControl.setCcdMotionThreshold(0.1f);
    		        bulletControl.setLinearVelocity(charac.getViewDirection().mult(300));
    		        normalb.setName(""+getCurrentWeapon().getDamage());
    		        normalb.addControl(bulletControl);
    		        rootNode.attachChild(normalb);
    		        bulletAppState.getPhysicsSpace().add(bulletControl);
    		        break;
    	    	}
    	    	
    	    	case CWeapon.ROCKET_WEAPON_TYPE :
    		    {
    		        Geometry rocketb= rocketbulletgeo.clone();
    		        rocketb.setLocalTranslation(charac.getPhysicsLocation().add(charac.getViewDirection().mult(5)));
    		        CBulletControl bulletControl = new CBulletControl(assetManager,rocketbulletCollisionShape, 1,3f,"rocket");
    		        bulletControl.setCcdMotionThreshold(0.1f);
    		        bulletControl.setLinearVelocity(charac.getViewDirection().mult(200));
    		        rocketb.setName(""+getCurrentWeapon().getDamage());
    		        rocketb.addControl(bulletControl);
    		        rootNode.attachChild(rocketb);
    		        bulletAppState.getPhysicsSpace().add(bulletControl); 
    		        break;
    		    }
    		    
    	    	case CWeapon.LIGHT_WEAPON_TYPE :
    		    {
    		    	Geometry normalb= normalbulletgeo.clone();
    		    	normalb.setLocalTranslation(charac.getPhysicsLocation().add(charac.getViewDirection().mult(5)));
    		    	CBulletControl bulletControl = new CBulletControl(assetManager,rocketbulletCollisionShape, 1,3f,"light");
    		    	bulletControl.setCcdMotionThreshold(0.1f);
    		    	bulletControl.setLinearVelocity(charac.getViewDirection().mult(100));
    		    	normalb.setName(""+getCurrentWeapon().getDamage());
    		    	normalb.addControl(bulletControl);
    		    	rootNode.attachChild(normalb);
    		    	bulletAppState.getPhysicsSpace().add(bulletControl);
    		    	break;
    		    }
    	    }
    	    channel.setAnim("Walk");
    	    
            lastshoottime=System.currentTimeMillis();
        }
    }

	@Override
	public void onAnimChange(AnimControl arg0, AnimChannel arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAnimCycleDone(AnimControl arg0, AnimChannel arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}
  }
