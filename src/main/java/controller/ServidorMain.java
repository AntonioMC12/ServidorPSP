package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
			System.out.println(paqueteUsuario.toString());
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

		case 3: //UPDATEAR USUARIO
			Paquete<Usuario> paqueteCuenta = (Paquete<Usuario>) paquete;
			try {
				paqueteCuenta.setObjeto(new UsuarioDAO().updateUsuario(paqueteCuenta.getObjeto()));
				paqueteCuenta.setResultado(true);
				this.sm.sendObjectToServer(paqueteCuenta);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		case 4:
			Paquete<Cuenta> paqueteCuenta1 = (Paquete<Cuenta>) paquete;
			new CuentaController().ingresarDinero(paqueteCuenta1.getObjeto(), paqueteCuenta1.getCantidad());
			break;

		case 5:
			/*Paquete<Usuario> paqueteUsuario2 = (Paquete<Usuario>) paquete;
			Paquete<Object> respuestPaqueteUsuario2 = new Paquete<Object>();
			ObjectOutputStream salidaObjetoUsuario2;
			try {
				salidaObjetoUsuario2 = new ObjectOutputStream(cliente.getOutputStream());
				new UsuarioController().createUsuario(paqueteUsuario2.getObjeto());
				respuestPaqueteUsuario2.setResultado(true);
				salidaObjetoUsuario2.writeObject(respuestPaqueteUsuario2);
			} catch (IOException e1) { // TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			break;

		case 6:
			Paquete<Cuenta> paqueteCuenta2 = (Paquete<Cuenta>) paquete;
			new CuentaController().CreateCuenta(paqueteCuenta2.getObjeto());
			break;

		case 7:
			new UsuarioController().getAllUsuarios();
			break;

		case 8:
			Paquete<Cuenta> paqueteCuenta3 = (Paquete<Cuenta>) paquete;
			new CuentaController().mostrarCuenta(paqueteCuenta3.getObjeto().getId());
			break;

		case 9:
			/*Paquete<Cuenta> paqueteCuenta4 = (Paquete<Cuenta>) paquete;
			Paquete<Object> respuestPaqueteCuenta4 = new Paquete<Object>();
			ObjectOutputStream salidaObjetoCuenta4;
			new CuentaController().BorrarCuenta(paqueteCuenta4.getObjeto());
			respuestPaqueteCuenta4.setResultado(true);
			try {
				salidaObjetoCuenta4 = new ObjectOutputStream(cliente.getOutputStream());
				salidaObjetoCuenta4.writeObject(respuestPaqueteCuenta4);
			} catch (IOException e1) { // TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e) { // TODO: handle exception
			}*/
			break;

		case 10:
			Paquete<Administrador> paqueteAdministrador = (Paquete<Administrador>) paquete;
			new AdministradorController().getAdminById(paqueteAdministrador.getObjeto().getId());
			break;

		case 11: // LOGIN USUARIO, DEVUELVE TRUE SI ESTA EN LA BD Y FALSE SI NO.
			Paquete<Usuario> paqueteUsuario11 = (Paquete<Usuario>) paquete;
			try {
				Boolean bool = new UsuarioController().logUser(paqueteUsuario11.getObjeto());
				if (bool) {
					paqueteUsuario11.setResultado(true);

					Usuario u=new UsuarioDAO().getUsuarioByNamePassword(paqueteUsuario11.getObjeto());
				
					paqueteUsuario11.setObjeto(u);
					
					System.out.println("+++++++++++++++++++++++++++++");
					System.out.println(paqueteUsuario11);
					this.sm.sendObjectToServer(paqueteUsuario11);
				} else {
					paqueteUsuario11.setResultado(false);
					this.sm.sendObjectToServer(paqueteUsuario11);
				}
			} catch (Exception e) {
			}
			break;

		case 12: // LOGIN ADMINISTRADOR, DEVUELVE TRUE SI ESTA EN LA BD Y FALSE SI NO.
			Paquete<Administrador> paqueteAdministrador4 = (Paquete<Administrador>) paquete;
			try {
				Boolean bool = new AdministradorController().logAdministrador(paqueteAdministrador4.getObjeto());
				if (bool) {
					paqueteAdministrador4.setResultado(true);
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
				System.out.println(action.toString());
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
