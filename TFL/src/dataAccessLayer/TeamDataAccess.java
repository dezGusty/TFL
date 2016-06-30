package dataAccessLayer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import helpers.EntitiesManager;
import model.Player;
import model.Team;

public class TeamDataAccess {
	
	public  static List<Team> listTeams() {
		EntityManager em=EntitiesManager.GetManager();

	    TypedQuery<Team> query =em.createQuery("SELECT t FROM Team t",Team.class);
		List<Team> result = new ArrayList<Team>();
		result = query.getResultList();
		for(Team p:result)
		{
			System.out.println(p.getName());
		}
		return result;
	}
	
	public static Team FindTeam(int teamId)
	{
		EntityManager em=EntitiesManager.GetManager();
		Team game=em.find(Team.class, teamId);
		em.close();
		return game;
	}
	
	public static Team UpdateTeam(Team teamToSave) {

		EntityManager em=EntitiesManager.GetManager();
		em.getTransaction().begin();
		
		Team team= em.find(Team.class, teamToSave.getId());			
		team.setName(teamToSave.getName());
		team.setScore(teamToSave.getScore());
		team.setWinner(teamToSave.getWinner());
		team.setGoals(teamToSave.getGoals());
		
		em.merge(team);
		em.getTransaction().commit();
		em.refresh(team);
		em.close();
		return team;	
	}
	
	public static Team RemoveAllPlayers(int teamId)
	{
		EntityManager em=EntitiesManager.GetManager();
		em.getTransaction().begin();
		
		Team team=em.find(Team.class,teamId);	
		
		if(team !=null)
		{
			team.getPlayers().clear();
			team.setScore(0);
		}
		em.getTransaction().commit();
		em.refresh(team);
		em.close();
		return team;
	}
	
	public static Team RemovePlayerFromTeam(int teamID,int playerID)
	{
		EntityManager em=EntitiesManager.GetManager();
		em.getTransaction().begin();
		
		Team team=em.find(Team.class,teamID);
		Player player=em.find(Player.class, playerID);
		
		if(team !=null && player!=null)
		{
			team.getPlayers().remove(player);
			team.setScore(team.getScore()-player.getRating());
		}
		em.getTransaction().commit();
		em.refresh(team);
		em.close();
		return team;
	}
	
	public static Team SaveTeamName(int teamID,String name)
	{
		EntityManager em=EntitiesManager.GetManager();
		em.getTransaction().begin();
		
		Team team=em.find(Team.class,teamID);		
		
		if(team !=null)
		{
			team.setName(name);
		}
		em.getTransaction().commit();
		em.refresh(team);
		return team;
	}
	
	
	public static Team AddNewPlayer(int teamID, int playerID) {

		EntityManager em=EntitiesManager.GetManager();
		em.getTransaction().begin();
		
		Team t= em.find(Team.class, teamID);			
		Player p=em.find(Player.class,playerID);
		 
		t.addNewPlayer(p);
		em.merge(t);
		em.getTransaction().commit();
		em.refresh(t);		
		em.close();
		return t;	
	}
	
	public static Team CreateNewTeam(Team team)
	{
		EntityManager em=EntitiesManager.GetManager();
		em.getTransaction().begin();
		
		em.persist(team);
		em.getTransaction().commit();
		em.refresh(team);
		em.close();
		return team;
	}
}