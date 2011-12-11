package TourGuide;

import java.util.ArrayList;
import java.util.List;

public class ProfileObject {

	private List<String> P2;
	
	public List<String> getP2() {
		if(P2 == null){
			P2 = new ArrayList<String>();
		}
		return P2;
	}
	public void setP2(List<String> p2) {
		P2 = p2;
	}
	
	
}
