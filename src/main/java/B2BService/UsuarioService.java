package B2BService;

import spark.Request;
import spark.Response;
import B2BDao.UsuarioDAO;
import B2BModel.Usuario;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UsuarioService {
	private UsuarioDAO testeDAO;

	public UsuarioService() {
		testeDAO = new UsuarioDAO();
		testeDAO.conect();
	}

	public String hash(String base) throws Exception {
		MessageDigest m = MessageDigest.getInstance("MD5");
	    m.update(base.getBytes(), 0, base.length());
	    String novo = new BigInteger(1, m.digest()).toString(16);
		return novo;
	}

	public Object add(Request request, Response response) throws Exception {
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");

		if (testeDAO.preventSame(email) != null) {
			response.status(404); // 404 Not found
			return "Email " + email + " invalido.";
		} else {
			senha = hash(senha);
			int id = testeDAO.getMaxId() + 1;

			Usuario user = new Usuario(id, email, senha);
			testeDAO.addUser(user);

			response.status(201); // 201 Created
			response.redirect("/catalogo.html");
			return "Sim";
		}
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		Usuario teste = (Usuario) testeDAO.getUser(id);

		if (teste != null) {
			response.header("Content-Type", "application/xml");
			response.header("Content-Encoding", "UTF-8");

			// return teste.toString();

			return "<usuario>\n" + "\t<id>" + teste.getId() + "</id>\n" + "\t<nome>" + teste.getNome() + "</nome>\n"
					+ "\t<senha>" + teste.getSenha() + "</senha>\n" + "</usuario>\n";

		} else {
			response.status(404); // 404 Not found
			return "Usuario " + id + " n�o encontrado.";
		}
	}

	public Object update(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		Usuario teste = (Usuario) testeDAO.getUser(id);

		if (teste != null) {
			teste.setNome(request.queryParams("nome"));
			teste.setSenha(request.queryParams("paginas"));

			testeDAO.updateUser(teste);

			return id;
		} else {
			response.status(404); // 404 Not found
			return "Usuario n�o encontrado.";
		}
	}

	public Object login(Request request, Response response) throws Exception{
		String nome = request.params(":id1");
		String senha = hash(request.params(":id2"));

		Usuario teste = (Usuario) testeDAO.getLogin(nome, senha);

		if (teste != null) {

			response.header("Content-Type", "application/json");
			response.header("Content-Encoding", "UTF-8");

			Map<String, Object> user = new HashMap<>();

			user.put("id", teste.getId());
			user.put("titulo", teste.getNome());
			user.put("senha", teste.getSenha());

			response.status(200);

			return new JSONObject(user);
		} else {
			response.status(404); // 404 Not found
			return "Credenciais incorretas";
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
			return "Usuario nao encontrado.";
		}
	}

	public Object getAll(Request request, Response response) {
		JSONArray usuarios = new JSONArray();

		for (Usuario teste : testeDAO.getAllUser()) {
			Map<String, Object> usuario = new HashMap<>();
			usuario.put("id", teste.getId());
			usuario.put("nome", teste.getNome());
			usuario.put("senha", teste.getSenha());
			usuarios.add(usuario);
		}

		response.header("Content-Type", "application/json");
		response.header("Content-Encoding", "UTF-8");
		System.out.println(usuarios);

		return usuarios;
	}

}
