package natac;

import java.awt.Color;

import edu.princeton.cs.algs4.StdDraw;

public class GUI {

	private ResourceType bankGive = null;
	private ResourceType bankReceive = null;
	private GameEngine engine;
	private Board board;
	private Player[] players;
	private GameState state;
	private double[][] coords = new double[54][2];
	private int playerCount;
	private int vpGoal;
	private int placementIndex = 0;
	private boolean placingRoad = false;
	private int lastSettlementIndex;
	private String statusMessage = "";
	private boolean hasRolled = false;
	private Buildable selectedBuild = null;
	private int firstRoadVertex = -1;
	private double[][] tileCenters = {
		{ 0.2761, 0.7100 }, { 0.3800, 0.7100 }, { 0.4839, 0.7100 },
		{ 0.2241, 0.6200 }, { 0.3280, 0.6200 }, { 0.4320, 0.6200 }, { 0.5359, 0.6200 },
		{ 0.1722, 0.5300 }, { 0.2761, 0.5300 }, { 0.3800, 0.5300 }, { 0.4839, 0.5300 }, { 0.5878, 0.5300 },
		{ 0.2241, 0.4400 }, { 0.3280, 0.4400 }, { 0.4320, 0.4400 }, { 0.5359, 0.4400 },
		{ 0.2761, 0.3500 }, { 0.3800, 0.3500 }, { 0.4839, 0.3500 }
	};

	public GUI() {
		this.playerCount = 0;
		this.state = GameState.MAIN_MENU;
		this.vpGoal = 5;

		StdDraw.setCanvasSize(1500, 1000);
		StdDraw.setXscale(0, 1.5);
		StdDraw.setYscale(0, 1);
		StdDraw.enableDoubleBuffering();

		initCoords();
	}

	private void initCoords() {
		coords[0]  = new double[] { 0.2761, 0.7700 };
		coords[1]  = new double[] { 0.3800, 0.7700 };
		coords[2]  = new double[] { 0.4839, 0.7700 };
		coords[3]  = new double[] { 0.2241, 0.7400 };
		coords[4]  = new double[] { 0.3280, 0.7400 };
		coords[5]  = new double[] { 0.4320, 0.7400 };
		coords[6]  = new double[] { 0.5359, 0.7400 };
		coords[7]  = new double[] { 0.2241, 0.6800 };
		coords[8]  = new double[] { 0.3280, 0.6800 };
		coords[9]  = new double[] { 0.4320, 0.6800 };
		coords[10] = new double[] { 0.5359, 0.6800 };
		coords[11] = new double[] { 0.1722, 0.6500 };
		coords[12] = new double[] { 0.2761, 0.6500 };
		coords[13] = new double[] { 0.3800, 0.6500 };
		coords[14] = new double[] { 0.4839, 0.6500 };
		coords[15] = new double[] { 0.5878, 0.6500 };
		coords[16] = new double[] { 0.1722, 0.5900 };
		coords[17] = new double[] { 0.2761, 0.5900 };
		coords[18] = new double[] { 0.3800, 0.5900 };
		coords[19] = new double[] { 0.4839, 0.5900 };
		coords[20] = new double[] { 0.5878, 0.5900 };
		coords[21] = new double[] { 0.1202, 0.5600 };
		coords[22] = new double[] { 0.2241, 0.5600 };
		coords[23] = new double[] { 0.3280, 0.5600 };
		coords[24] = new double[] { 0.4320, 0.5600 };
		coords[25] = new double[] { 0.5359, 0.5600 };
		coords[26] = new double[] { 0.6398, 0.5600 };
		coords[27] = new double[] { 0.1202, 0.5000 };
		coords[28] = new double[] { 0.2241, 0.5000 };
		coords[29] = new double[] { 0.3280, 0.5000 };
		coords[30] = new double[] { 0.4320, 0.5000 };
		coords[31] = new double[] { 0.5359, 0.5000 };
		coords[32] = new double[] { 0.6398, 0.5000 };
		coords[33] = new double[] { 0.1722, 0.4700 };
		coords[34] = new double[] { 0.2761, 0.4700 };
		coords[35] = new double[] { 0.3800, 0.4700 };
		coords[36] = new double[] { 0.4839, 0.4700 };
		coords[37] = new double[] { 0.5878, 0.4700 };
		coords[38] = new double[] { 0.1722, 0.4100 };
		coords[39] = new double[] { 0.2761, 0.4100 };
		coords[40] = new double[] { 0.3800, 0.4100 };
		coords[41] = new double[] { 0.4839, 0.4100 };
		coords[42] = new double[] { 0.5878, 0.4100 };
		coords[43] = new double[] { 0.2241, 0.3800 };
		coords[44] = new double[] { 0.3280, 0.3800 };
		coords[45] = new double[] { 0.4320, 0.3800 };
		coords[46] = new double[] { 0.5359, 0.3800 };
		coords[47] = new double[] { 0.2241, 0.3200 };
		coords[48] = new double[] { 0.3280, 0.3200 };
		coords[49] = new double[] { 0.4320, 0.3200 };
		coords[50] = new double[] { 0.5359, 0.3200 };
		coords[51] = new double[] { 0.2761, 0.2900 };
		coords[52] = new double[] { 0.3800, 0.2900 };
		coords[53] = new double[] { 0.4839, 0.2900 };
	}

