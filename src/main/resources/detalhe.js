var book = "";
var user = undefined;
window.onload = () => {
  const urlParams = new URLSearchParams(window.location.search);
  book = urlParams.get("book");

  if (
    urlParams.get("user") != null &&
    urlParams.get("user") != undefined &&
    urlParams.get("user") != 0
  ) {
    user = urlParams.get("user");
  }
  $.ajax({
    url: `http://localhost:6789/livro/${book}`,
    type: "GET",
    dataType: "json",
  }).done(function (data) {
    createBookInDetalhe(data);
  });
};

function createBookInDetalhe(data) {
  book = `      <img src="${data.capa}" alt="" id="book-img-detail" />
                <div id="detailed-description">
                    <h2>${data.titulo}</h2>
                        <p id="description">Escrito por ${data.autor}.</p>
                        <p id="description">Ano de publicação: ${data.data} </p> 
                        <p id="description">Categoria: ${data.categoria}</p>
                        <p id="description">Avaliação: ${data.avaliacao}.</p>
                </div>
                <button id="ler" onclick="window.location.href='/conteudo.html?id1=${data.id}&id2=${user}'"><i class="fas fa-book-open"></i> Ler</button>`;

  description = `<p>
                ${data.sinopse}
              </p>`;
  console.log(data);
  $("#book-summary").html(book);
  $("#sinopse").html(description);
}
