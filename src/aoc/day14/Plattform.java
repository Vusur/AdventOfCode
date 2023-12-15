package aoc.day14;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import static aoc.day14.Category.*;

public class Plattform {

	List<List<Category>> spots = new ArrayList<List<Category>>();
	Map<Integer, List<List<Category>>> uniqueMaps = new HashMap<>();
	int loopCount = 0;
	int width;
	int height;
	
	public Plattform(String fpath) throws FileNotFoundException {
		var input = new File(fpath);
		var sc = new Scanner(input);
		while(sc.hasNextLine()) {
			spots.add( Arrays.asList(sc.next().split("")).stream()
					.map(n -> mapper(n))
					.collect(Collectors.toList()) );
		}
		width = spots.get(0).size();
		height = spots.size();
		sc.close();
	}
	
	
	public int rotate() {
		tiltNorth();
		tiltWest();
		tiltSouth();
		tiltEast();
		loopCount += 1;
		return spots.hashCode();
	}
	
	public void tiltNorth() {
		//for each col		
		for(int x = 0; x < width; x++) {
			List<Integer> emptySpots = new ArrayList<>();
			for(int y = 0; y < height; y++) {
				var spot = spots.get(y).get(x);
				
				if(spot == FREE) {
					emptySpots.add(y);
				}
				else if(spot == PILLAR) {
					emptySpots.clear();
				}
				else if(!emptySpots.isEmpty()) {
					var spotY = emptySpots.remove(0);
					spots.get(y).set(x, FREE);
					spots.get(spotY).set(x, STONE);
					y -= 1;
				}
			}
		}
	}
	
	private void tiltSouth() {
		for(int x = 0; x < width; x++) {
			List<Integer> emptySpots = new ArrayList<>();
			for(int y = height-1; 0 <= y ; y--) {
				var spot = spots.get(y).get(x);
				
				if(spot == FREE) {
					emptySpots.add(y);
				}
				else if(spot == PILLAR) {
					emptySpots.clear();
				}
				else if(!emptySpots.isEmpty()) {
					var spotY = emptySpots.remove(0);
					spots.get(y).set(x, FREE);
					spots.get(spotY).set(x, STONE);
					y += 1;
				}
			}
		}	
	}
	
	private void tiltWest() {
		for(int y = 0; y < height; y++) {
			List<Integer> emptySpots = new ArrayList<>();
			for(int x = 0; x < width; x++) {
				var spot = spots.get(y).get(x);
				
				if(spot == FREE) {
					emptySpots.add(x);
				}
				else if(spot == PILLAR) {
					emptySpots.clear();
				}
				else if(!emptySpots.isEmpty()) {
					var spotX = emptySpots.remove(0);
					spots.get(y).set(x, FREE);
					spots.get(y).set(spotX, STONE);
					x -= 1;
				}
			}
		}
	}
	
	private void tiltEast() {
		for(int y = 0; y < height; y++) {
			List<Integer> emptySpots = new ArrayList<>();
			for(int x = width -1; 0 <= x; x--) {
				var spot = spots.get(y).get(x);
				
				if(spot == FREE) {
					emptySpots.add(x);
				}
				else if(spot == PILLAR) {
					emptySpots.clear();
				}
				else if(!emptySpots.isEmpty()) {
					var spotX = emptySpots.remove(0);
					spots.get(y).set(x, FREE);
					spots.get(y).set(spotX, STONE);
					x += 1;
				}
			}
		}
	}
	
	
	
	public int countLoad() {
		int load = 0;
		for(int i = height-1; 0 <= i; i--) {
			for(Category c : spots.get(i)) {
				if(c == STONE) {
					load += height - i;
				}
			}
		}
		return load;
		
	}
	
	private int findAllStates() {
		var keys = new HashSet<Integer>();
		while(keys.add(rotate())) ;
		
		return keys.size();
	}
	
	private int findCycleLen() {
		var keys = new HashSet<Integer>();
		while(keys.add(rotate())) ;
		
		return keys.size();
	}
	
	private void loopToTheMax(int max) {
		var initloops = findAllStates();
		var cycle = findCycleLen();
		
		/*
		var rest = (max - loopCount) % cycle;
		System.out.println("TODO: " + rest);
		rotate();
		System.out.println(countLoad());
		*/
		while(loopCount+cycle < max) {
			loopCount += cycle;
		}
		
		for(int i = loopCount; i < max; i++) {
			rotate();	
		}
		
	}
	
	
	
	
	public void printSolution() {
		tiltNorth();
		System.out.println("Part1 : " + countLoad());
		loopToTheMax(1000);
		System.out.println("Part2 : " + countLoad());
		//printSpots();
		

	}
	
	private Category mapper(String s) {
		if(s.equals("O")) return STONE;
		else if(s.equals("#")) return PILLAR;
		else return FREE;
	}
	
	public void printSpots() {
		for(List<Category> line : spots) {
			for(Category s :line) {
				System.out.print(s.toString());
			}
			System.out.println();
		}
		System.out.println();
	}
			
}
