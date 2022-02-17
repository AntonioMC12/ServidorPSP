package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
			Paquete<Usuario> op1 = (Paquete<Usuario>) paquete;
			new UsuarioController().getUsuarioById(op1.getObjeto().getId());
			break;
			
		case 2:
			


		default:
			break;
		}
	}

	


	public void run() {
		try {
			while(true) {
				Socket cliente = servidor.accept();
				System.out.println("Un nuevo cliente está conectado al servidor, la información es: \n " + cliente);
				//Escuchamos las entradas de los clientes
				DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
				String msn = flujoEntrada.readUTF();
				
				
				
				
				//Segun la entrada hacemos una acción u otra.
				//recibo op 1 del cliente
				ObjectInputStream entradaCliente = new ObjectInputStream(cliente.getInputStream());
				Object action = entradaCliente.readObject();
				serverController(action);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	


}
