package helpers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Paula
 *
 */
public class EntitiesManager {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("TFL");

	private static EntityManager em = emf.createEntityManager();

	/**
	 * @return EntityManager of database TFL
	 */
	public static EntityManager GetManager() {
		return em;
	}
}
