package natac;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class GameEngine {

	private Board board;
	private Queue<Player> players;
	private Player[] playersArr;
	private Player currentPlayer;
	private GameState state;
	private int vpGoal;
	private int roundCount;
	private boolean frenzyMode;
	private Player roadBonus;
	private Player cannonBonus;
	
	public GameEngine(Board board, int vpGoal, Player[] playersArr) {
		this.board = board;
		this.vpGoal = vpGoal;
		this.players = new Queue<Player>();
		this.playersArr = playersArr;
		for(Player p: playersArr) {
			players.enqueue(p);
		}
		this.currentPlayer = players.peek();
		this.state = GameState.INITIAL_PLACEMENT;
		this.roundCount = 0;
		this.roadBonus = null;
		this.cannonBonus = null;
	}
	
	public int rollDice() {
		int roll = StdRandom.uniformInt(1, 7) + StdRandom.uniformInt(1, 7);
		if(roll != 7) {
			board.distributeResources(roll, playersArr);
		}
		return roll;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void nextPlayer() {
		players.enqueue(players.dequeue());
		currentPlayer = players.peek();
	}
	
	public void endTurn() {
		if(checkWin() != null) {
			state = GameState.WIN_SCREEN;
			return;
		}
			
		nextPlayer();
		if(currentPlayer == playersArr[0])
			roundCount++;
	}
	
	public void buildRoad(int v, int w) {
		if(!currentPlayer.canAfford(Buildable.ROAD))
			return;
		if(!board.isValidRoad(v, w, currentPlayer))
			return;
		currentPlayer.removeResource(ResourceType.WOOD, 1);
		currentPlayer.removeResource(ResourceType.BRICK, 1);
		board.placeRoad(v, w, currentPlayer);
		updateLongestRoad();
	}
	
	public void buildSettlement(int ind) {
		if(!currentPlayer.canAfford(Buildable.SETTLEMENT))
			return;
		if(!board.isValidSettlement(ind, currentPlayer))
			return;
		currentPlayer.removeResource(ResourceType.WOOD, 1);
		currentPlayer.removeResource(ResourceType.BRICK, 1);
		currentPlayer.removeResource(ResourceType.SHEEP, 1);
		currentPlayer.removeResource(ResourceType.WHEAT, 1);
		board.placeStructure(ind, StructureType.SETTLEMENT, currentPlayer);
	}
	
	public void buildCity(int ind) {
		if(!currentPlayer.canAfford(Buildable.CITY))
			return;
		Crossroads c = board.crossroadsAt(ind);
		if(!c.hasSettlement() || c.getOwner() != currentPlayer)
			return;
		currentPlayer.removeResource(ResourceType.ORE, 3);
		currentPlayer.removeResource(ResourceType.WHEAT, 2);
		board.placeStructure(ind, StructureType.CITY, currentPlayer);
	}
	
	public Player checkWin() {
		for(Player p: playersArr) {
			if(p.getVP() >= vpGoal)
				return p;
		}
		return null;
	}
	
	public void updateLongestRoad() {
		Player longest = playersArr[0];
		for(Player p: playersArr) {
			p.setLongestRoad(board.longestRoad(p));
			if(p.getLongestRoad() > longest.getLongestRoad())
				longest = p;
		}
		if(longest.getLongestRoad() < 5)
			return;
		if(roadBonus == null || longest.getLongestRoad() > roadBonus.getLongestRoad()) {
			if(roadBonus != null)
				roadBonus.changeVP(-2);
			longest.changeVP(2);
			roadBonus = longest;
		}
	}
}
