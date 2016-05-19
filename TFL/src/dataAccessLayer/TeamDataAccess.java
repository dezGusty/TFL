package dataAccessLayer;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import helpers.DatabaseConnection;
import model.Player;
import model.Team;

public class TeamDataAccess {
	
	public  static List<Team> listTeams() {
		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
	    TypedQuery<Team> query =em.createQuery("SELECT t FROM Team t",Team.class);
		List<Team> result = new ArrayList<Team>();
		result = query.getResultList();
		for(Team p:result)
		{
			System.out.println(p.getName());
		}
		em.close();
		return result;
	}
	
	public static Team UpdateTeam(Team teamToSave) {

		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
		Team t= em.find(Team.class, teamToSave.getId());			
		t.setName(teamToSave.getName());
		t.setScore(teamToSave.getScore());
		t.setPlayers(teamToSave.getPlayers());
		System.out.println("Team to save is winner: "+teamToSave.getWinner());
		t.setWinner(teamToSave.getWinner());
		em.merge(t);
		em.getTransaction().commit();
		em.close();
		return t;	
	}
	
	public static Team CreateNewTeam(Team team)
	{
		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
		em.persist(team);
		em.getTransaction().commit();
		em.refresh(team);
		em.close();
		return team;
	}
	
	public static Team addNewPlayer(int playerId, int teamId) {
		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
		Player player = em.find(Player.class, playerId);
		System.out.println(player.getId()+" "+player.getUsername());
		Team team= em.find(Team.class, teamId);
		System.out.println(team.getId()+" "+team.getName());
		em.close();
		return team;
	}
}