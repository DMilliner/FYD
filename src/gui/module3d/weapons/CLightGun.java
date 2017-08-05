package gui.module3d.weapons;

public class CLightGun extends CWeapon
{
    public CLightGun()
    {
         super(10,1000,"Light Gun",LIGHT_WEAPON_TYPE,5);       
    }
    @Override
    public int WeaponType()
   {
        return LIGHT_WEAPON_TYPE; 
   }
}
