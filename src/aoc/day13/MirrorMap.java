package aoc.day13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MirrorMap {

	private List<String> cols = new ArrayList<String>();
	private List<String> rows = new ArrayList<String>();
		
	public void addCol(String row) {
		rows.add(row);
		var s = Arrays.asList(row.split(""));

		if(cols.isEmpty()) {
				cols.addAll(s);
		}
		else {
			for(int i = 0; i < s.size(); i++) {
				cols.set(i, cols.get(i)+s.get(i));
				}
		}
		
		
	}
		
	public List<String> cols() {
		return this.cols;
	}
	
	public List<String> rows() {
		return this.rows;
	}
}
