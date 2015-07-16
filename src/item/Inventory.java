package item;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Stores all the items currently held by the player,
 * as well as some data about them, e.g.
 * number of items held.
 * @author Adrian Cheung
 */
public class Inventory {
	private ArrayList<Item> items = null;
	private int numitems;
	private int numsword;
	private int numamulet;
	private int numtome;
	private int numelixir;
	
	// getters and setters

	public int getNumitems() {
		return numitems;
	}


	public void setNumitems(int numitems) {
		this.numitems = numitems;
	}


	public int getNumsword() {
		return numsword;
	}


	public void setNumsword(int numsword) {
		this.numsword = numsword;
	}


	public int getNumamulet() {
		return numamulet;
	}


	public void setNumamulet(int numamulet) {
		this.numamulet = numamulet;
	}


	public int getNumtome() {
		return numtome;
	}


	public void setNumtome(int numtome) {
		this.numtome = numtome;
	}


	public int getNumelixir() {
		return numelixir;
	}


	public void setNumelixir(int numelixir) {
		this.numelixir = numelixir;
	}
	

	public ArrayList<Item> getItems() {
		return items;
	}

	/**
	 * Initialize an empty inventory containing zero items
	 */
	public Inventory(){
		items = new ArrayList<Item>();
		this.numitems = 0;
		this.numsword = 0;
		this.numamulet = 0;
		this.numtome = 0;
		this.numelixir = 0;

	}
		


	/**
	 * Add the specified item to the inventory and increase the
	 * relevant counters.
	 * @param item: item to be added to the inventory
	 */
	public void addItem(Item item){
		items.add(item);
		if(item instanceof Sword){
			this.numsword++;
		}else if(item instanceof Amulet){
			this.numamulet++;
		}else if(item instanceof Tome){
			this.numtome++;
		}else if(item instanceof Elixir){
			this.numelixir++;
		}
		this.numitems++;
	}
	
	/**
	 * Remove any consumed items which no longer have any use
	 * from the inventory
	 */
	public void removeUsed(){
		Iterator<Item> it = null;
		it = this.items.iterator();
		while(it.hasNext()){
			if(it.next().isConsumed()){
				it.remove();
			}
		}
	}
}
