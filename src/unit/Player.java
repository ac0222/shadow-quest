package unit;
/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Adrian Cheung <acheung1>
 */

import item.Inventory;
import item.Item;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import env.Position;
import env.RPG;
import env.Stats;
import env.World;

/** Represents a single player character 
 * The player can be controlled by the user and can perform a number of actions:
 * - Move: 2D movement
 * - Attack: Deal damage to all enemies within range
 * - Interact: Interact with all villagers within range
 * - Pickup: Pickup all items within range
 * @author Adrian Cheung
 *
 */
public class Player extends Unit{
	private final String PANELREF = "/assets/panel.png";
	private Image panel;
	private Inventory inventory;
	
	private final double ATKRANGE = 50;
	private final double VILRANGE = 50;
	private final double ITEMPICKUPRANGE = 50;
	private final Position RESPAWNPOS = new Position(738, 549);
	private final double TALKCD = 500;
	private double talkTimer;
	
	/** Constructor to make a new unit object.
	 * @param pos: Position on the map which the unit spawns at
	 * @param stats: The base stats the unit has when it is created
	 * @param name: Unit's name
	 * @param ref: Folder location of image used when rendering the unit
	 * @throws SlickException
	 * General exception thrown by Slick library classes
	 */
	public Player(Position pos, Stats stats, String name, String ref) 
	throws SlickException
	{
		super(pos, stats, name, ref);
		this.panel = new Image(PANELREF);
		inventory = new Inventory();
		this.talkTimer = 0;
	}
	
	// getters 

	public Image getPanel() {
		return panel;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public double getATKRANGE() {
		return ATKRANGE;
	}

	public double getVILRANGE() {
		return VILRANGE;
	}

	public double getITEMPICKUPRANGE() {
		return ITEMPICKUPRANGE;
	}
	


	/** Movement in the x direction
	 * 
	 * @param dir: If positive move right, if negative move left, else stay still
	 * @param delta: Number of milliseconds since last update
	 * @param world: World in which movement takes place
	 */
	public void move_x(int dir, int delta, World world){
		Position newPos;
		newPos = new Position(this.pos.getxPos() + delta*this.stats.getSpeed()*dir, this.pos.getyPos());
		if(world.isBlocked(newPos) == false && dir != 0){
				this.pos = newPos;
		}
	}
	
	/** Movement in the y direction
	 * 
	 * @param dir: If positive move right, if negative move left, else stay still
	 * @param delta: Number of milliseconds since last update
	 * @param world: World in which movement takes place
	 */
	public void move_y(int dir, int delta, World world){
		Position newPos;
		newPos = new Position(this.pos.getxPos(), this.pos.getyPos() + delta*this.stats.getSpeed()*dir);
		if(world.isBlocked(newPos) == false && dir != 0){
				this.pos = newPos;
		}
	}
	
	/**
	 * Deal damage to every monster within range. Damage dealt
	 * is a random number between 0 and Player's MaxDmg inclusive.
	 * @param world: game world in which the attack occurs
	 */
	public void attack(World world){
		double dmg = Math.random()*this.stats.getDamage();
		for(Monster m: world.getMonsters()){
			if(checkRange(m) <= ATKRANGE){
				m.takeDamage(dmg);
				this.cdTimer = (int)this.stats.getCoolDown();
			}
		}
	}
	
	/**
	 * @param dmg: Reduce player's current hp by specified amount
	 */
	public void takeDamage(double dmg){
		setHp(this.hp - dmg);
	}
	
	/**
	 * Interact with all villagers within range of the player
	 * @param world: World in which player and villagers exist
	 */
	public void interact(World world){
		for(Villager v: world.getVillagers()){
			if((checkRange(v) <= VILRANGE)){
				v.interact(this);
				this.talkTimer = TALKCD;
			}
		}
	}
	
	/**
	 * When the player's health falls to zero or less, player is teleported
	 * back to respawn location, specified as a constant, and player's current
	 * HP is restored to maximum.
	 */
	public void reSpawn(){
		this.pos = RESPAWNPOS;
		this.hp = this.stats.getMaxHP();
	}
	
	/**
	 * Pick up all the items within range of the player, and apply their effects once.
	 * Important to note that the effect causes a permanent change and need only be
	 * applied once.
	 * @param world: world which items and player exist in
	 * @throws SlickException
 	 * General exception thrown by Slick library classes
	 */
	public void getItem(World world)
	throws SlickException
	{
		for(Item i : world.getItems()){
			if(this.pos.getDist(i.getPos()) <= ITEMPICKUPRANGE && !i.isHeld()){
				this.inventory.addItem(i);
				i.applyEffect(this);
				i.setHeld(true);
			}
		}
	}
	
    /** Renders the player's status panel.
     * @param g The current Slick graphics context.
     * @throws SlickException 
	 * General exception thrown by Slick library classes
	 */
	public void renderPanel(Graphics g) 
	throws SlickException
	{
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp

        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle
        int inv_x, inv_y;           // Coordinates to draw inventory item

        float health_percent;       // Player's health, as a percentage

        // Panel background image
        this.getPanel().draw(0, RPG.SCREENHEIGHT - RPG.PANELHEIGHT);

        // Display the player's health
        text_x = 15;
        text_y = RPG.SCREENHEIGHT - RPG.PANELHEIGHT + 25;
        g.setColor(LABEL);
        g.drawString("Health:", text_x, text_y);
        text = (int)this.getHp() + "/" + (int)this.getStats().getMaxHP();                               
        bar_x = 90;
        bar_y = RPG.SCREENHEIGHT - RPG.PANELHEIGHT + 20;
        bar_width = 90;
        bar_height = 30;
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

        // Display the player's damage and cooldown
        text_x = 200;
        g.setColor(LABEL);
        g.drawString("Damage:", text_x, text_y);
        text_x += 80;
        text = String.valueOf((int)this.getStats().getDamage());                                    // TODO: Damage
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
        text_x += 40;
        g.setColor(LABEL);
        g.drawString("Rate:", text_x, text_y);
        text_x += 55;
        text = String.valueOf((int)this.getCdTimer());                                   // TODO: Cooldown
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's inventory
        g.setColor(LABEL);
        g.drawString("Items:", 420, text_y);
        bar_x = 490;
        bar_y = RPG.SCREENHEIGHT - RPG.PANELHEIGHT + 10;
        bar_width = 288;
        bar_height = bar_height + 20;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);

        inv_x = 490;
        inv_y = RPG.SCREENHEIGHT - RPG.PANELHEIGHT
            + ((RPG.PANELHEIGHT - 72) / 2);
        for(Item i : this.getInventory().getItems())              // TODO
        {
            // Render the item to (inv_x, inv_y)
        	i.getImg().draw(inv_x, inv_y);
            inv_x += 72;
        }
    }
	
