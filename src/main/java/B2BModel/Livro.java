package B2BModel;

import java.io.Serializable;

public class Livro implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String titulo_PADRAO = "Placeholder";
	private int id;
	private int qtSec;
	private int data;
	private String titulo;
	private String autor;
	private String sinopse;
	private String categoria;
	
	//Metodos construtores
	public Livro() {
		id = -1;
		qtSec = -1;
		data = -1;
		titulo = titulo_PADRAO;
		autor = titulo_PADRAO;
		sinopse = titulo_PADRAO;
		categoria = titulo_PADRAO;
	}
	public Livro(int id, int qtSec, int data, String titulo, String autor, String categoria, String sinopse) {
		setId(id);
		setQtSec(qtSec);
		setData(data);
		setTitulo(titulo);
		setAutor(autor);
		setCategoria(categoria);
		setSinopse(sinopse);
	}
	
	//Metodos SET
	public void setId(int id) {
		this.id = id;
	}
	public void setQtSec(int qtSec) {
		this.qtSec = qtSec;
	}
	public void setData(int data) {
		this.data = data;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	//Metodos GET
	public int getId() {
		return id;
	}
	public int getQtSec() {
		return qtSec;
	}
	public int getData() {
		return data;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getAutor() {
		return autor;
	}
	public String getSinopse() {
		return sinopse;
	}
	public String getCategoria() {
		return categoria;
	}
	
	
	//Metodos EQUAL
	public boolean equalsLivro(Object obj) {
		return (this.getId() == ((Livro) obj).getId());
	}
	
	/**
	 * Metodo sobreposto da classe Object. Executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	
	@Override
	public String toString() {
		return "LivroID: " + id + ",Titulo: " + titulo + ",Autor: " + autor + ",QTD de secoes: " + qtSec + ",Data de lancamento: " + data + ",Categoria: " + categoria + ", Sinopse: " + sinopse;
	}
}