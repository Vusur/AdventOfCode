package aoc.day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Almanac {

	
	//[ [dest1, source1, range1, dest2, source2, range2, ....],  	first map
	//	[dest1, source1, range1, dest2, source2, range2, ....], 	second map
	//	... ]
	private List<List<Long>> maps = new ArrayList<>();
	private List<Long> seeds = new ArrayList<>();
	
	public Almanac(String fpath) throws FileNotFoundException {
		File input = new File(fpath);
		Scanner sc = new Scanner(input);
		String line = "";
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			//line is seed line
			if(Pattern.compile("seeds:").matcher(line).find()) {
				Arrays.asList(line.split(" "))
					.stream()
					.skip(1)
					.forEach(s -> seeds.add(Long.parseLong(s)));
			}
			//line is start of new map block
			else if(Pattern.compile("map:").matcher(line).find()) {				
				List<Long> temp = new ArrayList<>();				
				while(sc.hasNextLine()) {
					line = sc.nextLine();
					//escape for end of block
					if(line.matches("")) break;
					Arrays.asList(line.split(" "))
						.stream()
						.forEach(n -> temp.add(Long.parseLong(n)));	
				}
				maps.add(temp);
			}
		}
		sc.close();
	}
	
	public void printSolution() {
		System.out.println("Part 1: " + getMinLocation());
		System.out.println("Part 2: " + getMinSeedRangeLocation());
	}
	
	private Long getMinLocation() {
		return seeds.stream().mapToLong(s -> getLocation(s)).min().orElse(-1);	
	}
	
	private Long getMinSeedRangeLocation() {
		Long currentMin = Long.MAX_VALUE;
		for(int i = 0; i < seeds.size(); i += 2)
		{
			Long seed = seeds.get(i);
			Long range = seeds.get(i+1);
			Long max = seed+range;
			for(Long l = seed; l < max; l++) {
				Long location = getLocation(l);
				currentMin = Math.min(location, currentMin);
			}
		}
		return currentMin;
		
	}
	
	private Long getLocation(Long source) {
		Long current = source;
		for(List<Long> map : maps) {
			current = getDestination(current, map);
		}	
		return current;
	}
	
	private Long getDestination(Long source, List<Long> map) {
		for(int i = 0; i < map.size(); i +=3) {
			Long dest = map.get(i);
			Long src = map.get(i+1);
			Long range = map.get(i+2);
			if(src <= source && source < src+range) return dest+source-src;
		}
		return source;
	}
	
}
