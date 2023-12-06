package aoc.day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BoatRace {
	
	private List<String> maxTimes = new ArrayList<String>();
	private List<String> distRecords = new ArrayList<String>();
	
	public BoatRace(String fpath) throws FileNotFoundException {
		File input = new File(fpath);
		Scanner sc = new Scanner(input);	
		maxTimes = Arrays.asList(sc.nextLine().replaceAll("\s+", " ").split(" "));
		distRecords = Arrays.asList(sc.nextLine().replaceAll("\s+", " ").split(" "));
		sc.close();
	}	
	
	private long getWinProduct() {
		long result = 1;
		for(int i = 1; i < maxTimes.size(); i++) {
			int time = Integer.parseInt(maxTimes.get(i));
			int dist = Integer.parseInt(distRecords.get(i));
			result *= numberOfWins(time, dist);
		}
		return result;
	}
	
	private long numberOfWins(long time, long dist) {
		double x1 = (-time + Math.sqrt(time*time - (4*dist))) / -2;
		double x2 = (-time - Math.sqrt(time*time - (4*dist))) / -2;
		
		double a = Math.ceil(x1);
		double b = Math.floor(x2);
		a = a==x1? a+1 : a;
		b = b==x2? b-1 : b;	
		return ((long)(b-a+1));
	}
	
	private long kerningWin() {
		String timeString = "";
		String distString = "";
		for(int i = 1; i < maxTimes.size(); i++) {
			timeString += maxTimes.get(i);
			distString += distRecords.get(i);
		}
		return numberOfWins(Long.parseLong(timeString), Long.parseLong(distString));
	}
	
	public void printSolution() {
		System.out.println("Part 1: " + getWinProduct());
		System.out.println("Part 2: " + kerningWin());
	}
}
