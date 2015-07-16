package util;
/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Adrian Cheung <acheung1>
 */

import org.newdawn.slick.SlickException;

import env.Position;
import env.RPG;
import unit.Player;

/** Represents the camera that controls our viewpoint.
 */
public class Camera
{

    /** The unit this camera is following */
    private Player unitFollow;
    
    /** The width and height of the screen */
    /** Screen width, in pixels. */
    public final int screenwidth;
    /** Screen height, in pixels. */
    public final int screenheight;

    
    /** The camera's position in the world, in x and y coordinates. */
   Position pos;

    /** Return camera's current position
     * @return Camera's current position
     */
    public Position getPos() {
    	return this.pos;
    }
    
    /** Create a new Camera object. 
     * @param player: The player which the camera will follow
     * @param screenwidth: width (in pixels) of the camera
     * @param screenheight height (in pixels) of the camera
     */
    public Camera(Player player, int screenwidth, int screenheight)
    {   
    	this.pos = player.getPos();
    	this.screenwidth = screenwidth;
    	this.screenheight = screenheight;
    	this.unitFollow = player;
    }

    /** Update the game camera to recentre it's viewpoint around the player 
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public void update()
    throws SlickException
    {
    	this.pos = unitFollow.getPos();
    }
    
    /** @return Returns the minimum x value on screen 
     */
    public int getMinX(){
    	return (int)(this.pos.getxPos() - screenwidth/2);
    }
    

    /**
     * @return Returns the maximum x value on screen 
     */
    public int getMaxX(){
    	return (int)(this.pos.getxPos() + screenwidth/2);
    }
    
    /** @return Returns the minimum y value on screen 
     */
    public int getMinY(){
    	return (int)(this.pos.getyPos() - (screenheight - RPG.PANELHEIGHT)/2);
    }
    
    /** @return Returns the maximum y value on screen 
     */

    public int getMaxY(){
    	return (int)(this.pos.getyPos() + (screenheight - RPG.PANELHEIGHT)/2);
    }
    
	/**
	 * Checks whether the given map position is currently being
	 * displayed by the camera, with some offset in pixels
	 * @param pos: Position to check
	 * @return true if position is on screen, false otherwise
	 */
	public boolean onScreen(Position pos){
		final int OFFSET = 50;
		if(pos.getxPos() < this.getMinX() - OFFSET||
				pos.getxPos() > this.getMaxX() + OFFSET){
			return false;
		}
		if(pos.getyPos() < this.getMinY() - OFFSET||
				pos.getyPos() > this.getMaxY() + OFFSET){
			return false;
		}
		return true;
	}

    /** Tells the camera to follow a given unit. 
     * @param unit: Unit to follow
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public void followUnit(Object unit)
    throws SlickException
    {
    	unitFollow = (Player)unit;
    }
    
}