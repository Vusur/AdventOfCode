package aoc.day07;

import static aoc.day07.Category.*;
import static aoc.day07.Card.*;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.comparing;


import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import java.util.stream.Stream;


public record Hand(Category category, Integer bid, Card... cards) implements Comparable<Hand>{
	
	private static Map<String, Card> dict = Map.ofEntries(
			Map.entry("X", JOKER),
			Map.entry("2", TWO), 
			Map.entry("3", THREE),
			Map.entry("4", FOUR), 
			Map.entry("5", FIVE),
			Map.entry("6", SIX), 
			Map.entry("7", SEVEN),
			Map.entry("8", EIGHT), 
			Map.entry("9", NINE),
			Map.entry("T", TEN), 
			Map.entry("J", JACK),
			Map.entry("Q", QUEEN), 
			Map.entry("K", KING),
			Map.entry("A", ACE)
			);

	public static Hand evaluate(String input, boolean joker) {
		var temp = input.strip().split(" ");
		var cards = getCards(temp[0], joker);
		var bid = Integer.parseInt(temp[1]);
		
		var counts =  Arrays.stream(cards).collect(groupingBy(card -> card, counting()));
		counts = joker ? convertJoker(counts) : counts;
		var ranks =	counts.entrySet().stream()
				.sorted(comparing(Entry<Card, Long>::getValue).reversed())
				.map(Entry::getKey)
				.toArray(Card[]::new);
		
		if(ranks.length == 4) {
			return new Hand(ONE_PAIR, bid, cards);
		} else if(ranks.length == 3) {
			return new Hand(counts.get(ranks[0]) == 2 ? TWO_PAIR : THREE_OF_A_KIND, bid, cards);
		} else if(ranks.length == 2) {
			return new Hand(counts.get(ranks[0]) == 3 ? FULL_HOUSE : FOUR_OF_A_KIND, bid, cards);
		} else {
			return new Hand(counts.get(ranks[0]) == 5 ? FIVE_OF_A_KIND : HIGH_CARD, bid, cards);
		}
	}
	
	private static Card[] getCards(String input, boolean joker) {
		input = joker ? input.replace("J", "X") : input;
		return Stream.of(input.split("")).map(in -> dict.get(in)).toArray(Card[]::new);
	}
	
	private static Map<Card, Long> convertJoker(Map<Card, Long> current){
		var joker = current.get(JOKER);
		if(joker != null && joker != 5) {
			current.remove(JOKER);
			var n = current.entrySet().stream()
				.sorted(comparing(Entry<Card, Long>::getValue).reversed())
				.findFirst().get();
			current.replace(n.getKey(), n.getValue()+joker);
		}
		return current;
	}
	
	@Override
	public int compareTo(Hand other) {
		return comparing(Hand::category).thenComparing(Hand::cards, Arrays::compare).compare(this, other);
	}


	
}
