package gui.module3d.weapons;

public class CSW extends CWeapon
{
    public CSW()
    {
        super(20,200,"S&W", NORMAL_WEAPON_TYPE,15);
    }
    

	@Override
	public int WeaponType()
	{
		return NORMAL_WEAPON_TYPE;
	}
}