package br.com.CadastroUsu.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import br.com.CadastroUsu.conexao.Conexao;
import br.com.CadastroUsu.dao.ControleBD;
import br.com.CadastroUsu.entidades.Telefone;
import br.com.CadastroUsu.entidades.Usuario;

//Classe responsável por fazer a ligação entre as telas e o banco de dados

@SessionScoped
@ManagedBean
public class UsuariosCon {

	private Usuario usuario = new Usuario();
	private Telefone telefone = new Telefone();
	private List<Usuario> listaUsu = new ArrayList<Usuario>();
	private ControleBD controle;
	private Conexao con;
	private Usuario usuarioatual;
	private Usuario usuarionovo;
	

	//Adicionar um novo usuario
	public String add() {
		
		con = new Conexao();
		controle = new ControleBD();
		
		//Separando o ddd do telefone
		String ddds = telefone.getTelefoneComp().substring(1, 3);
		int ddd = Integer.parseInt(ddds);
		
		//Separando o numero do telefone
		String numero = telefone.getTelefoneComp().substring(5, 14);
		
		//Pegando o primeiro digito do numero
		int digito0 = numero.charAt(0) - '0';
		
		//Identificando se o numero é residencial ou celular
		if(digito0 < 7) {
			telefone.setTipo("Residencia");
		}else {
			telefone.setTipo("Celular");
		}
		
		telefone.setNumero(numero);
		telefone.setDdd(ddd);
		
		//Incluindo no banco de dados
		if(controle.incluir(usuario, con) && controle.incluirFK(telefone, usuario, con)) {
			usuario = new Usuario();
			telefone = new Telefone();
			return "Usuario";
		}else {
			return "";
		}
		
	}
	
	//Listar todos os usuarios cadastrados
	public void buscar(){
		con = new Conexao();
		controle = new ControleBD();
		listaUsu = controle.consultaUsuarios(con);
	}
	
	//Realizar login
	public String login() {
		
		con = new Conexao();
		controle = new ControleBD();
		
		if(controle.login(usuario, con)) {
			buscar();
			usuario = new Usuario();
			return "Logado";
		}else {
			return "Cadastro";
		}
		
	}
	
	//Remover um usuário
	public void remover(Usuario usuario) {
		
		con = new Conexao();
		controle = new ControleBD();
		
		//Se removeu com sucesso, atualize a tabela
		if(controle.remover(usuario, con)) {
			buscar();
			usuario = new Usuario();
		}else {
			
		}
		
	}
	
	public String telaEditar(Usuario usuario) {
		usuarioatual = usuario;
		return "Editar";
	}
	
	//Remover um usuário
	public String editar() {
			
		con = new Conexao();
		controle = new ControleBD();
		List<Telefone> listaTel = new ArrayList<Telefone>();
		
		//Separando o ddd do telefone
		String ddds = telefone.getTelefoneComp().substring(1, 3);
		int ddd = Integer.parseInt(ddds);
				
		//Separando o numero do telefone
		String numero = telefone.getTelefoneComp().substring(5, 14);
				
		//Pegando o primeiro digito do numero
		int digito0 = numero.charAt(0) - '0';
				
		//Identificando se o numero é residencial ou celular
		if(digito0 < 7) {
			telefone.setTipo("Residencia");
		}else {
			telefone.setTipo("Celular");
		}
				
		telefone.setNumero(numero);
		telefone.setDdd(ddd);
		
		listaTel.add(telefone);
		
		usuario.setTelefone(listaTel);
		
		System.out.println("Nome = " + usuarioatual.getNome());
			
		//Se removeu com sucesso, atualize a tabela
		if(controle.editar(usuarioatual, usuario, con)) {
			buscar();
			usuario = new Usuario();
			telefone = new Telefone();
			return "Logado";
		}else {
			return "";
		}
			
	}
	
	//Getters e Setters
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Usuario> getListaUsu() {
		return listaUsu;
	}
	public void setListaUsu(List<Usuario> listaUsu) {
		this.listaUsu = listaUsu;
	}
	
	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}
	
	public Usuario getUsuarioAtual() {
		return usuarioatual;
	}

	public void setUsuarioatual(Usuario usuarioatual) {
		this.usuarioatual = usuarioatual;
	}
	
	
	
}
