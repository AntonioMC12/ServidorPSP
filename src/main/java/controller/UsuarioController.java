package controller;

import java.util.ArrayList;
import java.util.List;

import dao.UsuarioDAO;
import model.Usuario;
import utils.DAOException;

public class UsuarioController {

	public synchronized boolean createUsuario(Usuario usuario) {
		try {
			new UsuarioDAO().save(usuario);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public synchronized List<Usuario> getAllUsuarios() {
		try {
			return new UsuarioDAO().showAll();
		} catch (DAOException e) {
			e.printStackTrace();
			return new ArrayList<Usuario>();
		}
	}

	public synchronized Boolean getUsuarioById(Long id) {
		try {
			return new UsuarioDAO().showById(id);
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public synchronized void deleteUsuario(Usuario usuario) {
		try {
			new UsuarioDAO().delete(usuario);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	public synchronized Boolean logUser(Usuario usuario) {
		try {
			return new UsuarioDAO().logUser(usuario);	
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public synchronized List<Usuario> mostarUsuariosPorAdmin(Long id) {
		try {
			return new UsuarioDAO().showAllByAdmin(id);
		} catch (DAOException e) {
			e.printStackTrace();
			return new ArrayList<Usuario>();
		}
	}
}
