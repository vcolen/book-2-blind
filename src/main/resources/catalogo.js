window.onload = () => {
  $.ajax({
    url: "http://localhost:6789/livro",
    type: "GET",
    dataType: "json",
  }).done(function (data) {
    createBookInCatalog(data);
  });
};

function createBookInCatalog(data) {
  books = "";
  data.forEach((book) => {
    books += `<div id="${book.id}" class="book">
                    <h2>${book.titulo}</h2>
                    <img src="${book.capa}" alt="" />
                    <p id="description">
                    ${book.sinopse}
                     </p>
                     <button id="ler" onclick="window.location.href='/detalhe.html?id=${book.id}'"><i class="fas fa-book-open"></i> Ler</button>
                </div>`;
  });
  $("#all-books").html(books);
}
