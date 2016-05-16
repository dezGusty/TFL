package dataAccessLayer;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import model.Player;
import model.Team;

public class TeamDataAccess {
	
	public  static List<Team> listTeams() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TFL");
		EntityManager em = emf.createEntityManager();
	    TypedQuery<Team> query =em.createQuery("SELECT t FROM Team t",Team.class);
		List<Team> result = new ArrayList<Team>();
		result = query.getResultList();
		for(Team p:result)
		{
			System.out.println(p.getName());
		}
		em.close();
		emf.close();
		return result;
	}
	
	public static Team UpdateTeam(Team teamToSave) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TFL");
		EntityManager em = emf.createEntityManager();
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
		emf.close();
		return t;	
	}
	
	public static Team CreateNewTeam(Team team)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TFL");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(team);
		em.getTransaction().commit();
		em.refresh(team);
		em.close();
		emf.close();
		return team;
	}
	
	public static Team addNewPlayer(int playerId, int teamId) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TFL");
		EntityManager em = emf.createEntityManager();
		Player player = em.find(Player.class, playerId);
		System.out.println(player.getId()+" "+player.getUsername());
		Team team= em.find(Team.class, teamId);
		System.out.println(team.getId()+" "+team.getName());
		em.close();
		emf.close();
		return team;
	}
}