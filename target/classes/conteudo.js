var book = "";
var user = undefined;
var secao = "";

window.onload = () => {
  const urlParams = new URLSearchParams(window.location.search);
  if (urlParams.get("id2") == "undefined" || urlParams.get("id2") == null) {
    alert("Por favor, faça login para ler um livro.");
    window.location.replace("http://localhost:6789/catalogo.html");
  } else {
    user = urlParams.get("id2");
    setNavbar();

    book = urlParams.get("id1");

  }

  $.ajax({
    url: `http://localhost:6789/emleitura/${book}/${user}`,
    type: "GET",
    dataType: "json",
  }).done(function (data) {
    secao = data.secAtual;
    $("#startSpeakTextAsyncButton").html(`<i class="fas fa-play"></i> Reproduzir Seção ${secao}`)
    $.ajax({
      url: `http://localhost:6789/conteudo/${book}/${data.secAtual}`,
      type: "GET",
      dataType: "json",
    }).done(function (data) {
      createBookSection(data);
      createNextSectionButton();
    });
  });

  $.ajax({
    url: `http://localhost:6789/livro/${book}`,
    type: "GET",
    dataType: "json",
  }).done(function (data) {
    createBookTitle(data);
  });
};

function setNavbar() {
  navbar = `<a href="http://localhost:6789/index.html?id=${user}" id="nav-item"> <i class="fas fa-home"></i> Home</a>
      <p>|</p>
      <a href="http://localhost:6789/catalogo.html?id=${user}" id="nav-item"
        ><i class="fas fa-book"></i> Livros</a
      >
      <p>|</p>
      <a href="http://localhost:6789/sobre.html?id=${user}" id="nav-item"
        ><i class="far fa-address-card"></i> Sobre</a
      >`;

  $("#navbar").html(navbar);
}

function createBookTitle(data) {
  $("#book-title").html(data.titulo);
}

function createBookSection(sec) {
  $("#conteudo").html(sec.sec);
}

function createNextSectionButton() {
  if (secao == 1) {
    buttons = `<button id="voltar-secao" disabled><i class="fas fa-angle-double-left"></i> <br> Seção anterior</button>
    <button id="proxima-secao" onclick="nextSection()"><i class="fas fa-angle-double-right"></i><br>Próxima seção</button>`;
  } else if (secao == 100) {
    buttons = `<button id="voltar-secao" onclick="previousSection()"><i class="fas fa-angle-double-left"></i> <br> Seção anterior</button>
    <button id="proxima-secao" disabled><i class="fas fa-angle-double-right"></i><br>Próxima seção</button>`;
  } else {
    buttons = `<button id="voltar-secao" onclick="previousSection()"><i class="fas fa-angle-double-left"></i> <br> Seção anterior</button>
    <button id="proxima-secao" onclick="nextSection()"><i class="fas fa-angle-double-right"></i><br>Próxima seção</button>`;
  }

  $("#button-section").html(buttons);
}

function nextSection() {
  $.ajax({
    url: `http://localhost:6789/emleitura/update/${book}/${user}`,
    type: "PUT",
    dataType: "json",
    data: {
      secAtual: secao + 1
    }
  }).done(function(data) {
    console.log(data);
    window.location.replace(`http://localhost:6789/conteudo.html?id1=${book}&id2=${user}`)
  }).fail(function(error) {
    console.error(error);
  });
}

function previousSection() {
  $.ajax({
    url: `http://localhost:6789/emleitura/update/${book}/${user}`,
    type: "PUT",
    dataType: "json",
    data: {
      secAtual: secao - 1
    }
  }).done(function(data) {
    console.log(data);
    window.location.replace(`http://localhost:6789/conteudo.html?id1=${book}&id2=${user}`)
  }).fail(function(error) {
    console.error(error);
  });
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
        window.console.log("Error: " + err);

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
