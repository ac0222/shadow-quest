package item;

import org.newdawn.slick.SlickException;

import env.Position;
import unit.Unit;

/**
 * Sword class. Item type which increases max damage dealt
 * @author Adrian Cheung
 */
public class Sword extends Item{
	private final double DMGCHANGE = 30;
	
	/**
	 * Create a sword object
	 * @param name: Item's special name
	 * @param pos: Position to create item at
	 * @param ref: Location (file path) of image used for rendering
	 * @throws SlickException
	 * General exception type thrown by Slick library classes
	 */
	public Sword(String name, Position pos, String ref)
	throws SlickException
	{
		super(name, pos, ref);
	}
	/* (non-Javadoc)
	 * @see ShadowQuest.Item#applyEffect(ShadowQuest.Unit)
	 */
	/**
	 * Swords increase the player's damage
	 */
	public void applyEffect(Unit target){
		target.getStats().setDamage(target.getStats().getDamage() + DMGCHANGE);
    }
}
