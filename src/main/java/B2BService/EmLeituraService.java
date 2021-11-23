package B2BService;
import spark.Request;
import spark.Response;

import B2BDao.EmLeituraDAO;
import B2BModel.EmLeitura;

public class EmLeituraService {
	private EmLeituraDAO testeDAO;

	public EmLeituraService() {
		testeDAO = new EmLeituraDAO();
		testeDAO.conect();
	}

	public Object add(Request request, Response response) {
		int userId = Integer.parseInt(request.queryParams("user"));
		int livroId = Integer.parseInt(request.queryParams("livro"));
		int secAtual = Integer.parseInt(request.queryParams("secao"));

		EmLeitura teste = new EmLeitura(livroId, userId, secAtual);
		testeDAO.addEmLeitura(teste);

		response.status(201); // 201 Created
		return userId + " " + livroId;
	}

	public Object get(Request request, Response response) {
		int livroId = Integer.parseInt(request.params(":id1"));
		int userId = Integer.parseInt(request.params(":id2"));
		
		EmLeitura teste = (EmLeitura) testeDAO.getEmLeitura(userId, livroId);
		
		if (teste != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<emLeitura>\n" + 
            		"\t<livroId>" + teste.getLivroId() + "</livroId>\n" +
            		"\t<userId>" + teste.getUserId() + "</userId>\n" +
            		"\t<secAtual>" + teste.getSecAtual() + "</secAtual>\n" +
            		"</emLeitura>\n";
        } else {
            response.status(404); // 404 Not found
            return "Usuario " + userId + " com registro do Livro " + livroId + " não encontrado.";
        }
	}

	public Object update(Request request, Response response) {
		int livroId = Integer.parseInt(request.params(":id1"));
		int userId = Integer.parseInt(request.params(":id2"));
		
		EmLeitura teste = (EmLeitura) testeDAO.getEmLeitura(userId, livroId);

        if (teste != null) {
        	teste.setUserId(Integer.parseInt(request.queryParams("user")));
        	teste.setLivroId(Integer.parseInt(request.queryParams("livro")));

        	testeDAO.updateEmLeitura(teste);
        	
            return true;
        } else {
            response.status(404); // 404 Not found
            return "EmLeitura não encontrado.";
        }
	}

	public Object remove(Request request, Response response) {
		int livroId = Integer.parseInt(request.params(":id1"));
		int userId = Integer.parseInt(request.params(":id2"));
		
		EmLeitura teste = (EmLeitura) testeDAO.getEmLeitura(userId, livroId);

        if (teste != null) {

            testeDAO.removeEmLeitura(userId, livroId);

            response.status(200); // success
        	return true;
        } else {
            response.status(404); // 404 Not found
            return "EmLeitura não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<testes type=\"array\">");
		for (EmLeitura teste : testeDAO.getAllEmLeitura()) {
			returnValue.append("<emLeitura>\n" + 
            		"\t<livroId>" + teste.getLivroId() + "</livroId>\n" +
            		"\t<userId>" + teste.getUserId() + "</userId>\n" +
            		"\t<secAtual>" + teste.getSecAtual() + "</secAtual>\n" +
            		"</emLeitura>\n");
		}
		returnValue.append("</testes>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}