package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
//classe para estabelecer conexao

import model.Userposjava;

public class UserPosDAO {
	private Connection connection;

	// construtor que irá injetar a conexão da singlaconnection e o método
	// getconnection
	// quando instanciar o objeto da classe userposdao, ele já vai instanciar uma
	// conexao local para fazer operações aqui necessarias

	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}

	// método de insert
	public void salvar(Userposjava userposjava) {

		try {
			String sql = "insert into userposjava (id, nome, email) " + "values (?, ?, ?)";
			// instnaciar preparedstatement, que é quem fará o insert
			// prepara a instrução passando a string sql para conexão
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setLong(1, userposjava.getId()); //
			insert.setString(2, userposjava.getNome());
			insert.setString(3, userposjava.getEmail());
			insert.execute(); // executa no bd os inserts acima

			connection.commit(); // salva no banco
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public List<Userposjava> listar() throws Exception {
		
		List<Userposjava> list = new ArrayList<Userposjava>();

		String sql = "select * from userposjava";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Userposjava userposjava = new Userposjava();
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));

			list.add(userposjava);
		}

		return list;
	}

	public Userposjava buscar(Long id) throws Exception {
		Userposjava retorno = new Userposjava();

		String sql = "select * from userposjava where id = " + id;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { // retorna apenas um ou nenhum

			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));

		}

		return retorno;
	}

	public void atualizar(Userposjava userposjava) {
		try {

			String sql = "update userposjava set nome = ? where id = " + userposjava.getId();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userposjava.getNome());

			statement.execute();
			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	public void deletar(Long id) {
		try {

			String sql = "delete from userposjava where id = " + id;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();

		}
	}

}
