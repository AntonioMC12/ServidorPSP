package controller;

public class AppMain {

	public static void main(String[] args) {

		
		ServidorMain server = new ServidorMain();
		Thread threadServer = new Thread(server);
		threadServer.start();
		/**
		Administrador admin = new Administrador(null,"Miguel","Garcia","admin@gmail.com","1234",new ArrayList<Usuario>());
		Usuario usuario = new Usuario(null, "antonio", "munios", "prueba@gmail.com", "1234", new ArrayList<Cuenta>(), admin);
		Cuenta cuenta = new Cuenta(null, "1234",10.0, usuario);
		List<Cuenta> cuentas = new ArrayList<Cuenta>();
		cuentas.add(cuenta);
		List<Usuario> users = new ArrayList<Usuario>();
		users.add(usuario);
		usuario.setCuentas(cuentas);
		admin.setUsuario(users);
		
		try {
			new AdministradorDAO().save(admin);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
