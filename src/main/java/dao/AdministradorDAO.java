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

	public Administrador show(Long id) throws DAOException {
		Administrador result = null;

		EntityManager em = createEM();

		try {
			em.getTransaction().begin();
			result = em.find(Administrador.class, id);
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
