package br.com.CadastroUsu.entidades;

import java.util.List;


public class Telefone {

	private String numero;
	private int ddd;
	private String tipo;
	private String telefoneComp;
	
	
	public String getTelefoneComp() {
		return telefoneComp;
	}
	public void setTelefoneComp(String telefoneComp) {
		this.telefoneComp = telefoneComp;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getDdd() {
		return ddd;
	}
	public void setDdd(int ddd) {
		this.ddd = ddd;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
