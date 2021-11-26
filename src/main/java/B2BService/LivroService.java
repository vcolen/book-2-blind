package B2BService;

import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
		boolean status = testeDAO.addLivro(teste);

		if (status) {
			response.status(201);
			return id;
		} else {
			return "false";
		}
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		Livro teste = (Livro) testeDAO.selecionarLivro(id);

		if (teste != null) {
			response.header("Content-Type", "application/xml");
			response.header("Content-Encoding", "UTF-8");

			return "<livro>\n" + "\t<id>" + teste.getId() + "</id>\n" + "\t<titulo>" + teste.getTitulo() + "</titulo>\n"
					+ "\t<autor>" + teste.getAutor() + "</autor>\n" + "\t<data>" + teste.getData() + "</data>\n"
					+ "\t<categoria>" + teste.getCategoria() + "</categoria>\n" + "\t<qtsec>" + teste.getQtSec()
					+ "</qtsec>\n" + "\t<sinopse>" + teste.getSinopse() + "</sinopse>\n" + "</livro>\n";

			// return teste.toString();
		} else {
			response.status(404); // 404 Not found
			return "Livro " + id + " n�o encontrado.";
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
			return "Livro n�o encontrado.";
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
			return "Livro n�o encontrado.";
		}

	}

	public Object getAll(Request request, Response response) {
		JSONArray livros = new JSONArray();
		// JSONObject livro = new JSONObject();

		for (Livro teste : testeDAO.getAll()) {
			Map<String, Object> livro = new HashMap<>();
			livro.put("id", teste.getId());
			livro.put("titulo", teste.getTitulo());
			livro.put("autor", teste.getAutor());
			livro.put("categoria", teste.getCategoria());
			livro.put("qtsec", teste.getQtSec());
			livro.put("data", teste.getData());
			livro.put("sinopse", teste.getSinopse());
			livros.add(livro);

		}

		response.header("Content-Type", "application/json");
		response.header("Content-Encoding", "UTF-8");
		System.out.println(livros);

		return livros;
	}
}
