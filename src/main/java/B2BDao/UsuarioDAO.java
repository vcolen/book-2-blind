package B2BDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import B2BModel.Usuario;

public class UsuarioDAO {
	private Connection conexao;
	
	public UsuarioDAO(){
		conexao = null;
	}
	
	public boolean conect() {
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
	
	public boolean addUser(Usuario usuario) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO usuario (id, nome, senha) "
				       + "VALUES ("+usuario.getId()+ ", '"  
				       + usuario.getNome() + "', " + usuario.getSenha() + ");");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean updateUser(Usuario usuario) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE usuario SET senha = '" + usuario.getSenha() + "', nome = '" + usuario.getNome() + "'"
				   + " WHERE id = " + usuario.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean removeUser(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM usuario WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public Usuario[] getAllUser() {
		Usuario[] usuarios = null;
	
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM usuario");		
			if(rs.next()){
				rs.last();
				usuarios = new Usuario[rs.getRow()];
				rs.beforeFirst();
				
				for(int i = 0; rs.next(); i++) {
					usuarios[i] = new Usuario(rs.getInt("id"), rs.getString("nome"),
                		rs.getInt("senha"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
	public Usuario getUser(int id) {
		Usuario[] teste = this.getAllUser();
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
			ResultSet rs = st.executeQuery("SELECT MAX(id) AS maxId FROM usuario");		
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



