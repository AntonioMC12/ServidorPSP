package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import model.Usuario;
import utils.DAOException;
import utils.PersistenceUnit;

public class UsuarioDAO {

	public static EntityManager createEM() {
		return PersistenceUnit.getEM();
	}

	public boolean save(Usuario usuario) throws DAOException {
		boolean result= false;
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			em.persist(usuario);
			if(usuario != null){
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

	public List<Usuario> showAll() throws DAOException {
		List<Usuario> Usuarios = new ArrayList<Usuario>();
		EntityManager em = createEM();

		try {
			em.getTransaction().begin();
			TypedQuery<Usuario> q = em.createNamedQuery("getAllUsuarios", Usuario.class);
			Usuarios = q.getResultList();
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

		return Usuarios;
	}

	public Boolean showById(Long id) throws DAOException {
		Boolean result = false;

		EntityManager em = createEM();

		try {
			em.getTransaction().begin();
			Usuario usuario = em.find(Usuario.class, id);
			if (usuario != null) {
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

	public void delete(Usuario usuario) throws DAOException {
		EntityManager em = createEM();

		try {
			em.getTransaction().begin();
			em.remove(usuario);
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
	
	public Boolean logUser(Usuario usuario) throws DAOException{
		Boolean result = false;
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			TypedQuery<Usuario> q = em.createNamedQuery("getUsuarioByNombrePassword", Usuario.class);
			q.setParameter("nombre", usuario.getNombre());
			q.setParameter("password", usuario.getPassword());
			Usuario usuario2 = q.getSingleResult();
			System.out.println(usuario2);
			if(usuario2 != null){
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
	
	public Usuario getUsuarioByNamePassword(Usuario usuario) throws DAOException{
		Usuario result = null;
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			TypedQuery<Usuario> q = em.createNamedQuery("getUsuarioByNombrePassword", Usuario.class);
			q.setParameter("nombre", usuario.getNombre());
			q.setParameter("password", usuario.getPassword());
			Usuario usuario2 = q.getSingleResult();
			System.out.println(usuario2);
			if(usuario2 != null){
				result = usuario2;
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
	public Usuario updateUsuario(Usuario usuario) throws DAOException{
		EntityManager em = createEM();
		try {
			em.getTransaction().begin();
			em.merge(usuario);
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
		return usuario;
	}
}
