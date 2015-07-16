package env;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import util.Camera;

/**
 * A very broad super class encompassing all classes except camera
 * All GameObjects have a position and are renderable, and hence have
 * images associated with them. All GameObjects also have a name. * 
 * @author Adrian Cheung
 */
public abstract class GameObject {
	/** The image used when rendering the object*/
	protected Image img;
	/** The position on the map of the game object*/
	protected Position pos;
	/** The name of the game object*/
	protected String name;
	
	
	// getters and setters	
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public Position getPos() {
		return pos;
	}
	public void setPos(Position pos) {
		this.pos = pos;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *
	 * @param name: Name of the game object
	 * @param pos: Spawn position of the game object
	 * @param ref: Folder location of image reference
	 * @throws SlickException
     * General exception thrown by Slick library classes
	 */
	public GameObject(String name, Position pos, String ref)
	throws SlickException
	{
		this.name = name;
		this.pos = pos;
		this.img = new Image(ref);
	}
	
	
    /**
     * Render the GameObject at the specified position on the SCREEN, not the map
     * @param x: x location on the map to render image at
     * @param y: y location on the map to render image at
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public void render(float x, float y)
    throws SlickException
    {
    	img.drawCentered(x, y);
    }	
    /**
     * Render the GameObject in the centre of the screen
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public void renderCentre()
    throws SlickException
    {
       	this.render(RPG.SCREENWIDTH/2, (RPG.SCREENHEIGHT - RPG.PANELHEIGHT)/2);
    }
    	    
    /**
     * Return an object's position on screen as the camera moves
     * @param camera: Camera displaying the world
     * @return Position on screen relative to the camera
     */
    public Position relativePos(Camera camera){
       	float dx, dy;
       	dx = (float)(camera.getPos().getxPos() - this.getPos().getxPos());
    	dy = (float)(camera.getPos().getyPos() - this.getPos().getyPos());
    	return new Position(RPG.SCREENWIDTH/2 - dx, (RPG.SCREENHEIGHT - RPG.PANELHEIGHT)/2 - dy);
    }
    	    
    /**
     * Render the GameObject at some location onscreen relative to the camera's
     * position on the map and the GameObject's position on the map
     * @param camera: Camera displaying the world
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public void renderOther(Camera camera) 
    throws SlickException{
    	Position pos;
    	pos = relativePos(camera);
    	this.render((float)pos.getxPos(), (float)pos.getyPos());
    }
}
