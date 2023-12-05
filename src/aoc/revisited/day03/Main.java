package aoc.revisited.day03;

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) {
		String fpath = "./src/inputs/day3.txt";	
		try {
			Engine engine = new Engine(fpath);
			engine.printSolution();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
