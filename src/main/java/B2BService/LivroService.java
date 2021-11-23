package B2BService;
import spark.Request;
import spark.Response;

import B2BDao.LivroDAO;
import B2BModel.Livro;

public class LivroService {
	private LivroDAO testeDAO;

	public LivroService() {
		testeDAO = new LivroDAO();
		testeDAO.conectar();
	}

	public Object add(Request request, Response response) {
		String titulo = request.queryParams("titulo");
		String autor = request.queryParams("autor");
		String categoria = request.queryParams("categoria");
		String sinopse = request.queryParams("sinopse");
		int data = Integer.parseInt(request.queryParams("data"));
		int qtsec = Integer.parseInt(request.queryParams("qtsec"));
		int id = testeDAO.getMaxId() + 1;

		Livro teste = new Livro(id, qtsec, data, titulo, autor, categoria, sinopse);
		testeDAO.addLivro(teste);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Livro teste = (Livro) testeDAO.selecionarLivro(id);
		
		if (teste != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

    	    
            return "<livro>\n" + 
            		"\t<id>" + teste.getId() + "</id>\n" +
            		"\t<titulo>" + teste.getTitulo() + "</titulo>\n" +
            		"\t<autor>" + teste.getAutor() + "</autor>\n" +
            		"\t<data>" + teste.getData() + "</data>\n" +
            		"\t<categoria>" + teste.getCategoria() + "</categoria>\n" +
            		"\t<qtsec>" + teste.getQtSec() + "</qtsec>\n" +
            		"\t<sinopse>" + teste.getSinopse() + "</sinopse>\n" +
            		"</livro>\n";
            
    	    //return teste.toString();
        } else {
            response.status(404); // 404 Not found
            return "Livro " + id + " não encontrado.";
        }
	}

	public Object update(Request request, Response response) {
		
        int id = Integer.parseInt(request.params(":id"));
        
		Livro teste = (Livro) testeDAO.selecionarLivro(id);

        if (teste != null) {
        	teste.setTitulo(request.queryParams("titulo"));
        	teste.setAutor(request.queryParams("autor"));
        	teste.setCategoria(request.queryParams("categoria"));
        	teste.setSinopse(request.queryParams("sinopse"));
        	teste.setData(Integer.parseInt(request.queryParams("data")));
        	teste.setQtSec(Integer.parseInt(request.queryParams("secqt")));

        	testeDAO.atualizarLivro(teste);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Livro não encontrado.";
        }
	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Livro teste = (Livro) testeDAO.selecionarLivro(id);

        if (teste != null) {

            testeDAO.excluirLivro(id);

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Livro não encontrado.";
        }

	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<testes type=\"array\">");
		for (Livro teste : testeDAO.getAll()) {
			returnValue.append("<livro>\n" + 
            		"\t<id>" + teste.getId() + "</id>\n" +
            		"\t<titulo>" + teste.getTitulo() + "</titulo>\n" +
            		"\t<autor>" + teste.getAutor() + "</autor>\n" +
            		"\t<data>" + teste.getData() + "</data>\n" +
            		"\t<categoria>" + teste.getCategoria() + "</categoria>\n" +
            		"\t<qtsec>" + teste.getQtSec() + "</qtsec>\n" +
            		"\t<sinopse>" + teste.getSinopse() + "</sinopse>\n" +
            		"</livro>\n");
		}
		returnValue.append("</testes>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}


