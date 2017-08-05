package gui.module3d.weapons;

/**
 *
 * @author yann
 */
public abstract class CWeapon {
	public static final int UNKNOWN_WEAPON_TYPE = -1;
	public static final int NORMAL_WEAPON_TYPE =  0;
	public static final int ROCKET_WEAPON_TYPE =  1;
	public static final int LIGHT_WEAPON_TYPE =  2;	
	
    protected int MaxAmmo;
    protected int BulletFlow; // plus il est petit et plus c'est rapide
    protected String name;
    protected int currentAmmo;
    protected int mType;
    protected int damage;
    
    public CWeapon(int MaxAmmo,int BulletFlow,String name, int pType,int damage)
    {
        this.MaxAmmo=MaxAmmo;
        this.BulletFlow=BulletFlow;
        this.name=name;
        this.damage=damage;
        currentAmmo=MaxAmmo;
        mType = pType;
    }
    
    public int GetBulletFlow()
    {
        return BulletFlow;
    }
    
    public String GetName()
    {
        return name;
    }
    
    public int GetcurrentAmmo()
    {
        return currentAmmo;
    }
        public int GetMaxAmmo()
    {
        return MaxAmmo;
    }
        
    public Boolean Shoot()
    {
        if(currentAmmo>0) 
        {
            currentAmmo--;
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    public int getDamage()
    {
        return damage;
    }
    
    public void Reload()
    {
        currentAmmo=MaxAmmo;
    }
    
    public abstract int WeaponType();
}
