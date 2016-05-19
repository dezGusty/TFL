package helpers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConnection {
	private static EntityManagerFactory emf= Persistence.createEntityManagerFactory("TFL");
	public static EntityManager GetConnection()
	{
		EntityManager em = emf.createEntityManager();
		return em;
	}
}
