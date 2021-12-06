var user = undefined;
var email = "";
var senha = "";

function checkUserData() {
  email = document.getElementById("id").value;
  senha = document.getElementById("id2").value;

  $.ajax({
    url: `http://localhost:6789/usuario`,
    type: "POST",
    data: {
      email: email,
      senha: senha,
    },
  })
    .done(function (data) {
      $.ajax({
        url: `http://localhost:6789/usuario`,
        type: "GET",
        dataType: "json",
      }).done(function (data) {
        getUserId(data);
        alert(
          "Cadastro realizado com sucesso. Você será redirecionado para o nosso catálogo."
        );
        window.location.replace(
          `http://localhost:6789/catalogo.html?id=${user}`
        );
      });
    })
    .fail(function (error) {
      alert(error.responseText);
    });
}

document.getElementById("submit").addEventListener("click", function (event) {
  event.preventDefault();
});

function getUserId(data) {
  data.forEach((usuario) => {
    if (usuario.nome == email) {
      user = usuario.id;      
    }
  });
}


1
// (?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])