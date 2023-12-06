package aoc.day06;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
		String fpath = "./src/inputs/day6.txt";	
		try {
			BoatRace race = new BoatRace(fpath);
			race.printSolution();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
