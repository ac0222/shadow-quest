package unit;
import item.Elixir;
import item.Item;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import env.Position;
import env.Stats;


/**
 * Primary quest giver. Return elixir to this character to win the game
 * @author Adrian Cheung
 */
public class Prince extends Villager{
	private final String NOELIXIR = "Please seek out the Elixir of Life to cure the king.";
	private final String HASELIXIR = "The elixir! My father is cured! Thankyou!";


	/** Constructor to make a new Prince object.
	 * @param pos: Position on the map which the unit spawns at
	 * @param stats: The base stats the unit has when it is created
	 * @param name: Unit's name
	 * @param ref: Folder location of image used when rendering the unit
	 * @throws SlickException
	 * General exception thrown by Slick library classes
	 */
	public Prince(Position pos, Stats stats, String name, String ref) 
	throws SlickException
	{
		super(pos, stats, name, ref);
	}
	
	/* (non-Javadoc)
	 * @see ShadowQuest.Villager#sayNext(ShadowQuest.Unit)
	 */
	/**
	 * Dialogue depends on whether or not target has the elixir item
	 * in their inventory
	 */
	public void sayNext(Unit target){
		if(!(target instanceof Player)){
			return;
		}
		Player p1 = (Player)target;
		if(p1.getInventory().getNumelixir() == 0){
			this.dialogue = NOELIXIR;
		}else{
			this.dialogue = HASELIXIR;
		}
	}
	
	/* (non-Javadoc)
	 * @see ShadowQuest.Villager#interact(ShadowQuest.Unit)
	 */
	/**
	 * If target has elixir item in their inventory, remove it.
	 * YOU WIN
	 */
	public void interact(Unit target){
		Player p1 = (Player)target;
		ArrayList<Item> inv = p1.getInventory().getItems();
		for(Item i: inv){
			if(i instanceof Elixir){
				i.setConsumed(true);
			}
		}
		p1.getInventory().removeUsed();
		sayNext(target);
		talkTimer = TALKCOOLDOWN;
	}
}
