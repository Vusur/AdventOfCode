package aoc.day09;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
		String fpath = "./src/inputs/day9.txt";
		
		try {
			Oasis oasis = new Oasis(fpath);
			oasis.printSolution();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
