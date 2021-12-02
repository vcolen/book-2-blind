package B2BService;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

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
    	    response.header("Content-Type", "application/json");
    	    response.header("Content-Encoding", "UTF-8");

			Map<String, Object> livro = new HashMap<>();

			livro.put("livroId", teste.getLivroId());
			livro.put("userId", teste.getUserId());
			livro.put("secAtual", teste.getSecAtual());

			return new JSONObject(livro);
        } else {
            response.status(404); // 404 Not found
            return "Usuario " + userId + " com registro do Livro " + livroId + " n�o encontrado.";
        }
	}

	public Object update(Request request, Response response) {
		int livroId = Integer.parseInt(request.params(":id1"));
		int userId = Integer.parseInt(request.params(":id2"));
		int secAtual = Integer.parseInt(request.queryParams("secAtual"));

		EmLeitura teste = (EmLeitura) testeDAO.getEmLeitura(userId, livroId);

        if (teste != null) {
        	teste.setUserId(userId);
        	teste.setLivroId(livroId);
			teste.setSecAtual(secAtual);

        	testeDAO.updateEmLeitura(teste);
        	
            return true;
        } else {
            response.status(404); // 404 Not found
            return "EmLeitura n�o encontrado.";
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
            return "EmLeitura n�o encontrado.";
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