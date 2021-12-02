var user = "";

function checkUserData() {
  let email = document.getElementById("id").value;
  let senha = document.getElementById("id2").value;

  $.ajax({
    url: `http://localhost:6789/usuario/login/${email}/${senha}`,
    type: "GET",
    dataType: "json",
  }).done(function (data) {
    user = data.id;
    alert("Login realizado com sucesso. Você será redirecionado para o nosso catálogo.")
    window.location.replace(`http://localhost:6789/catalogo.html?id=${user}`)
  })
}

document.getElementById("submit").addEventListener("click", function (event) {
  event.preventDefault();
});