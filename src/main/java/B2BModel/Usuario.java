package B2BModel;

import java.io.Serializable;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String nome_PADRAO = "User Padrao";
	private int id;
	private String nome;
	private int senha;
	
	//Funcoes construtoras
	public Usuario() {
		id = 0;
		nome = nome_PADRAO;
		senha = 0;
	}
	public Usuario(int id, String nome, int senha) {
		setId(id);
		setNome(nome);
		setSenha(senha);
	}
	
	//Metodos set
	public void setId(int id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setSenha(int senha) {
		this.senha = senha;
	}
	
	//Metodos get
	public int getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public int getSenha() {
		return senha;
	}
	
	/**
	 * Metodo sobreposto da classe Object. Executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		String r = "Nome: " + nome + ",ID: " + String.valueOf(id) + ",Senha: " + String.valueOf(senha);
		return r;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Usuario) obj).getId());
	}	
}