	public void run() {
		boolean redraw = true;
		while (true) {
			if (redraw) {
				StdDraw.clear();

				switch (state) {
				case MAIN_MENU:
					displayMainMenu();
					break;
				case SETUP_PLAYERS:
					displaySetupPlayers();
					break;
				case SETUP_VP:
					displaySetupVP();
					break;
				case INITIAL_PLACEMENT:
					displayInitialPlacement();
					break;
				case MAIN_GAME:
					displayBoard();
					displayPlayerCards();
					displayResourcePanel();
					displayActionButtons();
					break;
				case BUILDING:
					displayBoard();
					displayPlayerCards();
					displayResourcePanel();
					displayActionButtons();
					displayBuildMenu();
					break;
				case WIN_SCREEN:
					displayWinScreen(engine.checkWin());
					break;
				case BANK_TRADE:
				    displayBankTrade();
				    break;
				case RULES:
				    displayRules();
				    break;
				default:
					break;
				}

				StdDraw.show();
				redraw = false;
			}

			if (StdDraw.isMousePressed()) {
				handleClick(StdDraw.mouseX(), StdDraw.mouseY());
				redraw = true;
				while (StdDraw.isMousePressed()) {
					StdDraw.pause(20);
				}
			}

			StdDraw.pause(20);
		}
	}

