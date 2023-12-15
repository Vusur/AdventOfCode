package aoc.day14;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
		String fpath = "./src/inputs/day14.txt";
		
		try {
			Plattform p = new Plattform(fpath);
			p.printSolution();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

}
