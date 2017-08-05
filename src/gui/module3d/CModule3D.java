package gui.module3d;
 
import exceptions.network.NoClientCreatedException;
import gui.dialogs.CCreateNameDialogGui;
import gui.module3d.maps.CMap;
import gui.module3d.players.CCharacter;
import gui.module3d.weapons.CBulletControl;
import gui.module3d.weapons.CWeapon;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;

import network.CNetwork;
import network.client.CClient;
import utils.CKeysSettingsManager;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
 

public class CModule3D extends SimpleApplication
        implements ActionListener,PhysicsCollisionListener {
 
  private static CModule3D sInstance = null;
	
  float hauteurbaseMap;
  private BulletAppState bulletAppState;
  private CharacterControl player;
  private CMap map;
  private Vector3f walkDirection = new Vector3f();
  private boolean left = false, right = false, up = false, down = false, shoot = false;
  DepthOfFieldFilter dofFilter;
  Material stone_mat;
  private CCharacter me;
  ChaseCamera chaseCam;
  Node perso;
  private boolean etatpause=false;
  SpotLight spot;
  Boolean spotSwitch=true;
  //Weapons
  SphereCollisionShape normalbulletCollisionShape;
  Material matnormalBullet;
  Sphere normalbullet;
  Geometry normalbulletgeo;
  SphereCollisionShape rocketbulletCollisionShape;
  Material matrocketBullet;
  Sphere rocketbullet;
  Geometry rocketbulletgeo;
  ParticleEmitter effect;
  
  // multiplayer var
  //CCharacter addplayer = null;
  //moveplayer moveplayer =null;
  //shootplayer shootplayer=null;
  private Vector<CCharacter> mPlayersList;

  CCharacter enemmi;
  public static CModule3D getInstance()
	{
		if (sInstance == null)
		{
			sInstance = new CModule3D();
			if (sInstance!= null)
			{
				sInstance.createCanvas();
			}
		}
		return sInstance;
	}
	

 	private CModule3D()
	{
		super();
		java.util.logging.Logger.getLogger("").setLevel(Level.SEVERE);
		mPlayersList = new Vector<CCharacter>();
		
	    AppSettings cfg = new AppSettings(true);
	    
	    cfg.setFrameRate(25); // set to less than or equal screen refresh rate
	    cfg.setVSync(true);   // prevents page tearing
	    cfg.setFrequency(60); // set to screen refresh rate
	    cfg.setResolution(800, 600);   
	    cfg.setFullscreen(true); 
	    cfg.setSamples(2);    // anti-aliasing
	    setSettings(cfg);
	    
	    setDisplayStatView(false);
	    setDisplayFps(true);
	    
	    /*TODO
	     * 
	     */
	    setPauseOnLostFocus(true);

	}

  
  
  @Override
  public void simpleInitApp() {
      
	    /** Set up Physics */
	bulletAppState = new BulletAppState();
	stateManager.attach(bulletAppState);
	flyCam.setMoveSpeed(100);
	
    map = new CMap (rootNode,assetManager,bulletAppState,viewPort,getCamera(),this);
    
    setUpPersos();
    prepareBullet();
    setUpKeys();
    initHUD();
    initMaterials();

    getPhysicsSpace().addCollisionListener(this);

    // on indique au Thread client que le module 3d est chargé
    CNetwork.s3DLoaded=true;
 
  }
  /** We over-write some navigational key mappings here, so we can
   * add physics-controlled walking and jumping: */
  
  
  private void setUpPersos(){
    
	  	//enemmi = new CCharacter (rootNode,assetManager,bulletAppState,new Vector3f(-400,100, -80),"test");
	  	
	// definition du personnage
	    // Create a appropriate physical shape for it
	    perso = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
	    String name = CCreateNameDialogGui.getInstance().getNamePlayerTextField().getText();
	    perso.setName(name);
	    perso.setShadowMode(ShadowMode.Cast);
	    
	    CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(3f, 4f);
	    // Attach physical properties to model and PhysicsSpace

	    player = new CharacterControl(capsuleShape, 0.05f);
	    //quelques parametres
	    player.setJumpSpeed(30);
	    player.setFallSpeed(40);
	    player.setGravity(70);
	    //defini quand une pente est un obstacle ou pas
	    player.setMaxSlope(0.3f);
	    perso.addControl(player);
	    player.setPhysicsLocation(new Vector3f(-400,100, -80));
	    //defini le groupe d'appartenance dans le monde physique
	    //rootNode.attachChild(perso);
	    getPhysicsSpace().add(player);
	    
	    me = new CCharacter(player,CCreateNameDialogGui.getInstance().getNamePlayerTextField().getText());
	    spot = new SpotLight();
	    spot.setSpotRange(100f);                           // distance
	    spot.setSpotInnerAngle(20f * FastMath.DEG_TO_RAD); // inner light cone (central beam)
	    spot.setSpotOuterAngle(30f * FastMath.DEG_TO_RAD); // outer light cone (edge of the light)
	    spot.setColor(ColorRGBA.White.mult(2.0f));         // light color
	    spot.setPosition(player.getPhysicsLocation());               // shine from camera loc
	    spot.setDirection(player.getViewDirection());             // shine forward from camera loc
	    rootNode.addLight(spot);
	    
  }
  
  public void setUpKeys() {
	CKeysSettingsManager lKSM = CKeysSettingsManager.getInstance();

	
	inputManager.clearMappings();
	
	inputManager.addMapping("Up",         lKSM.getMoveForwardKeyTrigger());
    inputManager.addMapping("Down",       lKSM.getMoveBackwardKeyTrigger());
    inputManager.addMapping("Left",       lKSM.getMoveLeftKeyTrigger());
    inputManager.addMapping("Right",      lKSM.getMoveRightKeyTrigger());
    inputManager.addMapping("reload",     lKSM.getReloadKeyTrigger());
    inputManager.addMapping("LightSwitch",     lKSM.getLightSwitchKeyTrigger());
    
    inputManager.addMapping("nextWeapon", new MouseAxisTrigger(MouseInput.AXIS_WHEEL,true));
    inputManager.addMapping("prevWeapon", new MouseAxisTrigger(MouseInput.AXIS_WHEEL,false));
    inputManager.addMapping("Jump",       new KeyTrigger(KeyInput.KEY_SPACE));
    inputManager.addMapping("pause",      new KeyTrigger(KeyInput.KEY_ESCAPE));    
    inputManager.addMapping("shoot",      new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    
    inputManager.addListener(this, "Left");
    inputManager.addListener(this, "Right");
    inputManager.addListener(this, "Up");
    inputManager.addListener(this, "Down");
    inputManager.addListener(this, "Jump");
    inputManager.addListener(this, "shoot");
    inputManager.addListener(this, "nextWeapon");
    inputManager.addListener(this, "prevWeapon");
    inputManager.addListener(this, "reload");
    inputManager.addListener(this, "pause");
    inputManager.addListener(this, "LightSwitch");
  }
  

   protected void initHUD() 
   {
	   int width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	   int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    guiNode.detachAllChildren();
    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
    BitmapText ch = new BitmapText(guiFont, false);
    ch.setSize(guiFont.getCharSet().getRenderedSize()*2);
    ch.setText("+");        // crosshairs
    //center
    ch.setLocalTranslation(width / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,height / 2 + ch.getLineHeight() / 2, 0);
    guiNode.attachChild(ch);
    
    CWeapon w= me.getCurrentWeapon();
    BitmapText weapon = new BitmapText(guiFont,false);
    weapon.setSize(guiFont.getCharSet().getRenderedSize());
    weapon.setText(w.GetName());
    weapon.setColor(ColorRGBA.Red);
    weapon.setLocalTranslation(20,200, 0);
    guiNode.attachChild(weapon);
    
    BitmapText weaponbullet = new BitmapText(guiFont,false);
    weaponbullet.setSize(guiFont.getCharSet().getRenderedSize());
    String Ammo = w.GetcurrentAmmo()+" / "+ w.GetMaxAmmo();
    weaponbullet.setText(Ammo);
    weaponbullet.setColor(ColorRGBA.Red);
    weaponbullet.setLocalTranslation(20,180, 0);
    guiNode.attachChild(weaponbullet);
    
    BitmapText lifecount = new BitmapText(guiFont,false);
    lifecount.setSize(guiFont.getCharSet().getRenderedSize());
    String life = "HP : "+me.getLife();
    lifecount.setText(life);
    lifecount.setColor(ColorRGBA.Red);
    lifecount.setLocalTranslation(20,160, 0);
    guiNode.attachChild(lifecount);
    
    BitmapText ClientConnected = new BitmapText(guiFont,false);
    ClientConnected.setSize(guiFont.getCharSet().getRenderedSize());
    String clientsnames="connected : \n"+me.getName()+"\n";
    for(int i=0;i<mPlayersList.size();i++)
    	clientsnames=clientsnames+mPlayersList.get(i).getName()+"\n";
    ClientConnected.setText(clientsnames);
    ClientConnected.setColor(ColorRGBA.Red);
    ClientConnected.setLocalTranslation(width-100,height-100, 0);
    guiNode.attachChild(ClientConnected);
    
  }
   
  /** These are our custom actions triggered by key presses.
   * We do not walk yet, we just keep track of the direction the user pressed. */
  public void onAction(String binding, boolean value, float tpf) {
       
    if (binding.equals("Left")) 
    {
      if (value) { left = true; } else { left = false; }
    } else if (binding.equals("Right")) {
      if (value) { right = true; } else { right = false; }
    } else if (binding.equals("Up")) {
      if (value) { up = true; } else { up = false; }
    } else if (binding.equals("Down")) {
      if (value) { down = true; } else { down = false; }
    } else if (binding.equals("Jump")) {
      player.jump();
    } else if (binding.equals("pause")) {
    	if (value){
            inputManager.setCursorVisible(!etatpause);
            flyCam.setEnabled(etatpause);  
            etatpause=!etatpause;
        
        }
    } else if (binding.equals("shoot")) {
        if (value) { shoot = true; 
			try {
				CClient.getInstance().emitShootMessage(getPlayerX(),getPlayerY(),getPlayerZ(),getPlayerviewDirection(),me.getcurrentweapon());
			} catch (IOException | NoClientCreatedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else { shoot = false;
			try {
				CClient.getInstance().emitEndShootMessage();
			} catch (IOException | NoClientCreatedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    } 
    
    else if (value&&binding.equals("nextWeapon"))
    {
        me.nextweapon();
        initHUD();
    } else if (value&&binding.equals("prevWeapon"))
    {
        me.prevweapon();
        initHUD();
        
    }else if (value&&binding.equals("reload"))
    {
        CWeapon w=me.getCurrentWeapon();
        w.Reload();
        initHUD();        
    }else if (value&&binding.equals("LightSwitch"))
    {

			try {
				CClient.getInstance().emitSwitchLightMessage();
			} catch (IOException | NoClientCreatedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        
    	if(spotSwitch)
    	{
    		spot.setColor(ColorRGBA.Black.mult(2.0f));
    	}else
    	{
    		spot.setColor(ColorRGBA.White.mult(2.0f));
    	}
    	spotSwitch=!spotSwitch;
    }
        
  }
 
  /**
   * This is the main event loop--walking happens here.
   * We check in which direction the player is walking by interpreting
   * the camera direction forward (camDir) and to the side (camLeft).
   * The setWalkDirection() command is what lets a physics-controlled player walk.
   * We also make sure here that the camera moves with player.
   */
  
  long lastshoottime;
  @Override
  public void simpleUpdate(float tpf) {
    Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
    Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
    walkDirection.set(0, 0, 0);  

    map.changeShadowDirection(camDir);
    
    //enemmi.Shoot(new Vector3f(1,0,0), 0);
    
    if (left)  { walkDirection.addLocal(camLeft); }
    if (right) { walkDirection.addLocal(camLeft.negate()); }
    if (up)    { walkDirection.addLocal(camDir);
                 walkDirection.setY(0);}
    if (down)  { walkDirection.addLocal(camDir.negate());
                 walkDirection.setY(0);}
    if (shoot) 
    { 
    CWeapon w= me.getCurrentWeapon();
    if (System.currentTimeMillis()-lastshoottime>w.GetBulletFlow())
        {
        makeBullet();
        lastshoottime=System.currentTimeMillis();
        }
    }

// bloquont le Upside-down de la camera
float[] angles=new float[3];
cam.getRotation().toAngles(angles);
//check the x rotation
Quaternion tmpQuat= new Quaternion();
if(angles[0]>(FastMath.HALF_PI-0.1f)){
            angles[0]=(FastMath.HALF_PI-0.1f);
            cam.setRotation(tmpQuat.fromAngles(angles));
}else if(angles[0]<-FastMath.HALF_PI+0.1f){
            angles[0]=-FastMath.HALF_PI+0.1f;
            cam.setRotation(tmpQuat.fromAngles(angles));
}

    player.setWalkDirection(walkDirection);
    spot.setPosition(player.getPhysicsLocation());               // shine from camera loc
    spot.setDirection(cam.getDirection());     
    cam.setLocation(player.getPhysicsLocation());
    
		int i=0;
		while(i<mPlayersList.size() && mPlayersList.size()>0)
		{
	  		mPlayersList.get(i).Shoot();
	  		i++;
		}

    /*
    // update for multiplayer message
    if(addplayer!=null)
    {
		mPlayersList.add(addplayer);
	    initHUD();
	    addplayer=null;
    }
    
    if(moveplayer!=null)
    {
    	// Recherche du joueur dans la lise des joueurs.
  		int i=0;
  		while(i<mPlayersList.size() && mPlayersList.size()>0)
  		{
  		if(mPlayersList.get(i).getName().matches(moveplayer.pPlayerName))
  		{ 
  	  		mPlayersList.get(i).setLocalPosition(moveplayer.move.add(0, 0.01f, 0));
  	  		mPlayersList.get(i).setViewDirection(moveplayer.viewdir);
  	  		break;
  		}else
  		{
  			i++;
  		}
  		}
  		moveplayer=null;
    }
    if(shootplayer!=null)
    {
    	// Recherche du joueur dans la lise des joueurs.
  		int i=0;
  		while(i<mPlayersList.size() && mPlayersList.size()>0)
  		{
  		if(mPlayersList.get(i).getName().matches(shootplayer.pPlayerName))
  		{ 
  	  		mPlayersList.get(i).setLocalPosition(shootplayer.move.add(0, 0.01f, 0));
  	  		mPlayersList.get(i).setViewDirection(shootplayer.viewdir);
  	  		mPlayersList.get(i).Shoot(shootplayer.viewdir, shootplayer.weaponid);
  	  		break;
  		}else
  		{
  			i++;
  		}
  		}
  		shootplayer=null;
    }*/
  }
  
  //---------------- init materials --------------------------------
   public void initMaterials() {
    
    // Bullets
    stone_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
    key2.setGenerateMips(true);
    Texture tex2 = assetManager.loadTexture(key2);
    stone_mat.setTexture("ColorMap", tex2);
    
   }
  
   
  //---------------gestion des balles--------------------------
  private void prepareBullet() {
      
      //balles normales
      normalbullet = new Sphere(32, 32, 0.1f, true, false);
      normalbullet.setTextureMode(TextureMode.Projected);
      normalbulletCollisionShape = new SphereCollisionShape(0.2f);
      matnormalBullet = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
      matnormalBullet.setColor("Color", ColorRGBA.DarkGray);
      matnormalBullet.setColor("GlowColor", ColorRGBA.DarkGray);
      getPhysicsSpace().addCollisionListener(this);
      normalbulletgeo = new Geometry("bullet", normalbullet);
      normalbulletgeo.setMaterial(matnormalBullet);
      normalbulletgeo.setShadowMode(ShadowMode.CastAndReceive);
      //rocket
      rocketbullet = new Sphere(32, 32, 0.7f, true, false);
      rocketbullet.setTextureMode(TextureMode.Projected);
      rocketbulletCollisionShape = new SphereCollisionShape(0.7f);
      matrocketBullet = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
      matrocketBullet.setColor("Color", ColorRGBA.DarkGray);
      matrocketBullet.setColor("GlowColor", ColorRGBA.DarkGray);
      getPhysicsSpace().addCollisionListener(this);
      rocketbulletgeo = new Geometry("rocket", rocketbullet);
      rocketbulletgeo.setMaterial(matrocketBullet);
      rocketbulletgeo.setShadowMode(ShadowMode.CastAndReceive);

    }
  
  
  public void makeBullet() 
  {
    
    CWeapon lWeapon = me.getCurrentWeapon();
    
    
    if ( (lWeapon != null) && (lWeapon.Shoot()) )
    {
	    initHUD();
	    
	    switch (lWeapon.WeaponType())
	    {
	    
	    	case CWeapon.NORMAL_WEAPON_TYPE :
	    	{
		        Geometry normalb= normalbulletgeo.clone();
		        normalb.setLocalTranslation(player.getPhysicsLocation().add(cam.getDirection().mult(5)));
		        CBulletControl bulletControl = new CBulletControl(normalbulletCollisionShape, 1,1f);
		        bulletControl.setCcdMotionThreshold(0.1f);
		        bulletControl.setLinearVelocity(cam.getDirection().mult(300));
		        normalb.setName(""+me.getCurrentWeapon().getDamage());
		        normalb.addControl(bulletControl);
		        rootNode.attachChild(normalb);
		        getPhysicsSpace().add(bulletControl);
		        break;
	    	}
	    	
	    	case CWeapon.ROCKET_WEAPON_TYPE :
		    {
		        Geometry rocketb= rocketbulletgeo.clone();
		        rocketb.setLocalTranslation(player.getPhysicsLocation().add(cam.getDirection().mult(5)));
		        CBulletControl bulletControl = new CBulletControl(assetManager,rocketbulletCollisionShape, 1,3f,"rocket");
		        bulletControl.setCcdMotionThreshold(0.1f);
		        bulletControl.setLinearVelocity(cam.getDirection().mult(200));
		        rocketb.setName(""+me.getCurrentWeapon().getDamage());
		        rocketb.addControl(bulletControl);
		        rootNode.attachChild(rocketb);
		        getPhysicsSpace().add(bulletControl); 
		        break;
		    }
		    
	    	case CWeapon.LIGHT_WEAPON_TYPE :
		    {
		    	Geometry normalb= normalbulletgeo.clone();
		    	normalb.setLocalTranslation(player.getPhysicsLocation().add(cam.getDirection().mult(5)));
		    	CBulletControl bulletControl = new CBulletControl(assetManager,rocketbulletCollisionShape, 1,3f,"light");
		    	bulletControl.setCcdMotionThreshold(0.1f);
		    	bulletControl.setLinearVelocity(cam.getDirection().mult(100));
		    	normalb.setName(""+me.getCurrentWeapon().getDamage());
		    	normalb.addControl(bulletControl);
		    	rootNode.attachChild(normalb);
		    	getPhysicsSpace().add(bulletControl);
		    	break;
		    }
	    }
    }
  }
  
  private PhysicsSpace getPhysicsSpace()
  {
        return bulletAppState.getPhysicsSpace();
  }

  public void collision(PhysicsCollisionEvent event) 
  {
	  if (event.getNodeA().getName().equals(me.getName()) && event.getObjectB() instanceof CBulletControl) {
		  int damage= Integer.parseInt(event.getNodeB().getName());
		  if(me.getLife() <= damage)
	        {
			  me.setLife(1000);
			  player.setPhysicsLocation(new Vector3f(-400,100, -100));
			  initHUD();
			  
	        }else
	        {
		  me.setLife(me.getLife()-damage);
		  initHUD();
	        }
		 
	  }
  }

  	
  	
  	/**
  	 * 
  	 * @param pPlayer
  	 * @return
  	 */
  	public boolean addPlayer(String name,Vector3f initposition)
  	{
  		boolean lResult = false;
  	    
  		//addplayer =new CCharacter (rootNode,assetManager,bulletAppState,initposition,name);
	    lResult = true;
  			// TODO : g�rer un nombre maxi de jouers.
		mPlayersList.add(new CCharacter (rootNode,assetManager,bulletAppState,initposition,name));
	    initHUD();
  		return lResult;
  	}
  	
  	// TODO : impl�menter removePlayer.
  	
  	public void setPlayerAtPosition(
  			String pPlayerName,
  			Vector3f move, Vector3f viewdir)
  	{
  		//moveplayer = new moveplayer(pPlayerName,move,viewdir);
  	// Recherche du joueur dans la lise des joueurs.
  		int i=0;
  		while(i<mPlayersList.size() && mPlayersList.size()>0)
  		{
  		if(mPlayersList.get(i).getName().matches(pPlayerName))
  		{ 
  	  		mPlayersList.get(i).setLocalPosition(move.add(0, 0.01f, 0));
  	  		mPlayersList.get(i).setViewDirection(viewdir);
  	  		break;
  		}else
  		{
  			i++;
  		}
  		}
  	}
  	
  	public void setPlayerShootAtPosition(
  			String pPlayerName,
  			Vector3f move, Vector3f viewdir,int weaponnumber)
  	{
  		//shootplayer = new shootplayer(pPlayerName,move,viewdir,0);
  	// Recherche du joueur dans la lise des joueurs.
  		int i=0;
  		while(i<mPlayersList.size() && mPlayersList.size()>0)
  		{
  		if(mPlayersList.get(i).getName().matches(pPlayerName))
  		{ 
  	  		mPlayersList.get(i).setLocalPosition(move.add(0, 0.01f, 0));
  	  		mPlayersList.get(i).setViewDirection(viewdir);
  	  		mPlayersList.get(i).setWeaponNumber(weaponnumber);
  	  		mPlayersList.get(i).isShoot=true;
  	  		break;
  		}else
  		{
  			i++;
  		}
  		}
  	}
  	
  	public void setPlayerEndShoot(String pPlayerName)
  	{
  		//shootplayer = new shootplayer(pPlayerName,move,viewdir,0);
  	// Recherche du joueur dans la lise des joueurs.
  		int i=0;
  		while(i<mPlayersList.size() && mPlayersList.size()>0)
  		{
  		if(mPlayersList.get(i).getName().matches(pPlayerName))
  		{ 
  	  		mPlayersList.get(i).isShoot=false;
  	  		break;
  		}else
  		{
  			i++;
  		}
  		}
  	}
  	
  	public void setPlayerSwitchLight(String pPlayerName)
  	{
  		int i=0;
  		while(i<mPlayersList.size() && mPlayersList.size()>0)
  		{
  		if(mPlayersList.get(i).getName().matches(pPlayerName))
  		{ 
  	  		mPlayersList.get(i).SwitchLight();
  	  		break;
  		}else
  		{
  			i++;
  		}
  		}
  	}

  	/**
  	 * 
  	 * @return
  	 */
  	public float getPlayerX()
  	{
  	    return player.getPhysicsLocation().clone().x;
  	}

  	/**
  	 * 
  	 * @return
  	 */
  	public float getPlayerY()
  	{
  	    return player.getPhysicsLocation().clone().y;
  	}
  	
  	/**
  	 * 
  	 * @return
  	 */
  	public float getPlayerZ()
  	{
  	    return player.getPhysicsLocation().clone().z;
  	}
  	
  	public Vector3f getPlayerV3f()
  	{
  		return player.getPhysicsLocation().clone();
  	}
  	public Vector3f getPlayerviewDirection()
  	{
  		return cam.getDirection().clone();
  	}

}

