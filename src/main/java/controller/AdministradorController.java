package controller;

import dao.AdministradorDAO;
import model.Administrador;
import utils.DAOException;

public class AdministradorController {
    
    public boolean getAdminById(Long id) {
        try {
			new AdministradorDAO().getAdmin(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
    }
	public boolean logAdministrador(Administrador admin) {
		try {
			new AdministradorDAO().logAdmin(admin);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
