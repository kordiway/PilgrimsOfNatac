package natac;

/**
 * sets up each crossroads of the tiles and assigns there values throughout the
 * game
 * 
 * @author Korbin Ordiway
 */
public class Crossroads {

	private StructureType structure;
	private Player owner;

	public Crossroads() {
		this.structure = StructureType.EMPTY;
		this.owner = null;
	}

	public StructureType getStructure() {
		return structure;
	}

	public Player getOwner() {
		return owner;
	}

	public void setStructure(StructureType structure) {
		this.structure = structure;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public boolean isEmpty() {
		return structure == StructureType.EMPTY;
	}

	public boolean hasSettlement() {
		return structure == StructureType.SETTLEMENT;
	}

	public boolean hasCity() {
		return structure == StructureType.CITY;
	}

}
