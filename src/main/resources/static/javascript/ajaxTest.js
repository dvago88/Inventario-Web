//TODO: tiene que haber una forma mas bonita de selccionar estos elementos...
//(Ademas esta selección no te permite mover los elementos...)

function myFunction(elem) {
    let token = $(elem).parent().children("input").attr("value");
    let header = $(elem).parent().children("input").attr("name");
    let url = $(elem).parent().parent().attr("action");
    let headers = {};
    let $inputField = $(elem).parent().children("div").children("input");
    let howMany = $inputField.val();
    if (howMany === "") {
        howMany = "1";
    }
    let data = {
        howMany: howMany,
        moreOrLess: $(elem).attr("value")
    };
    headers[header] = token;
    $.ajax(url, {
        type: "POST",
        headers: headers,
        data: data,
        success: function (response) {
            let cantidad = response.cantidad;
            let nombre = response.nombre;
            let test2 = $(elem).parent().parent().parent().parent().parent().parent().children("td[name='cantidad']");
            $(test2).text(cantidad);
            $inputField.val("");
        }
        ,
    })
    ;
}


