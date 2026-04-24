package natac;

import edu.princeton.cs.algs4.DepthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdRandom;

/**
 * builds the overall board for pilgrims of natac and establishes logic of
 * building and earning victory points
 * 
 * @author Korbin Ordiway
 */
public class Board {

	private Tile[] tiles;
	private Graph graph;
	private Crossroads[] crossroads;
	private ST<String, Player> roadOwner;
	private int[][] tileCrossroads = { { 0, 3, 4, 7, 8, 12 }, { 1, 4, 5, 8, 9, 13 }, { 2, 5, 6, 9, 10, 14 },
			{ 7, 11, 12, 16, 17, 22 }, { 8, 12, 13, 17, 18, 23 }, { 9, 13, 14, 18, 19, 24 }, { 10, 14, 15, 19, 20, 25 },
			{ 16, 21, 22, 27, 28, 33 }, { 17, 22, 23, 28, 29, 34 }, { 18, 23, 24, 29, 30, 35 },
			{ 19, 24, 25, 30, 31, 36 }, { 20, 25, 26, 31, 32, 37 }, { 28, 33, 34, 38, 39, 43 },
			{ 29, 34, 35, 39, 40, 44 }, { 30, 35, 36, 40, 41, 45 }, { 31, 36, 37, 41, 42, 46 },
			{ 39, 43, 44, 47, 48, 51 }, { 40, 44, 45, 48, 49, 52 }, { 41, 45, 46, 49, 50, 53 }, };

	public Board() {
		tiles = new Tile[19];
		crossroads = new Crossroads[54];
		roadOwner = new ST<String, Player>();
		buildTiles();
		buildCrossroads();
		buildGraph();
	}

	/**
	 * randomly assigns tiles a number token and resorce type at the start of each
	 * game allowing for random map generation
	 */
	private void buildTiles() {
		int[] numbs = { 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12 };
		ResourceType[] resource = { ResourceType.WOOD, ResourceType.WOOD, ResourceType.WOOD, ResourceType.WOOD,
				ResourceType.SHEEP, ResourceType.SHEEP, ResourceType.SHEEP, ResourceType.SHEEP, ResourceType.WHEAT,
				ResourceType.WHEAT, ResourceType.WHEAT, ResourceType.WHEAT, ResourceType.BRICK, ResourceType.BRICK,
				ResourceType.BRICK, ResourceType.ORE, ResourceType.ORE, ResourceType.ORE, null };
		StdRandom.shuffle(resource);
		StdRandom.shuffle(numbs);

		int j = 0;
		for (int i = 0; i < 19; i++) {
			if (resource[i] == null)
				tiles[i] = new Tile(null, 0);
			else {
				tiles[i] = new Tile(resource[i], numbs[j]);
				j++;
			}
		}
	}

	private void buildCrossroads() {
		for (int i = 0; i < 54; i++) {
			crossroads[i] = new Crossroads();
		}
	}

	private void buildGraph() {
		graph = new Graph(new In("src/natac/resources/graph.txt"));
	}

	/**
	 * builds each individual player graph to be used in tracking of longest road
	 * 
	 * @param player
	 * @return
	 */
	private Graph buildPlayerGraph(Player player) {
		Graph playerGraph = new Graph(54);
		for (int v = 0; v < 54; v++) {
			for (int w : graph.adj(v)) {
				if (w > v) {
					Player owner = getRoadOwner(v, w);
					if (owner != null && owner == player) {
						playerGraph.addEdge(v, w);
					}
				}
			}
		}
		return playerGraph;
	}

	/**
	 * returns a specific tile
	 * 
	 * @param ind
	 * @return
	 */
	public Tile tileAt(int ind) {
		return tiles[ind];
	}

	/**
	 * returns all tiles
	 * 
	 * @return
	 */
	public Tile[] allTiles() {
		return tiles;
	}

	/**
	 * returns a specific crossroad
	 * 
	 * @param ind
	 * @return
	 */
	public Crossroads crossroadsAt(int ind) {
		return crossroads[ind];
	}

	/**
	 * returns all 54 crossroads
	 * 
	 * @return
	 */
	public Crossroads[] allCrossroads() {
		return crossroads;
	}

