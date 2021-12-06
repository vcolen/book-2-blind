var user;

window.onload = () => {
  $.ajax({
    url: "http://localhost:6789/livro",
    type: "GET",
    dataType: "json",
  }).done(function (data) {
    createBookInCatalog(data);
  });
  const urlParams = new URLSearchParams(window.location.search);
  if (urlParams.get("id") != "undefined" && urlParams.get("id") != null) {
    user = urlParams.get("id");
    setNavbar();
  }
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
                     <button id="ler" onclick="window.location.href='/detalhe.html?book=${book.id}&user=${user}'"><i class="fas fa-book-open"></i> Ler</button>
                </div>`;
  });
  $("#all-books").html(books);
}

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
