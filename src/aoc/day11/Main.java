package aoc.day11;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
		String fpath = "./src/inputs/day11.txt";	
		
		try {
			GalaxyMap gm = new GalaxyMap(fpath);
			gm.print();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
