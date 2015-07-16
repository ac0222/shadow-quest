package unit;
import org.newdawn.slick.SlickException;

import env.Position;
import env.Stats;


/**
 * Villager which is able to heal the player.
 * @author Adrian Cheung
 */
public class Healer extends Villager{
	private final String HEALTHY = "Return to me if you ever need healing.";
	private final String GOTHEALED = "You're looking much healthier now.";


	/** Constructor to make a new Healer object.
	 * @param pos: Position on the map which the unit spawns at
	 * @param stats: The base stats the unit has when it is created
	 * @param name: Unit's name
	 * @param ref: Folder location of image used when rendering the unit
	 * @throws SlickException
	 * General exception thrown by Slick library classes
	 */
	public Healer(Position pos, Stats stats, String name, String ref) 
	throws SlickException
	{
		super(pos, stats, name, ref);
	}
	
	/* (non-Javadoc)
	 * @see ShadowQuest.Villager#sayNext(ShadowQuest.Unit)
	 */
	/**
	 * Different dialogue depending on whether target was on full
	 * health when interaction took place
	 */
	public void sayNext(Unit target){
		if(target.getHp() == target.getStats().getMaxHP()){
			this.dialogue = HEALTHY;
		}else{
			this.dialogue = GOTHEALED;
		}
	}
	
	/* (non-Javadoc)
	 * @see ShadowQuest.Villager#interact(ShadowQuest.Unit)
	 */
	/**
	 * Work out what dialogue to say first.
	 * Next, perform the relevant action:
	 * - Set the target's current health to be equal to 
	 * their max health
	 * - Reset the time since last interaction occurred
	 */
	public void interact(Unit target){
		sayNext(target);
		target.setHp(target.getStats().getMaxHP());
		talkTimer = TALKCOOLDOWN;
	}
}
