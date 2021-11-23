package B2BDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import B2BModel.Livro;

public class LivroDAO {
	private Connection conexao;

	public LivroDAO(){
		conexao = null;
	}
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "book-to-blind.postgres.database.azure.com";
		String mydatabase = "b2b";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "notBlindMan@book-to-blind";
		String password = "qz*%X+6W2QH(2bqfpUJ!46HW4cKVjyXYzQ2";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean addLivro(Livro livro) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO livro (id, qtsec, data, titulo, categoria, sinopse, autor) "
				       + "VALUES ("+ livro.getId() + ", "  + livro.getQtSec() + ", " + livro.getData() + ", '" 
					+ livro.getTitulo() + "', '" + livro.getCategoria() +"', '" + livro.getSinopse() + "', '" + livro.getAutor() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean atualizarLivro(Livro livro) {
		boolean status = false;
		
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE livro SET autor = '" + livro.getAutor() + "', titulo = '" + livro.getTitulo() 
			+ "', categoria = '" + livro.getCategoria() + " WHERE id = " + livro.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	public boolean excluirLivro(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM livro WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public Livro[] getAll() {
		Livro[] livros = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM livro");		
	         if(rs.next()){
	             rs.last();
	             livros = new Livro[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                livros[i] = new Livro(rs.getInt("id"), rs.getInt("qtsec"), rs.getInt("data"), rs.getString("titulo"), rs.getString("autor"), 
	                		rs.getString("categoria"), rs.getString("sinopse"));
	             }
	         } 
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return livros;
	}	
	
	public Livro selecionarLivro(int id) {
		Livro[] teste = this.getAll();
		for(int i = 0; i < teste.length; i++) {
			if(teste[i].getId() == id) {
				return teste[i];
			}
		}
		return null;
	}
	public int getMaxId(){
		int i = 0;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT MAX(id) AS maxId FROM livro");		
			if(rs.next()){
				
				i = rs.getInt("maxId");
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return i;
	}
}
