package aoc.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Schematic {
	
	private String grid[][];
	private int gridWidth;
	private int gridHeight;
	
	private int x1 = 0; 
	private int x2 = -1;
	private int y = 0;
	
	private int currentNumber = 0;
	private List<Gear> gears = new ArrayList<Gear>();
	
	public Schematic(String fpath) throws FileNotFoundException {
		File input = new File(fpath);
		Scanner sc = new Scanner(input);
		List<List<String>> lines = new ArrayList<>();
		while(sc.hasNextLine()) {
			lines.add(Arrays.asList(sc.nextLine().split("")));
		}
		sc.close();
		grid = lines.stream().map(line -> line.toArray(new String[line.size()])).toArray(String[][]::new);
		gridWidth = grid[0].length;
		gridHeight = grid.length;
	}
	
	public int sumOfAllValidNumbers() {
		int result = 0;
		while(regionNextNumber()) {
			result += borderContains("[^\\.0-9]") ? currentNumber : 0;
		}
		return result;
	}
	
	public int sumOfAllGearRatios() {		
		int result = 0;
		
		for(Gear g : gears) {
			int size = g.numbers.size();
			if(size == 2) result += (g.numbers.get(0)*g.numbers.get(1));
		}	
		return result;
	}
	
	private boolean regionNextNumber() {
		Pattern pattern = Pattern.compile("\\d");
		for(int col = y; col < gridHeight; col++) {
			for(int row = x2+1; row < gridWidth; row++) {
				if(pattern.matcher(grid[col][row]).matches()) {
					y = col;
					x1 = row;
					x2 = row;
					while(x2+1 < gridWidth) {
						if(!pattern.matcher(grid[y][x2+1]).matches()) break;
						x2++;
					}
					currentNumber = setNumber();
					return true;
				}
			}
			x2 = -1;
		}
		return false;
	}

	
	private int setNumber() {
		String result = "";
		for(int i = x1; i <= x2; i++) {
			result += grid[y][i];
		}
		return Integer.parseInt(result);
	}
	
	private boolean borderContains(String regex) {
		//set the border, make sure you are not oob
		int bx1 = Math.max(x1-1, 0);
		int bx2 = Math.min(x2+1, gridWidth-1);
		int by1 = Math.max(y-1, 0);
		int by2 = Math.min(y+1, gridHeight-1);
		boolean contains = false;
		
		Pattern pattern = Pattern.compile(regex);
		Pattern star = Pattern.compile("\\*");
		
		for(int col = by1; col <= by2; col++) {
			for(int row = bx1; row <= bx2; row++) {
				//skip
				if(isInside(col, row)) continue;
				//check if symbol
				if(pattern.matcher(grid[col][row]).matches()) contains = true;
				//check if star
				if(star.matcher(grid[col][row]).matches()) addGear(col, row);
			}
		}
		return contains;
	}
	
	private void addGear(int col, int row) {
		for(Gear g : gears) {
			if(g.y == col && g.x == row) {
				g.numbers.add(currentNumber);
				return;
			}
		}
		gears.add(new Gear(row, col, currentNumber));

	}
	
	private boolean isInside(int col, int row) {
		return ((col == y) && (x1 <= row) && (row <= x2));
	}
}
