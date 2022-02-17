package controller;

import java.util.ArrayList;
import java.util.List;

import dao.UsuarioDAO;
import model.Usuario;
import utils.DAOException;

public class UsuarioController {

	public synchronized void createUsuario(Usuario usuario) {
		try {
			new UsuarioDAO().save(usuario);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public synchronized List<Usuario> getAllUsuarios() {
		try {
			return new UsuarioDAO().showAll();
		} catch (DAOException e) {
			e.printStackTrace();
			return new ArrayList<Usuario>();
		}
	}

	public synchronized Usuario getUsuarioById(Long id) {
		try {
			return new UsuarioDAO().show(id);
		} catch (DAOException e) {
			e.printStackTrace();
			return new Usuario();
		}
	}

	public synchronized void deleteUsuario(Usuario usuario) {
		try {
			new UsuarioDAO().delete(usuario);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	//

}
