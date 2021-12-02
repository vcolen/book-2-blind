var user = undefined;

window.onload = () => {
  const urlParams = new URLSearchParams(window.location.search);

  if (urlParams.get("id") != null && urlParams.get("id") != undefined) {
    user = urlParams.get("id");
    setNavbar();
  }
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
