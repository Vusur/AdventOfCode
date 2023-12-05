package aoc.revisited.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Engine {
	
	//Geardata example:
	//[ [x, y, number, number] 			#first found gear
	//	[x, y, number, number, number] 	#second found gear
	//	[x, y, number] ]				#third found gear
	private List<List<Integer>> gears = new ArrayList<>();
	
	private final int LINELENGTH;
	private final int MAXLINES;
	
	private List<String> lines = new ArrayList<>();	
	private int sumValidNumbers = 0;
	private int sumGearPower = 0;
	private int currentNumber = 0;
	
	public Engine(String fpath) throws FileNotFoundException {
		File input = new File(fpath);
		Scanner sc = new Scanner(input);
		while(sc.hasNextLine()) {
			lines.add(sc.nextLine());
		}
		sc.close();
		
		LINELENGTH = lines.get(0).length();
		MAXLINES = lines.size();
	}
	
	public void printSolution() {
		scanSchematic();

		System.out.println("Part 1: " + sumValidNumbers);
		System.out.println("Part 2: " + sumGearPower);
	}
	
	private void scanSchematic() {
		Pattern pattern = Pattern.compile("\\d+");
		for(int i = 0; i < MAXLINES; i++) {
			Matcher matcher = pattern.matcher(lines.get(i));
			while(matcher.find()) {
				currentNumber = Integer.parseInt(matcher.group());
				int xStart = matcher.start();
				int xEnd = matcher.end() - 1;
				scanRegion(i, xStart, xEnd);
			}
		}
		gears.stream().filter(g -> g.size()==4).forEach(g -> sumGearPower += g.get(2)*g.get(3));
	}
	
	private void scanRegion(int lineNumber, int xStart, int xEnd) {
		//set boundries to look at
		int bx1 = Math.max(xStart-1, 0);
		int bx2 = Math.min(xEnd+1, LINELENGTH-1);
		int by1 = Math.max(lineNumber-1, 0);
		int by2 = Math.min(lineNumber+1,  MAXLINES-1);
		boolean isValid = false;
		//check the whole box/region
		for(int y=by1; y <= by2; y++) {
			for(int x=bx1; x <= bx2; x++) {
				//we have to check the whole region for stars, remember true, if it was once true with or-logic
				isValid = isValid || checkSymbol(x, y); 
			}
		}
		sumValidNumbers += isValid ? currentNumber : 0;
	}
	
	private boolean checkSymbol(int x, int y) {
		Pattern pattern = Pattern.compile("[^\\.0-9]");
		String symbol = String.valueOf(lines.get(y).charAt(x));
		if(pattern.matcher(symbol).matches()) {
			if(symbol.equals("*")) 
				addGear(x, y);
			return true;
		}
		return false;
	}
	
	private void addGear(int x, int y) {
		for(List<Integer> gear : gears) {
			if((gear.get(0) == x) && (gear.get(1) == y)) {
				gear.add(currentNumber);
				return; //short circuit addGear
			}
		}
		//we did not found a matching gear, so add the current gear
		List<Integer> newGear = new ArrayList<>(Arrays.asList(x, y, currentNumber));
		gears.add(newGear);
	}
}
