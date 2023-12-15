package aoc.day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

	static List<Map<String, Integer>> boxes = new ArrayList<>();
	
	public static void main(String[] args) throws FileNotFoundException {
		String fpath = "./src/inputs/day15.txt";
		
		var input = new File(fpath);
		var sc = new Scanner(input);
		var line = sc.nextLine().split(",");
		var r = 0;
		
		for(int i = 0; i < 256; i++) {
			boxes.add(new LinkedHashMap<String, Integer>());
		}
		
		for(String s : line) {
			r += toHash(s);
			putInBox(s);
		}
		System.out.println(r);
		System.out.println(calcPower());
		sc.close();
		
				
	}
	
	public static int toHash(String string) {
		var value = 0;
		for(int i = 0; i < string.length(); i++) {
			value = value + string.charAt(i);
			value = value * 17;
			value = value % 256;
		}
		return value;
	}
	
	private static void putInBox(String string) {
		var adding = string.contains("=");
		var input = string.split("[=-]");
		var hash = toHash(input[0]);
		var map = boxes.get(hash);
		if(adding) {			
			map.put(input[0], Integer.parseInt(input[1]));
		}
		else {
			map.remove(input[0]);
		}
	}
	
	private static int calcPower() {
		var r = 0;
		for(int boxInd = 0; boxInd < boxes.size(); boxInd++) {
			var map = boxes.get(boxInd);
			var keys = map.keySet();
			var keyInd = 0;
			for(String key : keys) {
				var boxPower = (boxInd+1) * (keyInd+1) * map.get(key);
				r += boxPower;
				keyInd += 1;
			}		
		}
		return r;
	}	
}
