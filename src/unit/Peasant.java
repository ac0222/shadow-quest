package unit;
import org.newdawn.slick.SlickException;

import env.Position;
import env.Stats;


/**
 * Villager who gives advice on where to find items.
 * @author Adrian Cheung
 *
 */
public class Peasant extends Villager{
	/** Constructor to make a new Peasant object.
	 * @param pos: Position on the map which the unit spawns at
	 * @param stats: The base stats the unit has when it is created
	 * @param name: Unit's name
	 * @param ref: Folder location of image used when rendering the unit
	 * @throws SlickException
	 * General exception thrown by Slick library classes
	 */
	public Peasant(Position pos, Stats stats, String name, String ref) 
	throws SlickException
	{
		super(pos, stats, name, ref);
	}
	
	/* (non-Javadoc)
	 * @see ShadowQuest.Villager#sayNext(ShadowQuest.Unit)
	 */
	/**
	 * Gives advice on where to find items. Advice changes depending on which items
	 * player is currently holding
	 */
	public void sayNext(Unit target){
		if(!(target instanceof Player)){
			return;
		}
		Player p1 = (Player)target;
		
		if(p1.getInventory().getNumamulet() == 0){
			this.dialogue = "Find the Amulet of Vitality, across the river to the west";
		}else if(p1.getInventory().getNumsword() == 0){
			this.dialogue = "Find the Sword of Strength - cross the river and back, on the east side.";
		}else if(p1.getInventory().getNumtome() == 0){
			this.dialogue = "Find the Tome of Agility, in the Land of Shadows.";
		}else{
			 this.dialogue = "You have found all the treasure I know of.";
		}
	}
	
	/* (non-Javadoc)
	 * @see ShadowQuest.Villager#interact(ShadowQuest.Unit)
	 */
	/**
	 * Interaction consists solely of speaking. No other actions taken
	 */
	public void interact(Unit target){
		sayNext(target);
		talkTimer = TALKCOOLDOWN;
	}
}