	private void displayMainMenu() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0.75, 0.80, "Pilgrims of NataC");
		StdDraw.text(0.75, 0.74, "A Catan Inspired Strategy Game");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.75, 0.55, 0.10, 0.04);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.55, 0.10, 0.04);
		StdDraw.text(0.75, 0.55, "Play");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.75, 0.45, 0.10, 0.04);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.45, 0.10, 0.04);
		StdDraw.text(0.75, 0.45, "Rules");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.75, 0.35, 0.10, 0.04);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.35, 0.10, 0.04);
		StdDraw.text(0.75, 0.35, "Exit");
	}
	private void displayRules() {
	    StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.text(0.75, 0.92, "Pilgrims of NataC - Rules");
	    
	    double y = 0.85;
	    double step = 0.04;
	    
	    StdDraw.text(0.75, y, "OBJECTIVE: First player to reach the Victory Point goal wins.");
	    y -= step * 1.5;
	    
	    StdDraw.text(0.75, y, "SETUP:");
	    y -= step;
	    StdDraw.text(0.75, y, "Each player places 2 settlements and 2 roads in snake order.");
	    y -= step;
	    StdDraw.text(0.75, y, "Second settlement gives starting resources from adjacent tiles.");
	    y -= step * 1.5;
	    
	    StdDraw.text(0.75, y, "TURN ORDER:");
	    y -= step;
	    StdDraw.text(0.75, y, "1. Roll the dice (resources distribute to matching tile numbers)");
	    y -= step;
	    StdDraw.text(0.75, y, "2. Build any structures you can afford");
	    y -= step;
	    StdDraw.text(0.75, y, "3. Trade 4:1 with the bank if needed");
	    y -= step;
	    StdDraw.text(0.75, y, "4. End turn");
	    y -= step * 1.5;
	    
	    StdDraw.text(0.75, y, "BUILD COSTS:");
	    y -= step;
	    StdDraw.text(0.75, y, "Road: 1 Wood + 1 Brick");
	    y -= step;
	    StdDraw.text(0.75, y, "Settlement: 1 Wood + 1 Brick + 1 Wheat + 1 Sheep (worth 1 VP)");
	    y -= step;
	    StdDraw.text(0.75, y, "City: 2 Wheat + 3 Ore (upgrades settlement, worth 2 VP)");
	    y -= step * 1.5;
	    
	    StdDraw.text(0.75, y, "SPECIAL: Longest Road bonus (5+ roads) = 2 VP");

	    
	    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
	    StdDraw.filledRectangle(0.10, 0.10, 0.08, 0.04);
	    StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.rectangle(0.10, 0.10, 0.08, 0.04);
	    StdDraw.text(0.10, 0.10, "Back");
	}

	private void displaySetupPlayers() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0.75, 0.85, "Setup");
		StdDraw.text(0.75, 0.75, "Players:");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.55, 0.60, 0.05, 0.05);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.55, 0.60, 0.05, 0.05);
		StdDraw.text(0.55, 0.60, "2");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.75, 0.60, 0.05, 0.05);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.60, 0.05, 0.05);
		StdDraw.text(0.75, 0.60, "3");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.95, 0.60, 0.05, 0.05);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.95, 0.60, 0.05, 0.05);
		StdDraw.text(0.95, 0.60, "4");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.10, 0.10, 0.08, 0.04);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.10, 0.10, 0.08, 0.04);
		StdDraw.text(0.10, 0.10, "Back");
	}

	private void displaySetupVP() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0.75, 0.85, "Setup");
		StdDraw.text(0.75, 0.75, "Set Victory Points");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.75, 0.60, 0.04, 0.03);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.60, 0.04, 0.03);
		StdDraw.text(0.75, 0.60, "^");

		StdDraw.text(0.75, 0.52, "Current: " + vpGoal + " VP");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.75, 0.44, 0.04, 0.03);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.44, 0.04, 0.03);
		StdDraw.text(0.75, 0.44, "v");

		StdDraw.text(0.75, 0.36, "Recommended: 5-7");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.10, 0.10, 0.08, 0.04);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.10, 0.10, 0.08, 0.04);
		StdDraw.text(0.10, 0.10, "Back");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(1.40, 0.10, 0.08, 0.04);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(1.40, 0.10, 0.08, 0.04);
		StdDraw.text(1.40, 0.10, "Start");
	}

	private void displayInitialPlacement() {
		displayBoard();
		displayPlayerCards();

		int round = placementIndex / players.length;
		int pos = placementIndex % players.length;
		Player current = (round == 0) ? players[pos] : players[players.length - 1 - pos];

		StdDraw.setPenColor(StdDraw.BLACK);
		if (placingRoad) {
			StdDraw.text(0.75, 0.18, current.getName() + " - Place a road");
		} else {
			StdDraw.text(0.75, 0.18, current.getName() + " - Place a settlement");
		}

		if (!statusMessage.isEmpty()) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.text(0.75, 0.14, statusMessage);
		}
	}

	private void displayBoard() {
		for (int i = 0; i < 19; i++) {
			Tile t = board.tileAt(i);
			double cx = tileCenters[i][0];
			double cy = tileCenters[i][1];

			Color tileColor;
			if (t.getType() == null) {
				tileColor = StdDraw.AQUA;
			} else {
				switch (t.getType()) {
				case WOOD:  tileColor = StdDraw.GREEN; break;
				case SHEEP: tileColor = StdDraw.LIME; break;
				case WHEAT: tileColor = StdDraw.YELLOW; break;
				case BRICK: tileColor = StdDraw.MAROON; break;
				case ORE:   tileColor = StdDraw.GRAY; break;
				default:    tileColor = StdDraw.WHITE;
				}
			}

			double hexSize = 0.06;
			double[] xs = new double[6];
			double[] ys = new double[6];
			for (int v = 0; v < 6; v++) {
				double angle = Math.toRadians(60 * v - 90);
				xs[v] = cx + hexSize * Math.cos(angle);
				ys[v] = cy + hexSize * Math.sin(angle);
			}

			StdDraw.setPenColor(tileColor);
			StdDraw.filledPolygon(xs, ys);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.polygon(xs, ys);

			if (t.getNumberToken() > 0) {
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.filledCircle(cx, cy, 0.018);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.circle(cx, cy, 0.018);
				StdDraw.text(cx, cy, String.valueOf(t.getNumberToken()));
			}
		}

		for (int i = 0; i < 54; i++) {
			for (int j = i + 1; j < 54; j++) {
				Player roadOwner = board.getRoadOwner(i, j);
				if (roadOwner != null) {
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.setPenRadius(0.011);
					StdDraw.line(coords[i][0], coords[i][1], coords[j][0], coords[j][1]);
					StdDraw.setPenColor(roadOwner.getColor());
					StdDraw.setPenRadius(0.008);
					StdDraw.line(coords[i][0], coords[i][1], coords[j][0], coords[j][1]);
					StdDraw.setPenRadius();
				}
			}
		}

		for (int i = 0; i < 54; i++) {
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledCircle(coords[i][0], coords[i][1], 0.012);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.circle(coords[i][0], coords[i][1], 0.012);
		}

		for (int i = 0; i < 54; i++) {
			Crossroads c = board.crossroadsAt(i);
			if (!c.isEmpty()) {
				StdDraw.setPenColor(c.getOwner().getColor());
				if (c.hasCity()) {
					StdDraw.filledSquare(coords[i][0], coords[i][1], 0.018);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.square(coords[i][0], coords[i][1], 0.018);
				} else {
					StdDraw.filledCircle(coords[i][0], coords[i][1], 0.015);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.circle(coords[i][0], coords[i][1], 0.015);
				}
			}
		}
	}

	private void displayPlayerCards() {
		double cardWidth = 0.30;
		double cardHeight = 0.08;
		double cardSpacing = 0.32;
		double y = 0.92;

		double totalWidth = 3 * cardSpacing;
		double startX = 0.75 - (totalWidth / 2);

		for (int i = 0; i < players.length; i++) {
			Player p = players[i];
			double cx = startX + (i * cardSpacing);

			if (engine != null && p == engine.getCurrentPlayer()) {
				StdDraw.setPenColor(StdDraw.YELLOW);
				StdDraw.filledRectangle(cx, y, cardWidth/2 + 0.005, cardHeight/2 + 0.005);
			}

			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledRectangle(cx, y, cardWidth/2, cardHeight/2);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.rectangle(cx, y, cardWidth/2, cardHeight/2);

			StdDraw.text(cx - 0.10, y + 0.02, "Player" + (i + 1));
			StdDraw.setPenColor(p.getColor());
			StdDraw.filledCircle(cx - 0.05, y + 0.02, 0.018);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.circle(cx - 0.05, y + 0.02, 0.018);

			StdDraw.text(cx - 0.07, y - 0.02, "Victory Points: " + p.getVP());
			StdDraw.text(cx + 0.07, y - 0.02, "Total Resources: " + p.resourceCount());
		}
	}

	private void displayResourcePanel() {
		Player current = engine.getCurrentPlayer();
		double x = 0.85;
		double yStart = 0.75;
		double yStep = 0.05;

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(x, yStart + yStep, current.getName() + "'s Hand");
		StdDraw.text(x, yStart, "Wood: " + current.getResource(ResourceType.WOOD));
		StdDraw.text(x, yStart - yStep, "Brick: " + current.getResource(ResourceType.BRICK));
		StdDraw.text(x, yStart - yStep * 2, "Wheat: " + current.getResource(ResourceType.WHEAT));
		StdDraw.text(x, yStart - yStep * 3, "Sheep: " + current.getResource(ResourceType.SHEEP));
		StdDraw.text(x, yStart - yStep * 4, "Ore: " + current.getResource(ResourceType.ORE));
	}

	private void displayActionButtons() {
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.10, 0.22, 0.06, 0.03);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.10, 0.22, 0.06, 0.03);
		StdDraw.text(0.10, 0.22, "Roll");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.10, 0.14, 0.06, 0.03);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.10, 0.14, 0.06, 0.03);
		StdDraw.text(0.10, 0.14, "End Turn");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(1.40, 0.22, 0.08, 0.03);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(1.40, 0.22, 0.08, 0.03);
		StdDraw.text(1.40, 0.22, "Build");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(1.40, 0.14, 0.08, 0.03);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(1.40, 0.14, 0.08, 0.03);
		StdDraw.text(1.40, 0.14, "Bank 4:1");

		if (engine != null) {
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(0.75, 0.10, engine.getCurrentPlayer().getName() + "'s turn");

			if (!statusMessage.isEmpty()) {
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.text(0.75, 0.06, statusMessage);
			}
		}
	}

	private void displayBuildMenu() {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(0.75, 0.50, 0.20, 0.18);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.50, 0.20, 0.18);
		StdDraw.text(0.75, 0.65, "Select what to build");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.75, 0.58, 0.15, 0.025);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.58, 0.15, 0.025);
		StdDraw.text(0.75, 0.58, "Road - 1 Wood + 1 Brick");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.75, 0.52, 0.15, 0.025);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.52, 0.15, 0.025);
		StdDraw.text(0.75, 0.52, "Settlement - 1W 1B 1Wh 1Sh");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.75, 0.46, 0.15, 0.025);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.46, 0.15, 0.025);
		StdDraw.text(0.75, 0.46, "City - 2 Wheat + 3 Ore");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.75, 0.40, 0.06, 0.025);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.40, 0.06, 0.025);
		StdDraw.text(0.75, 0.40, "Cancel");
	}

	private void displayWinScreen(Player winner) {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0.75, 0.70, "GAME OVER");
		StdDraw.text(0.75, 0.60, winner.getName() + " WINS!");

		StdDraw.setPenColor(winner.getColor());
		StdDraw.filledCircle(0.75, 0.50, 0.05);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.circle(0.75, 0.50, 0.05);

		StdDraw.text(0.75, 0.40, winner.getVP() + " Victory Points");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.75, 0.25, 0.10, 0.04);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.25, 0.10, 0.04);
		StdDraw.text(0.75, 0.25, "New Game");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(0.75, 0.17, 0.10, 0.04);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(0.75, 0.17, 0.10, 0.04);
		StdDraw.text(0.75, 0.17, "Exit");
	}

	private int findClickedCrossroad(double x, double y) {
		for (int i = 0; i < 54; i++) {
			double dx = x - coords[i][0];
			double dy = y - coords[i][1];
			if (dx * dx + dy * dy < 0.0003) return i;
		}
		return -1;
	}
	private void displayBankTrade() {
	    StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.text(0.75, 0.85, "Bank Trade 4:1");
	    StdDraw.text(0.40, 0.75, "GIVE (4 of):");
	    StdDraw.text(1.10, 0.75, "RECEIVE (1 of):");

	    Player current = engine.getCurrentPlayer();
	    ResourceType[] types = ResourceType.values();
	    
	    for (int i = 0; i < types.length; i++) {
	        double y = 0.65 - (i * 0.07);
	        
	        if (bankGive == types[i]) {
	            StdDraw.setPenColor(StdDraw.YELLOW);
	        } else {
	            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
	        }
	        StdDraw.filledRectangle(0.40, y, 0.12, 0.025);
	        StdDraw.setPenColor(StdDraw.BLACK);
	        StdDraw.rectangle(0.40, y, 0.12, 0.025);
	        StdDraw.text(0.40, y, types[i] + " (" + current.getResource(types[i]) + ")");
	        
	        if (bankReceive == types[i]) {
	            StdDraw.setPenColor(StdDraw.YELLOW);
	        } else {
	            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
	        }
	        StdDraw.filledRectangle(1.10, y, 0.12, 0.025);
	        StdDraw.setPenColor(StdDraw.BLACK);
	        StdDraw.rectangle(1.10, y, 0.12, 0.025);
	        StdDraw.text(1.10, y, types[i].toString());
	    }

	    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
	    StdDraw.filledRectangle(0.10, 0.10, 0.08, 0.04);
	    StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.rectangle(0.10, 0.10, 0.08, 0.04);
	    StdDraw.text(0.10, 0.10, "Cancel");

	    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
	    StdDraw.filledRectangle(1.40, 0.10, 0.08, 0.04);
	    StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.rectangle(1.40, 0.10, 0.08, 0.04);
	    StdDraw.text(1.40, 0.10, "Send");

	    if (!statusMessage.isEmpty()) {
	        StdDraw.setPenColor(StdDraw.RED);
	        StdDraw.text(0.75, 0.18, statusMessage);
	    }
	}

	private void handleClick(double x, double y) {
		if (state == GameState.MAIN_MENU) {
			if (x > 0.65 && x < 0.85 && y > 0.51 && y < 0.59) {
				state = GameState.SETUP_PLAYERS;
			} else if (x > 0.65 && x < 0.85 && y > 0.41 && y < 0.49) {
				state = GameState.RULES;
			} else if (x > 0.65 && x < 0.85 && y > 0.31 && y < 0.39) {
				System.exit(0);
			}
		} else if (state == GameState.SETUP_PLAYERS) {
			if (x > 0.50 && x < 0.60 && y > 0.55 && y < 0.65) {
				playerCount = 2;
				state = GameState.SETUP_VP;
			} else if (x > 0.70 && x < 0.80 && y > 0.55 && y < 0.65) {
				playerCount = 3;
				state = GameState.SETUP_VP;
			} else if (x > 0.90 && x < 1.00 && y > 0.55 && y < 0.65) {
				playerCount = 4;
				state = GameState.SETUP_VP;
			} else if (x > 0.02 && x < 0.18 && y > 0.06 && y < 0.14) {
				state = GameState.MAIN_MENU;
			}
		} else if (state == GameState.SETUP_VP) {
			if (x > 0.71 && x < 0.79 && y > 0.57 && y < 0.63) {
				if (vpGoal < 10) vpGoal++;
			} else if (x > 0.71 && x < 0.79 && y > 0.41 && y < 0.47) {
				if (vpGoal > 3) vpGoal--;
			} else if (x > 0.02 && x < 0.18 && y > 0.06 && y < 0.14) {
				state = GameState.SETUP_PLAYERS;
			} else if (x > 1.32 && x < 1.48 && y > 0.06 && y < 0.14) {
				String[] names = { "Player 1", "Player 2", "Player 3", "Player 4" };
				Color[] colors = { Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN };

				players = new Player[playerCount];
				for (int i = 0; i < playerCount; i++) {
					players[i] = new Player(names[i], colors[i]);
				}

				board = new Board();
				engine = new GameEngine(board, vpGoal, players);
				state = GameState.INITIAL_PLACEMENT;
			}
		} else if (state == GameState.INITIAL_PLACEMENT) {
			int round = placementIndex / players.length;
			int pos = placementIndex % players.length;
			Player current = (round == 0) ? players[pos] : players[players.length - 1 - pos];

			boolean foundClick = false;

			if (!placingRoad) {
				for (int i = 0; i < 54; i++) {
					double dx = x - coords[i][0];
					double dy = y - coords[i][1];
					if (dx * dx + dy * dy < 0.0003) {
						foundClick = true;
						if (!board.crossroadsAt(i).isEmpty()) {
							statusMessage = "Spot already taken";
							return;
						}
						if (!board.isInitialSettlementValid(i)) {
							statusMessage = "Too close to another settlement";
							return;
						}
						board.placeStructure(i, StructureType.SETTLEMENT, current);
						lastSettlementIndex = i;
						placingRoad = true;
						statusMessage = "";
						return;
					}
				}
				if (!foundClick) statusMessage = "Click on a valid intersection";
			} else {
				for (int i = 0; i < 54; i++) {
					if (i == lastSettlementIndex) continue;
					double dx = x - coords[i][0];
					double dy = y - coords[i][1];
					if (dx * dx + dy * dy < 0.0003) {
						foundClick = true;
						if (!board.areAdjacent(lastSettlementIndex, i)) {
							statusMessage = "Road must connect to your settlement";
							return;
						}
						if (board.getRoadOwner(lastSettlementIndex, i) != null) {
							statusMessage = "Road already exists there";
							return;
						}
						board.placeRoad(lastSettlementIndex, i, current);

						if (placementIndex >= players.length) {
							board.giveStartingResources(lastSettlementIndex, current);
						}

						placingRoad = false;
						placementIndex++;
						statusMessage = "";
						if (placementIndex >= 2 * players.length) {
							state = GameState.MAIN_GAME;
						}
						return;
					}
				}
				if (!foundClick) statusMessage = "Click on a valid road endpoint";
			}
		} else if (state == GameState.MAIN_GAME) {
			if (selectedBuild != null) {
				int clicked = findClickedCrossroad(x, y);
				if (clicked != -1) {
					if (selectedBuild == Buildable.SETTLEMENT) {
						engine.buildSettlement(clicked);
						if (board.crossroadsAt(clicked).hasSettlement()) {
							statusMessage = "Settlement built";
							selectedBuild = null;
							if (engine.checkWin() != null) state = GameState.WIN_SCREEN;
						} else {
							statusMessage = "Cannot build settlement there";
						}
					} else if (selectedBuild == Buildable.CITY) {
						engine.buildCity(clicked);
						if (board.crossroadsAt(clicked).hasCity()) {
							statusMessage = "City built";
							selectedBuild = null;
							if (engine.checkWin() != null) state = GameState.WIN_SCREEN;
						} else {
							statusMessage = "Cannot build city there";
						}
					} else if (selectedBuild == Buildable.ROAD) {
					    if (firstRoadVertex == -1) {
					        firstRoadVertex = clicked;
					        statusMessage = "Click second crossroads to complete road";
					    } else {
					        if (!board.areAdjacent(firstRoadVertex, clicked)) {
					            statusMessage = "Vertices must be adjacent";
					            firstRoadVertex = -1;
					            return;
					        }
					        engine.buildRoad(firstRoadVertex, clicked);
					        if (board.getRoadOwner(firstRoadVertex, clicked) != null) {
					            statusMessage = "Road built";
					            selectedBuild = null;
					            firstRoadVertex = -1;
					            if (engine.checkWin() != null) state = GameState.WIN_SCREEN;
					        } else {
					            statusMessage = "Cannot build road there";
					            firstRoadVertex = -1;
					        }
					    }
					}
					return;
				} else if (state == GameState.BANK_TRADE) {
				    ResourceType[] types = ResourceType.values();
				    
				    for (int i = 0; i < types.length; i++) {
				        double btnY = 0.65 - (i * 0.07);
				        
				        if (x > 0.28 && x < 0.52 && y > btnY - 0.025 && y < btnY + 0.025) {
				            bankGive = types[i];
				            return;
				        }
				        if (x > 0.98 && x < 1.22 && y > btnY - 0.025 && y < btnY + 0.025) {
				            bankReceive = types[i];
				            return;
				        }
				    }

				    if (x > 0.02 && x < 0.18 && y > 0.06 && y < 0.14) {
				        state = GameState.MAIN_GAME;
				        statusMessage = "";
				    }
				    else if (x > 1.32 && x < 1.48 && y > 0.06 && y < 0.14) {
				        Player current = engine.getCurrentPlayer();
				        if (bankGive == null || bankReceive == null) {
				            statusMessage = "Select both give and receive";
				            return;
				        }
				        if (bankGive == bankReceive) {
				            statusMessage = "Give and receive must be different";
				            return;
				        }
				        if (current.getResource(bankGive) < 4) {
				            statusMessage = "Need at least 4 " + bankGive + " to trade";
				            return;
				        }
				        current.removeResource(bankGive, 4);
				        current.addResource(bankReceive, 1);
				        statusMessage = "Traded 4 " + bankGive + " for 1 " + bankReceive;
				        state = GameState.MAIN_GAME;
				        bankGive = null;
				        bankReceive = null;
				    }
				}
			}

			if (x > 0.04 && x < 0.16 && y > 0.19 && y < 0.25) {
				if (hasRolled) {
					statusMessage = "Already rolled this turn";
					return;
				}
				int roll = engine.rollDice();
				statusMessage = engine.getCurrentPlayer().getName() + " rolled a " + roll;
				hasRolled = true;
			}
			else if (x > 0.04 && x < 0.16 && y > 0.11 && y < 0.17) {
				if (!hasRolled) {
					statusMessage = "Roll the dice before ending turn";
					return;
				}
				engine.endTurn();
				hasRolled = false;
				selectedBuild = null;
				firstRoadVertex = -1;
				statusMessage = engine.getCurrentPlayer().getName() + "'s turn";
				if (engine.checkWin() != null) state = GameState.WIN_SCREEN;
			}
			else if (x > 1.32 && x < 1.48 && y > 0.19 && y < 0.25) {
				if (!hasRolled) {
					statusMessage = "Roll first before building";
					return;
				}
				state = GameState.BUILDING;
				statusMessage = "";
			}
			else if (x > 1.32 && x < 1.48 && y > 0.11 && y < 0.17) {
			    state = GameState.BANK_TRADE;
			    bankGive = null;
			    bankReceive = null;
			    statusMessage = "";
			}
		} else if (state == GameState.BUILDING) {
			if (x > 0.60 && x < 0.90 && y > 0.555 && y < 0.605) {
				if (!engine.getCurrentPlayer().canAfford(Buildable.ROAD)) {
					statusMessage = "Cannot afford road";
					return;
				}
				selectedBuild = Buildable.ROAD;
				firstRoadVertex = -1;
				statusMessage = "Click two adjacent crossroads to place road";
				state = GameState.MAIN_GAME;
			}
			else if (x > 0.60 && x < 0.90 && y > 0.495 && y < 0.545) {
				if (!engine.getCurrentPlayer().canAfford(Buildable.SETTLEMENT)) {
					statusMessage = "Cannot afford settlement";
					return;
				}
				selectedBuild = Buildable.SETTLEMENT;
				statusMessage = "Click a crossroads to place settlement";
				state = GameState.MAIN_GAME;
			}
			else if (x > 0.60 && x < 0.90 && y > 0.435 && y < 0.485) {
				if (!engine.getCurrentPlayer().canAfford(Buildable.CITY)) {
					statusMessage = "Cannot afford city";
					return;
				}
				selectedBuild = Buildable.CITY;
				statusMessage = "Click your settlement to upgrade to city";
				state = GameState.MAIN_GAME;
			}
			else if (x > 0.69 && x < 0.81 && y > 0.375 && y < 0.425) {
				state = GameState.MAIN_GAME;
				selectedBuild = null;
				statusMessage = "";
			}
		} else if (state == GameState.WIN_SCREEN) {
			if (x > 0.65 && x < 0.85 && y > 0.21 && y < 0.29) {
				state = GameState.MAIN_MENU;
				placementIndex = 0;
				placingRoad = false;
				hasRolled = false;
				selectedBuild = null;
				firstRoadVertex = -1;
				statusMessage = "";
				engine = null;
				board = null;
				players = null;
			}
			else if (x > 0.65 && x < 0.85 && y > 0.13 && y < 0.21) {
				System.exit(0);
			}
		} else if (state == GameState.BANK_TRADE) {
		    ResourceType[] types = ResourceType.values();
		    
		    for (int i = 0; i < types.length; i++) {
		        double btnY = 0.65 - (i * 0.07);
		        
		        if (x > 0.28 && x < 0.52 && y > btnY - 0.025 && y < btnY + 0.025) {
		            bankGive = types[i];
		            return;
		        }
		        if (x > 0.98 && x < 1.22 && y > btnY - 0.025 && y < btnY + 0.025) {
		            bankReceive = types[i];
		            return;
		        }
		    }

		    if (x > 0.02 && x < 0.18 && y > 0.06 && y < 0.14) {
		        state = GameState.MAIN_GAME;
		        statusMessage = "";
		    }
		    else if (x > 1.32 && x < 1.48 && y > 0.06 && y < 0.14) {
		        Player current = engine.getCurrentPlayer();
		        if (bankGive == null || bankReceive == null) {
		            statusMessage = "Select both give and receive";
		            return;
		        }
		        if (bankGive == bankReceive) {
		            statusMessage = "Give and receive must be different";
		            return;
		        }
		        if (current.getResource(bankGive) < 4) {
		            statusMessage = "Need at least 4 " + bankGive + " to trade";
		            return;
		        }
		        current.removeResource(bankGive, 4);
		        current.addResource(bankReceive, 1);
		        statusMessage = "Traded 4 " + bankGive + " for 1 " + bankReceive;
		        state = GameState.MAIN_GAME;
		        bankGive = null;
		        bankReceive = null;
		    }
		} else if (state == GameState.RULES) {
		    if (x > 0.02 && x < 0.18 && y > 0.06 && y < 0.14) {
		        state = GameState.MAIN_MENU;
		    }
		}
	}

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.run();
	}
}