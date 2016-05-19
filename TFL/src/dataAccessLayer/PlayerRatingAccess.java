package dataAccessLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import helpers.DatabaseConnection;
import model.PlayerRating;

@ManagedBean(name = "playerRatingAccess")
@ApplicationScoped
public class PlayerRatingAccess implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  public  List<PlayerRating> listPlayerRatings() {
		  EntityManager em=DatabaseConnection.GetConnection();
		  em.getTransaction().begin();
		  TypedQuery<PlayerRating> query = em.createQuery("SELECT p FROM PlayerRating p",PlayerRating.class);
		  List<PlayerRating> result = new ArrayList<PlayerRating>();
		  result = query.getResultList();
		  em.close();
		  for (PlayerRating g : result) {
				System.out.println("ID"+g.getId()+" Rating:" +g.getRating()+" Date:"+g.getDate()+" Player:"+g.getPlayer().getUsername());
		  }
		 return result;
	  }
		  
	  public static void main(String[] args) {

	}

}
