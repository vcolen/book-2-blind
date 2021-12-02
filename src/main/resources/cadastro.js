var user = undefined;
var email = "";
var senha = "";

function checkUserData() {
  email = document.getElementById("id").value;
  senha = document.getElementById("id2").value;

  $.ajax({
    url: `http://localhost:6789/usuario`,
    type: "GET",
    dataType: "json",
  }).done(function (data) {
      if(checkAllUsers(data) == true) {
        $.ajax({
            url: `http://localhost:6789/usuario`,
            type: "POST",
            data: {
              email: email,
              senha: senha
            }
          }).done(function (data) {
            alert("Cadastro realizado com sucesso. Você será redirecionado para o nosso catálogo.")
            window.location.replace(`http://localhost:6789/catalogo.html?id=${user}`)
          }).fail(function (error) {
            alert("Erro: ", error);
          });
       }
  });
}

function checkAllUsers(data) {
  data.forEach((usuario) => {
    if (email == usuario.nome) {
        alert("Este email já está sendo utilizado.")
        return false
    }
  });
  return true
}

document.getElementById("submit").addEventListener("click", function (event) {
  event.preventDefault();
});
