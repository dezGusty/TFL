package helpers;

import java.util.ArrayList;
import java.util.List;

import dataAccessLayer.PlayerDataAccess;
import model.Player;

public class Text {
	public static void main(String[] args) {
		List<Player> list=new ArrayList<Player>();
		list.add(PlayerDataAccess.FindPlayer(4));
		list.add(PlayerDataAccess.FindPlayer(7));
		list.add(PlayerDataAccess.FindPlayer(10));
		list.add(PlayerDataAccess.FindPlayer(18));
		
		System.out.println("Lista care se doreste a fi sortata:");
		for(Player p:list)
		{
			System.out.println(p.getUsername()+" "+p.getRating());
		}
		TeamGenerator tg=new TeamGenerator(list);
		List<Player> firstList = tg.GetBestTeam();
		System.out.println("Cele mai echilibrate echipe ce se pot obtine sunt :");
		System.out.println("Prima echipa: ");
		for(Player p:firstList)
		{
			System.out.println(p.getUsername()+" "+p.getRating());
		}
		System.out.println("A doua echipa: ");
		for(Player p:list)
		{
			if(firstList.contains(p)==false)
				System.out.println(p.getUsername()+" "+p.getRating());
		}
	}
}