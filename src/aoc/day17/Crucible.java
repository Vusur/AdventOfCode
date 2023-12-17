package aoc.day17;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Stream;

import aoc.utils.Direction;

import static aoc.utils.Direction.*;

public class Crucible {
	
	List<List<Integer>> grid = new ArrayList<>();
	int width;
	int height;
	
	
	
	public Crucible(String fpath) throws FileNotFoundException {
		var input = new File(fpath);
		var sc = new Scanner(input);
		while(sc.hasNextLine()) {
			var line = Stream.of(sc.nextLine().split(""))
					.map(e -> Integer.parseInt(e))
					.toList();
			grid.add(line);
		}
		width = grid.get(0).size();
		height = grid.size();
		sc.close();
	}
	
	//~ Dijkstra’s Algorithm
	//distance cost + movement condition
	public int findPath(int minSteps, int maxSteps) {
		//TODO: implement type safety structures
		//PrioQueue sorted after distance. We use the element with the least distance so far for the next check
		//the elements are a list with the structure [row, col, dir, stepcount, distance]
		PriorityQueue<List<Object>> prioQueue = new PriorityQueue<List<Object>>((e1, e2) 
				-> Integer.compare((int)e1.get(4), (int)e2.get(4)));
		//store all visited elements. We only need row, col, dir and stepcount
		Map<List<Object>, Integer> visited = new LinkedHashMap<>();
		
		
		var dirset = EnumSet.range(EAST, NORTH);
		//initial element, step is -1 to reflect the starting tile. => will become 1
		var initElement = createQueueElement(0, 0, CENTER, -1, 0);
		prioQueue.add(initElement);
		//as long as we have elements in the queue
		while(!prioQueue.isEmpty()) {
			var element = prioQueue.poll();
			var row = (int)element.get(0);
			var col = (int)element.get(1);
			//early exit if we are now at the end position
			//due prio queue, this should be the least distance
			if(row == height-1 && col == width-1) {
				return (int)element.get(4);
			}
			//create a key list without the distance
			var key = createKey(element);
			//if we haven't checked this element, add it, no overwrite(!)
			if(!visited.containsKey(key)) {
				visited.put(key, (int)element.get(4));
				
				//add all neighbors to the queue, if they meet the conditions
				for(var d : dirset) {	
					var oldDir = (Direction)element.get(2);
					var oldStep = (int)element.get(3);
					
					var nextRow = row + d.getDir().y;
					var nextCol = col + d.getDir().x;					
					var newDir = d;
					//increase the stepcounter if we move in the same direction, else restart the counter
					var newStep = oldDir == newDir ? oldStep+1 : 1;
					
					//check boundries
					var inColBoundry = ((0 <= nextCol) && (nextCol < width));
					var inRowBoundry = ((0 <= nextRow) && (nextRow < height));
					//we can only move forward, left and right, not back
					var notReversed = !isReverse(oldDir, newDir);
						
					var validSteps = (newStep <= maxSteps //we can not move more than max Steps in one direction
							&& (newDir==oldDir  //if we move in the same directtion, go on. max steps is already covered
								|| oldStep>=minSteps //if we wanna turn, then only alow it if we already moved minSteps in one direction
								|| oldStep==-1)); //capture starting tile
					
					//if all conditions are true, this step is a valid step and we put it into the queue
					//this element can populate further
					if(inColBoundry && inRowBoundry && notReversed && validSteps) {
						var cost = grid.get(nextRow).get(nextCol);
						var newDist = (int)element.get(4) + cost;
						prioQueue.add(createQueueElement(nextRow, nextCol, newDir, newStep, newDist));	
					}
				}
			}
		}
		return -1;
		
	}
	
	private static List<Object> createQueueElement(int row, int col, Direction dir, int steps, int distance){
		List<Object> queueElement = new ArrayList<Object>();
		queueElement.add(row);
		queueElement.add(col);
		queueElement.add(dir);
		queueElement.add(steps);
		queueElement.add(distance);
		return queueElement;
	}
	
	private static List<Object> createKey(List<Object> queueElement){
		List<Object> key = new ArrayList<Object>();
		key.add(queueElement.get(0));
		key.add(queueElement.get(1));
		key.add(queueElement.get(2));
		key.add(queueElement.get(3));
		return key;
	}
}
