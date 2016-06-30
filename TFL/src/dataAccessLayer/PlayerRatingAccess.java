package dataAccessLayer;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

import helpers.EntitiesManager;
import model.PlayerRating;

@ManagedBean(name = "playerRatingAccess")
@ApplicationScoped
public class PlayerRatingAccess implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 public static PlayerRating RegisterNewRating(PlayerRating playerRating)
	 {
		 EntityManager em=EntitiesManager.GetManager();
			try {
				em.getTransaction().begin();
				
				em.persist(playerRating);
				em.getTransaction().commit();
				em.refresh(playerRating);
				em.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			return playerRating;
	 }
		  
	 public static void DeleteRating(int ratingId)
	  {
		 EntityManager em=EntitiesManager.GetManager();
		 em.getTransaction().begin();
			
		 PlayerRating playerRating=em.find(PlayerRating.class, ratingId);
		 if(playerRating !=null)
		 {
			em.remove(playerRating);
		 }
		 em.getTransaction().commit();
		 em.close();
	  }	 
}