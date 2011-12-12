package CommonClasses;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

public class Proposal implements Serializable {

	private Set<String> price;
	private int tour;
	
	public Proposal(int tour, Set<String> price) {
		this.tour = tour;
		this.price = price;
	}
	
	public Set<String> getPrice() {
		return price;
	}
	
	public int getTour() {
		return tour;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Proposal) {
			Proposal p = (Proposal)o;
			if((tour == p.getTour())) {
				boolean flag = false;
				if(price.size() != p.getPrice().size()) {
					return false;
				}
				Iterator<String> it = p.getPrice().iterator();
				while(it.hasNext()) {
					if(price.contains(it.next())) {
						flag = true;
					} else {
						flag = false;
						break;
					}
				}
				return flag;
			}
		}
		return false;
	}
}
