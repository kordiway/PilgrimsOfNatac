package natac;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

/**
 * handles actual gameplay logic such as dice rolls whose turn it is and checking vitory conditions
 * as well as adding victory points to Player objects for bonuses and build logic to take resources from players
 * upon build
 * 
 * @author Korbin Ordiway 
 */

public class GameEngine {

	private Board board;
	private Queue<Player> players;
	private Player[] playersArr;
	private Player currentPlayer;
	private GameState state;
	private int vpGoal;
	private int roundCount; // not implemented
	private boolean frenzyMode; // not implemented
	private Player roadBonus;
	private Player cannonBonus; // not implemented
	
	public GameEngine(Board board, int vpGoal, Player[] playersArr) {
		this.board = board;
		this.vpGoal = vpGoal;
		this.players = new Queue<Player>();
		this.playersArr = playersArr;
		
		//creates initial player order of turns
		for(Player p: playersArr) {
			players.enqueue(p);
		}
		
		this.currentPlayer = players.peek();
		this.state = GameState.INITIAL_PLACEMENT;
		this.roundCount = 0;
		this.roadBonus = null;
		this.cannonBonus = null;
	}
	
	/**
	 * simulates rolling 2 six sided dice to so the odds of rolling each number are correct
	 * as well as checking if the number is not 7 as 7 is the only one that does nothing
	 * in game current state
	 * 
	 * @return a number 2-12 based and 2 random 1-6 rolls
	 */
	public int rollDice() {
		int roll = StdRandom.uniformInt(1, 7) + StdRandom.uniformInt(1, 7);
		if(roll != 7) {
			board.distributeResources(roll, playersArr);
		}
		return roll;
	}
	
	/**
	 * getter for current player
	 * 
	 * @return
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * requeues current player to end of queue and end turn moving to next player
	 * through a peek.
	 */
	public void nextPlayer() {
		players.enqueue(players.dequeue());
		currentPlayer = players.peek();
	}
	
	/**
	 * checks if win conditions are met and if they are advances game state to the win screen
	 * otherwise contiuning to nextplayers turn and incrementing round count if all players have 
	 * gone this round
	 */
	public void endTurn() {
		if(checkWin() != null) {
			state = GameState.WIN_SCREEN;
			return;
		}
			
		nextPlayer();
		if(currentPlayer == playersArr[0])
			roundCount++;
	}
	
	/**
	 * calls other methods to see if current player can afford road then if 
	 * road build edge is valid if both conditions are met removes resources and
	 * checks if the longest road was increased and should be swapped to a new player. 
	 * uses 2 vertexes to find edge location
	 * 
	 * @param v vertex
	 * @param w vertex
	 */
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
	
	
	/**
	 * checks if a player can afford and the placement spot is valid
	 * for a settlement and then will place using the given index as 
	 * the veticie
	 * 
	 * @param ind
	 */
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
	
	/**
	 * checks that player can afford city build requirements as well
	 * as if the crossroad they are selecting is a settlement that they own
	 * 
	 * @param ind
	 */
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
	
	/**
	 * checks if any player meets the vp goal requirements
	 * 
	 * @return
	 */
	public Player checkWin() {
		for(Player p: playersArr) {
			if(p.getVP() >= vpGoal)
				return p;
		}
		return null;
	}
	
	/**
	 * sets longest road and gives bonus points to holder while also handling
	 * tie logic with the first person to reach that number keeping the bonus until surpassed
	 */
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
