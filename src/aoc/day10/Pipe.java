package aoc.day10;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static aoc.day10.Direction.*;

public class Pipe {
	
	private String type;
	private AreaType area;
	private int x;
	private int y;
	private boolean visited;
	
	
	private Map<Direction, List<String>> connections = new HashMap<Direction, List<String>>();
	
	public Pipe(String type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.visited = false;

		createDirectionDictionary();
	}
	
	public String type() {
		return type;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public boolean visited() {
		return visited;
	}
	public void setVisited() {
		visited = true;
	}
	
	private boolean hasDir(Direction dir) {
		return connections.containsKey(dir);
	}
		
	private void createDirectionDictionary() {	
		var norths = Arrays.asList("|", "L", "J");
		var easts = Arrays.asList("-", "F", "L");
		var souths = Arrays.asList("|", "7", "F");
		var wests = Arrays.asList("-", "J", "7");
		
		//if the current pipe has a north, then it can connect to pipes with souths
		if(norths.contains(type)) {
			connections.put(NORTH, souths);
		}
		if(easts.contains(type)) {
			connections.put(EAST, wests);
		}
		if(souths.contains(type)) {
			connections.put(SOUTH, norths);
		}	
		if(wests.contains(type)) {
			connections.put(WEST, easts);
		}
		
		//Pipe S is special, because it is a wildcard
		if(type.equals("S")) {
			connections.put(NORTH, souths);
			connections.put(EAST, wests);
			connections.put(SOUTH, norths);
			connections.put(WEST, easts);
		}
	}
	
	public boolean nextIsValid(Pipe other, Direction dir) {
		var type = other.type();
		if(connections.containsKey(dir)) {
			if(connections.get(dir).contains(type)) {
				return true;
			}
		}	
		return false;
	}
	
	public void convertPipe(Pipe n, Pipe e, Pipe s, Pipe w) {
		connections.clear();
		var hasNorth = n.hasDir(SOUTH);
		var hasEast = e.hasDir(WEST);
		var hasSouth = s.hasDir(NORTH);
		var hasWest = w.hasDir(WEST);
		
		if(hasNorth && hasSouth) this.type = "|";
		if(hasEast && hasWest) this.type = "-";
		if(hasNorth && hasEast) this.type = "L";
		if(hasNorth && hasWest) this.type = "J";
		if(hasSouth && hasWest) this.type = "7";
		if(hasSouth && hasEast) this.type = "F";
		
		createDirectionDictionary();
		
	}
	

}
