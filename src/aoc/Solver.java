package aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class Solver {

	protected List<List<String>> inputMap = new ArrayList<>();
	protected Long resultPartOne = 0L;
	protected Long resultPartTwo = 0L;
	
	public Solver(String fpath) throws FileNotFoundException {
		var input = new File(fpath);
		var sc = new Scanner(input); 
		while(sc.hasNextLine()) {
			inputMap.add(Arrays.asList(sc.nextLine().split("")));
		}
		sc.close();
	}
	
	public Long getResultPartOne() {
		return resultPartOne;
	}
	
	public Long getResultPartTwo() {
		return resultPartTwo;
	}
}
