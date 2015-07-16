package env;

/**
 * Base stats of a unit, they include:
 * - MaxHP: the maximum health the unit has
 * - CoolDown: the minimum time interval between two attacks
 * - Damage: the maximum amount of damage one attack can deal
 * - Speed: the number of pixels the unit can move per update
 * @author Adrian Cheung

 */
public class Stats {
	private double MaxHP;
	private double CoolDown;
	private double Damage;
	private double Speed;
	
	public double getMaxHP() {
		return MaxHP;
	}


	public void setMaxHP(double maxHP) {
		MaxHP = maxHP;
	}


	public double getCoolDown() {
		return CoolDown;
	}


	public void setCoolDown(double coolDown) {
		CoolDown = coolDown;
	}


	public double getDamage() {
		return Damage;
	}


	public void setDamage(double damage) {
		Damage = damage;
	}


	public double getSpeed() {
		return Speed;
	}


	public void setSpeed(double speed) {
		Speed = speed;
	}


	/**
	 * Create a stats object
	 * @param MaxHP: Maximum HP
	 * @param Damage: Max damage dealt per attack
	 * @param CoolDown: Min time allowed between attacks
	 * @param Speed: Max dist in pixels moved between updates
	 */
	public Stats(double MaxHP, double Damage, double CoolDown, double Speed){
		this.MaxHP = MaxHP;
		this.CoolDown = CoolDown;
		this.Damage = Damage;
		this.Speed = Speed;
	}
}
