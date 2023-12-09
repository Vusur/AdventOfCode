package aoc.day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CamelPoker {

	List<Hand> hands = new ArrayList<>();
	List<Hand> handJoker = new ArrayList<>();
	
	public CamelPoker(String fpath) throws FileNotFoundException {
		File input = new File(fpath);
		Scanner sc = new Scanner(input);
		//Hand hand = new Hand(sc.nextLine());
		while(sc.hasNextLine()) {
			var in = sc.nextLine();
			hands.add(Hand.evaluate(in, false));
			handJoker.add(Hand.evaluate(in, true));
		}
		sc.close();
		hands.sort(Hand::compareTo);
		handJoker.sort(Hand::compareTo);
	}
	
	public int totalWinnings(List<Hand> h) {
		int i = 1;
		int result = 0;
		for(Hand hand : h) {
			result += hand.bid()*i;
			i++;
		}
		return result;
	}
	
	public void printSolution() {
		System.out.println("Part 1: " + totalWinnings(hands));
		System.out.println("Part 2: " + totalWinnings(handJoker));
	}
}
