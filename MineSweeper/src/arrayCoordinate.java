import java.util.ArrayList;

public class arrayCoordinate {
	private ArrayList<ArrayList<Integer>> coordinate = new ArrayList<ArrayList<Integer>>();
	
	public void put(Integer x, Integer y) {
		ArrayList<Integer> pairs = new ArrayList<Integer>();
		pairs.add(x);
		pairs.add(y);
		this.coordinate.add(pairs);
	}
	public void remove(Integer x, Integer y) {
		ArrayList<Integer> pairs = new ArrayList<Integer>();
		pairs.add(x);
		pairs.add(y);
		this.coordinate.remove(pairs);
	}
	public boolean contains(int x, int y) {
		ArrayList<Integer> pairs = new ArrayList<Integer>();
		pairs.add(x);
		pairs.add(y);
		if(this.coordinate.contains(pairs)) {
			return true;
		}
		return false;
	}
	
	public int size() {
		
		return this.coordinate.size();
	}
	public ArrayList<ArrayList<Integer>> getArray() {
		return coordinate;
	}
	
}
