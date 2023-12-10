package aoc.day02;

import java.util.Arrays;
import java.util.List;

/***
 * Represents the bag for a played set
 */
public class Bag {
	
	//all balls in the bag
	public int red = 0;
	public int green = 0;
	public int blue = 0;
	
	/***
	 * Creates the bag with all the balls
	 * @param input	the current set
	 */
	public Bag(String input) {
		List<String> balls = Arrays.asList(input.split(","));
		for(String ball : balls) {
			String[] tuple = ball.trim().split(" ");
			switch (tuple[1]) {
			case "red":
				red = Integer.parseInt(tuple[0].trim());
				break;
			case "green":
				green = Integer.parseInt(tuple[0].trim());
				break;
			case "blue":
				blue = Integer.parseInt(tuple[0].trim());
				break;
			}
		}
	}
}
