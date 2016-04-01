package dataAccessLayer;

import model.Player;
import model.Team;
import model.TeamPlayer;

public class TeamPlayerDataAccess {

	public TeamPlayerDataAccess() {
		if(!EntityManagerHelper.em.getTransaction().isActive())
		  {
			EntityManagerHelper.em.getTransaction().begin();
		  }
	}
	
	public static boolean createNewTeamPlayer(Team team,Player player) {
		try {
			TeamPlayer tp=new TeamPlayer();
			Player play = EntityManagerHelper.em.find(Player.class, player.getId());
			Team teeam=EntityManagerHelper.em.find(Team.class,team.getId());
			System.out.println("Found team:"+teeam.getId()+" and player"+play.getUsername()+" with id "+play.getId());
			tp.setTeam(teeam);
			tp.setPlayer(play);
			EntityManagerHelper.em.persist(tp);
			EntityManagerHelper.em.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
	
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		TeamPlayerDataAccess tpda=new TeamPlayerDataAccess();
		tpda.createNewTeamPlayer(1,1);
		System.out.println("Done");
	}
	
	public boolean createNewTeamPlayer(int teamId, int playerId)
	{
		Team t=EntityManagerHelper.em.find(Team.class,teamId);
		Player p=EntityManagerHelper.em.find(Player.class,playerId);	
		
		TeamPlayer tp=new TeamPlayer();
		tp.setPlayer(p);
		tp.setTeam(t);
		t.addTeamPlayer(tp);
		EntityManagerHelper.em.persist(tp);
		EntityManagerHelper.em.getTransaction().commit();
		return true;
	}
	

}
