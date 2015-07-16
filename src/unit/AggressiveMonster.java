package unit;

import org.newdawn.slick.SlickException;

import env.Position;
import env.Stats;
import env.World;

/**
 * Class containing all aggressive type monsters, which chase and attack 
 * the player.
 * @author Adrian Cheung
 */
public class AggressiveMonster extends Monster{
	 // Range within which monster tries to chase and attack the player
	private final double CHASERANGE = 150;
	private final double ATKRANGE = 50;
	
	/**
	 * Creates an aggressive monster
	 * 
	 * @param pos: Position on the map the monster spawns at
	 * @param stats: Monster base stats, refer to Stats class for more information 
	 * @param name: The monster type, e.g. Zombie, Skeleton, Bandit etc. 
	 * @param ref: Location of the image used when rendering the monster
	 * @throws SlickException
	 * General exception type thrown by Slick library classes
	 */
	public AggressiveMonster(Position pos, Stats stats, String name, String ref)
	throws SlickException
	{
		super(pos, stats, name, ref);
	}
	
	/**
	 * Perform an attack on all enemy units within range
	 * @param world: The world containing attack-able units
	 */
	public void attack(World world){
		double dmg = Math.random()*this.stats.getDamage();
		world.getPlayer().takeDamage(dmg);
		this.cdTimer = (int)this.stats.getCoolDown();
	}
	

	/* (non-Javadoc)
	 * @see ShadowQuest.Unit#update(double, double, double, double, int, ShadowQuest.World)
	 */
	/**
	 * Refer to Unit update for full details
	 * Every update, monster's cooldown decreases.
	 * If the player is within range the monster will chase or attack them
	 */
	public void update(double dir_x, double dir_y, double a_on, double t_on, int delta, World world)
	throws SlickException
	{
		if(cdTimer > 0){
			cdTimer -= delta;
		}
		
		if(checkRange(world.getPlayer()) <= ATKRANGE){
			if(cdTimer <= 0){
				attack(world);
			}
		}else if(checkRange(world.getPlayer()) <= CHASERANGE){
			moveTo(world.getPlayer().getPos(), world, true, delta);
		}

	}
	

}
