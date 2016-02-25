package model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Test {

    private static EntityManager em;

    public static void main(String[] args) {

	System.out.println("Context Initilaise \n");
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("TFL");
	em = emf.createEntityManager();
	createPlayer();
	// createType();
	System.out.println("Player saved!");
	System.out.println("Done");
    }

    private static void createPlayer() {
	em.getTransaction().begin();
	Player emp = new Player();
	emp.setId(1);
	emp.setType(1);
	emp.setUsername("paula");
	emp.setPassword("pass");
	emp.setRating(20.5);
	emp.setAvailable(true);
	em.persist(emp);
	em.getTransaction().commit();
    }

    private static void createType() {
	em.getTransaction().begin();
	PlayerType emp = new PlayerType();
	emp.setId(1);
	emp.setType("admin");
	em.persist(emp);
	em.getTransaction().commit();
    }

}