	/**
	 * finds who owns a road using the symbol table values
	 * 
	 * @param v
	 * @param w
	 * @return owner of road(edge)
	 */
	public Player getRoadOwner(int v, int w) {
		return roadOwner.get(edgeKey(v, w));
	}

	/**
	 * tracks edges by saving them as a string to be used in the Symbol table
	 * allowing for easy tracking of owner ship
	 * 
	 * @param v
	 * @param w
	 * @return
	 */
	private String edgeKey(int v, int w) {
		return Math.min(v, w) + "-" + Math.max(v, w);
	}

	/**
	 * places structure at a vertex and sets owner along with structure type while
	 * awarding new owner with victory points
	 * 
	 * @param ind
	 * @param structure
	 * @param owner
	 */
	public void placeStructure(int ind, StructureType structure, Player owner) {
		crossroads[ind].setStructure(structure);
		crossroads[ind].setOwner(owner);

		if (structure == StructureType.SETTLEMENT) {
			owner.changeVP(1);
		} else if (structure == StructureType.CITY) {
			owner.changeVP(1);
		}
	}

	/**
	 * checks if settlement placement would be valid by seeing that it is at least 2
	 * roads(edges) away from another settlement
	 * 
	 * @param ind
	 * @return
	 */
	public boolean isValidSettlement(int ind) {
		if (!crossroads[ind].isEmpty())
			return false;

		for (int adj : graph.adj(ind)) {
			if (!crossroads[adj].isEmpty())
				return false;
		}

		return true;
	}

	/**
	 * assigns owner to a certain vertex edge by putting a road down
	 * 
	 * @param v
	 * @param w
	 * @param owner
	 */
	public void placeRoad(int v, int w, Player owner) {
		roadOwner.put(edgeKey(v, w), owner);
	}

	/**
	 * checks if a road is valid to play in this spot by checking adjacent roads and
	 * settelemnts
	 * 
	 * @param v
	 * @param w
	 * @param owner
	 * @return true if valid false if not valid
	 */
	public boolean isValidRoad(int v, int w, Player owner) {
		if (getRoadOwner(v, w) != null)
			return false;
		return hasRoadOrSettlement(v, owner) || hasRoadOrSettlement(w, owner);
	}

	/**
	 * Checks if a player has a structure or adjacent road at the given vertex. Used
	 * by isValidRoad to check road placement adjacency rules.
	 * 
	 * @param v
	 * @param owner
	 * @return if road or settlement at v
	 */
	private boolean hasRoadOrSettlement(int v, Player owner) {
		if (crossroads[v].getOwner() == owner)
			return true;

		for (int w : graph.adj(v)) {
			if (getRoadOwner(v, w) == owner)
				return true;
		}

		return false;
	}

	/**
	 * tracks the longest road for each individual player
	 * 
	 * @param owner
	 * @return
	 */
	public int longestRoad(Player owner) {
		Graph playerGraph = buildPlayerGraph(owner);
		int longest = 0;
		for (int v = 0; v < 54; v++) {
			DepthFirstPaths dfs = new DepthFirstPaths(playerGraph, v);
			for (int w = 0; w < 54; w++) {
				if (dfs.hasPathTo(w)) {
					int length = 0;
					for (int x : dfs.pathTo(w)) {
						length++;
					}
					length--;
					if (length > longest) {
						longest = length;
					}
				}
			}
		}
		owner.setLongestRoad(longest);
		return longest;
	}

	/**
	 * distributes resources to all players on a tile with a rolled number token
	 * 
	 * @param roll
	 * @param players
	 */
	public void distributeResources(int roll, Player[] players) {
		for (int i = 0; i < 19; i++) {
			if (tiles[i].getNumberToken() == roll) {
				for (int tc : tileCrossroads[i]) {
					if (crossroads[tc].hasSettlement())
						crossroads[tc].getOwner().addResource(tiles[i].getType(), 1);
					else if (crossroads[tc].hasCity())
						crossroads[tc].getOwner().addResource(tiles[i].getType(), 2);

				}
			}
		}
	}
}
