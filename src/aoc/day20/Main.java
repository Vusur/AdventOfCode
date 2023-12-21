package aoc.day20;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		String fpath = "./src/inputs/day20.txt";
		Circuit c = new Circuit(fpath);
		c.printSolution();
	}

}
