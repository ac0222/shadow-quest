package item;

import org.newdawn.slick.SlickException;

import env.Position;
import unit.Unit;

/**
 * Tome class. Item type which reduces cooldown
 * @author Adrian Cheung
 *
 */
public class Tome extends Item{
	private final double CDCHANGE = -300;
	
	/**
	 * Create a tome object
	 * @param name: Item's special name
	 * @param pos: Position to create item at
	 * @param ref: Location (file path) of image used for rendering
	 * @throws SlickException
	 * General exception type thrown by Slick library classes
	 * 
	 */
	public Tome(String name, Position pos, String ref)
	throws SlickException
	{
		super(name, pos, ref);
	}    
	/* (non-Javadoc)
	 * @see ShadowQuest.Item#applyEffect(ShadowQuest.Unit)
	 */
	/**
	 * Tomes decrease attack cooldown
	 */
	public void applyEffect(Unit target){
		target.getStats().setCoolDown(target.getStats().getCoolDown() + CDCHANGE);
    }
}
