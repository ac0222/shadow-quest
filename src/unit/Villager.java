package unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import env.Position;
import env.Stats;
import env.World;
import util.Camera;


/**
 * Villagers are friendly units who help the player. 
 * The player can interact with villagers if they are close enough,
 * prompting some dialogue from the villager, and some event to occur.
 * Villagers cannot move, attack, or be attacked.
 * @author Adrian Cheung
 */
public abstract class Villager extends Unit{
	protected final double TALKCOOLDOWN = 4000;
	protected double talkTimer;
	protected String dialogue;
	

	public String getDialogue(){
		return this.dialogue;
	}
	

	public double getTalkTimer() {
		return talkTimer;
	}


	public void setTalkTimer(double talkTimer) {
		this.talkTimer = talkTimer;
	}

	/** Constructor to make a new Villager object.
	 * @param pos: Position on the map which the unit spawns at
	 * @param stats: The base stats the unit has when it is created
	 * @param name: Unit's name
	 * @param ref: Folder location of image used when rendering the unit
	 * @throws SlickException
	 * General exception thrown by Slick library classes
	 */
	public Villager(Position pos, Stats stats, String name, String ref) 
	throws SlickException
	{
		super(pos, stats, name, ref);
		talkTimer = 0;
	}
	
	/* (non-Javadoc)
	 * @see ShadowQuest.Unit#update(double, double, double, double, int, ShadowQuest.World)
	 */
	/**
	 * This update only serves to decrease the cooldown since the villager was last spoken to
	 */
	public void update(double dir_x, double dir_y, double a_on, double t_on, int delta, World world)
	throws SlickException
	{
		if(this.talkTimer > 0){
			talkTimer -= delta;
		}
	}
	
    /**
     * Render the relevant dialogue on the screen. Text is rendered slightly
     * above the villager's label.
     * @param g: Slick graphics object, used for drawing
     * @param camera: Camera used for displaying the world
     */
    public void renderDialogue(Graphics g, Camera camera){
      	// Relative position of unit to central unit
    	Position relpos = relativePos(camera);
    	
    	// Label colours
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp

        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangle
        int bar_width, bar_height;  // Size of rectangle to draw
        

        // Display the unit's name
        text_x = (int)relpos.getxPos();
        text_y = (int)(relpos.getyPos() - this.getImg().getHeight());
        text = this.getDialogue(); 
        
        if(g.getFont().getWidth(text) + 6 < 70){
            bar_width = 70;
        }else{
        	bar_width = g.getFont().getWidth(text);
        }
        bar_height = 20;
        bar_x = text_x - bar_width/2;
        bar_y = text_y;

        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
    }
    
    /**
     * Render the villager image itself, then render the villager's label
     * Next, render the villager's dialogue appropriately depending on the context
     * if the villager was interacted with recently.
     * @param g: Slick graphics object, used for drawing
     * @param camera: Camera, displays the game world
     * @throws SlickException
	 * General exception thrown by Slick library classes
	 */
    public void renderVillager(Graphics g, Camera camera)
    throws SlickException
    {
    	renderOther(camera);
    	renderLabel(g, camera);
    	if(this.getTalkTimer() > 0){
			this.renderDialogue(g, camera);
    	}
    }

	/**
	 * Determine the dialogue the villager should say, depending on the context of the
	 * interaction.
	 * Could also depend on the target
	 * @param target: Unit which the dialogue is directed to
	 */
	public abstract void sayNext(Unit target);
		
	/**
	 * Villager performs some action, depending on the villager type and the
	 * context of the interaction.
	 * Could also depend on the target
	 * @param target: Unit which the villager is interacting with
	 */
	public abstract void interact(Unit target);
}
