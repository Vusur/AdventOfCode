package aoc.day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScratchcardGame {
	
	//[#1 wins], [#1 copies], [#2 wins], [#2 copies],...
	//index+1 played card
	//index*2 wins for the played card
	//index*2+1 copies of that card
	//total amount of cards is cardResult/2
	private List<Integer> cardResults = new ArrayList<>();

	public ScratchcardGame(String fpath) throws FileNotFoundException {		
		File input = new File(fpath);
		Scanner sc = new Scanner(input);
		while(sc.hasNextLine()) {
			String[] temp = sc.nextLine().split(":")[1].split("\\|");
			playCurrentCard(temp[0], temp[1]);
		}
		sc.close();
		addCardCopies();
	}
	
	private void playCurrentCard(String winningNumbers, String myNumbers) {
		Pattern pattern = Pattern.compile("\\W+");
		List<String> winNum = Arrays.asList(pattern.split(winningNumbers.trim()));
		List<String> myNum = Arrays.asList(pattern.split(myNumbers.trim()));
		int wins = (int)myNum.stream().filter(winNum::contains).count();
		this.cardResults.add(wins); //add the wins
		this.cardResults.add(1); //add the original as a copy
	}
	
	//If we know the total wins of the current card, we know how many next cards are affected. (i+1 to i+wins with i as the current card)
	//If we also know how many copies the current card has, we can add that amount to the next card.
	//i.e.
	//card 2 has 6 wins and 4 copies (including the original)
	//card 2 affects card 3, 4, 5, 6, 7, 8 because of the wins
	//each card gets 4 copies, because we can play card 2 four times.
	private void addCardCopies() {
		for(int i = 0; i < (cardResults.size() / 2); i++) {
			int wins = cardResults.get(i*2);
			int copies = cardResults.get(i*2+1);
			int maxIndex = cardResults.size()-1;
			for(int j = i+1; j <= Math.min(i+wins, maxIndex); j++) {			
				cardResults.set(j*2+1, (cardResults.get(j*2+1) + copies));
			}
		}
	}
	
	private int sumOfWins() {
		int sum = 0;
		for(int i = 0; i < (cardResults.size() / 2); i++) {
			int wins = cardResults.get(i*2);
			sum += wins > 0 ? Math.pow(2, wins-1) : 0;
		}
		return sum;
	}
	
	private int sumOfCards() {
		int sum = 0;
		for(int i = 0; i < (cardResults.size() / 2); i++) {
			sum += cardResults.get(i*2 + 1);
		}
		return sum;
	}
	
	public void printSolution() {
		System.out.println("Part1: " + sumOfWins());
		System.out.println("Part2: " + sumOfCards());
	}
	
}
