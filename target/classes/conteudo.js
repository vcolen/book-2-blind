window.onload = () => {
  const urlParams = new URLSearchParams(window.location.search);
  const livro = urlParams.get("id1");
  //const secao = urlParams.get("id2")
  const secao = 3;
  const usuario = 1;

  $.ajax({
    url: `http://localhost:6789/livro/${livro}`,
    type: "GET",
    dataType: "json",
  }).done(function (title) {
    createBookTitle(title);
  });
  
  // $.ajax({
  //   url: `http://localhost:6789/emleitura/${livro}/${usuario}`,
  //   type: "GET",
  //   dataType: "json",
  // }).done(function (livro, secao) {
  $.ajax({
    url: `http://localhost:6789/conteudo/${livro}/${secao}`,
    type: "GET",
    dataType: "json",
  }).done(function (sec) {
    createBookSection(sec);
  });
  // });

  
};

function createBookTitle(title) {
  $("#book-title").html(title.titulo);
}

function createBookSection(sec) {
  $("#conteudo").html(sec.sec);
}

function createNextSectionButton() {
  buttons = `<button id="voltar-secao">Seção anterior</button>
    <button id="salvar-secao">Salvar seção</button>
    <button id="proxima-secao">Próxima seção</button>`;

  $("#button-section").html(buttons);
}

document.addEventListener("DOMContentLoaded", function () {
  var phraseDiv;
  var startSpeakTextAsyncButton;

  // subscription key and region for speech services.
  var subscriptionKey, serviceRegion;
  var SpeechSDK;
  var synthesizer;
  startSpeakTextAsyncButton = document.getElementById(
    "startSpeakTextAsyncButton"
  );
  subscriptionKey = "808715e50b4b47a68003ec5b3aeaeec0";
  serviceRegion = "eastus";
  phraseDiv = document.getElementById("conteudo");

  startSpeakTextAsyncButton.addEventListener("click", function () {
    startSpeakTextAsyncButton.disabled = true;

    var speechConfig = SpeechSDK.SpeechConfig.fromSubscription(
      subscriptionKey,
      serviceRegion
    );

    speechConfig.speechSynthesisLanguage = "pt-BR";
    speechConfig.speechSynthesisVoiceName = "pt-BR-FranciscaNeural";

    synthesizer = new SpeechSDK.SpeechSynthesizer(speechConfig);

    let inputText = phraseDiv.innerText;
    synthesizer.speakTextAsync(
      inputText,
      function (result) {
        startSpeakTextAsyncButton.disabled = false;
        if (
          result.reason === SpeechSDK.ResultReason.SynthesizingAudioCompleted
        ) {
          rconsole.log("synthesis finished for [" + inputText + "].\n");
        } else if (result.reason === SpeechSDK.ResultReason.Canceled) {
          console.log(
            "synthesis failed. Error detail: " + result.errorDetails + "\n"
          );
        }
        window.console.log(result);
        synthesizer.close();
        synthesizer = undefined;
      },
      function (err) {
        startSpeakTextAsyncButton.disabled = false;
        console.log("Error: " + err);

        synthesizer.close();
        synthesizer = undefined;
      }
    );
  });

  if (!!window.SpeechSDK) {
    SpeechSDK = window.SpeechSDK;
    startSpeakTextAsyncButton.disabled = false;

    //document.getElementById("content").style.display = "block";
    //document.getElementById("warning").style.display = "none";

    // in case we have a function for getting an authorization token, call it.
    if (typeof RequestAuthorizationToken === "function") {
      RequestAuthorizationToken();
    }
  }
});
