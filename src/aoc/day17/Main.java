package aoc.day17;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		String fpath = "./src/inputs/day17.txt";
		Crucible c = new Crucible(fpath);
		var r1 = c.findPath(1, 3);
		var r2 = c.findPath(4, 10);
		System.out.println("Part1: " + r1);
		System.out.println("Part1: " + r2);
		

	}

}
