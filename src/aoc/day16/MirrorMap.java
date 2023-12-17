package aoc.day16;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MirrorMap {

	List<List<MirrorTile>> mirrorLayout = new ArrayList<>();
	int width;
	int height;
	
	public MirrorMap(String fpath) throws FileNotFoundException {
		var input = new File(fpath);
		var sc = new Scanner(input);
		var lineNumber = 0;
		while(sc.hasNextLine()) {			
			var mirrorLine = new ArrayList<MirrorTile>();
			var line = sc.nextLine().split("");
			for(int i = 0; i < line.length; i++) {
				mirrorLine.add(new MirrorTile(i, lineNumber, line[i]));
			}
			mirrorLayout.add(mirrorLine);
			lineNumber += 1;
		}
		width = mirrorLayout.get(0).size();
		height = mirrorLayout.size();
		sc.close();
	}
	
	private void moveBeam(MirrorTile startTile, Point direction) {
		var beams = new HashMap<MirrorTile, List<Point>>();
		var dirList = new ArrayList<Point>();
		dirList.add(direction);
		beams.put(startTile, dirList);
		
		while(!beams.isEmpty()) {
			var nextBeams = new HashMap<MirrorTile, List<Point>>();
			for(var beam : beams.entrySet()) {
				var mirror = beam.getKey();
				var dirs = beam.getValue();
				var mx = mirror.x();
				var my = mirror.y();
				
				//for each incomming dir that a tile currently has
				for(var md : dirs) {
					//did we came already from this dir?
					if(mirror.checkDir(md)) {
						//collect the next dirs for this tile and direction
						var nextDirs = mirror.changeDirection(md);
						//check if we can go in this direction
						for(var d : nextDirs) {
							var nextX = mx + d.x;
							var nextY = my + d.y;
							if(	0 <= nextX && nextX < width && 0 <= nextY && nextY < height) {
								//grab the valid mirror
								var nextMirror = mirrorLayout.get(nextY).get(nextX);
								//is it already in queue?
								if(nextBeams.containsKey(nextMirror)) {
									nextBeams.get(nextMirror).add(d);
								}
								//it's a new mirror
								else {
									var l = new ArrayList<Point>();
									l.add(d);
									nextBeams.put(nextMirror, l);
								}
							}
						}
					}	
				}				
			}
			beams = nextBeams;			
		}
	}
	
	public int countEnergizedTiles() {
		var count = 0;
		for(List<MirrorTile> mts : mirrorLayout) {
			for(MirrorTile mt : mts) {
				count += mt.energized() ? 1 : 0;
			}
		}
		return count;
	}
	
	public void clearBoard() {
		for(var l : mirrorLayout) {
			for(var t : l) {
				t.clearTile();
			}
		}
	}
	
	public int findMaxEnergized() {
		var max = 0;
		
		for(int i=0; i < width; i++) {
			clearBoard();
			moveBeam(mirrorLayout.get(0).get(i), new Point(0, 1));
			max = Math.max(max, countEnergizedTiles());
			
			clearBoard();
			moveBeam(mirrorLayout.get(height-1).get(i), new Point(0, -1));
			max = Math.max(max, countEnergizedTiles());
		}
		
		for(int i=0; i < height; i++) {
			clearBoard();
			moveBeam(mirrorLayout.get(i).get(0), new Point(1, 0));
			max = Math.max(max, countEnergizedTiles());
			
			clearBoard();
			moveBeam(mirrorLayout.get(i).get(width-1), new Point(-1, 0));
			max = Math.max(max, countEnergizedTiles());	
		}		
		return max;
	}
		
	public void printSolution() {
		moveBeam(mirrorLayout.get(0).get(0), new Point(1, 0));

		System.out.println("Part 1: " + countEnergizedTiles());
		System.out.println("Part 2: " + findMaxEnergized());		
	}
}
