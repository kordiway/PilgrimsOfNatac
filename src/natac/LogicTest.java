package natac;

import java.awt.Color;
/**
 * test logic of already made classes to simulate game sitaution without having a functional gui
 * at this time
 * 
 * @author Korbin Ordiway
 */
public class LogicTest {

	public static void main(String[] args) {
		System.out.println("=== PILGRIMS OF NATAC - BOARD TEST ===\n");

		// Create players
		Player red = new Player("Red", Color.RED);
		Player blue = new Player("Blue", Color.BLUE);
		Player[] players = { red, blue };

		// Create board
		Board board = new Board();
		System.out.println("Board created with 19 tiles and 54 crossroads\n");

		// Print the board layout
		System.out.println("--- TILE LAYOUT ---");
		for (int i = 0; i < 19; i++) {
			Tile t = board.tileAt(i);
			String resource = (t.getType() == null) ? "OASIS" : t.getType().toString();
			System.out.println("Tile " + i + ": " + resource + " (token " + t.getNumberToken() + ")");
		}

		System.out.println();

		// Test placing structures
		System.out.println("--- PLACING STRUCTURES ---");
		System.out.println("Red places settlement at crossroads 0");
		board.placeStructure(0, StructureType.SETTLEMENT, red);
		System.out.println("Red VP: " + red.getVP() + "\n");

		System.out.println("Blue places settlement at crossroads 10");
		board.placeStructure(10, StructureType.SETTLEMENT, blue);
		System.out.println("Blue VP: " + blue.getVP());

		System.out.println();
		// Test valid settlement rule
		System.out.println("--- VALID SETTLEMENT TESTS ---");
		System.out.println("Can Red place at 0 again? " + board.isValidSettlement(0) + " (expected false)");
		System.out.println("Can Red place at 3? " + board.isValidSettlement(3) + " (expected false - adjacent to 0)");
		System.out.println("Can Red place at 20? " + board.isValidSettlement(20) + " (expected true)");

		System.out.println();
		// Test placing roads
		System.out.println("--- PLACING ROADS ---");
		System.out.println("Red places road 0-3");
		board.placeRoad(0, 3, red);
		System.out.println("Red places road 3-7");
		board.placeRoad(3, 7, red);
		System.out.println("Red places road 7-11");
		board.placeRoad(7, 11, red);

		System.out.println();
		// Test valid road rule
		System.out.println("--- VALID ROAD TESTS ---");
		System.out.println("Can Red place road 11-16? " + board.isValidRoad(11, 16, red) + " (expected true)");
		System.out.println("Can Blue place road 0-3? " + board.isValidRoad(0, 3, blue) + " (expected false - already Red's)");
		System.out.println("Can Blue place road 10-14? " + board.isValidRoad(10, 14, blue) + " (expected true)");

		System.out.println();
		// Test longest road
		System.out.println("--- LONGEST ROAD ---");
		System.out.println("Red longest road: " + board.longestRoad(red) + " (expected 3)");
		System.out.println("Blue longest road: " + board.longestRoad(blue) + " (expected 0)");

		System.out.println();
		// Test resource distribution
		System.out.println("--- RESOURCE DISTRIBUTION ---");
		System.out.println("Red starting resources: " + red.resourceCount());
		System.out.println("Testing all dice rolls 2-12...");
		for (int roll = 2; roll <= 12; roll++) {
			if (roll == 7)
				continue;
			board.distributeResources(roll, players);
		}
		System.out.println("Red resources after rolls:");
		for (ResourceType type : ResourceType.values()) {
			System.out.println("  " + type + ": " + red.getResource(type));
		}
		System.out.println("Blue resources after rolls:");
		for (ResourceType type : ResourceType.values()) {
			System.out.println("  " + type + ": " + blue.getResource(type));
		}

		System.out.println();
		// Test canAfford
		System.out.println("--- AFFORD CHECKS ---");
		red.addResource(ResourceType.WOOD, 5);
		red.addResource(ResourceType.BRICK, 5);
		red.addResource(ResourceType.WHEAT, 5);
		red.addResource(ResourceType.SHEEP, 5);
		red.addResource(ResourceType.ORE, 5);
		System.out.println("After giving Red 5 of everything:");
		System.out.println("Can afford ROAD? " + red.canAfford(Buildable.ROAD));
		System.out.println("Can afford SETTLEMENT? " + red.canAfford(Buildable.SETTLEMENT));
		System.out.println("Can afford CITY? " + red.canAfford(Buildable.CITY));
	}
}