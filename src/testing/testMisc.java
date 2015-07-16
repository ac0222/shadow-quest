package testing;
import org.newdawn.slick.SlickException;

import env.Position;
import env.Stats;
import unit.Unit;

public class testMisc {
	public static void main(String argv[]) 
		throws SlickException
	{
		Position p1 = new Position(0,0);
		Position p2 = new Position(3,4);
		Stats s1 = new Stats(1,0,0,0);
		Unit u1 = null;
		
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p2.getDist(p1));
		System.out.println(s1);
		System.out.println(u1);

	}

}
