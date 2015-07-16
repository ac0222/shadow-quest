package item;

import org.newdawn.slick.SlickException;

import env.Position;
import unit.Unit;

/**
 * Persistent item which boosts player health.
 * @author Adrian Cheung
 */
public class Amulet extends Item{

	private final double HPBOOST = 80;
	
	/**
	 * Create an amulet item object
	 * @param name: Item's special name
	 * @param pos: Position to create item at
	 * @param ref: Location (file path) of image used for rendering
	 * @throws SlickException
	 * General exception type thrown by Slick library classes
	 * 
	 */
	public Amulet(String name, Position pos, String ref)
	throws SlickException
	{
		super(name, pos, ref);
	}
	
    /* (non-Javadoc)
     * @see ShadowQuest.Item#applyEffect(ShadowQuest.Unit)
     */
	/**
	 * Boost Player's MaxHP by 80 points
	 */
    public void applyEffect(Unit target){
    	target.getStats().setMaxHP(target.getStats().getMaxHP() + HPBOOST);
    	target.setHp(target.getHp() + HPBOOST);
    }
}
