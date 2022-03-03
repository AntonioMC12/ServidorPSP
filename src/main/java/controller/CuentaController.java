package controller;

import java.util.List;

import dao.CuentaDAO;
import model.Cuenta;
import utils.DAOException;

public class CuentaController {
	
	public boolean CreateCuenta(Cuenta cuenta) {
		try {
			return new CuentaDAO().save(cuenta, cuenta.getUsuario().getId());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean BorrarCuenta(Long id) {
		try {
			System.out.println("Borrando cuenta");
			return new CuentaDAO().deleteCuenta(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public Cuenta mostrarCuenta(Long id) {
		try {
			return new CuentaDAO().showCuenta(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<Cuenta> mostrarCuentas() {
		try {
			return new CuentaDAO().showAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void extraerDinero(Cuenta cuenta, double cantidad) {
		try {
			new CuentaDAO().extraerDinero(cuenta, cantidad);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ingresarDinero(Cuenta cuenta, double cantidad) {
		try {
			new CuentaDAO().ingresarDinero(cuenta, cantidad);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
