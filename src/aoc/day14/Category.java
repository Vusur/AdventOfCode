package aoc.day14;

public enum Category {
	STONE("O"),
	PILLAR("#"),
	FREE(".");
	private final String name;
	
	Category(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	

}
