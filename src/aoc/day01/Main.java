package aoc.day01;

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) 
	{
		String fpath = "./src/inputs/day1.txt";
		try {
			Calibrator calibrator = new Calibrator(fpath);
			calibrator.printSolution();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
