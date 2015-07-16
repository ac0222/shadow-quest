package unit;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import env.Position;
import env.Stats;
import env.World;
import util.Camera;

/**
 * Class providing methods to enable monster interaction. Monsters are split into two types:
 * Aggressive Monsters and Passive Monsters, which have different behaviour.
 * All monsters can move, have stats, and can be attacked and killed by the player.
 * @author Adrian Cheung
 */
public abstract class Monster extends Unit{	
	/**
	 * Creates a monster object
	 * 
	 * @param pos: Position on the map the monster spawns at
	 * @param stats: Monster base stats, refer to Stats class for more information 
	 * @param name: The monster 'type', e.g. Zombie, Skeleton, Bandit etc. 
	 * @param ref: Location of the image used when rendering the monster
	 * @throws SlickException
	 * General exception type thrown by Slick library classes
	 */
	public Monster(Position pos, Stats stats, String name, String ref)
	throws SlickException
	{
		super(pos, stats, name, ref);
	}
	
	/**
	 * 
	 * @param dmg: Amount to reduce monster's health by
	 */
	public void takeDamage(double dmg){
		setHp(this.hp - dmg);
	}
	
	/**
	 * Given a set distance to move per update(speed), the monster will move towards or
	 * away from a target location
	 * @param target: The target location to move towards, or away from
	 * @param world: The world in which the movement takes place
	 * @param approach: If this is true, move towards the target, else, move away
	 * @param delta: Number of milliseconds between updates
	 */
	public void moveTo(Position target, World world, boolean approach, int delta){
		double amount = this.stats.getSpeed();
		double dist_x, dist_y, dx, dy;
		double new_x, new_y;
		Position newpos;
		
		dist_x = this.pos.getxPos() - target.getxPos();
		dist_y = this.pos.getyPos() - target.getyPos();
		dx = dist_x/this.pos.getDist(target) * amount * delta;
		dy = dist_y/this.pos.getDist(target) * amount * delta;
		
		if(approach == true){
			new_x = this.pos.getxPos() - dx;
			new_y = this.pos.getyPos() - dy;
		}else{
			new_x = this.pos.getxPos() + dx;
			new_y = this.pos.getyPos() + dy;
		}
		
		newpos = new Position(new_x, this.pos.getyPos());
		if(world.isBlocked(newpos) == false){
			this.pos = newpos;
		}
		newpos = new Position(this.pos.getxPos(), new_y);
		if(world.isBlocked(newpos) == false){
			this.pos = newpos;
		}
	}
	
	/**
	 * Render the monster relative to the camera position.
	 * Render both the monster itself as well as the label 
	 * showing monster's type and current health.
	 * @param g: The Slick graphics object, used for drawing
	 * @param camera: Camera used to display the world 
	 * @throws SlickException
	 * General exception thrown by Slick library classes
	 */
	public void renderMonster(Graphics g, Camera camera)
	throws SlickException
	{
		renderOther(camera);
		renderLabel(g, camera);
	}
}
