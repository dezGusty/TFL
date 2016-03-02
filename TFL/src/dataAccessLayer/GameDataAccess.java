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

import model.Game;
import model.GameLoser;
import model.GamePlayer;
import model.GameWinner;

@ManagedBean(name = "gameDataAccess")
@ApplicationScoped
public class GameDataAccess implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static EntityManagerFactory emf=Persistence.createEntityManagerFactory("TFL");
	 public static EntityManager em = emf.createEntityManager();
	  
	 // public GameDataAccess() {
		//	emf = Persistence.createEntityManagerFactory("TFL");
			//em = emf.createEntityManager();
			//em.getTransaction().begin();
	//  }
	  
	  public  List<Game> listGames() {
		 // emf = Persistence.createEntityManagerFactory("TFL");
			//em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Game> query = em.createQuery("SELECT g FROM Game g", Game.class);
			List<Game> result = new ArrayList<Game>();
			result = query.getResultList();
		   
			for (Game g : result) {
				System.out.println("ID"+g.getId()+" Date:" +g.getDate()+" Difference"+g.getDifference());
				System.out.println("Game players:");
				for(GamePlayer p:g.getGamePlayers())
				{
					System.out.println(p.getPlayer().getUsername());
				}
				for(GameLoser gl: g.getGameLosers())
				{
					System.out.println(gl.getPlayer().getUsername());
				}
				for(GameWinner gw: g.getGameWinners())
				{
					System.out.println(gw.getPlayer().getUsername());
				}
			}
			return result;
	}
	 
//	  public static void main(String[] args) {
//		 
//		listGames();
//	}
}
