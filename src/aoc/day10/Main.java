package aoc.day10;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
		String fpath = "./src/inputs/day10.txt";	
		
		try {
			Maze maze = new Maze(fpath);
			maze.printSolution();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
