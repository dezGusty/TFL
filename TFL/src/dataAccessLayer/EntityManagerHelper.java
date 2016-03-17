package dataAccessLayer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerHelper {

	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("TFL");
	public static EntityManager em = emf.createEntityManager();
}
