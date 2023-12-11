package aoc.day11;


import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import aoc.Solver;

public class GalaxyMap extends Solver {

	private List<Point> galaxies = new ArrayList<>();
	private List<Integer> emptyRowInd = new ArrayList<>();
	private List<Integer> emptyColInd = new ArrayList<>();
	
	public GalaxyMap(String fpath) throws FileNotFoundException {
		super(fpath);
		init();
	}
		
	private void init() {
		var width = inputMap.get(0).size();
		var height = inputMap.size();
		for(int i = 0; i < width; i++) {
			emptyColInd.add(i);
		}
		for(int i = 0; i < height; i++) {
			emptyRowInd.add(i);
		}
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				var symbol = inputMap.get(row).get(col);
				if(symbol.equals("#")) {
					emptyRowInd.set(row, -1);
					emptyColInd.set(col, -1);
					galaxies.add(new Point(col, row));
				}
			}
		}
		emptyRowInd = emptyRowInd.stream().filter(n -> n != -1).toList();
		emptyColInd = emptyColInd.stream().filter(n -> n != -1).toList();
	}
	
	private Long calcDistances(int spaceIncrease) {
		var totalDist = 0L;
		var galaxies =expandSpace(spaceIncrease);
		for(int i=0; i < galaxies.size(); i++) {
			for(int j=i+1; j < galaxies.size(); j++) {
				var dist = manhattenDistance(galaxies.get(i), galaxies.get(j));
				totalDist += dist;
			}
		}
		return totalDist;
	}
	
	private List<Point> expandSpace(int spaceIncrease) {
		var expandedGalaxies = new ArrayList<Point>();
		for(Point p : galaxies) {
			var expX = getExpansionMulti(p.x, emptyColInd);
			var expY = getExpansionMulti(p.y, emptyRowInd);
			var newX = p.x + spaceIncrease*expX;
			var newY = p.y + spaceIncrease*expY;
			expandedGalaxies.add(new Point((int)newX, (int)newY));
		}
		return expandedGalaxies;
	}
	
	private long getExpansionMulti(int value, List<Integer> emptyInd) {
		return emptyInd.stream().filter(n -> n < value).count();
	}
	
	private int manhattenDistance(Point p1, Point p2) {
		return Math.abs(p2.x - p1.x) + Math.abs(p2.y - p1.y);
	}
	
	public void print() {
		System.out.println("Part 1: " + calcDistances(1));
		System.out.println("Part 2: " + calcDistances(999999));
	}

}
