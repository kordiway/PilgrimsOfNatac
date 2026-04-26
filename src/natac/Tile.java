package natac;

/**
 * sets up the game boards tiles with a resource type and a number token for
 * game operations
 * 
 * @author Korbin Ordiway
 */
public class Tile {

	private ResourceType type;
	private int numberToken;
	private int plagueRoundLeft; // not implemented

	public Tile(ResourceType type, int numberToken) {
		this.type = type;
		this.numberToken = numberToken;
	}

	public ResourceType getType() {
		return type;
	}

	public int getNumberToken() {
		return numberToken;
	}

}
