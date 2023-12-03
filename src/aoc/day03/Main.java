package aoc.day03;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) 
	{
		String fpath = "./src/inputs/day3.txt";
		try {
			Schematic s = new Schematic(fpath);
			System.out.println(s.sumOfAllValidNumbers());
			System.out.println(s.sumOfAllGearRatios());
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
