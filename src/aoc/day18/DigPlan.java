package aoc.day18;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import aoc.utils.Direction;
import aoc.utils.Tuple;

import static aoc.utils.Direction.*;

public class DigPlan {

	List<Tuple<Direction, Long>> digPlan = new ArrayList<Tuple<Direction,Long>>();
	List<Tuple<Direction, Long>> digPlanHex = new ArrayList<Tuple<Direction,Long>>();
	
	public DigPlan(String fpath) throws FileNotFoundException {
		var input = new File(fpath);
		var sc = new Scanner(input);
		while(sc.hasNextLine()) {
			var line = sc.nextLine().split(" ");
			var dir = mapDir(line[0]);
			var l = Long.parseLong(line[1]);
			digPlan.add(new Tuple<Direction, Long>(dir, l));
			
			var hex = line[2].substring(2, line[2].length()-1);
			var hexLength = hex.substring(0, hex.length()-1);
			var hexDir = hex.substring(hex.length()-1, hex.length());
			
			digPlanHex.add(new Tuple<Direction, Long>(mapDir(hexDir), Long.parseLong(hexLength, 16)));
		}
		sc.close();
	}
	
	public void printSolution() {
		var result1 = calcArea(digPlan);
		System.out.println("Part1: " + result1);
		var result2 = calcArea(digPlanHex);
		System.out.println("Part2: " + result2);
	}
	
	
	private Long calcArea(List<Tuple<Direction, Long>> plan) {
		//create the first node and a copy of it
		var firstNode = createNode(new Point(0, 0), plan.get(0).getFirst(), plan.get(0).getSecond());
		var previousNode = createNode(new Point(0, 0), plan.get(0).getFirst(), plan.get(0).getSecond());
		//add borderlenght of the dig
		Long borderlength = (long)plan.get(0).getSecond();
		//empty second node
		var currentNode = new Point(0, 0);
		//sum area
		Long sumArea = 0L;
		//start from the second node
		for(int i = 1; i < plan.size(); i++) {
			//calc current node from previous node and add borderlenght
			var dir = plan.get(i).getFirst();
			var length =  plan.get(i).getSecond();
			borderlength += length;
			currentNode = createNode(previousNode, dir, length);
			//Shoelace
			sumArea += (long)previousNode.x*(long)currentNode.y - (long)previousNode.y*(long)currentNode.x;
			//set previous to current node for next iteration
			previousNode = currentNode;
		}
		//complete Shoelace with adding last and first node pair and divide the sum by 2
		sumArea = (sumArea + ((long)currentNode.x*(long)firstNode.y - (long)currentNode.y*(long)firstNode.x)) / 2;
		//Pick for inner points
		var innerPoints = sumArea - borderlength / 2 + 1;
		//the trench has the size of innerPoints + border
		return innerPoints + borderlength;
	}
	
	//TODO: create point that can handle longs
	//this time it went ok
	private Point createNode(Point previosNode, Direction dir, Long length) {
		var x = previosNode.x + dir.getX()*length;
		var y = previosNode.y + dir.getY()*length;
		return new Point((int)x, (int)y);
	}
	
	
	private Direction mapDir(String s) {
		if(s.matches("[U3]")) return NORTH;
		if(s.matches("[R0]")) return EAST;
		if(s.matches("[D1]")) return SOUTH;
		if(s.matches("[L2]")) return WEST;
		return CENTER;
	}
}
