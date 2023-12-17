package aoc.day16;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		String fpath = "./src/inputs/day16.txt";
		
		MirrorMap mm = new MirrorMap(fpath);
		mm.printSolution();

	}

}
