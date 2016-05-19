package dataAccessLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import helpers.DatabaseConnection;
import model.Player;

@ManagedBean(name = "playerDataAccess")
@ApplicationScoped
public class PlayerDataAccess implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Player createUser(String username, String password) {
		Player emp = new Player(username,password);
		try {
			EntityManager em = DatabaseConnection.GetConnection();
			em.getTransaction().begin();
			em.persist(emp);
			em.getTransaction().commit();
			em.refresh(emp);
			em.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return emp;
	}

	public static Player loginUser(String username, String password) {	
		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
		TypedQuery<Player> querynew = em.createQuery("SELECT c FROM Player c WHERE c.username = :name AND c.password=:pass", Player.class);
		querynew.setParameter("name", username);
		querynew.setParameter("pass", password);
		Player play = new Player();
		try {
			play = querynew.getSingleResult();
		} catch (Exception ex) {
			System.out.println("Username or password incorrect!"+ex.getMessage());
		}
		em.close();
		return play;
	}

	public static Player updatePassword(int playerId, String password) {
		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
		Player play =em.find(Player.class, playerId);
		try {
			    play.setPassword(password);
			   System.out.println(play.getPassword());
				//em.persist(player);
				em.getTransaction().commit();
				//em.refresh(player);
				em.close();
				return play;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}

	public static Player changeAvailable(Player player)
	{	
		EntityManager em = DatabaseConnection.GetConnection();
		em.getTransaction().begin();
		Player play =em.find(Player.class, player.getId());
		play.setAvailable(player.getAvailable());
		em.persist(play);
		em.getTransaction().commit();
		em.refresh(play);
		em.close();
        return play;
	}
	
	  public static List<Player> ListAllPlayers() {

		  EntityManager em = DatabaseConnection.GetConnection();
			em.getTransaction().begin();
		    TypedQuery<Player> query =em.createQuery("SELECT p FROM Player p",Player.class);
			List<Player> result = new ArrayList<Player>();
			result = query.getResultList();
			for(Player p:result)
			{
				System.out.println(p.getUsername());
			}
			em.close();
			return result;
	}

	  public static boolean removePlayer(int playerId) {
		    EntityManager em = DatabaseConnection.GetConnection();
			em.getTransaction().begin();
			Player player=em.find(Player.class, playerId);
			if(player!=null)
			{			
				player.setArchive(true);
				em.getTransaction().commit();
				em.close();
				return true;
			}
			else
			{
				System.out.println("Player not found");
				return false;
			}
		}
	  
	  public static void main(String[] args) {
		  Player p=createUser("mircea","parola");
		  System.out.println(p.getId());
	}
}