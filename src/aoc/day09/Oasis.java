package aoc.day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Oasis {

	Long sumPart1 = 0L;
	Long sumPart2 = 0L;
	
	public Oasis(String fpath) throws FileNotFoundException {
		var input = new File(fpath);
		var sc = new Scanner(input);	
		while(sc.hasNextLine()) {
			var sequence = Stream.of(sc.nextLine().split(" "))
					.map(n -> Long.parseLong(n))
					.collect(Collectors.toList());
			sumPart1 += predictNumber(sequence);
			sumPart2 += predictNumber(sequence.reversed());
		}		
		sc.close();
	}
	
	private Long predictNumber(List<Long> sequence) {		
		if(sequence.stream().allMatch(number -> number == 0)){ 
			return 0L;
		}
		List<Long> nextNumber = new ArrayList<Long>();
		for(var i = 0; i < sequence.size()-1; i++) {
			var next = sequence.get(i+1) - sequence.get(i);
			nextNumber.add(next);
		}
		return sequence.get(sequence.size()-1) + predictNumber(nextNumber);
	}
	
	public void printSolution() {
		System.out.println("Part 1: " + sumPart1);
		System.out.println("Part 2: " + sumPart2);
	}
}
