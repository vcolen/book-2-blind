package B2BDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import B2BModel.EmLeitura;

public class EmLeituraDAO {
	private Connection conexao;
	
	public EmLeituraDAO(){
		conexao = null;
	}
	
	public boolean conect() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "book-to-blind.postgres.database.azure.com";
		String mydatabase = "b2b";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String useraname = "notBlindMan@book-to-blind";
		String password = "qz*%X+6W2QH(2bqfpUJ!46HW4cKVjyXYzQ2";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, useraname, password);
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
	
	public boolean addEmLeitura(EmLeitura emLeitura) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO emleitura (livroid, userid, secatual) "
				       + "VALUES ("+emLeitura.getLivroId()+ ", "  
				       + emLeitura.getUserId() + ", " + emLeitura.getSecAtual() + ");");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean updateEmLeitura(EmLeitura emLeitura) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE EmLeitura SET secatual = " + emLeitura.getSecAtual() + ""
				   + " WHERE livroid = " + emLeitura.getLivroId() + " AND userid = " + emLeitura.getUserId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean removeEmLeitura(int userid, int livroid) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM emleitura WHERE livroid = " + livroid + " AND userid = " + userid);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public EmLeitura[] getAllEmLeitura() {
		EmLeitura[] emLeituras = null;
	
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM emleitura");		
			if(rs.next()){
				rs.last();
				emLeituras = new EmLeitura[rs.getRow()];
				rs.beforeFirst();
				
				for(int i = 0; rs.next(); i++) {
					emLeituras[i] = new EmLeitura(rs.getInt("livroid"),
                		rs.getInt("userid"), rs.getInt("secatual"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return emLeituras;
	}
	
	public EmLeitura getEmLeitura(int userid, int livroid) {
		EmLeitura[] teste = this.getAllEmLeitura();
		for(int i = 0; i < teste.length; i++) {
			if(teste[i].getUserId() == userid && teste[i].getLivroId() == livroid) {
				return teste[i];
			}
		}
		return null;
	}
}



