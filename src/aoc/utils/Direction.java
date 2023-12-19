package aoc.utils;

import java.awt.Point;

public enum Direction {

	CENTER("Center"),	
	EAST("East"),
	SOUTH("South"),
	WEST("West"),
	NORTH("North");
	
	
	private Point dir;
	
	Direction(String type) {
		switch(type) {
		case "Center":
			this.dir = new Point(0, 0);
			break;
		case "East":
			this.dir = new Point(1, 0);
			break;
		case "South":
			this.dir = new Point(0, 1);
			break;
		case "West":
			this.dir = new Point(-1, 0);
			break;
		case "North":
			this.dir = new Point(0, -1);
			break;
		}
	}
	
	public Point getDir() {
		return this.dir;
	}
	
	public int getX() {
		return this.dir.x;
	}
	
	public int getY() {
		return this.dir.y;
	}
	
	public static boolean isReverse(Direction firstDir, Direction secondDir) {
		
		if(firstDir.getDir().x * -1 == secondDir.getDir().x &&
				firstDir.getDir().y * -1 == secondDir.getDir().y) {
			return true;
		}
		return false;
	}
	
	public static Point next(Point point, Direction direction) {
		var dir = direction.getDir();
		return new Point(point.x + dir.x, point.y + dir.y);
	}
	
	public static void move(Point point, Direction direction) {
		var dir = direction.getDir();
		point.setLocation(point.x+dir.x, point.y+dir.y);
	}
}
