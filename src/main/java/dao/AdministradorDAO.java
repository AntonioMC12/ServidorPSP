package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;

import model.Administrador;
import utils.DAOException;
import utils.PersistenceUnit;

public class AdministradorDAO {
	
	public static EntityManager createEM() {
		return PersistenceUnit.getEM();
	}

	public boolean getAdmin(Long id) throws DAOException {

		boolean result = false;

		EntityManager em = createEM();

		try {
			em.getTransaction().begin();
			Administrador admin = em.find(Administrador.class, id);
			if (admin != null) {
				result = true;
			}
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			throw new DAOException("Error, la entidad ya existe");
		} catch (IllegalStateException e) {
			throw new DAOException("Error de estado, puede ser del begin, el commit", e);
		} catch (RollbackException e) {
			throw new DAOException("Error al hacer el commit de la transaccion. Deshaciendo cambios...", e);
		} catch (TransactionRequiredException e) {
			throw new DAOException("Error, no hay una transaccion empezada al hacer el persist", e);
		} catch (IllegalArgumentException e) {
			throw new DAOException("La id pasada al find es invalida", e);
		} catch (Exception e) {
			throw new DAOException(e);
		}

		return result;
	}
}