	/**
	 * Render the player itself in the middle of the screen, then the player's status panel
	 * @param g: Slick graphics object, used for drawing
	 * @throws SlickException
 	 * General exception thrown by Slick library classes

	 */
	public void renderPlayer(Graphics g) 
	throws SlickException
	{
		renderCentre();
		renderPanel(g);
	}
	
	/** On update:
	 * - Check death and respawn if dead
	 * - Decrease talk and attack cooldowns, if above zero
	 * - Move player
	 * - Perform attack if relevant key is pressed
	 * - Attempt interaction if relevant key is pressed 
	 */
	/* (non-Javadoc)
	 * @see ShadowQuest.Unit#update(double, double, double, double, int, ShadowQuest.World)
	 */
	public void update(double dir_x, double dir_y, double a_on, double t_on, int delta, World world)
	throws SlickException
    {
		if(this.hp <= 0){
			reSpawn();
		}
		if(cdTimer > 0){
			cdTimer -= delta;
		}
		if(talkTimer > 0){
			talkTimer -= delta;
		}
    	if(dir_x != 0){
    		this.move_x((int)dir_x, delta, world);
    		// flip player to face direction of movement
    		if(dir_x < 0){
    			this.img = LeftImage;
    		}else{
    			this.img = RightImage;
    		}
    	}
    	
    	if(dir_y != 0){
    		this.move_y((int)dir_y, delta, world);
    	}
    	
    	if(a_on != 0 && cdTimer <= 0){
    		attack(world);
    	}
    	
    	if(t_on != 0 && talkTimer <= 0){
    		interact(world);
    	}
    	
    	getItem(world);
    }


}
