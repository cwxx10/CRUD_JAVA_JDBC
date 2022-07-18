package conexaojdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
	
	private static String url = "jdbc:postgresql://localhost:5432/posjava"; //5433/posjava?
	private static String password ="admin"; 
	private static String user = "postgres";
	private static Connection connection = null;
	
	static {
		Conectar();
	}
	
	public SingleConnection() {
		Conectar();
	}
	
	private static void Conectar() {
		try {
			//verificaçao se a conexao for nula ele conecta
			if (connection == null) {
				//carregamento do driver do banco de dados utilizado
				Class.forName("org.postgresql.Driver");
				//usando o drivermanager do drive carregado acima para passar os parametros
				connection = DriverManager.getConnection(url, user, password);
				//para nao salvar automaticamente usa como false
				connection.setAutoCommit(false);
				System.out.println("Conectado.");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
}
