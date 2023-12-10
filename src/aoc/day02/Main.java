package aoc.day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) 
	{
		String fpath = "./src/inputs/day2.txt";
		Game game;
		int result1 = 0;
		int result2 = 0;
		try {
			File input = new File(fpath);
			Scanner sc = new Scanner(input);
			while(sc.hasNextLine()) {
				game = new Game(sc.nextLine());
				result1 += game.getValidId();
				result2 += game.getPower();
			}
			System.out.println("Part 1: " + result1);
			System.out.println("Part 2: " + result2);
			sc.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
