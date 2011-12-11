package TourGuide;

import java.util.Map;
import java.util.Set;

public class ProfileObject {

	private Map<String, String> P1 = null;
	private Set<String> P2 = null;
	private Map<String, Integer> P3_1 = null;
	private Map<String, Integer> P3_2 = null;
	
	public boolean hasP1() {
		if(P1 == null) {
			return false;
		}
		return true;
	}
	
	public boolean hasP2() {
		if(P2 == null) {
			return false;
		}
		return true;
	}
	
	public boolean hasP3_1() {
		if(P3_1 == null) {
			return false;
		}
		return true;
	}
	
	public boolean hasP3_2() {
		if(P3_2 == null) {
			return false;
		}
		return true;
	}
	
	public void setP1(Map<String,String> P1) {
		this.P1 = P1;
	}
	
	public void setP2(Set<String> P2) {
		this.P2 = P2;
	}
	
	public void setP3_1(Map<String, Integer> P3_1) {
		this.P3_1 = P3_1;
	}
	
	public void setP3_2(Map<String, Integer> P3_2) {
		this.P3_2 = P3_2;
	}
	
	public Set<String> getP2() {
		return P2;
	}
	
	public Map<String, Integer> getP3_1() {
		return P3_1;
	}
	
	public Map<String, Integer> getP3_2() {
		return P3_2;
	}
}
