package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.Administrador;
import model.Cuenta;
import model.Paquete;
import model.Usuario;

public class ServidorMain implements Runnable {

	ServerSocket servidor;

	public ServidorMain() {
		try {
			this.servidor = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void serverController(Object action) {

		Paquete<?> paquete = (Paquete<?>) action;
		
		

		switch (paquete.getOpcion()) {
		case 1:
			Paquete<Usuario> paqueteUsuario = (Paquete<Usuario>) paquete;
			new UsuarioController().getUsuarioById(paqueteUsuario.getObjeto().getId());
			break;
		case 2:
			Paquete<Usuario> paqueteUsuario1 = (Paquete<Usuario>) paquete;
			new CuentaController().mostrarCuenta(paqueteUsuario1.getObjeto().getId());
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
			new UsuarioController().createUsuario(paqueteUsuario2.getObjeto());
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
			Paquete<Cuenta> paqueteCuenta4 = (Paquete<Cuenta>) paquete;
			new CuentaController().BorrarCuenta(paqueteCuenta4.getObjeto());
			break;
		case 10:
			Paquete<Administrador> paqueteAdministrador = (Paquete<Administrador>) paquete;
			new AdministradorController().getAdminById(paqueteAdministrador.getObjeto().getId());
			break;
		default:
			break;
		}
	}

	public void run() {
		try {
			while (true) {
				Socket cliente = servidor.accept();
				System.out.println("Un nuevo cliente est� conectado al servidor, la informaci�n es: \n " + cliente);
				// Escuchamos las entradas de los clientes
				DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
				String msn = flujoEntrada.readUTF();

				// Segun la entrada hacemos una acci�n u otra.
				// recibo op 1 del cliente
				ObjectInputStream entradaCliente = new ObjectInputStream(cliente.getInputStream());
				Object action = entradaCliente.readObject();
				serverController(action);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
