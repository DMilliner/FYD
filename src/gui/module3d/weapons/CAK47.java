package gui.module3d.weapons;

public class CAK47 extends CWeapon
{
   public CAK47()
   {
       super(50, 80, "AK47", NORMAL_WEAPON_TYPE,20);
   }

	@Override
	public int WeaponType()
	{
		return NORMAL_WEAPON_TYPE;
	}
}
