package item;

import org.newdawn.slick.SlickException;

import env.GameObject;
import env.Position;
import unit.Unit;
import util.Camera;

/**
 * Abstract item class for handling item objects.
 * Items are static elements which can be picked up by the player.
 * When acquired they affect the player in some way. 
 * @author Adrian Cheung
 */
public abstract class Item extends GameObject{
	// Data members
	/*Determine whether the item has been picked up and is
	 * currently being held by a player
	 */
	protected boolean held;
	
	/* Determine whether the item has been consumed;
	 * only relevant for elixir at this point
	 */
	protected boolean consumed;
	
	// getters and setters
	public boolean isConsumed(){
		return consumed;
	}

	public void setConsumed(boolean consumed){
		this.consumed = consumed;
	}

	public boolean isHeld() {
		return held;
	}

	public void setHeld(boolean held) {
		this.held = held;
	}

	/* (non-Javadoc)
	 * @see ShadowQuest.GameObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (held != other.held)
			return false;
		return true;
	}
	
	/**
	 * Create an item object
	 * @param name: Item's special name
	 * @param pos: Position to create item at
	 * @param ref: Location (file path) of image used for rendering
	 * @throws SlickException
	 * General exception type thrown by Slick library classes
	 * 
	 */
	public Item(String name, Position pos, String ref)
	throws SlickException
	{
		super(name, pos, ref);
		this.held = false;
		this.consumed = false;
	}  
	
	/**
	 * Render the item relative to camera's position
	 * @param camera: Camera used to display the world map
	 * @throws SlickException
	 * General exception type thrown by Slick library classes
	 */
	public void renderItem(Camera camera) 
	throws SlickException
	{
		renderOther(camera);
	}
    /**
     * Apply whatever effect the item has on to the target unit.
     * Possible effects include:
     * -Increasing/Decreasing MaxHP
     * -Increasing/Decreasing MaxDmg
     * -Increasing/Decreasing Attack Cooldown
     * etc
     * @param target: Target to apply the effect to
     */
    public abstract void applyEffect(Unit target);
}
