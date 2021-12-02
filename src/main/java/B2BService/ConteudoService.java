package B2BService;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import B2BDao.ConteudoDAO;
import B2BModel.Conteudo;

public class ConteudoService {
	private ConteudoDAO testeDAO;

	public ConteudoService() {
		testeDAO = new ConteudoDAO();
		testeDAO.conect();
	}

	public Object add(Request request, Response response) {
		String secao = request.queryParams("secao");
		int livroId = Integer.parseInt(request.queryParams("livro"));
		int secId = testeDAO.getMaxId(livroId);
		if(secId >= 0) {
			secId = secId + 1;
		}else {
			secId = 0;
		}

		Conteudo teste = new Conteudo(livroId, secId, secao);
		testeDAO.addConteudo(teste);

		response.status(201); // 201 Created
		return secId;
	}

	public Object get(Request request, Response response) {
		int livroId = Integer.parseInt(request.params(":id1"));
		int secId = Integer.parseInt(request.params(":id2"));
		
		Conteudo teste = (Conteudo) testeDAO.getConteudo(livroId, secId);
		
		if (teste != null) {
    	    response.header("Content-Type", "application/json");
			response.header("Content-Encoding", "UTF-8");

			Map<String, Object> livro = new HashMap<>();

			livro.put("sec", teste.getSecao());
			livro.put("secId", teste.getSecId());
			livro.put("livroId", teste.getLivroId());

            return new JSONObject(livro);
        } else {
            response.status(404); // 404 Not found
            return "Secao " + secId + " do Livro " + livroId + " n�o encontrado.";
        }
	}

	public Object update(Request request, Response response) {
		int livroId = Integer.parseInt(request.params(":id1"));
		int secId = Integer.parseInt(request.params(":id2"));
		
		Conteudo teste = (Conteudo) testeDAO.getConteudo(livroId, secId);

        if (teste != null) {
        	teste.setSecao(request.queryParams("secao"));
        	teste.setLivroId(Integer.parseInt(request.queryParams("livro")));

        	testeDAO.updateConteudo(teste);
        	
            return secId;
        } else {
            response.status(404); // 404 Not found
            return "Conteudo n�o encontrado.";
        }
	}

	public Object remove(Request request, Response response) {
		int livroId = Integer.parseInt(request.params(":id1"));
		int secId = Integer.parseInt(request.params(":id2"));
		
		Conteudo teste = (Conteudo) testeDAO.getConteudo(livroId, secId);

        if (teste != null) {

            testeDAO.removeConteudo(livroId, secId);

            response.status(200); // success
        	return secId;
        } else {
            response.status(404); // 404 Not found
            return "Conteudo n�o encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<testes type=\"array\">");
		for (Conteudo teste : testeDAO.getAllConteudo()) {
			returnValue.append("<conteudo>\n" + 
            		"\t<livroId>" + teste.getLivroId() + "</livroId>\n" +
            		"\t<secId>" + teste.getSecId() + "</secId>\n" +
            		"\t<sec>" + teste.getSecao() + "</sec>\n" +
            		"</conteudo>\n");
		}
		returnValue.append("</testes>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
	
	public Object getAllFrom(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<testes type=\"array\">");
		int livroId = Integer.parseInt(request.params(":id"));
		
		Conteudo[] testes = testeDAO.getAllFrom(livroId);
		
		for (Conteudo teste : testes) {
			returnValue.append("<conteudo>\n" + 
            		"\t<livroId>" + teste.getLivroId() + "</livroId>\n" +
            		"\t<secId>" + teste.getSecId() + "</secId>\n" +
            		"\t<sec>" + teste.getSecao() + "</sec>\n" +
            		"</conteudo>\n");
		}
		returnValue.append("</testes>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}