package dataAccessLayer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Player;
import model.Team;

public class TeamPlayerDataAccess {

	public static boolean createNewTeamPlayer(Team team,Player player) {
		try {
			//TeamPlayer tp=new TeamPlayer();
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("TFL");
			EntityManager em = emf.createEntityManager();
			Player play = em.find(Player.class, player.getId());
			Team teeam=em.find(Team.class,team.getId());
			System.out.println("Found team:"+teeam.getId()+" and player"+play.getUsername()+" with id "+play.getId());
			//tp.setTeam(teeam);
			//tp.setPlayer(play);
			//EntityManagerHelper.em.persist(tp);
			em.getTransaction().commit();
			em.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
	
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {

	}
}
