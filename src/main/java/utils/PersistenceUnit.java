package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUnit {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static final String REMOTE = "aplicacionMariaDB";

	public static EntityManagerFactory getInstance() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(REMOTE);
		}
		return emf;
	}

	public static EntityManager getEM() {
		if (em == null) {
			EntityManagerFactory emf = PersistenceUnit.getInstance();
			em = emf.createEntityManager();
			return em;
		} else {
			return em;
		}
	}

}
