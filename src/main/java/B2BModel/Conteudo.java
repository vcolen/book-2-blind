package B2BModel;

import java.io.Serializable;

public class Conteudo implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String sec_PADRAO = "Placeholder";
	private int livroId;
	private int secId;
	private String secao;

	
	//Metodos construtoras
	public Conteudo() {
		livroId = 0;
		secao = sec_PADRAO;
		secId = 0;
	}
	public Conteudo(int livroId, int secId, String secao) {
		setLivroId(livroId);
		setSecId(secId);
		setSecao(secao);
	}
	
	//Metodos set
	public void setLivroId(int livroId) {
		this.livroId = livroId;
	}
	public void setSecId(int secId) {
		this.secId = secId;
	}
	public void setSecao(String secao) {
		this.secao = secao;
	}
	
	//Metodos get
	public int getLivroId() {
		return livroId;
	}
	public int getSecId() {
		return secId;
	}
	public String getSecao() {
		return secao;
	}
	
	//Metodos equal
	public boolean equalsLivro(Object obj) {
		return (this.getLivroId() == ((Conteudo) obj).getLivroId());
	}
	public boolean equalsSec(Object obj) {
		return (this.getSecId() == ((Conteudo) obj).getSecId());
	}
}