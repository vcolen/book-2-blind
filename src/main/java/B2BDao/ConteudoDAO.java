package B2BDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import B2BModel.Conteudo;

public class ConteudoDAO {
	private Connection conexao;
	
	public ConteudoDAO(){
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
			System.out.println("Conex�o efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conex�o N�O efetuada com o postgres -- Driver n�o encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conex�o N�O efetuada com o postgres -- " + e.getMessage());
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
	
	public boolean addConteudo(Conteudo conteudo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO conteudo (livroid, secid, secao) "
				       + "VALUES ("+conteudo.getLivroId()+ ", "  
				       + conteudo.getSecId() + ", '" + conteudo.getSecao() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean updateConteudo(Conteudo conteudo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE conteudo SET secao = '" + conteudo.getSecao() + "'"
				   + " WHERE livroid = " + conteudo.getLivroId() + " AND secid = " + conteudo.getSecId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean removeConteudo(int livroId, int secId) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM conteudo WHERE livroid = " + livroId + " AND secid = " + secId);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public Conteudo[] getAllConteudo() {
		Conteudo[] conteudos = null;
	
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM conteudo ORDER BY livroid");
			if(rs.next()){
				rs.last();
				conteudos = new Conteudo[rs.getRow()];
				rs.beforeFirst();
				
				for(int i = 0; rs.next(); i++) {
					conteudos[i] = new Conteudo(rs.getInt("livroid"),
                		rs.getInt("secid"), rs.getString("secao"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return conteudos;
	}
	
	public Conteudo[] getAllFrom(int livroId) {
		Conteudo[] conteudos = null;
	
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM conteudo WHERE livroid = " + livroId);		
			if(rs.next()){
				rs.last();
				conteudos = new Conteudo[rs.getRow()];
				rs.beforeFirst();
				
				for(int i = 0; rs.next(); i++) {
					conteudos[i] = new Conteudo(rs.getInt("livroid"),
                		rs.getInt("secid"), rs.getString("secao"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return conteudos;
	}
	
	public Conteudo getConteudo(int livroId, int secId) {
		Conteudo[] teste = this.getAllConteudo();
		for(int i = 0; i < teste.length; i++) {
			if(teste[i].getLivroId() == livroId && teste[i].getSecId() == secId) {
				return teste[i];
			}
		}
		return null;
	}
	public int getMaxId(int livroId){
		int i = 0;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT MAX(secid) AS maxId FROM conteudo WHERE livroid = " + livroId);
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



