package gui.module3d.players;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.light.SpotLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;

import gui.module3d.weapons.CBulletControl;

/**
 *
 * @author r1z3r
 */
public class CCharacControl extends CharacterControl implements PhysicsCollisionListener{

    String name;
    
    private SpotLight spot;
    private int life;
    private boolean switchLight = true;
        
    public CCharacControl (CollisionShape shape, float mass,String name,int life)
    {
        super(shape,mass);
        this.life=life;
        this.name = name;
    }
    
    @Override
    public void setPhysicsSpace(PhysicsSpace space) {
        super.setPhysicsSpace(space);
        if (space != null) {
            space.addCollisionListener(this);
            spot = new SpotLight();
            spot.setSpotRange(100f);                           // distance
            spot.setSpotInnerAngle(20f * FastMath.DEG_TO_RAD); // inner light cone (central beam)
            spot.setSpotOuterAngle(30f * FastMath.DEG_TO_RAD); // outer light cone (edge of the light)
            spot.setColor(ColorRGBA.White.mult(2.0f));         // light color
            spot.setPosition(this.getPhysicsLocation());               // shine from camera loc
            spot.setDirection(this.getViewDirection());             // shine forward from camera loc
            spatial.getParent().addLight(spot);
        }
    }
   
   
    public void collision(PhysicsCollisionEvent event) {

        if (space == null) {
            return;
        }
        if (event.getNodeA().getName().equals(name) && event.getObjectB() instanceof CBulletControl) {
        
        int damage= Integer.parseInt(event.getNodeB().getName());
        
        if(life <= damage)
        {
        	//this.setPhysicsLocation(new Vector3f(-400,100, -100));
            //space.remove(this);
            //spatial.removeFromParent();
            //spot.setColor(ColorRGBA.Black);
        }else
        {
            life=life-damage;
        }
        }
     
    }
    
    public int getLife()
    {
    	return life;
    }
    
    public void switchLight()
    {
    	if(switchLight)
    	{
    		spot.setColor(ColorRGBA.Black.mult(2.0f));
    	}else
    	{
    		spot.setColor(ColorRGBA.White.mult(2.0f));
    	}
    	switchLight=!switchLight;
    }
    
    @Override
    public void update(float tpf) {
    super.update(tpf);
    if (space != null) {
        spot.setPosition(this.getPhysicsLocation());            
        spot.setDirection(this.getViewDirection());    
    }
    }
}