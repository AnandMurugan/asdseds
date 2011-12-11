package CommonClasses;

import java.util.Comparator;
import java.util.Map;

class ValueComparator implements Comparator {

	Map<String,Integer> base;
	public ValueComparator(Map<String, Integer> map) {
		this.base = map;
	}

	public int compare(Object a, Object b) {

		if((Integer)base.get(a) < (Integer)base.get(b)) {
			return 1;
		} else if((Integer)base.get(a) == (Integer)base.get(b)) {
			return 0;
		} else {
			return -1;
		}
	}
}