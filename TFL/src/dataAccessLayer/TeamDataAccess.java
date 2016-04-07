package dataAccessLayer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import model.Game;
import model.Player;
import model.Team;

public class TeamDataAccess {

	public TeamDataAccess() {
		if(!EntityManagerHelper.em.getTransaction().isActive())
		  {
			EntityManagerHelper.em.getTransaction().begin();
		  }
	}
	
	public  static List<Team> listTeams() {
	    TypedQuery<Team> query =EntityManagerHelper.em.createQuery("SELECT t FROM Team t",Team.class);
		List<Team> result = new ArrayList<Team>();
		result = query.getResultList();
		for(Team p:result)
		{
			System.out.println(p.getName());
		}
		return result;
}
	
	public Team createNewTeam(String teamName,Game game) {
		Team newTeam=new Team();
		Game gamee = EntityManagerHelper.em.find(Game.class, game.getId());
		EntityManagerHelper.em.refresh(gamee);
		System.out.println("This game has "+gamee.getTeams().size()+" teams!");
		try {
			if(gamee.getTeams().size()==2)
			{
				System.out.println("This game already has teams");
			}
			else
			{
				newTeam.setName(teamName);
				newTeam.setGameBean(gamee);
				
				EntityManagerHelper.em.persist(newTeam);
				EntityManagerHelper.em.getTransaction().commit();
				EntityManagerHelper.em.refresh(newTeam);
				return newTeam;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());

		}
		return null;
	}
	
	public Team addNewPlayer(int playerId, int teamId) {
		Player player = EntityManagerHelper.em.find(Player.class, playerId);
		System.out.println(player.getId()+" "+player.getUsername());
		Team team= EntityManagerHelper.em.find(Team.class, teamId);
		System.out.println(team.getId()+" "+team.getName());
//		if(team.inThisTeam(player.getId()))
//		{
//			TeamPlayer tp=new TeamPlayer();
//			tp.setPlayer(player);
//			tp.setTeam(team);
//			
//			team.addTeamPlayer(tp);
//			EntityManagerHelper.em.persist(tp);
//			EntityManagerHelper.em.merge(team);
//			EntityManagerHelper.em.getTransaction().commit();
//		}
		return team;
	}
	
	
	public static void main(String[] args) {
		TeamDataAccess tda=new TeamDataAccess();
		tda.addNewPlayer(14, 48);
	}
}
