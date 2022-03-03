package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import model.Cuenta;
import model.Usuario;
import utils.DAOException;
import utils.PersistenceUnit;

public class CuentaDAO {
	public static EntityManager createEM() {
		return PersistenceUnit.getEM();
	}

	public void extraerDinero(Cuenta cuenta, double cantidad) throws DAOException {
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			//si el saldo es menos que la cantidad a extraer
			if(cuenta.getSaldo()-cantidad<0) { 
				throw new DAOException("No se puede extraer esa cantidad");
			}
			cuenta.setSaldo(cuenta.getSaldo() - cantidad);
			em.merge(cuenta);
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

	public void ingresarDinero(Cuenta cuenta, double cantidad) throws DAOException {
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			cuenta.setSaldo(cuenta.getSaldo() + cantidad);
			em.merge(cuenta);
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
	public List<Cuenta> getCuentasByUsario(Long id) throws DAOException {
		EntityManager em = createEM();
		try {
			TypedQuery<Cuenta> query = em.createQuery("SELECT c FROM Cuenta c WHERE c.usuario.id = :id", Cuenta.class);
			query.setParameter("id", id);
			return query.getResultList();
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

	public boolean save(Cuenta cuenta,Long idUser) throws DAOException {
		boolean result = false;
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			Usuario usuario = em.find(Usuario.class, idUser);
			cuenta.setUsuario(usuario);
			em.persist(cuenta);
			if(cuenta != null){
				result = true;
			}
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
		return result;
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
	//Metodo para solo borar la cuenta de la base de datos por el id
	public boolean deleteCuenta(Long id) throws DAOException {
		boolean result = false;
		System.out.println("Entro al delete");
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			System.out.println("Entro al begin");
			Cuenta cuenta = em.find(Cuenta.class, id);
			System.out.println("Entro al find");
			System.out.println(cuenta.getId());
			//solo borra la cuenta de la base de datos
			em.remove(cuenta);
			System.out.println("Entro al remove");	
			em.getTransaction().commit();
			System.out.println("Entro al commit");
			result = true;
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
