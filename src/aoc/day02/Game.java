package aoc.day02;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/***
 * Represents each individual game
 */
public class Game {
	
	//the ID of the current game
	private int id;
	//All played sets are represented as bags
	private List<Bag> bags = new ArrayList<Bag>();
	
	//limit the valid game
	final private int maxRed = 12;
	final private int maxBlue = 13;
	final private int maxGreen = 14;
	
	/***
	 * Creates the game with its played sets
	 * @param input	the current game data
	 */
	public Game(String input) {
		String[] str = input.split(":");
		id = Integer.parseInt(str[0].split(" ")[1].trim());
		for(String i : str[1].split(";")) {
			bags.add(new Bag(i));
		}
	}
	
	/***
	 * Get the ID of the game, if all played sets are within the given limit
	 * @return The ID or 0
	 */
	public int getValidId() {
		if (!isValid()) return 0;
		return this.id;
	}
	
	/***
	 * Get the power of each game. The power is defined as the product of the fewest number of balls for each color needed to play the given game
	 * @return The power of the game
	 */
	public int getPower() {
		int red = bags.stream().max(Comparator.comparing(bag -> bag.red)).get().red;
		int green = bags.stream().max(Comparator.comparing(bag -> bag.green)).get().green;
		int blue = bags.stream().max(Comparator.comparing(bag -> bag.blue)).get().blue;
		return (red*green*blue);
	}
	
	/***
	 * Checks if all played set are valid
	 * @return boolean
	 */
	private boolean isValid() {
		return bags.stream().allMatch(bag -> {
			return ((bag.red <= maxRed) && (bag.green <= maxGreen) && (bag.blue <= maxBlue));
		});
	}
}
