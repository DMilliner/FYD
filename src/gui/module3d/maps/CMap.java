/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.module3d.maps;

import gui.module3d.CModule3D;

import java.util.ArrayList;
import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.water.WaterFilter;

/**
 *
 * @author yann
 */
public class CMap {
    
    private TerrainQuad terrain;
    private Material mat_terrain;
    private int  hauteurbaseMap=18;
    private RigidBodyControl landscape;
    private Node rootNode;
    private AssetManager assetManager;
    private BulletAppState bulletAppState;
    private ViewPort viewPort;
    private WaterFilter water;
    private FilterPostProcessor fpp;
    private Vector3f lightDir = new Vector3f(-3.9236743f, -8.27054665f, -5.896916f);
    private BasicShadowRenderer bsr;
    private PssmShadowRenderer pssmRenderer;
    private Spatial gameLevel;
    private int grassdensity = 30;
    
    public CMap(Node rootNode,AssetManager assetManager,BulletAppState bulletAppState,ViewPort viewPort,Camera cam,CModule3D app){
    
        this.rootNode=rootNode;
        this.assetManager=assetManager;
        this.bulletAppState=bulletAppState;
        this.viewPort=viewPort;
        
        setUpTerrain(cam);
        //setUpGrass(app);
        setUpLight();
        setUpEffects();
        
    };
    private void setUpTerrain(Camera cam) {
    
    //definit la hauteur y de base de la map ( pour la verdure)
    //definition du ciel
    
    //rootNode.attachChild(SkyFactory.createSky(assetManager,assetManager.loadTexture("Textures/nightsky.png"),false));
    // alphamap
    mat_terrain = new Material(assetManager,"Common/MatDefs/Terrain/Terrain.j3md");
    mat_terrain.setTexture("Alpha", assetManager.loadTexture("Textures/test.png"));
    // red layer
    Texture grass = assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");
    grass.setWrap(Texture.WrapMode.Repeat);
    mat_terrain.setTexture("Tex1", grass);
    mat_terrain.setFloat("Tex1Scale", 64f);
    //green layer
    Texture dirt = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
    dirt.setWrap(Texture.WrapMode.Repeat);
    mat_terrain.setTexture("Tex2", dirt);
    mat_terrain.setFloat("Tex2Scale", 32f);
    //blue layer
    Texture rock = assetManager.loadTexture("Textures/Terrain/splat/road.jpg");
    rock.setWrap(Texture.WrapMode.Repeat);
    mat_terrain.setTexture("Tex3", rock);
    mat_terrain.setFloat("Tex3Scale", 128f);
    // heightmap
    AbstractHeightMap heightmap = null;
    Texture heightMapImage = assetManager.loadTexture("Textures/heightmap.png");
    heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
    heightmap.load();
    // creation du terrain
    terrain = new TerrainQuad("my terrain", 65, 513, heightmap.getHeightMap());
    terrain.setMaterial(mat_terrain);
    terrain.setLocalTranslation(0, -20, 0);
    terrain.setLocalScale(2f, 0.5f, 2f);
    //terrain.setShadowMode(ShadowMode.Receive);   
    //rootNode.attachChild(terrain);
 
    gameLevel = assetManager.loadModel("Scenes/map.j3o");
    gameLevel.setName("MAP");
    gameLevel.setLocalTranslation(0, -20, 0);
    gameLevel.setLocalScale(2f, 2f, 2f);
    gameLevel.setShadowMode(ShadowMode.Receive);
    rootNode.attachChild(gameLevel);
    CollisionShape terrainShape = CollisionShapeFactory.createMeshShape((Node) gameLevel);
    landscape = new RigidBodyControl(terrainShape, 0);
    gameLevel.addControl(landscape);
    bulletAppState.getPhysicsSpace().add(landscape);        
            
    // The LOD (level of detail) depends on were the camera is
    List<Camera> cameras = new ArrayList<Camera>();
    cameras.add(cam);
    TerrainLodControl control = new TerrainLodControl(terrain, cameras);
    terrain.addControl(control);
    /*
    // definition physique du terrain
    CollisionShape terrainShape = CollisionShapeFactory.createMeshShape((Node) terrain);
    landscape = new RigidBodyControl(terrainShape, 0);
    terrain.addControl(landscape);
    
    bulletAppState.getPhysicsSpace().add(terrain);*/
  }
    
