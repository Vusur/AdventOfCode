package aoc.day04;

import java.io.FileNotFoundException;



public class Main {

	public static void main(String[] args) 
	{
		String fpath = "./src/inputs/day4.txt";
		try {
			ScratchcardGame game = new ScratchcardGame(fpath);
			game.printSolution();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
