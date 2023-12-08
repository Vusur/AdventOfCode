package aoc.day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Network {

	String travelPath;
	List<Node> nodes = new ArrayList<Node>();
	
	public Network(String fpath) throws FileNotFoundException {
		File input = new File(fpath);
		Scanner sc = new Scanner(input);		
		travelPath = sc.nextLine();
		while(sc.hasNextLine()) {
			var line = sc.nextLine();
			if(!line.equals("")) {
				var in = line.replaceAll("\\W+", ",").split(",");
				nodes.add(new Node(in[0], in[1], in[2]));
			}
		}
		sc.close();
		Node startNode = nodes.stream().filter(n -> n.name().equals("AAA")).findFirst().orElse(null);
		System.out.println("Part 1: " + travelSteps(startNode, "ZZZ"));
		
		//The cycles between each .*Z -> .*Z have the same length. 
		//.*A -> .*Z is the same as .*Z -> .*Z in length, so we just need the first cycle
		//find the lcm of all steps
		List<Long> allTravelSteps = nodes.stream().filter(n -> n.name().matches(".*A$")).map(n -> travelSteps(n, ".*Z$")).collect(Collectors.toList());
		System.out.println("Part 2: " + lcm(allTravelSteps, 0));
		
	}
	
	private long travelSteps(Node startNode, String endRule) {
		var currentNode = startNode;
		var ruleLength = travelPath.length();
		var step = 0;		
		while(!currentNode.name().matches(endRule)) {
			var next = travelPath.charAt(step%ruleLength) == 'L' ? currentNode.leftNext() : currentNode.rightNext();
			currentNode = nodes.stream().filter(n -> n.name().equals(next)).findFirst().orElse(null);
			step++;
		}
		return step;
	}
	
	static Long gcd(Long a, Long b) {
		return (b == 0) ? a : gcd(b, a % b);
	}
	
	static Long lcm(List<Long> args, int i) {
		if(i == (args.size()-1)) return args.get(i);
		var a = args.get(i);
		var b = lcm(args, i+1);
		return (a*b / gcd(a, b));
		
	}
}
