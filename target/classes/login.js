function checkUserData() {
    let email = document.getElementById('id').value
    let senha = document.getElementById('id2').value

  $.ajax({
    url: `http://localhost:6789/usuario/login/${email}/${senha}`,
    type: "GET",
    dataType: "json",
  }).error(function () {
      console.log("deu ruim")
  });
}