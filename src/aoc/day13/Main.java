package aoc.day13;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
		String fpath = "./src/inputs/day13.txt";	
		
		try {
			Notes mm = new Notes(fpath);
			mm.printSolution();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
