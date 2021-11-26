window.onload = () =>{
    $.ajax({
        url: "http://localhost:6789/livro",
        type: "GET",
        dataType: "json",
    })
        .done(function (data) {
            console.log(data)
            createCard(data)
        });
}

function createCard(data){
    cards = ""
    data.forEach((book) => {
        cards += `<div id="book">
                    <h2>${book.titulo}</h2>
                    <img src="${book.imgUrl}" alt="" />
                    <p id="description">
                    ${book.sinopse}
                     </p>
                </div>`
        console.log(book)
    });
    $('#all-books').html(cards)
}