    private void setUpGrass(CModule3D app)
  {
    
    Box Grass = new Box(Vector3f.ZERO,0.1f,2f, 3f);
    Box Grass2 = new Box(Vector3f.ZERO,0.1f,2f, 3f);
    Geometry Grass_geo = new Geometry("Grass", Grass);
    Geometry Grass_geo2 = new Geometry("Grass2", Grass2);
    
    Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setTexture("ColorMap",assetManager.loadTexture("Textures/grass.png")); 
    mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
    mat.getAdditionalRenderState().setAlphaTest(true);
    mat.getAdditionalRenderState().setAlphaFallOff(0.8f);
    //Grass_geo.setLocalTranslation(50f,terrain.getHeight(new Vector2f(20f,0f)),50f);
    //Grass_geo2.setLocalTranslation(50f,terrain.getHeight(new Vector2f(20f,0f)),50f);
    Grass_geo2.rotate(0f, 2f, 0f);
    Grass_geo.setMaterial(mat);
    Grass_geo2.setMaterial(mat);
     for (int y = 0; y < grassdensity; y++){
            for (int x = 0; x < grassdensity; x++){
                Geometry grassInstance = Grass_geo.clone();
                Geometry grassInstance2 = Grass_geo2.clone();
                float x1= -200 +x + (float)(Math.random()*500f);
                float z1= -200+ y + (float)(Math.random()*500f);
                float hauteur=terrain.getHeightmapHeight(new Vector2f(x1,z1));
                if(hauteur<70&&hauteur>50)
                {
                    Vector3f f= new Vector3f(x1,hauteur*0.5f-hauteurbaseMap, z1);
                    grassInstance.setLocalTranslation(f);
                     grassInstance2.setLocalTranslation(f);
                     //grassInstance.scale (0.4f, 0.4f + random.nextFloat()*.2f, 0.4f);
                     rootNode.attachChild(grassInstance);
                     rootNode.attachChild(grassInstance2);
                    
                }


            }
     }
  }
    
    private void setUpEffects()
  {
     fpp = new FilterPostProcessor(assetManager);
     
     // blur
     /*
     dofFilter = new DepthOfFieldFilter();
     dofFilter.setFocusDistance(0);
     dofFilter.setFocusRange(50);
     dofFilter.setBlurScale(1.4f);
     fpp.addFilter(dofFilter);
     */
     // water
     water = new WaterFilter(rootNode, lightDir);
     fpp.addFilter(water);
     
     //ombre basique
     /*bsr = new BasicShadowRenderer(assetManager, 256);
     bsr.setDirection(lightDir); // light direction
     viewPort.addProcessor(bsr);*/
     
     //ombre avancée
     pssmRenderer = new PssmShadowRenderer(assetManager, 1024, 3);
     pssmRenderer.setDirection(lightDir); // light direction
     pssmRenderer.setShadowIntensity(0.3f);
     viewPort.addProcessor(pssmRenderer);
    
    //ombre oclusion
    //SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.92f, 0.33f, 0.61f);
    //fpp.addFilter(ssaoFilter);
    
    //brume
    /*FogFilter fog = new FogFilter();
    fog.setFogColor(new ColorRGBA(0.8f, 0.8f, 1.0f, 1.0f));
    fog.setFogDistance(2000);
    fog.setFogDensity(1.4f);
    fpp.addFilter(fog);*/
    
    
    viewPort.addProcessor(fpp);
  }
    
      private void setUpLight() {
    // We add light so we see the scene
    AmbientLight al = new AmbientLight();
    al.setColor(ColorRGBA.Blue.mult(0.7f));
    rootNode.addLight(al);
    DirectionalLight dl = new DirectionalLight();
    dl.setColor(ColorRGBA.White.mult(0.1f));
    dl.setDirection(lightDir);
    rootNode.addLight(dl);
  }
      public void changeShadowDirection(Vector3f dir)
      {
    	  //if(pssmRenderer!=null)
    		  //pssmRenderer.setDirection(dir);
      }
}
