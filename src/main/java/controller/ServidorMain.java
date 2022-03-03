package controller;

import java.net.ServerSocket;
import java.nio.LongBuffer;
import java.util.List;

import dao.AdministradorDAO;
import dao.UsuarioDAO;
import model.Administrador;
import model.Cuenta;
import model.Paquete;
import model.ServerManager;
import model.Usuario;
import utils.DAOException;

public class ServidorMain implements Runnable {

	ServerSocket servidor;
	ServerManager sm = new ServerManager(9999);

	public void serverController(Object action) {

		Paquete<?> paquete = (Paquete<?>) action;
		switch (paquete.getOpcion()) {

		case 1:
			Paquete<Usuario> paqueteUsuario = (Paquete<Usuario>) paquete;
			new UsuarioController().createUsuario(paqueteUsuario.getObjeto());
			break;

		case 2:
			/*Paquete<Object> respuestPaqueteCuenta5 = new Paquete<Object>();
			ObjectOutputStream salidaObjetoCuenta5;
			try {
				salidaObjetoCuenta5 = new ObjectOutputStream(cliente.getOutputStream());
				new CuentaController().mostrarCuentas();
				respuestPaqueteCuenta5.setResultado(true);
				salidaObjetoCuenta5.writeObject(respuestPaqueteCuenta5);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			break;

		case 3:
			Paquete<Cuenta> paqueteCuenta = (Paquete<Cuenta>) paquete;
			new CuentaController().extraerDinero(paqueteCuenta.getObjeto(), paqueteCuenta.getCantidad());
			break;

		case 4:
			Paquete<Cuenta> paqueteCuenta1 = (Paquete<Cuenta>) paquete;
			new CuentaController().ingresarDinero(paqueteCuenta1.getObjeto(), paqueteCuenta1.getCantidad());
			break;

		case 5:
			Paquete<Usuario> paqueteUsuario2 = (Paquete<Usuario>) paquete;
			System.out.println(paqueteUsuario2.getObjeto().toString());
				try {
					if(new UsuarioDAO().updateUsuario(paqueteUsuario2.getObjeto())!= null){
						paqueteUsuario2.setResultado(true);
						this.sm.sendObjectToServer(paqueteUsuario2);
					}else{
						paqueteUsuario2.setResultado(false);
						this.sm.sendObjectToServer(paqueteUsuario2);
					}
				} catch (DAOException e1) {
					e1.printStackTrace();
				};
			break;

		case 6:
			Paquete<Cuenta> paqueteCuenta2 = (Paquete<Cuenta>) paquete;
			System.out.println("hola");
			System.out.println(paqueteCuenta2.getObjeto().getUsuario().toString());
			if(new CuentaController().CreateCuenta(paqueteCuenta2.getObjeto())){
				paqueteCuenta2.setResultado(true);
				this.sm.sendObjectToServer(paqueteCuenta2);
			}else{
				paqueteCuenta2.setResultado(false);

				this.sm.sendObjectToServer(paqueteCuenta2);
			}
			break;

		case 7: //Mostramos usuarios del administrador
		Paquete<Administrador> paqueteAdministrador = (Paquete<Administrador>) paquete;
		Paquete<List<Usuario>> paqueteUsuarios = new Paquete<List<Usuario>>();
			paqueteUsuarios.setObjeto(new UsuarioController().mostarUsuariosPorAdmin(paqueteAdministrador.getObjeto().getId()));
			this.sm.sendObjectToServer(paqueteUsuarios);
			break;

		case 8:
			Paquete<Cuenta> paqueteCuenta3 = (Paquete<Cuenta>) paquete;
			new CuentaController().mostrarCuenta(paqueteCuenta3.getObjeto().getId());
			break;

		case 9:
			Paquete<Long> paqueteCuenta4 = (Paquete<Long>) paquete;
			System.out.println(paqueteCuenta4.getObjeto());
			if(new CuentaController().BorrarCuenta(paqueteCuenta4.getObjeto())){
				paqueteCuenta4.setResultado(true);
				this.sm.sendObjectToServer(paqueteCuenta4);
			}else{
				paqueteCuenta4.setResultado(false);
				this.sm.sendObjectToServer(paqueteCuenta4);
			}
			break;

		case 10:
			Paquete<List<Cuenta>> paqueteCuenta5L = new Paquete<List<Cuenta>>();
			paqueteCuenta5L.setObjeto(new CuentaController().mostrarCuentas());
			this.sm.sendObjectToServer(paqueteCuenta5L);

			break;

		case 11: // LOGIN USUARIO, DEVUELVE TRUE SI ESTA EN LA BD Y FALSE SI NO.
			Paquete<Usuario> paqueteUsuario11 = (Paquete<Usuario>) paquete;
			try {
				Boolean bool = new UsuarioController().logUser(paqueteUsuario11.getObjeto());
				if (bool) {
					paqueteUsuario11.setResultado(true);

					Usuario u=new UsuarioDAO().getUsuarioByNamePassword(paqueteUsuario11.getObjeto());
				
					paqueteUsuario11.setObjeto(u);
					this.sm.sendObjectToServer(paqueteUsuario11);
				} else {
					paqueteUsuario11.setResultado(false);
					this.sm.sendObjectToServer(paqueteUsuario11);
				}
			} catch (Exception e) {
			}
			break;

		case 12:
			Paquete<Administrador> paqueteAdministrador4 = (Paquete<Administrador>) paquete;
			try {
				if (new AdministradorController().logAdministrador(paqueteAdministrador4.getObjeto())) {
					Administrador a = new AdministradorDAO().getAdministradorByNamePassword(paqueteAdministrador4.getObjeto());
					paqueteAdministrador4.setResultado(true);
					paqueteAdministrador4.setObjeto(a);
					this.sm.sendObjectToServer(paqueteAdministrador4);
				} else {
					paqueteAdministrador4.setResultado(false);
					this.sm.sendObjectToServer(paqueteAdministrador4);
				}
			} catch (Exception e) {
			}
			break;
		default:
			break; 
		}
	}

	public void run() {
		try {
			System.out.println("INICIANDO SERVIDOR");
			while (true) {
				System.out.println("Un nuevo cliente esta conectado al servidor, la informacion es: \n ");
				Object action = sm.getObjectFromClient();
				serverController(action);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public List<Cuenta> removeUserFromCuentas(List<Cuenta> cuentas) {
		for (Cuenta cuenta : cuentas) {
			cuenta.setUsuario(null);
		}
		return cuentas;
	}

}
