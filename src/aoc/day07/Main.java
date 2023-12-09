package aoc.day07;

import java.io.FileNotFoundException;

public class Main {
	
	public static void main(String[] args) {
		String fpath = "./src/inputs/day7.txt";
		try {
			CamelPoker cp = new CamelPoker(fpath);
			cp.printSolution();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
