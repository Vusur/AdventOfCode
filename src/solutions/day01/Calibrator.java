package solutions.day01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Calibrator 
{	
	//Each line of the input is its own entry
	private List<String> lines = new ArrayList<String>();
	//mapper for the written numbers
	private static Map<String, String> numberMap = new HashMap<String, String>();
	static {
		numberMap.put("zero", "0");
		numberMap.put("one", "1");
		numberMap.put("two", "2");
		numberMap.put("three", "3");
		numberMap.put("four", "4");
		numberMap.put("five", "5");
		numberMap.put("six", "6");
		numberMap.put("seven", "7");
		numberMap.put("eight", "8");
		numberMap.put("nine", "9");

	}
	
	//constructor
	public Calibrator(String filePath) throws FileNotFoundException
	{
		File input = new File(filePath);
		Scanner sc = new Scanner(input);
		while(sc.hasNextLine()) {
			this.lines.add(sc.nextLine());
		}
		sc.close();
	}
	
	/** 
	 * Get the coordinate from a given String.
	 * The coordinate is defined as the combination of the first and last number of the string.
	 * @param 	line	is the line to extract the coordinate
	 * @return			The coordinate converted to an Integer
	 */
	private int getCoordinate(String line)
	{
		//replace all non numbers with nothing
		String str = line.replaceAll("[^0-9]+", "");
		//split the number in single digits
		List<String> numbers = Arrays.asList(str.trim().split(""));
		//combine the first and last number
		String coordinate = numbers.getFirst() + numbers.getLast();
		//return the combination as a real integer
		return Integer.parseInt(coordinate);
	}
	
	/**
	 * Get the real coordinate from a given String.
	 * The coordinate is defined as the combination of the first and last number of the String.
	 * This time, it's the integer OR the written value (example: 1 OR one).
	 * @param	line	is the line to extract the coordinate
	 * @return			The coordinate converted to an Integer
	 */
	private int getRealCoordinate(String line) 
	{
		//Stores all the numbers found
		List<String> numbers = new ArrayList<String>();
		//regexp for all potential values
		Pattern pattern = Pattern.compile("one|1|two|2|three|3|four|4|five|5|six|6|seven|7|eight|8|nine|9");
		//check the line
		Matcher matcher = pattern.matcher(line);
		//start index
		int i = 0;
		//as long as we find a match from the given starting index:
		while(matcher.find(i)) {
			//add the match
			numbers.add(matcher.group());
			//reset the index to the start of the match and also increase it by 1, to ignore the last match
			//this will make sure that cases like "oneight" is read as "one" and "eight"
			//without reseting the index, it will find only "one", put the index past "e" and only see "ight"
			i = matcher.start()+1;
		}
		//get the first entry
		String first = numbers.getFirst();
		//get the last entry
		String last = numbers.getLast();
		//the entries are either e.i. "1" or "one". Check if we can map the written entry, else get the default value
		String coordinate = numberMap.getOrDefault(first, first) + numberMap.getOrDefault(last, last);
		//return the combination as an Integer
		return Integer.parseInt(coordinate);
	}
	
	/**	Print both solution for the first day
	 */
	public void printSolution() {
		int result1 = 0;
		int result2 = 0;
		for (String line : lines) {
			result1 += getCoordinate(line);
			result2 += getRealCoordinate(line);
		}
		System.out.println("Part1: " + result1);
		System.out.println("Part2: " + result2);
	}	
}
