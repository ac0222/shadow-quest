package unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import env.GameObject;
import env.Position;
import env.Stats;
import env.World;
import util.Camera;

/**
 * Units in general represent humans in the game world
 * Most units can move, and all units interact with the player in some
 * way. All units have stats.
 * @author Adrian Cheung
 */
public abstract class Unit extends GameObject{
	// data members

	/** Image used to represent the unit*/
	protected Image RightImage;
	protected Image LeftImage;
	
	/** Unit base stats*/
	protected Stats stats;
	
	/** Unit current stats*/
	protected double hp;
	protected int cdTimer;

	// getters and setters

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public int getCdTimer() {
		return cdTimer;
	}

	public void setCdTimer(int cdTimer) {
		this.cdTimer = cdTimer;
	}

	/** Constructor to make a new unit object.
	 * @param pos: Position on the map which the unit spawns at
	 * @param stats: The base stats the unit has when it is created
	 * @param name: Unit's name
	 * @param ref: Folder location of image used when rendering the unit
	 * @throws SlickException
	 * General exception thrown by Slick library classes
	 */
	public Unit(Position pos, Stats stats, String name, String ref) 
	throws SlickException
	{
		super(name, pos, ref);
		this.stats = stats;
		this.hp = stats.getMaxHP();
		this.cdTimer = 0;
    	RightImage = this.img;
    	LeftImage = this.img.getFlippedCopy(true, false);
    }
	
	
	/**
	 * Check the distance in pixels between this unit
	 * and the target unit
	 * @param target: Unit to get distance between
	 * @return: Distance between units, in pixels
	 */
	public double checkRange(Unit target){
		return this.pos.getDist(target.getPos());
	}
	
    /**
     * Render the unit's label, which displays the name of the unit
     * as well as the current Health of the unit. The label is rendered
     * a small distance above the image of the unit.
     * @param g: Slick graphics object, used for drawing
     * @param camera: Camera, displays the game world
     */
    public void renderLabel(Graphics g, Camera camera){
    	// Relative position of unit to central unit
    	Position relpos = relativePos(camera);
    	
    	// Label colours
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp

        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangle
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle
        
        float health_percent;       // Unit's health, as a percentage

        // Display the unit's name
        text_x = (int)relpos.getxPos();
        text_y = (int)(relpos.getyPos() - this.getImg().getHeight()/1.5);
        text = this.getName(); 
        
        if(g.getFont().getWidth(text) + 6 < 70){
            bar_width = 70;
        }else{
        	bar_width = g.getFont().getWidth(text);
        }
        bar_height = 20;
        bar_x = text_x - bar_width/2;
        bar_y = text_y;

        health_percent = (float)(this.getHp()/this.getStats().getMaxHP());
        if(health_percent < 0){
        	health_percent = 0;
        }
        hp_bar_width = (int) (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
    }
	
	/**
	 * Update method takes user input for controlling the Player character and
	 * translates this into some in-game event. The update is done many times per second. The delta parameter is used
	 * to standardise gameplay across systems of differing performance. 
	 * @param dir_x: User input flag for horizontal movement: move left if negative, right if positive, don't move if zero
	 * @param dir_y: User input flag for vertical movement; same as above
	 * @param a_on: User input flag for attack
	 * @param t_on: User input flag for interact
	 * @param delta: Number of milliseconds since last update
	 * @param world: World in which updates are taking place
	 * @throws SlickException
	 * General exception thrown by Slick library classes
	 */
	public abstract void update(double dir_x, double dir_y, double a_on, double t_on, int delta, World world)
	throws SlickException;

}
