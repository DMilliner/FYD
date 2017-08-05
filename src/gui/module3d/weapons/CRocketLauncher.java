package gui.module3d.weapons;

public class CRocketLauncher extends CWeapon
{
   public CRocketLauncher()
   {
       super(4,1000,"Rocket Launcher", ROCKET_WEAPON_TYPE,50);       
   }
   
    @Override
	public int WeaponType()
	{
		return ROCKET_WEAPON_TYPE;
	}
}
