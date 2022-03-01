package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import model.Administrador;
import model.Cuenta;
import model.Paquete;
import model.Usuario;

public class ServidorMain implements Runnable {

	ServerSocket servidor ;

	public void serverController(Object action, Socket cliente) {

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
			Paquete<Object> respuestPaqueteCuenta5 = new Paquete<Object>();
			ObjectOutputStream salidaObjetoCuenta5;
				try {
					salidaObjetoCuenta5 = new ObjectOutputStream(cliente.getOutputStream());
					new CuentaController().mostrarCuentas();
					respuestPaqueteCuenta5.setResultado(true);
					salidaObjetoCuenta5.writeObject(respuestPaqueteCuenta5);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
			Paquete<Object> respuestPaqueteUsuario2 = new Paquete<Object>();
			ObjectOutputStream salidaObjetoUsuario2;
				try {
					salidaObjetoUsuario2 = new ObjectOutputStream(cliente.getOutputStream());
					new UsuarioController().createUsuario(paqueteUsuario2.getObjeto());
					respuestPaqueteUsuario2.setResultado(true);
					salidaObjetoUsuario2.writeObject(respuestPaqueteUsuario2);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
			Paquete<Object> respuestPaqueteCuenta4 = new Paquete<Object>();
			ObjectOutputStream salidaObjetoCuenta4;
			new CuentaController().BorrarCuenta(paqueteCuenta4.getObjeto());
			respuestPaqueteCuenta4.setResultado(true);
			try {
				salidaObjetoCuenta4 = new ObjectOutputStream(cliente.getOutputStream());
				salidaObjetoCuenta4.writeObject(respuestPaqueteCuenta4);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e) {
				//TODO: handle exception
			}
			break;

		case 10:
			Paquete<Administrador> paqueteAdministrador = (Paquete<Administrador>) paquete;
			new AdministradorController().getAdminById(paqueteAdministrador.getObjeto().getId());
			break;

		case 11:
			Paquete<Usuario> paqueteUsuario3 = (Paquete<Usuario>) paquete;
			Paquete<Usuario> respuestPaqueteUsuario3 = new Paquete();
			try {
				ObjectOutputStream salidaObjetoUsuario3 = new ObjectOutputStream(cliente.getOutputStream());
				
				if (new UsuarioController().logUser(paqueteUsuario3.getObjeto())) {
					respuestPaqueteUsuario3.setResultado(true);
					salidaObjetoUsuario3.writeObject(respuestPaqueteUsuario3);
				} else {
					respuestPaqueteUsuario3.setResultado(false);
					salidaObjetoUsuario3.writeObject(respuestPaqueteUsuario3);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case 12:
			Paquete<Administrador> paqueteAdministrador4 = (Paquete<Administrador>) paquete;
			Paquete<Administrador> respuestAdministrador4 = new Paquete();
			try {
				ObjectOutputStream salidaObjetoAdministrador4 = new ObjectOutputStream(cliente.getOutputStream());
				
				if (new AdministradorController().logAdministrador(paqueteAdministrador4.getObjeto())) {
					respuestAdministrador4.setResultado(true);
					salidaObjetoAdministrador4.writeObject(respuestAdministrador4);
				} else {
					respuestAdministrador4.setResultado(false);
					salidaObjetoAdministrador4.writeObject(respuestAdministrador4);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	public void run() {
		try {
			System.out.println("INICIANDO SERVIDOR");
			this.servidor = new ServerSocket(9999);
			while (true) {
				Socket cliente = servidor.accept();
				System.out.println("Un nuevo cliente esta conectado al servidor, la informacion es: \n " + cliente);
				// Escuchamos las entradas de los clientes
				ObjectInputStream entradaCliente = new ObjectInputStream(cliente.getInputStream());
				System.out.println();
				Object action = entradaCliente.readObject();
				System.out.println(action.toString());
				// Segun la entrada hacemos una acci�n u otra.
				serverController(action,cliente);
				// recibo op 1 del cliente
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
