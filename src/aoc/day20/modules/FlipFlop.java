package aoc.day20.modules;

import java.util.List;

public class FlipFlop extends Module {
	
	public FlipFlop(String name, List<String> targets) {
		this.name = name;
		this.targetKeys = targets;
		this.pulse = Pulse.LOW;

	}
	
	@Override
	public boolean recievePulse(String name, Pulse pulse) {
		if(pulse == Pulse.LOW) {
			this.pulse = this.pulse == Pulse.LOW ? Pulse.HIGH : Pulse.LOW;
			return true;
		}
		return false;
	}

}
