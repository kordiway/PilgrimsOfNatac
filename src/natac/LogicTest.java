package natac;

import java.awt.Color;

/**
 * Test logic of already made classes to simulate game situation without having a functional GUI
 * at this time.
 *
 * @author Korbin Ordiway
 */
public class LogicTest {

    public static void main(String[] args) {
        System.out.println("=== PILGRIMS OF NATAC - FULL LOGIC TEST ===\n");

        // ======== BOARD AND PLAYER SETUP ========
        Player red = new Player("Red", Color.RED);
        Player blue = new Player("Blue", Color.BLUE);
        Player[] players = { red, blue };
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

        // ======== INITIAL PLACEMENT (manual) ========
        System.out.println("--- INITIAL PLACEMENT ---");
        board.placeStructure(0, StructureType.SETTLEMENT, red);
        board.placeStructure(10, StructureType.SETTLEMENT, blue);
        board.placeRoad(0, 3, red);
        board.placeRoad(3, 7, red);
        board.placeRoad(7, 11, red);
        System.out.println("Red placed settlement at 0 and 3 roads to vertex 11");
        System.out.println("Blue placed settlement at 10");
        System.out.println("Red VP: " + red.getVP() + " | Blue VP: " + blue.getVP());

        System.out.println();

        // ======== BOARD VALIDATION TESTS ========
        System.out.println("--- VALID SETTLEMENT TESTS ---");
        System.out.println("Red at 0 again? " + board.isValidSettlement(0, red) + " (expected false)");
        System.out.println("Red at 11? " + board.isValidSettlement(11, red) + " (expected true)");
        System.out.println("Red at 7? " + board.isValidSettlement(7, red) + " (expected false - distance)");
        System.out.println("Blue at 11? " + board.isValidSettlement(11, blue) + " (expected false - no road)");

        System.out.println();
        System.out.println("--- VALID ROAD TESTS ---");
        System.out.println("Red road 11-16? " + board.isValidRoad(11, 16, red) + " (expected true)");
        System.out.println("Blue road 0-3? " + board.isValidRoad(0, 3, blue) + " (expected false - taken)");
        System.out.println("Blue road 10-14? " + board.isValidRoad(10, 14, blue) + " (expected true)");

        System.out.println();
        System.out.println("--- LONGEST ROAD ---");
        System.out.println("Red: " + board.longestRoad(red) + " (expected 3)");
        System.out.println("Blue: " + board.longestRoad(blue) + " (expected 0)");

        System.out.println();

        // ======== RESOURCE DISTRIBUTION ========
        System.out.println("--- RESOURCE DISTRIBUTION ---");
        for (int roll = 2; roll <= 12; roll++) {
            if (roll == 7) continue;
            board.distributeResources(roll, players);
        }
        System.out.println("Red after rolling all numbers:");
        for (ResourceType type : ResourceType.values())
            System.out.println("  " + type + ": " + red.getResource(type));
        System.out.println("Blue after rolling all numbers:");
        for (ResourceType type : ResourceType.values())
            System.out.println("  " + type + ": " + blue.getResource(type));

        System.out.println();

        // ======== AFFORDABILITY ========
        System.out.println("--- AFFORD CHECKS ---");
        red.addResource(ResourceType.WOOD, 5);
        red.addResource(ResourceType.BRICK, 5);
        red.addResource(ResourceType.WHEAT, 5);
        red.addResource(ResourceType.SHEEP, 5);
        red.addResource(ResourceType.ORE, 5);
        System.out.println("Red has 5+ of every resource");
        System.out.println("Can afford ROAD? " + red.canAfford(Buildable.ROAD));
        System.out.println("Can afford SETTLEMENT? " + red.canAfford(Buildable.SETTLEMENT));
        System.out.println("Can afford CITY? " + red.canAfford(Buildable.CITY));

        System.out.println();

        // ======== GAME ENGINE TESTS ========
        System.out.println("=== GAME ENGINE TESTS ===\n");
        GameEngine engine = new GameEngine(board, 5, players);
        System.out.println("GameEngine created with VP goal of 5");
        System.out.println();

        // Roll dice test
        System.out.println("--- DICE ROLL ---");
        for (int i = 0; i < 5; i++) {
            int roll = engine.rollDice();
            System.out.println("Rolled: " + roll);
        }

        System.out.println();

        // Build settlement through engine
        System.out.println("--- BUILDING THROUGH ENGINE ---");
        System.out.println("Red VP before: " + red.getVP());
        System.out.println("Red trying to build settlement at 11 (connected via road)");
        engine.buildSettlement(11);
        System.out.println("Red VP after: " + red.getVP() + " (expected to increase by 1)");
        System.out.println("Crossroads 11 has settlement? " + board.crossroadsAt(11).hasSettlement());

        System.out.println();
        System.out.println("Red trying to build city at 11 (upgrade settlement)");
        engine.buildCity(11);
        System.out.println("Red VP after: " + red.getVP() + " (expected to increase by 1 more)");
        System.out.println("Crossroads 11 has city? " + board.crossroadsAt(11).hasCity());

        System.out.println();

        // Build more roads to test longest road bonus
        System.out.println("--- LONGEST ROAD BONUS TEST ---");
        System.out.println("Red building roads to reach 5 in a row...");
        red.addResource(ResourceType.WOOD, 5);
        red.addResource(ResourceType.BRICK, 5);
        engine.buildRoad(11, 16);
        engine.buildRoad(16, 22);
        System.out.println("Red longest road: " + red.getLongestRoad());
        System.out.println("Red VP after longest road bonus: " + red.getVP() + " (should include +2 bonus)");

        System.out.println();

        // Turn rotation
        System.out.println("--- TURN ROTATION ---");
        System.out.println("Players in order: Red, Blue");
        engine.endTurn();
        System.out.println("After endTurn 1, current player should be Blue");
        engine.endTurn();
        System.out.println("After endTurn 2, current player should be Red (round 1 complete)");

        System.out.println();

        // Win check
        System.out.println("--- WIN CHECK ---");
        System.out.println("Red current VP: " + red.getVP());
        System.out.println("VP goal: 5");
        Player winner = engine.checkWin();
        System.out.println("Winner: " + (winner == null ? "no one yet" : winner.getName()));
    }
}