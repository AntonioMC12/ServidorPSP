package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import model.Cuenta;
import utils.DAOException;
import utils.PersistenceUnit;

public class CuentaDAO {
	public static EntityManager createEM() {
		return PersistenceUnit.getEM();
	}
	
	public void save(Cuenta cuenta) throws DAOException {
		EntityManager em = createEM();

		try {
			em.getTransaction().begin();
			em.persist(cuenta);
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

	public List<Cuenta> showAll() throws DAOException {
		List<Cuenta> Cuentas = new ArrayList<Cuenta>();
		EntityManager em = createEM();

		try {
			em.getTransaction().begin();
			TypedQuery<Cuenta> q = em.createNamedQuery("getAllCuentas", Cuenta.class);
			Cuentas = q.getResultList();
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			throw new DAOException("Error, la entidad ya existe");
		} catch (IllegalStateException e) {
			throw new DAOException("Error de estado, puede ser del begin, el commit o el resultList", e);
		} catch (RollbackException e) {
			throw new DAOException("Error al hacer el commit de la transaccion. Deshaciendo cambios...", e);
		} catch (TransactionRequiredException e) {
			throw new DAOException("Error, no hay una transaccion empezada al hacer el persist", e);
		} catch (IllegalArgumentException e) {
			throw new DAOException("La query es invalida o no se ha definido", e);
		} catch (Exception e) {
			throw new DAOException(e);
		}

		return Cuentas;
	}

	public Cuenta showCuenta(Long id) throws DAOException {
		Cuenta result = null;

		EntityManager em = createEM();

		try {
			em.getTransaction().begin();
			result = em.find(Cuenta.class, id);
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

	public void deleteCuenta(Cuenta cuenta) throws DAOException {
		EntityManager em = createEM();

		try {
			em.getTransaction().begin();
			em.remove(cuenta);
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

}
