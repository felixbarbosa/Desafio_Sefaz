package br.com.CadastroUsu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.CadastroUsu.entidades.Telefone;
import br.com.CadastroUsu.entidades.Usuario;
import br.com.CadastroUsu.conexao.Conexao;

//Classe responsável por criar os metodos sql
public class ControleBD {

	public boolean incluir(Usuario usuario, Conexao con) {
		
		Statement stmt = null;
		
		String query = "INSERT INTO USUARIOS VALUES ('" + usuario.getNome() + "','" + usuario.getEmail() + 
					   "','" + usuario.getSenha() + "');";
		
		try {
			
			stmt = con.conectar().createStatement();
			stmt.execute(query);
			return true;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean incluirFK(Telefone telefone, Usuario usuario, Conexao con) {
		
		Statement stmt = null;
		
		String query = "INSERT INTO TELEFONE VALUES ('" + telefone.getDdd() + "', '"
				     + telefone.getNumero() + "', '" + telefone.getTipo() + "', '" + 
				       usuario.getEmail() + "');";
		
		
		try {
			
			stmt = con.conectar().createStatement();
			stmt.execute(query);
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean login(Usuario usuario, Conexao con) {
		
		Statement stmt = null;
		ResultSet rs = null;
		
		String query = "SELECT * FROM USUARIOS "
				     + "WHERE email = '" + usuario.getEmail() + "' "
				     +"AND senha = '" + usuario.getSenha() + "'";
		
		try {
			
			stmt = con.conectar().createStatement();
			rs = stmt.executeQuery(query);
			
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	public List<Usuario> consultaUsuarios(Conexao con){
		
		List<Usuario> lista = new ArrayList<Usuario>();
	
		Usuario usuario;
		Telefone telefone;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		String query = "SELECT USUARIOS.NOME, USUARIOS.EMAIL, TELEFONE.NUMERO, TELEFONE.DDD, TELEFONE.TIPO FROM USUARIOS JOIN TELEFONE "
					 + "ON USUARIOS.EMAIL = TELEFONE.EMAILFK";
		
		
		try {
			
			stmt = con.conectar().createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				
				List<Telefone> listaFone = new ArrayList<Telefone>();
				usuario = new Usuario();
				telefone = new Telefone();
				
				usuario.setNome(rs.getString("NOME"));
				usuario.setEmail(rs.getString("email"));
				telefone.setDdd(rs.getInt("ddd"));
				telefone.setNumero(rs.getString("numero"));
				telefone.setTipo(rs.getString("tipo"));
				
				listaFone.add(telefone);
				
				usuario.setTelefone(listaFone);
				
				lista.add(usuario);
			}
			
			return lista;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public boolean removerFK(Usuario usuario, Conexao con) {
		
		Statement stmt = null;
		
		String query = "DELETE FROM TELEFONE WHERE emailfk = '" + usuario.getEmail() + "'";
		
		try {
			
			stmt = con.conectar().createStatement();
			stmt.execute(query);
			return true;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public boolean remover(Usuario usuario, Conexao con) {
		
		removerFK(usuario, con);
		
		Statement stmt = null;
		
		String query = "DELETE FROM USUARIOS WHERE email = '" + usuario.getEmail() + "'";
		
		try {
			
			stmt = con.conectar().createStatement();
			stmt.execute(query);
			return true;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public boolean editarFK(Usuario usuarioatual, Usuario usuarionovo, Conexao con) {
		
		Statement stmt = null;
		
		String query = "UPDATE TELEFONE SET numero = '" + usuarionovo.getTelefone().get(0).getNumero()
	                 + "', ddd = '" +  usuarionovo.getTelefone().get(0).getDdd() 
	                 + "' WHERE emailfk = '" + usuarioatual.getEmail() + "'";
		
		try {
			
			stmt = con.conectar().createStatement();
			stmt.executeUpdate(query);
			return true;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	//Só edita Nome e Telefone, pois email e senha são dados do login.
	public boolean editar(Usuario usuarioatual, Usuario usuarionovo, Conexao con) {
		
		editarFK(usuarioatual, usuarionovo, con);
		
		Statement stmt = null;
		
		String query = "UPDATE USUARIOS SET nome = '" + usuarionovo.getNome()
				     + "' WHERE email = '" + usuarioatual.getEmail() + "'";
		
		try {
			
			stmt = con.conectar().createStatement();
			stmt.executeUpdate(query);
			return true;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
}
