package CommonClasses;

import java.util.Set;

public class Proposal {

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
	
	public boolean equals(Object o) {
		if(o instanceof Proposal) {
			Proposal p = (Proposal)o;
			System.out.println(price);
			System.out.println(p.getPrice());
			if((tour == p.getTour()) && price.equals(p.getPrice())) {
				return true;
			}
		}
		return false;

	}
}
