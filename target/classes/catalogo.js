window.onload = () =>{
    $.ajax({
        url: `${window.location.protocol}//${window.location.host}/catalogo`,
        type: "GET",
        dataType: "json",
    })
        .done(function (data) {
            console.log(data);
            createCard(data);
        });
}

function createCard(data){
    cards = "";
    data.templates.forEach((e) => {
        cards += `<div id="book">
        <h2>${e.title}</h2>
        <img src="${e.imgURL}" alt="" />
        <p id="description">
          ${e.description}
        </p>
      </div>`
        console.log(e)
    });
    $('#selectVibes').html(cards);
}