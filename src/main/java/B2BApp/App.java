package B2BApp;
import static spark.Spark.*;

import B2BService.LivroService;
import B2BService.UsuarioService;
import B2BService.EmLeituraService;
import B2BService.ConteudoService;

public class App {
	private static UsuarioService userS = new UsuarioService();
	private static LivroService livroS = new LivroService();
	private static EmLeituraService readingS = new EmLeituraService();
	private static ConteudoService conteudoS = new ConteudoService();
	
	public static void main(String[] args) {
		port(6789);

        staticFiles.location("/");
		
		post("/usuario", (request, response) -> userS.add(request, response)); //Insere Usuario
        get("/usuario/:id", (request, response) -> userS.get(request, response)); //Detalha um Usuario
        get("/usuario/login/:id1/:id2", (request, response) -> userS.login(request, response)); //Realiza o login de um Usuario
        get("/usuario/update/:id", (request, response) -> userS.update(request, response)); //Atualiza um Usuario
        get("/usuario/delete/:id", (request, response) -> userS.remove(request, response)); //Remove um Usuario
        get("/usuario", (request, response) -> userS.getAll(request, response)); //Detalha todos os Usuarios
        
        
        post("/livro", (request, response) -> livroS.add(request, response)); //Insere um Livro
        get("/livro/:id", (request, response) -> livroS.get(request, response)); //Detalha um Livro
        get("/livro/update/:id", (request, response) -> livroS.update(request, response)); //Atualiza um Livro
        get("/livro/delete/:id", (request, response) -> livroS.remove(request, response)); //Remove um Livro
        get("/livro", (request, response) -> livroS.getAll(request, response)); //Detalha todos os Livro
        
        
        post("/emleitura", (request, response) -> readingS.add(request, response)); //Insere um EmLeitura
        get("/emleitura/:id1/:id2", (request, response) -> readingS.get(request, response)); //Detalha um EmLeitura
        put("/emleitura/update/:id1/:id2", (request, response) -> readingS.update(request, response)); //Atualiza um EmLeitura
        get("/emleitura/delete/:id1/:id2", (request, response) -> readingS.remove(request, response)); //Remove um EmLeitura
        get("/emleitura", (request, response) -> readingS.getAll(request, response)); //Detalha todos os EmLeitura
        
        
        post("/conteudo", (request, response) -> conteudoS.add(request, response)); //Insere um Conteudo
        get("/conteudo/:id1/:id2", (request, response) -> conteudoS.get(request, response)); //Detalha um Conteudo
        get("/conteudo/update/:id1/:id2", (request, response) -> conteudoS.update(request, response)); //Atualiza um Conteudo
        get("/conteudo/delete/:id1/:id2", (request, response) -> conteudoS.remove(request, response)); //Remove um Conteudo
        get("/conteudo", (request, response) -> conteudoS.getAll(request, response)); //Detalha todos os Conteudos
        get("/conteudo-getfrom/:id", (request, response) -> conteudoS.getAllFrom(request, response)); //Detalha todos os Conteudos de um livro
	}
}