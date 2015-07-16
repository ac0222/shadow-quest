package unit;

import org.newdawn.slick.SlickException;

import env.Position;
import env.Stats;
import env.World;

/**
 * Passive monsters wander around the map randomly by default. After a set
 * time interval, the monster will choose and move in one of 9 directions.
 * If the passive monster is attacked it will flee from the attacking unit
 * for a set time period, before resuming its default wandering behaviour.
 * Passive monsters cannot attack.
 * @author Adrian Cheung
 */
public class PassiveMonster extends Monster{
	// Time intervals to perform actions for, in milliseconds
	private double dmgTimer;
	private final double RUNTIMER = 5000;
	private double dirTimer;
	private double DIRCHANGE = 3000;
	private int dir;
	
	/**
	 * Creates a passive monster
	 * 
	 * @param pos: Position on the map the monster spawns at
	 * @param stats: Monster base stats, refer to Stats class for more information 
	 * @param name: The monster type, e.g. Zombie, Skeleton, Bandit etc. 
	 * @param ref: Location of the image used when rendering the monster
	 * @throws SlickException
	 * General exception type thrown by Slick library classes
	 */
	public PassiveMonster(Position pos, Stats stats, String name, String ref)
	throws SlickException
	{
		super(pos, stats, name, ref);
		dmgTimer = 0;
		dirTimer = 0;
		dir = (int)(Math.random()*9);
	}
	
	/* (non-Javadoc)
	 * @see ShadowQuest.Monster#takeDamage(double)
	 */
	/**
	 * Monster takes damage, and the time since
	 * last attack is reset to zero
	 */
	@Override
	public void takeDamage(double dmg){
		setHp(this.hp - dmg);
		dmgTimer = RUNTIMER;
	}
	
	/**
	 * @return: returns true if monster was recently attacked
	 */
	public boolean underAttack(){
		if(dmgTimer > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Randomly pick an integer between 0 - 8,
	 * and reset the time since last direction change
	 * to zero.
	 */
	public void changeDir(){
		this.dir = (int)(Math.random()*9);
		dirTimer = DIRCHANGE;
	}
	
	/**
	 * Move around the map in one of nine directions specified by dir
	 * @param dir: Integer between 0 - 8 determining direction of movement
	 * @param world: World which the monster moves in
	 * @param delta: milliseconds since last update
	 */
	public void wander(int dir, World world, int delta){
		Position target;
		double amount = this.stats.getSpeed()*delta;
		double new_x, new_y;
		double old_x = this.pos.getxPos(), old_y = this.pos.getyPos();
		
		if(dir == 0){
			new_x = old_x + amount;
			new_y = old_y;
		}else if(dir == 1){
			new_x = old_x - amount;
			new_y = old_y;
		}else if(dir == 2){
			new_x = old_x;
			new_y = old_y + amount;
		}else if(dir == 3){
			new_x = old_x;
			new_y = old_y - amount;
		}else if(dir == 4){
			new_x = old_x - amount;
			new_y = old_y - amount;
		}else if(dir == 5){
			new_x = old_x + amount;
			new_y = old_y + amount;
		}else if(dir == 6){
			new_x = old_x + amount;
			new_y = old_y - amount;
		}else if(dir == 7){
			new_x = old_x - amount;
			new_y = old_y + amount;
		}else{
			new_x = old_x;
			new_y = old_y;
		}
		target = new Position(new_x, new_y);
		moveTo(target, world, true, delta);		
	}
	
	/* (non-Javadoc)
	 * @see ShadowQuest.Unit#update(double, double, double, double, int, ShadowQuest.World)
	 */
	/**
	 * On update:
	 * - Update time since monster was last attacked
	 * - Determine whether monster is being attacked and, if it is, run away
	 * - Change direction if enough time has passed since the last change
	 * - Move in the aforementioned direction
	 */
	public void update(double dir_x, double dir_y, double a_on, double t_on, int delta, World world)
	throws SlickException
	{
		if(dmgTimer > 0){
			dmgTimer -= delta;
		}
		
		if(dmgTimer > 0){
			moveTo(world.getPlayer().getPos(), world, false, delta);
		}else{
			if(dirTimer <= 0){
				changeDir();
			}else{
				dirTimer -= delta;
			}
			wander(dir, world, delta);
		}
		
	}
}
