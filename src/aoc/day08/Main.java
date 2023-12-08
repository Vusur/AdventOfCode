package aoc.day08;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
		String fpath = "./src/inputs/day8.txt";
		
		try {
			Network net = new Network(fpath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
