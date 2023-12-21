package aoc.day20.modules;

import java.util.List;

public abstract class Module {

	public static enum Pulse { LOW, HIGH };
	
	protected String name;
	protected List<String> targetKeys;
	
	protected Pulse pulse;
	
	
	public String getName() {
		return this.name;
	}
	
	public Pulse getPulse() {
		return this.pulse;
	}
	
	public List<String> getTargetKeys(){
		return this.targetKeys;
	}
	
	public abstract boolean recievePulse(String name, Pulse pulse);
}
