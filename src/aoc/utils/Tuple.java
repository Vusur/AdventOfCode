package aoc.utils;

import java.util.Objects;

public class Tuple<T1, T2> {

	private T1 first;
	private T2 second;
	
	public Tuple(T1 element1, T2 element2) {
		this.first = element1;
		this.second = element2;
	}
	
	public T1 getFirst() {
		return this.first;
	}
	
	public T2 getSecond() {
		return this.second;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof Tuple)) {
			return false;
		}
		var that = (Tuple<?, ?>)obj;
		
		return first.equals(that.first) && second.equals(that.second);	
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}
	
	@Override
	public String toString() {
		var string = "Tuple(" + first.toString() + ","+ second.toString() + ")";
		return string;
	}
}
