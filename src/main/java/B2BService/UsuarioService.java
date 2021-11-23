package B2BService;
import spark.Request;
import spark.Response;
import B2BDao.UsuarioDAO;
import B2BModel.Usuario;

public class UsuarioService {
	private UsuarioDAO testeDAO;

	public UsuarioService() {
		testeDAO = new UsuarioDAO();
		testeDAO.conect();
	}

	public Object add(Request request, Response response) {
		String nome = request.queryParams("nome");
		int senha = Integer.parseInt(request.queryParams("senha"));
		int id = testeDAO.getMaxId() + 1;

		Usuario user = new Usuario(id, nome, senha);
		testeDAO.addUser(user);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Usuario teste = (Usuario) testeDAO.getUser(id);
		
		if (teste != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

    	    //return teste.toString();
    	    
            return "<usuario>\n" + 
            		"\t<id>" + teste.getId() + "</id>\n" +
            		"\t<nome>" + teste.getNome() + "</nome>\n" +
            		"\t<senha>" + teste.getSenha() + "</senha>\n" +
            		"</usuario>\n";
            
        } else {
            response.status(404); // 404 Not found
            return "Usuario " + id + " não encontrado.";
        }
	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
		Usuario teste = (Usuario) testeDAO.getUser(id);

        if (teste != null) {
        	teste.setNome(request.queryParams("nome"));
        	teste.setSenha(Integer.parseInt(request.queryParams("paginas")));

        	testeDAO.updateUser(teste);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Usuario não encontrado.";
        }
	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Usuario teste = (Usuario) testeDAO.getUser(id);

        if (teste != null) {

            testeDAO.removeUser(id);

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Usuario não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<testes type=\"array\">");
		for (Usuario teste : testeDAO.getAllUser()) {
			
			returnValue.append("<Usuario>\n" + 
            		"\t<id>" + teste.getId() + "</id>\n" +
            		"\t<nome>" + teste.getNome() + "</nome>\n" +
            		"\t<senha>" + teste.getSenha() + "</senha>\n" +
            		"</Usuario>\n");
            
			//returnValue.append(teste.toString());
		}
		returnValue.append("</testes>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}
