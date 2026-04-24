package natac;

import java.awt.Color;

import edu.princeton.cs.algs4.ST;

/**
 * Creates Player objects that will hold all values associated with the player
 * for clean and organized storage and updates
 * 
 * @author Korbin Ordiway
 */
public class Player {

	private String name;
	private Color color;
	private int vp;
	private int cannonsFired;
	private int longestRoad;
	private boolean hasCaravan;
	private boolean oasisBonus;
	private ST<ResourceType, Integer> resources;

	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
		this.vp = 0;
		this.longestRoad = 0;
		this.resources = new ST<ResourceType, Integer>();

		// constructs hand to have 0 of all resource types
		for (ResourceType type : ResourceType.values()) {
			resources.put(type, 0);
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the vp
	 */
	public int getVP() {
		return vp;
	}

	/**
	 * changes vp of this player negative if losing positive if gaining
	 * 
	 * @param amount of change in vp balance
	 */
	public void changeVP(int amount) {
		this.vp += amount;
	}

	/**
	 * @return the longestRoad
	 */
	public int getLongestRoad() {
		return longestRoad;
	}

	/**
	 * @param longestRoad the longestRoad to set
	 */
	public void setLongestRoad(int longestRoad) {
		this.longestRoad = longestRoad;
	}

	/**
	 * gets total resources held in players hand
	 * 
	 * @return
	 */
	public int resourceCount() {
		int total = 0;
		for (ResourceType type : ResourceType.values()) {
			total += resources.get(type);
		}
		return total;
	}

	/**
	 * gets total amount of a specified resource in players hand
	 * 
	 * @param type
	 * @return the number of specified resource held
	 */
	public int getResource(ResourceType type) {
		return resources.get(type);
	}

	/**
	 * adds resource to hand based on type and amount
	 * 
	 * @param type
	 * @param amount
	 */
	public void addResource(ResourceType type, int amount) {
		resources.put(type, resources.get(type) + amount);
	}

	/**
	 * removes resource from hand based on type and amount
	 * 
	 * @param type
	 * @param amount
	 */
	public void removeResource(ResourceType type, int amount) {
		resources.put(type, resources.get(type) - amount);
	}

	/**
	 * checks build requirements for each build to see if player holds enough of
	 * resources to build a selected buildable
	 * 
	 * @param b
	 * @return
	 */
	public boolean canAfford(Buildable b) {

		if (b == Buildable.ROAD) {
			return getResource(ResourceType.WOOD) >= 1 && getResource(ResourceType.BRICK) >= 1;
		} else if (b == Buildable.SETTLEMENT) {
			return getResource(ResourceType.WOOD) >= 1 && getResource(ResourceType.BRICK) >= 1
					&& getResource(ResourceType.WHEAT) >= 1 && getResource(ResourceType.SHEEP) >= 1;
		} else if (b == Buildable.CITY) {
			return getResource(ResourceType.WHEAT) >= 2 && getResource(ResourceType.ORE) >= 3;
		}

		return false;

	}

	/**
	 * @return the resources
	 */
	public ST<ResourceType, Integer> allResources() {
		return resources;
	}

}
