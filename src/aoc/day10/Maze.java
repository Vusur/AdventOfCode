package aoc.day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static aoc.day10.Direction.*;

public class Maze {
	
		
	private int height = 0;
	private int width = 0;
	List<List<Pipe>> pipes= new ArrayList<>();
	Pipe startPipe;

	public Maze(String fpath) throws FileNotFoundException {
		var input = new File(fpath);
		var sc = new Scanner(input);
		var y = 0;
		while(sc.hasNextLine()) {
			var line = sc.nextLine().split("");
			var pipeline = new ArrayList<Pipe>();
			for(int i = 0; i < line.length; i++) {
				var pipe = new Pipe(line[i], i, y);
				if(pipe.type().equals("S")) {
					startPipe = pipe;
				}
				pipeline.add(pipe);
			}			
			pipes.add(pipeline);
			y += 1;
		}
		convert();
		height = pipes.size();
		width = pipes.get(0).size();
		sc.close();
	}
	
	private void convert() {
		var x = startPipe.x();
		var y = startPipe.y();
		
		startPipe.convertPipe(
				pipes.get(y-1).get(x), 
				pipes.get(y).get(x+1), 
				pipes.get(y+1).get(x), 
				pipes.get(y).get(x-1));
		
	}

	
	public int traverse() {
		var steps = 0;
		var currentPipe = startPipe;
		Pipe nextPipe = null;
	
		do {
			steps += 1;
			var x = currentPipe.x();
			var y = currentPipe.y();
			currentPipe.setVisited();
			
			//check north
			if(nextPipe == null && 0 < y) {
				var p = pipes.get(y-1).get(x);
				nextPipe = (!p.visited() && currentPipe.nextIsValid(p, NORTH)) ? p : null;
			}
			//east
			if(nextPipe == null && x < width-1) {
				var p = pipes.get(y).get(x+1);
				nextPipe = (!p.visited() && currentPipe.nextIsValid(p, EAST)) ? p : null;
			}
			//south
			if(nextPipe == null && y < height-1) {
				var p = pipes.get(y+1).get(x);
				nextPipe = (!p.visited() && currentPipe.nextIsValid(p, SOUTH)) ? p : null;
			}
			//west
			if(nextPipe == null && 0 < x) {
				var p = pipes.get(y).get(x-1);
				nextPipe = (!p.visited() && currentPipe.nextIsValid(p, WEST)) ? p : null;
			}
			//there is no next pipe
			if(nextPipe == null) {
				break;
			}
			currentPipe = nextPipe;
			nextPipe = null;
		} while(currentPipe != startPipe);
		
		return steps;
	}
	
	public int countArea() {
		int counter = 0;
		
		for(int y = 0; y < height; y++) {
			var inside = false;
			for(int x = 0; x < width; x++) {
				var pipe = pipes.get(y).get(x);
				var type = pipe.type();
				if(pipe.visited() && (type.equals("|") || type.equals("7") || type.equals("F"))) {
					inside = !inside;
				}
				if(!pipe.visited() && inside) {
					counter += 1;
				}
			}
		}
		return counter;
	}
	

	
	public void printSolution() {
		var i = traverse();
		var j = countArea();
		System.out.println("Part 1 " + i/2);
		System.out.println("Part 2 " + j);
	}
}
