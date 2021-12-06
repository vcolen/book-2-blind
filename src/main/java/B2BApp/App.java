package B2BApp;

import static spark.Spark.*;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import B2BService.LivroService;
import B2BService.UsuarioService;
import B2BService.EmLeituraService;
import B2BService.ConteudoService;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;

public class App {
    private static UsuarioService userS = new UsuarioService();
    private static LivroService livroS = new LivroService();
    private static EmLeituraService readingS = new EmLeituraService();
    private static ConteudoService conteudoS = new ConteudoService();

    public static void main(String[] args) {
        port(6789);

        staticFiles.location("/");

        post("/usuario", (request, response) -> userS.add(request, response)); // Insere Usuario
        get("/usuario/:id", (request, response) -> userS.get(request, response)); // Detalha um Usuario
        get("/usuario/login/:id1/:id2", (request, response) -> userS.login(request, response)); // Realiza o login de um
                                                                                                // Usuario
        get("/usuario/update/:id", (request, response) -> userS.update(request, response)); // Atualiza um Usuario
        get("/usuario/delete/:id", (request, response) -> userS.remove(request, response)); // Remove um Usuario
        get("/usuario", (request, response) -> userS.getAll(request, response)); // Detalha todos os Usuarios

        post("/livro", (request, response) -> livroS.add(request, response)); // Insere um Livro
        get("/livro/:id", (request, response) -> livroS.get(request, response)); // Detalha um Livro
        get("/livro/update/:id", (request, response) -> livroS.update(request, response)); // Atualiza um Livro
        get("/livro/delete/:id", (request, response) -> livroS.remove(request, response)); // Remove um Livro
        get("/livro", (request, response) -> livroS.getAll(request, response)); // Detalha todos os Livro

        post("/emleitura", (request, response) -> readingS.add(request, response)); // Insere um EmLeitura
        get("/emleitura/:id1/:id2", (request, response) -> readingS.get(request, response)); // Detalha um EmLeitura
        put("/emleitura/update/:id1/:id2", (request, response) -> readingS.update(request, response)); // Atualiza um
                                                                                                       // EmLeitura
        get("/emleitura/delete/:id1/:id2", (request, response) -> readingS.remove(request, response)); // Remove um
                                                                                                       // EmLeitura
        get("/emleitura", (request, response) -> readingS.getAll(request, response)); // Detalha todos os EmLeitura

        post("/conteudo", (request, response) -> conteudoS.add(request, response)); // Insere um Conteudo
        get("/conteudo/:id1/:id2", (request, response) -> conteudoS.get(request, response)); // Detalha um Conteudo
        get("/conteudo/update/:id1/:id2", (request, response) -> conteudoS.update(request, response)); // Atualiza um
                                                                                                       // Conteudo
        get("/conteudo/delete/:id1/:id2", (request, response) -> conteudoS.remove(request, response)); // Remove um
                                                                                                       // Conteudo
        get("/conteudo", (request, response) -> conteudoS.getAll(request, response)); // Detalha todos os Conteudos
        get("/conteudo-getfrom/:id", (request, response) -> conteudoS.getAllFrom(request, response)); // Detalha todos
                                                                                                      // os Conteudos de
                                                                                                      // um livro

        SpeechConfig speechConfig = SpeechConfig.fromSubscription("808715e50b4b47a68003ec5b3aeaeec0", "eastus");
        speechConfig.setSpeechRecognitionLanguage("pt-BR");
        try {
            fromMic(speechConfig);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void apresentar(SpeechConfig speechConfig) {
        speechConfig.setSpeechSynthesisLanguage("pt-BR");

        speechConfig.setSpeechSynthesisVoiceName("pt-BR-FranciscaNeural");
        AudioConfig audioConfig = AudioConfig.fromDefaultSpeakerOutput();

        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
        synthesizer.SpeakText(
                "Ei gente, eu sou a Alice, muito prazer. Estarei ao seu dispor para o que precisar. Válido ressaltar, que devido à indisponibilidade de usar riéquiti e noude, eu tenho uma certa limitação nos meus serviços. Estou aqui para tirar suas dúvidas à respeito da plataforma. Caso queira fazer uma pergunta, basta me chamar antes.");
    }

    public static void comoUsarAPlataforma(SpeechConfig speechConfig) {
        speechConfig.setSpeechSynthesisLanguage("pt-BR");

        speechConfig.setSpeechSynthesisVoiceName("pt-BR-FranciscaNeural");
        AudioConfig audioConfig = AudioConfig.fromDefaultSpeakerOutput();

        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
        synthesizer.SpeakText(
                "O maior foco da B2B é democratizar o acesso aos livros. Os que temos disponíveis estão na seção catálogo. Pode ir lá dar uma conferida. Necessário saber que caso deseje ler um livro, precisa fazer o login. Caso deseje saber sobre o time da B2B, por favor diga: Quem está por trás de tudo isso?");
    }

    public static void quaisOsServicos(SpeechConfig speechConfig) {
        speechConfig.setSpeechSynthesisLanguage("pt-BR");

        speechConfig.setSpeechSynthesisVoiceName("pt-BR-FranciscaNeural");
        AudioConfig audioConfig = AudioConfig.fromDefaultSpeakerOutput();

        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
        synthesizer.SpeakText(
                "O maior foco da B2B é democratizar o acesso aos livros. Os que temos disponíveis estão na seção catálogo. Pode ir lá dar uma conferida. Necessário saber que caso deseje ler um livro, precisa fazer o login.");
    }

    public static void responder(SpeechConfig speechConfig) {
        speechConfig.setSpeechSynthesisLanguage("pt-BR");

        speechConfig.setSpeechSynthesisVoiceName("pt-BR-FranciscaNeural");
        AudioConfig audioConfig = AudioConfig.fromDefaultSpeakerOutput();

        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
        synthesizer.SpeakText(
                "Olá, o que deseja?");
    }

    public static void criadores(SpeechConfig speechConfig) {
        speechConfig.setSpeechSynthesisLanguage("pt-BR");

        speechConfig.setSpeechSynthesisVoiceName("pt-BR-FranciscaNeural");
        AudioConfig audioConfig = AudioConfig.fromDefaultSpeakerOutput();

        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
        synthesizer.SpeakText(
                "Lucas, Priscila e Victor são as pessoas por trás da B2B. Eles são alunos do curso de Ciências da Computação na púqui minas e estão no Segundo período.");
    }

    public static void fromMic(SpeechConfig speechConfig) throws InterruptedException, ExecutionException {
        AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);
        String text;

        while (true) {
            System.out.println("Speak into your microphone.");
            Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
            SpeechRecognitionResult result = task.get();
            text = result.getText();
            System.out.println("RECOGNIZED: Text=" + text);

            if (text.compareToIgnoreCase("se apresente.") == 0) {
                apresentar(speechConfig);
            } else if (text.compareToIgnoreCase("como usar a plataforma?") == 0
                    || text.compareToIgnoreCase("como usar a plataforma.") == 0) {
                comoUsarAPlataforma(speechConfig);
            } else if (text.compareToIgnoreCase("quais seus serviços?") == 0
                    || text.compareToIgnoreCase("quais seus serviços.") == 0
                    || text.compareToIgnoreCase("quais os serviços?") == 0
                    || text.compareToIgnoreCase("quais os serviços.") == 0) {
                quaisOsServicos(speechConfig);
            } else if (text.compareToIgnoreCase("alice.") == 0 || text.compareToIgnoreCase("alice?") == 0) {
                responder(speechConfig);
            } else if (text.compareToIgnoreCase("fale sobre os criadores.") == 0) {
                criadores(speechConfig);
            }
        }
    }
}
