package dataAccessLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Player;

@ManagedBean(name = "playerDataAccess")
@ApplicationScoped
public class PlayerDataAccess implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static EntityManagerFactory emf= Persistence.createEntityManagerFactory("TFL");
	public static EntityManager em= emf.createEntityManager();

	public PlayerDataAccess() {
		if(!em.getTransaction().isActive())
		  {
			  em.getTransaction().begin();
		  }
	}

	public boolean createUser(String username, String password, int type, boolean available, double rating) {
		try {
			Player emp = new Player();
			emp.setType(type);
			emp.setUsername(username);
			emp.setPassword(password);
			emp.setRating(rating);
			emp.setAvailable(available);
			em.persist(emp);
			em.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}

	public Player loginUser(String username, String password) {

		
		TypedQuery<Player> query = em.createQuery("SELECT c FROM Player c", Player.class);
		List<Player> result = new ArrayList<Player>();
		result = query.getResultList();

		for (Player player : result) {
			System.out.println(player.toString());
		}

		TypedQuery<Player> querynew = em
				.createQuery("SELECT c FROM Player c WHERE c.username = :name AND c.password=:pass", Player.class);
		querynew.setParameter("name", username);
		querynew.setParameter("pass", password);
		Player play = new Player();
		try {
			play = querynew.getSingleResult();
			if (play.getId() != 0) {
				System.out.println(play.toString());
				for (Player p : result) {
					if ((p.getUsername().compareTo(username) == 0) && (p.getPassword().compareTo(password) == 0)) {
						System.out.println(p.getPlayerRatings());
						return p;
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Username or password incorrect!");
		}
		return null;
	}

	public Player changePasswordForPlayer(int playerId, String password) {

		TypedQuery<Player> query = em.createQuery("SELECT c FROM Player c WHERE c.id = :id", Player.class);
		query.setParameter("id", playerId);

		Player play = new Player();
		try {
			play = query.getSingleResult();
			if (play.getId() != 0) {
				System.out.println(play.getPassword() + " este parola ce va fi schimbata!");
				play.setPassword(password);
				em.persist(play);
				em.getTransaction().commit();
				return play;
			}
		} catch (Exception ex) {
			System.out.println("Username or password incorrect!");
		}
		return null;
	}
	
	  public  List<Player> listPlayers() {
		    TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p",Player.class);
			List<Player> result = new ArrayList<Player>();
			result = query.getResultList();
		   
			for (Player g : result) {
				System.out.println("ID"+g.getId()+" Password:" +g.getPassword()+" Username"+g.getUsername());
				System.out.println("Game players:");
			}
			return result;
	}

}
