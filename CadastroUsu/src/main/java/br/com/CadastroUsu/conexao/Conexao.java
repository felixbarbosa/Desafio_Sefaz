package br.com.CadastroUsu.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Classe de Conexão com o Banco de Dados H2, utilizando o JDBC
public class Conexao {

	public static Connection conectar() throws ClassNotFoundException {
		
		Connection conexao = null;
		
		Class.forName("org.h2.Driver");
		
		try {
			
			conexao = DriverManager.getConnection("jdbc:h2:C:\\DataBase_CadastroUsu/cadastrousu;AUTO_SERVER=TRUE;DB_CLOSE_ON_EXIT=FALSE", "admin", "123");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conexao;
	}
	
}