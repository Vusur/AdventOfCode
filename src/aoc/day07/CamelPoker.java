package aoc.day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CamelPoker {

	List<Hand> hands = new ArrayList<>();
	List<Hand> HandJoker = new ArrayList<>();
	
	public CamelPoker(String fpath) throws FileNotFoundException {
		File input = new File(fpath);
		Scanner sc = new Scanner(input);
		//Hand hand = new Hand(sc.nextLine());
		while(sc.hasNextLine()) {
			var in = sc.nextLine();
			hands.add(Hand.evaluate(in));
			HandJoker.add(Hand.evaluateJoker(in));
		}
		sc.close();
		hands.sort(Hand::compareTo);
		HandJoker.sort(Hand::compareTo);
		System.out.println("Part 1: " + totalWinnings(hands));
		System.out.println("Part 2: " + totalWinnings(HandJoker));
	}
	
	public int totalWinnings(List<Hand> h) {
		int i = 1;
		int result = 0;
		for(Hand hand : h) {
			System.out.print("Rank " + i + ": " );
			System.out.print(hand.category().name() + "   ");
			Arrays.stream(hand.cards()).forEach(c -> System.out.print(c.name() + " "));
			System.out.println(" Bid: " + hand.bid());
			result += hand.bid()*i;
			i++;
		}
		return result;
	}
}
