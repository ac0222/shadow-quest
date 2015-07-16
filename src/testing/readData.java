package testing;
import java.io.*;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import env.Position;
import env.Stats;
import unit.Player;
import unit.Unit;

public class readData {
	public static void main(String argv[]) 
		throws SlickException
	{
		FileReader fr = null;
		BufferedReader inFile = null;
		
		String ref = "data/batdata.txt";
		String line;
		String statvalues[] = null;
		String posA[] = null;
	
		String classname = "", imgref = "", name = "";
		Stats tmpStat = null;
		Position tmpPos = null;
		
		ArrayList<Unit> units = new ArrayList<Unit>();
	
		try {
			fr = new FileReader(ref);
			inFile = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			classname = inFile.readLine();
			imgref = inFile.readLine();
			name = inFile.readLine();
			statvalues = inFile.readLine().split(",");
			tmpStat = new Stats(Double.parseDouble(statvalues[0]),
					Double.parseDouble(statvalues[1]),
					Double.parseDouble(statvalues[2]),
					Double.parseDouble(statvalues[3]));
			line = inFile.readLine();
			while(line != null){
				posA = line.split(",");
				tmpPos = new Position(Integer.parseInt(posA[0]),
						Integer.parseInt(posA[1]));
				if(classname.equals("Player")){
					units.add(new Player(tmpPos, tmpStat, imgref,""));
				}
				line = inFile.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Unit u : units){
			System.out.println(u);
		}
		
	}
	
}
