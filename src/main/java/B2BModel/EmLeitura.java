package B2BModel;

import java.io.Serializable;

public class EmLeitura implements Serializable {
	private static final long serialVersionUID = 1L;
	private int livroId;
	private int userId;
	private int secAtual;

	
	//Metodos construtoras
	public EmLeitura() {
		livroId = -1;
		userId = -1;
		secAtual = -1;
	}
	public EmLeitura(int livroId, int userId, int secAtual) {
		setLivroId(livroId);
		setUserId(userId);
		setSecAtual(secAtual);
	}
	
	//Metodos set
	public void setLivroId(int livroId) {
		this.livroId = livroId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setSecAtual(int secAtual) {
		this.secAtual = secAtual;
	}
	
	//Metodos get
	public int getLivroId() {
		return livroId;
	}
	public int getUserId() {
		return userId;
	}
	public int getSecAtual() {
		return secAtual;
	}
	
	//Metodos equal
	public boolean equalsLivro(Object obj) {
		return (this.getLivroId() == ((EmLeitura) obj).getLivroId());
	}
	public boolean equalsUser(Object obj) {
		return (this.getUserId() == ((EmLeitura) obj).getUserId());
	}
	
	/**
	 * Metodo sobreposto da classe Object. Executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	
	@Override
	public String toString() {
		return "LivroID: " + livroId + ",UsuarioID: " + userId + ",Secao Atual: " + secAtual;
	}
}