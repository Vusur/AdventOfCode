package aoc.day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;



public class Notes  {

	
	List<MirrorMap> maps = new ArrayList<MirrorMap>();
	int result = 0;
	
	public Notes(String fpath) throws FileNotFoundException {
		var input = new File(fpath);
		var sc = new Scanner(input); 
		var map = new MirrorMap();
		maps.add(map);
		while(sc.hasNextLine()) {
			var line = sc.nextLine();
			if(line.equals("")) {
				map = new MirrorMap();
				maps.add(map);
			}
			else {
				map.addCol(line);
			}
		}
		sc.close();
	}
	
	public void printSolution() {
		var p1 = 0;
		var p2 = 0;
		for(MirrorMap m : maps) {
			p1 += getMuddyReflectionLines(m.cols(), 0) + getMuddyReflectionLines(m.rows(), 0)*100;
			p2 += getMuddyReflectionLines(m.cols(), 1) + getMuddyReflectionLines(m.rows(), 1)*100;
		}		
		System.out.println("Part1: " + p1);
		System.out.println("Part2: " + p2);
	}

	//mudCount == allowed difference between two sides for a potential mirror
	//Part 1 is just Part 2 but with 0 mud
	public int getMuddyReflectionLines(List<String> lines, int mudCount) {
		var length = lines.size();
		//check each pair
		for(int i = 1; i < lines.size(); i++) {
			var left = lines.get(i-1);
			var right = lines.get(i);
			//the pair has a potential mirror line if it has 0 or mudCount differences.
			//also remember how much mud is used
			var remainingMud = mudCount - diffCount(left, right);
			if(0 <= remainingMud && remainingMud <= mudCount) {
				//set two indexes before and after the potential pair
				var lc = i-2;
				var rc = i+1;
				//as long as such pairs exists, check for differences
				while(0 <= lc && rc < length) {
					left = lines.get(lc);
					right = lines.get(rc);
					//calc the differences and use mud up
					remainingMud -= diffCount(left, right);
					//move indexes
					lc -= 1;
					rc += 1;
				}
				//we checked all pairs for the potential mirrorline
				//the mirrorline is a true mirrorline, if we have exactly 0 mud left
				if(remainingMud == 0) {
					return i;
				}
			}
			
		}		
		return 0;
	}
	
	private int diffCount(String l, String r) {
		return (int) IntStream.range(0, l.length())
				.filter(i -> l.charAt(i) != r.charAt(i))
				.count();
	}
	
}
