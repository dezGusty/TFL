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
import model.PlayerRating;

@ManagedBean(name = "playerRatingAccess")
@ApplicationScoped
public class PlayerRatingAccess implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static EntityManagerFactory emf= Persistence.createEntityManagerFactory("TFL");
	public static EntityManager em= emf.createEntityManager();

	public PlayerRatingAccess() {
		if(!em.getTransaction().isActive())
		  {
			  em.getTransaction().begin();
		  }
	}
	
	  public  List<PlayerRating> listPlayerRatings() {
		    TypedQuery<PlayerRating> query = em.createQuery("SELECT p FROM PlayerRating p",PlayerRating.class);
			List<PlayerRating> result = new ArrayList<PlayerRating>();
			result = query.getResultList();
		   
			for (PlayerRating g : result) {
				System.out.println("ID"+g.getId()+" Rating:" +g.getRating()+" Date:"+g.getDate()+" Player:"+g.getPlayer().getUsername());
			}
			return result;
	  }
	  
	  public List<PlayerRating> getPlayerRatings(Player player)
	  {
		  List<PlayerRating> listOfRatings=new ArrayList<PlayerRating>();
		  TypedQuery<PlayerRating> query = em.createQuery("SELECT c FROM PlayerRating c WHERE c.player.id=:id", PlayerRating.class);
		  query.setParameter("id", player.getId());
		  listOfRatings = query.getResultList();
		  
		  if(listOfRatings.size()!=0)
		  {
			  for (PlayerRating playerRat : listOfRatings) {
					System.out.println("Date:"+playerRat.getDate()+" Rating:"+playerRat.getRating());
				}
		  }
			return listOfRatings;		
	  }
	  
	  public static void main(String[] args) {
		 PlayerRatingAccess pla=new PlayerRatingAccess();
		 Player p=new Player();
		 p.setId(1);
		 
		 pla.listPlayerRatings();
		 System.out.println("Find custom player ratings:");
		 pla.getPlayerRatings(p);
	}

}
