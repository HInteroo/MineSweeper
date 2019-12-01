import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class mapCoordinate {
	private Map<Integer, Map<Integer, Integer>> coordinate = new HashMap<Integer, Map<Integer, Integer>>();
	private Map <Integer, Integer> secondMap = new HashMap <Integer, Integer>();

	private int v;
	
	public void checkPut(Integer x, Integer y) {
	    if (!coordinate.containsKey(x)) { 				//if x isn't repeated, put it inside the map 
	    	coordinate.put(x,null);
	    	secondMap = new HashMap<Integer, Integer>();
	    	secondMap.put(y,1);
	    	coordinate.put(x, secondMap);
		    
	    }
	    else { 														//if it does contains the key
	    	if(!this.contains(x,y)) { //if 2 is not in [0:{ 0:1, 1:1}]
		    	coordinate.get(x).put(y,1);
		    }
		    else if(coordinate.get(x).containsKey(y)) { //works perfectly
		    	coordinate.get(x).put(y, coordinate.get(x).get(y)+1);
		    }
	    }
	}
	
	public boolean contains(int x, int y) {
		if(this.coordinate.containsKey(x)) {
	    	if(this.coordinate.get(x).containsKey(y)) { //if 2 is not in [0:{ 0:1, 1:1}]
	    		return true;
	    	}
		}
		return false;
	}
	public Map<Integer, Map<Integer, Integer>> showMap() {
		
		return coordinate;
	}
}
