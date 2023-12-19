package aoc.day18;

import java.io.FileNotFoundException;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException {
		String fpath = "./src/inputs/day18.txt";
		DigPlan dp = new DigPlan(fpath);
		dp.printSolution();
		
	}

}
