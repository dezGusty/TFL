package dataAccessLayer;

import java.util.Comparator;

import model.Player;

public class RatingComparator implements Comparator<Player> {

	@Override
	public int compare(Player p1, Player p2) {
		if(p1.getRating()<p2.getRating())
		{
			return -1;
		}
		if(p1.getRating()>p2.getRating())
		{
			return 1;
		}
		return 0;
	}

}
