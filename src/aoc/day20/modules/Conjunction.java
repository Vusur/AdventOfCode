package aoc.day20.modules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Conjunction extends Module {

	private Map<String, Pulse> memory = new HashMap<String, Pulse>();
	
	public Conjunction(String name, List<String> targets) {
		this.name = name;
		this.targetKeys = targets;
		this.pulse = Pulse.LOW;
	}
	
	public void addMemory(String key, Pulse pulse) {
		memory.put(key, pulse);
	}
	
	public Set<String> getMemoryKeys(){
		return memory.keySet();
	}

	@Override
	public boolean recievePulse(String name, Pulse pulse) {
		addMemory(name, pulse);
		var allOn = memory.entrySet().stream().allMatch(e -> e.getValue()==Pulse.HIGH);
		this.pulse = allOn ? Pulse.LOW : Pulse.HIGH;
		return true;
	}
}
