package env;
/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Adrian Cheung <acheung1>
 */

import item.Item;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.*;

import unit.Monster;
import unit.Player;
import unit.Villager;
import util.Camera;
import util.DataReader;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
	/** Data Files*/
	// Unit data
	private final String PLAYERFILE = "data/playerdata.txt";
	private final String VILLAGERFILE = "data/villagerdata.txt";
	private final String MONSTERFILES[] = {"data/batdata.txt", "data/zombiedata.txt",
			"data/banditdata.txt", "data/skeletondata.txt", "data/draelicdata.txt"};
	// Item data
	private final String ITEMFILE = "data/itemdata.txt";
	
	/** Map details*/
	private TiledMap WorldMap;
	private int tileDim;
	private int screenX, screenY;
	private int tileX, tileY;
	private int width, height;
	
	/** Game objects*/
	private Camera camera;
	private ArrayList<Villager> villagers = new ArrayList<Villager>();
	private ArrayList<Monster> monsters = new ArrayList<Monster>();
	private Player player;
	
	private ArrayList<Item> items = new ArrayList<Item>();
	
	// getters and setters
	
    public ArrayList<Monster> getMonsters() {
		return monsters;
	}
    
    public ArrayList<Villager> getVillagers() {
		return villagers;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public Player getPlayer() {
		return player;
	}

	/** Create a new World object. 
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public World()
    throws SlickException
    {
    	WorldMap = new TiledMap("/assets/map.tmx", "/assets");
    	tileDim = WorldMap.getTileWidth();
    	setPlayer(PLAYERFILE);
    	setVillagers(VILLAGERFILE);
    	setMonsters(MONSTERFILES);
    	setItems(ITEMFILE);
    	camera = new Camera(player,RPG.SCREENWIDTH,RPG.SCREENHEIGHT);
    	width = RPG.SCREENWIDTH/tileDim + 2;
    	height = RPG.SCREENHEIGHT/tileDim + 2;
    }
    /**
     * Initialize player based on external data
     * @param playerfile: file location of data file
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public void setPlayer(String playerfile) 
    throws SlickException
    {
    	player = DataReader.readPlayer(playerfile, player);
    }
    
    /**
     * Initialize monsters based on external data
     * @param monsterfiles: An array of file locations specifying all the relevant monster files,
     * one for each type of monster
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public void setMonsters(String monsterfiles[])
    throws SlickException
    {
    	for(String file: monsterfiles){
    		DataReader.readMonster(file, monsters);
    	}
    }

    /**
     * Initialize villagers based on external data
     * @param villagerfile: A string specifying file location of file containing data 
     * for ALL the villagers in the game
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public void setVillagers(String villagerfile) 
    throws SlickException
    {
    	DataReader.readVillager(villagerfile, villagers);
    }
    
    /**
     * Initialize items based on external data
     * @param itemfile: File location of item data
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public void setItems(String itemfile)
    throws SlickException
    {
    	DataReader.readItem(itemfile, items);
    }
    
	/**
	 * Checks for terrain blocking on the tiledMap
	 * @param pos: target position
	 * @return returns true if target position is blocked off, false otherwise
	 */
	public boolean isBlocked(Position pos){
		int tileID = 0;
		int width = WorldMap.getWidth();
		int height = WorldMap.getHeight();
		
		// first check position is within map limits
		if((pos.getxPos() < 0 || pos.getxPos() > width*tileDim - 1)
			||( pos.getyPos() < 0 || pos.getyPos() > height*tileDim - 1)){
			return true;
		}
		
		// then check for tiles which block
		tileID = WorldMap.getTileId((int)pos.getxPos()/tileDim, (int)pos.getyPos()/tileDim, 0);
		return !(WorldMap.getTileProperty(tileID, "block", "0").equals("0"));
	}

	/**
	 * Removes all dead monsters (monsters with hp less than or equal to 0) from the monsters ArrayList
	 */
	public void removeDead(){
		Iterator<Monster> it = null;
    	it = this.monsters.iterator();
    	while(it.hasNext()){
    		if(it.next().getHp() <= 0){
    			it.remove();
    		}
    	}
	}
	
	/**
	 * Removes all items which have been picked up from the items ArraList
	 */
	public void removeHeldItems(){
		Iterator<Item> it = null;
    	it = this.items.iterator();
		while(it.hasNext()){
			if(it.next().isHeld()){
				it.remove();
			}
		}
	}

    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param a_on The key used to initiate an attack
     * @param t_on The key used to initiate an interaction
     * @param delta Time passed since last frame (milliseconds).
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public void update(double dir_x, double dir_y, double a_on, double t_on, int delta)
    throws SlickException
    {
    	/* Update and redraw map based on camera's position, which is in turn
   	 	based on player's position */
    	player.update(dir_x, dir_y, a_on, t_on, delta, this);
    	camera.update();
    	removeDead();
    	removeHeldItems();
    	for(Monster m: monsters){
    		m.update(dir_x, dir_y, a_on, t_on, delta, this);
    	}
    	for(Villager v: villagers){
    		v.update(dir_x, dir_y, a_on, t_on, delta, this);
    	}
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public void render(Graphics g)
    throws SlickException
    {
    	/* Draw map based on camera's position, which is in turn
    	 based on player's position */
    	screenX = 0 - camera.getMinX()%tileDim; 
    	screenY = 0 - camera.getMinY()%tileDim;
    	tileX = camera.getMinX()/tileDim;
    	tileY = camera.getMinY()/tileDim;
    	WorldMap.render(screenX,screenY,tileX,tileY,width,height);
    	
    	// render monsters, villagers (and their dialogue), items and lastly player
    	for(Monster m : monsters){
    		if(camera.onScreen(m.getPos())){
    			m.renderMonster(g, camera);
    		}
    	}
    	for(Villager v: villagers){
    		if(camera.onScreen(v.getPos())){
    			v.renderVillager(g, camera);
    		}
    	}
    	for(Item i: items){
    		if(camera.onScreen(i.getPos())){
    			i.renderItem(camera);
    		}
    	}
    	player.renderPlayer(g);
    }
}
