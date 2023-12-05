package aoc.day05;

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) {
		String fpath = "./src/inputs/day5.txt";	
		try {
			Almanac alm = new Almanac(fpath);
			alm.printSolution();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
