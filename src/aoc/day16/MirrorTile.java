package aoc.day16;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MirrorTile {

	private int x;
	private int y;
	private String type;
	private int energyLevel;
	private List<Point> checkedDir;
	
	public MirrorTile(int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.energyLevel = 0;
		this.checkedDir = new ArrayList<>();
	}
	
	public int x() {
		return this.x;
	}
	
	public int y() {
		return this.y;
	}
	
	public String type() {
		return this.type;
	}
	
	public boolean energized() {
		return (0 < this.energyLevel);
	}
	
	public int energyLevel() {
		return this.energyLevel;
	}
	
	//up 	-> (0, -1)
	//left	-> (-1, 0)
	//down	-> (0, 1)
	//right -> (1, 0)
	public List<Point> changeDirection(Point dir) {
		energyLevel += 1;
		var dirList = new ArrayList<Point>();
		checkedDir.add(dir);
		//rotate dir against \
		// right -> down <-> up -> left
		// left -> up <-> down -> right
		if(type.equals("\\")) {
			dirList.add(new Point(dir.y, dir.x));
		}
		//rotate dir against /
		// right -> up <-> down -> left
		// left -> down <-> up -> right
		else if(type.equals("/")) {
			dirList.add(new Point(dir.y * -1, dir.x * -1));
		}
		else if(dir.y != 0 && type.equals("-")) {
			dirList.add(new Point(1, 0));
			dirList.add(new Point(-1, 0));
		}
		else if(dir.x != 0 && type.equals("|")) {
			dirList.add(new Point(0, 1));
			dirList.add(new Point(0, -1));
		}
		else {
			dirList.add(dir);
		}		
		return dirList;
	}
	
	public boolean checkDir(Point dir) {
		for(var d : checkedDir) {
			if( d.x == dir.x && d.y == dir.y) {
				return false;
			}
		}

		return true;
	}
	
	public void clearTile() {
		this.energyLevel = 0;
		this.checkedDir = new ArrayList<Point>();
	}
}
