package item;

import org.newdawn.slick.SlickException;

import env.Position;
import unit.Unit;

/**
 * Special item which can be used to trigger an event;
 * in this case, winning the game.
 * @author Adrian Cheung
 */
public class Elixir extends Item{
	/**
	 * Create an elixir object
	 * @param name: Item's special name
	 * @param pos: Position to create item at
	 * @param ref: Location (file path) of image used for rendering
	 * @throws SlickException
	 * General exception type thrown by Slick library classes
	 * 
	 */
	public Elixir(String name, Position pos, String ref)
	throws SlickException
	{
		super(name, pos, ref);
	}
	
    /* (non-Javadoc)
     * @see ShadowQuest.Item#applyEffect(ShadowQuest.Unit)
     */
	/**
	 * The elixir has no direct effect on the player
	 */
    public void applyEffect(Unit target){
    	//No effect
    }
}
