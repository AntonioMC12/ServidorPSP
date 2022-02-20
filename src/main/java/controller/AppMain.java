package controller;

public class AppMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServidorMain server = new ServidorMain();
		Thread threadServer = new Thread(server);
		threadServer.start();
	}

}
