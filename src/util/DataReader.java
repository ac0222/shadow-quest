package util;

import item.Amulet;
import item.Elixir;
import item.Item;
import item.Sword;
import item.Tome;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;

import env.Position;
import env.Stats;
import unit.AggressiveMonster;
import unit.Healer;
import unit.Monster;
import unit.PassiveMonster;
import unit.Peasant;
import unit.Player;
import unit.Prince;
import unit.Villager;

/**
 * Read in all the data from external text files, including:
 * -stats
 * -names
 * -spawn locations (on the map)
 * -image locations
 * for all units and items, and use this data
 * to create the relevant objects in the data structure passed in.
 * @author Adrian Cheung
 */
public class DataReader {
	/**
	 * @param datafile: file containing player data
	 * @param player: player to initialize
	 * @return Return the newly initialized
	 * @throws SlickException
     * General exception thrown by Slick library classes
	 */
	public static Player readPlayer(String datafile, Player player) 
	throws SlickException
	{
		FileReader fr = null;
    	BufferedReader inFile = null;
    		
    	String line;
    	String statvalues[] = null;
    	String posA[] = null;
    	
    	String imgref = "", name = "";
    	Stats tmpStat = null;
    	Position tmpPos = null;
    		
    	
    	try {
    		fr = new FileReader(datafile);
    		inFile = new BufferedReader(fr);	
    	} 
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	try {
    		line = inFile.readLine();	
    		name = line;
    		imgref = inFile.readLine();
    		statvalues = inFile.readLine().split(",");
    		posA = inFile.readLine().split(",");
   			
    		tmpStat = new Stats(Double.parseDouble(statvalues[0]),
    				Double.parseDouble(statvalues[1]),
    				Double.parseDouble(statvalues[2]),
    				Double.parseDouble(statvalues[3]));
    		
    		tmpPos = new Position(Integer.parseInt(posA[0]),
    				Integer.parseInt(posA[1]));
    		player = new Player(tmpPos, tmpStat, name, imgref);  
        	inFile.close();
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    	return player;
	}
	
	/**
	 * @param datafile File containing data for all the villagers
	 * @param villagers An ArrayList of Villagers, which the stores the newly 
	 * created villager objects
	 * @throws SlickException
	 * General exception thrown by Slick library classes
	 */
	public static void readVillager(String datafile, ArrayList<Villager> villagers) 
	throws SlickException
	{
		FileReader fr = null;
    	BufferedReader inFile = null;
    		
    	String line;
    	String statvalues[] = null;
    	String data[] = null;
    	String posA[] = null;
    	
    	String classname = "", imgref = "", name = "";
    	Stats tmpStat = null;
    	Position tmpPos = null;
    		
    	
    	try {
    		fr = new FileReader(datafile);
    		inFile = new BufferedReader(fr);	
    	} 
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	try {
    		// data file has three lines of data per villager
    		line = inFile.readLine();	
    		while(line != null){
    			// assign the three lines 
    			data = line.split(",");
    			statvalues = inFile.readLine().split(",");
    			posA = inFile.readLine().split(",");

    			classname = data[0];
    			name = data[1];
    			imgref = data[2];
    			
    			tmpStat = new Stats(Double.parseDouble(statvalues[0]),
    					Double.parseDouble(statvalues[1]),
    					Double.parseDouble(statvalues[2]),
    					Double.parseDouble(statvalues[3]));
    			
    			tmpPos = new Position(Integer.parseInt(posA[0]),
    					Integer.parseInt(posA[1]));
    			
    			if(classname.equals("Prince")){
    				villagers.add(new Prince(tmpPos, tmpStat, name, imgref));
    			}else if(classname.equals("Healer")){
    				villagers.add(new Healer(tmpPos, tmpStat, name, imgref));
    			}
    			else if(classname.equals("Peasant")){
    				villagers.add(new Peasant(tmpPos, tmpStat, name, imgref));
    			}
    			line = inFile.readLine();
    		}
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}	
	}

    /**
     * @param datafile: File containing all the data on a single monster 'type'
     * @param monsters: ArrayList to store newly created monsters
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
	
    public static void readMonster(String datafile, ArrayList<Monster> monsters) 
    throws SlickException
    {
    	FileReader fr = null;
    	BufferedReader inFile = null;
    		
    	String line;
    	String statvalues[] = null;
    	String posA[] = null;
    	
    	String classname = "", imgref = "", type = "";
    	Stats tmpStat = null;
    	Position tmpPos = null;
    		
    	
    	try {
    		fr = new FileReader(datafile);
    		inFile = new BufferedReader(fr);	
    	} 
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    		
    	try {
    		classname = inFile.readLine();
    		imgref = inFile.readLine();
    		type = inFile.readLine();
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
    			
    			if(classname.equals("Passive")){
    				monsters.add(new PassiveMonster(tmpPos, tmpStat, type, imgref));
    			}else if(classname.equals("Aggressive")){
    				monsters.add(new AggressiveMonster(tmpPos, tmpStat, type, imgref));
    			}
    			line = inFile.readLine();
    		}
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}	
    }
    
    /**
     * @param datafile: File containing item information
     * @param items: ArrayList to store newly created item objects
     * @throws SlickException
     * General exception thrown by Slick library classes
     */
    public static void readItem(String datafile, ArrayList<Item> items) 
    throws SlickException
    {
    	FileReader fr = null;
    	BufferedReader inFile = null;
    		
    	String line, splitline[];
    	String classname = "", imgref = "", name = "";
    	Position tmpPos = null;
    		
    	
    	try {
    		fr = new FileReader(datafile);
    		inFile = new BufferedReader(fr);	
    	} 
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    		
    	try { 		
    		line = inFile.readLine();
    		while(line != null){
    			splitline = line.split(",");
    			classname = splitline[0];
    			name = splitline[1];
    			tmpPos = new Position(Integer.parseInt(splitline[2]),
    								  Integer.parseInt(splitline[3]));
    			imgref = splitline[4];		
    			if(classname.equals("Amulet")){
    				items.add(new Amulet(name, tmpPos, imgref));
    			}else if(classname.equals("Sword")){
    				items.add(new Sword(name, tmpPos, imgref));
    			}else if(classname.equals("Tome")){
    				items.add(new Tome(name, tmpPos, imgref));
    			}else if(classname.equals("Elixir")){
    				items.add(new Elixir(name, tmpPos, imgref));
    			}
    			line = inFile.readLine();
    		}
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}	
    }
    
}



