package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerManager {

    private final int port; 

    private Socket socket = null;

    private ObjectInputStream inputStream = null; 
    private ObjectOutputStream outputStream = null; 

    private Object obj; 


    public ServerManager(int port){
        this.port = port;
    }

    public Object getObjectFromClient(){

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            socket = serverSocket.accept();

            inputStream = new ObjectInputStream(socket.getInputStream());

            obj = inputStream.readObject();



        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    public void sendObjectToServer(Object obj){

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(obj);

            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public int getPort() {
		return port;
	}
    
}