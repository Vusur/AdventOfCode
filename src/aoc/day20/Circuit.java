package aoc.day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import aoc.day20.modules.Conjunction;
import aoc.day20.modules.FlipFlop;
import aoc.day20.modules.Module;
import aoc.day20.modules.Module.Pulse;


/*
 * Notes to Part 2:
 * rx recives its pulse from one single Module X.
 * This Module is a Conjunction, so it only sends a LOW pulse, if all input pulses are HIGH.
 * X recieves pulses from 4 other Conjunctions A, B, C, D. They are all inverter
 * The broadcaster sends 4 signals to 1, 2, 3, 4.
 * If you follow the wire, one signal will end in one Conjunction, i.e. 1->A, 2->B, 3->C, 4->D
 * The wirepath are distinct, meaning no signal will affect multiple Conjunctions before they join back at X.
 * By debugging and observation, Conjunction A, B, C, D send HIGH periodically.
 * With this information, all we need are the periodic cycle lengths for A, B, C, D to turn HIGH.
 * A, B, C, D all turn HIGH at the same time at lcm of those 4 cycles lengthes.
 */

public class Circuit {

	
	Map<String, Module> moduleMap = new LinkedHashMap<>();
	Queue<QueueItem> queue = new LinkedList<QueueItem>();
	List<QueueItem> start = new ArrayList<QueueItem>();
	Long lowCount = 0L;
	Long highCount = 0L;
	
	//Part 2
	Long presses = 0L;
	//stores the 4 conjunctions we observe and their cycle lenghtes
	Map<String, Long> observer = new HashMap<String, Long>();
	
	public Circuit(String fpath) throws FileNotFoundException {
		var input = new File(fpath);
		var sc = new Scanner(input);
		while(sc.hasNextLine()) {
			var line = sc.nextLine().split(" -> ");
			var moduleString = line[0];
			var targets = line[1].split(", ");
			if(moduleString.equals("broadcaster")) {
				for(var t : targets) {
					start.add(new QueueItem("broadcaster", t, Pulse.LOW));
				}
			}
			else {
				if(moduleString.contains("%")) {
					var name = moduleString.substring(1, moduleString.length());
					moduleMap.put(name, new FlipFlop(name, Arrays.asList(targets)));
				}
				else {
					var name = moduleString.substring(1, moduleString.length());
					moduleMap.put(name, new Conjunction(name, Arrays.asList(targets)));
				}
			}
			//connect conjunctions with modules
			//check all modules
			for(var sourceModule : moduleMap.entrySet()) {
				//check all targets for this module
				for(var targetKey : sourceModule.getValue().getTargetKeys()) {
					//if the target is a module itself, it is in the Map. Dead ends are not in the map
					if(moduleMap.containsKey(targetKey)) {
						var targetModule = moduleMap.get(targetKey);
						//if this targetModule is a Conjunction, set it's memory to the sourceModule and remember low
						if(targetModule instanceof Conjunction) {
							((Conjunction) targetModule).addMemory(sourceModule.getKey(), Pulse.LOW);
						}
					}
				}
			}		
		}
		//Part 2
		//Grab all modules, that feed into the module that feeds to RX
		for(var source : moduleMap.values()) {
			if(source instanceof Conjunction) {
				if(source.getTargetKeys().contains("rx")) {
					var feedSet = ((Conjunction) source).getMemoryKeys();
					for(var f : feedSet){
						observer.put(f, -1L);
					}
				}
			}
		}
		
		
		sc.close();
	}
	
	public boolean buttonPress() {
		presses += 1L;
		addPulseCount(Pulse.LOW);
		queue.addAll(start);
		
		while(!queue.isEmpty()) {
			var item = queue.poll();
			addPulseCount(item.pulse());
			
			//PART 2 START
			//if we encounter an observed module and this module sends high, then we have a cycle.
			//if a cycle is not set yet, set it once
			if(observer.containsKey(item.originKey()) 
					&& item.pulse() == Pulse.HIGH 
					&& observer.get(item.originKey()) == -1) {
				observer.put(item.originKey(), presses);
			}
			
			//when all observed modules have found a cycle, we are done
			if(observer.entrySet().stream().allMatch(e -> e.getValue() != -1)) {
				var cycles = observer.values().stream().toList();
				var allHigh = lcm(cycles, 0);
				presses = allHigh;
				//break the buttonpresses.
				return false;
			}
			
			
			//PART 2 END			
			if(moduleMap.containsKey(item.targetKey())) {
				var module = moduleMap.get(item.targetKey());			
				var change = module.recievePulse(item.originKey(), item.pulse());
				if(change) {
					var newPulse = module.getPulse();
					for(var next : module.getTargetKeys()) {
						queue.offer(new QueueItem(module.getName(), next, newPulse));
					}
				}
			}			
		}
		return true;
	}
	
	private void addPulseCount(Pulse pulse) {
		//limit presses to 1k for Part1
		//only possible, because cycles for Part2 are more than 1k
		if(presses <= 1000) {
			if(pulse == Pulse.LOW)
				lowCount += 1L;
			else
				highCount += 1L;
		}
	}
	
	public void printSolution() {
		//repeat till button found all cycles and calculated the presses
		while(buttonPress());
		
		var prod = lowCount * highCount;
		System.out.println("Part1: " + prod);
		System.out.println("Part2: " + presses);
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
