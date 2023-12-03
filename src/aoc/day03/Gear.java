package aoc.day03;

import java.util.ArrayList;
import java.util.List;

public class Gear {

	public int x;
	public int y;
	public List<Integer> numbers = new ArrayList<>();
	
	public Gear(int row, int col, int number) {
		this.x = row;
		this.y = col;
		this.numbers.add(number);
	}
}
