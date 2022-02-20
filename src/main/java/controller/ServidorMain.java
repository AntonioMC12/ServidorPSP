package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import model.Administrador;
import model.Cuenta;
import model.Paquete;
import model.Usuario;

public class ServidorMain implements Runnable {
	
	ServerSocket servidor;

	public void serverController(Object action) {

		Socket cliente = new Socket();
		OutputStream salida;
		try {
			salida = cliente.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Paquete<?> paquete = (Paquete<?>) action;

		switch (paquete.getOpcion()) {
		case 1:
			Paquete<Usuario> paqueteUsuario = (Paquete<Usuario>) paquete;
			System.out.println(paqueteUsuario.toString());
			new UsuarioController().createUsuario(paqueteUsuario.getObjeto());

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
		case 11:
			Paquete<Usuario> paqueteUsuario3 = (Paquete<Usuario>) paquete;
			Paquete<Usuario> respuestPaquete = new Paquete();

			if(new UsuarioController().logUser(paqueteUsuario3.getObjeto())) {
				respuestPaquete.setResultado(true);
				try {
					salida = cliente.getOutputStream();
					ObjectOutputStream salidaObjeto = new ObjectOutputStream(salida);
					salidaObjeto.writeObject(respuestPaquete);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				respuestPaquete.setResultado(false);
				try {
					salida = cliente.getOutputStream();
					ObjectOutputStream salidaObjeto = new ObjectOutputStream(salida);
					salidaObjeto.writeObject(respuestPaquete);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		default:
			break;
		}
	}

	public void run() {
		try {

			this.servidor = new ServerSocket(9999);

			while (true) {
				Socket cliente = servidor.accept();
				System.out.println("Un nuevo cliente est� conectado al servidor, la informaci�n es: \n " + cliente);
				ObjectInputStream entradaCliente = new ObjectInputStream(cliente.getInputStream());
				System.out.println();
				Object action = entradaCliente.readObject();
				// Segun la entrada hacemos una acci�n u otra.
				serverController(action);
				// recibo op 1 del cliente
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
