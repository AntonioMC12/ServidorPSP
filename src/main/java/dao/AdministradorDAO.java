package dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import model.Administrador;
import utils.DAOException;
import utils.PersistenceUnit;

public class AdministradorDAO {
	
	public static EntityManager createEM() {
		return PersistenceUnit.getEM();
	}
	
	public void save(Administrador admin) throws DAOException {
		EntityManager em = createEM();

		try {
			em.getTransaction().begin();
			em.persist(admin);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			throw new DAOException("Error, la entidad ya existe");
		} catch (IllegalStateException e) {
			throw new DAOException("Error de estado, puede ser del begin o el commit", e);
		} catch (RollbackException e) {
			throw new DAOException("Error al hacer el commit de la transaccion. Deshaciendo cambios...", e);
		} catch (TransactionRequiredException e) {
			throw new DAOException("Error, no hay una transaccion empezada al hacer el persist", e);
		} catch (IllegalArgumentException e) {
			throw new DAOException("La instacia pasada por parametro no es una entidad o es null", e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
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
	public Boolean logAdmin(Administrador admin) throws DAOException{
		Boolean result = false;
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			TypedQuery<Administrador> q = em.createNamedQuery("getAdminByNombrePassword", Administrador.class);
			q.setParameter("nombre", admin.getNombre());
			q.setParameter("password", admin.getPassword());
			Administrador administradorDummy = q.getSingleResult();
			if(administradorDummy != null){
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
			throw new DAOException("La query es invalida o no se ha definido", e);
		} catch (Exception e) {
			throw new DAOException(e);
		}		
		return result;
	}

	public Administrador getAdministradorByNamePassword(Administrador objeto) throws DAOException {
		EntityManager em = createEM();
		Administrador admin = null;
		try {
			em.getTransaction().begin();
			TypedQuery<Administrador> q = em.createNamedQuery("getAdminByNombrePassword", Administrador.class);
			q.setParameter("nombre", objeto.getNombre());
			q.setParameter("password", objeto.getPassword());
			admin = q.getSingleResult();
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
			throw new DAOException("La query es invalida o no se ha definido", e);
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return admin;
	}
}
