package helpers;

import java.util.List;
import model.Player;

public class PlayerHelper {
	public static boolean ExistsInList(Player player,List<Player> listOfPlayers)
	{
		for(Player play:listOfPlayers)
		{
			if(play.getId()==player.getId())
			{
				return true;
			}
		}
		return false;
	}
}
