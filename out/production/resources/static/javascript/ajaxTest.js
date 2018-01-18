//TODO: tiene que haber una forma mas bonita de selccionar estos elementos...
//(Ademas esta selección no te permite mover los elementos...)

function masmenos(elem) {
    let token = $(elem).parent().children("input").attr("value");
    let header = $(elem).parent().children("input").attr("name");
    let url = $(elem).parent().parent().attr("action");
    let headers = {};
    let $inputField = $(elem).parent().children("div").children("input");
    let howMany = $inputField.val();
    if (howMany === "") {
        howMany = "1";
        //    Si es 0 no vale la pena mandar el request al servidor
    } else if (howMany === "0") {
        $inputField.val("");
        return;
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
        success: function (data, textStatus, jqXRH) {
            let resp = jqXRH.getResponseHeader("queFue");
            //Este if maneja los mensajes de exito o fallo
            if (jqXRH.getResponseHeader("queFue") === "success") {
                console.log("success!!");
                $("#status").show();
                const $alertDiv = $("div>div[role='alert']");
                $alertDiv.text("Producto exitosamente actualizado");
                $alertDiv.removeClass("alert-danger").addClass("alert-success");
            } else if (resp === "fail") {
                const $stat = $("#status");
                const $alertDiv = $stat.children().first();
                $stat.show();
                $alertDiv.text("No puede haber menos de 0 productos, si deseas eliminar el producto has click en Borrar");
                $alertDiv.removeClass("alert-success").addClass("alert-danger");
            }
            let cantidad = data.cantidad;
            let cantidadVendido = data.cantidadVendido;
            let test2 = $(elem).parent().parent().parent().parent().parent().parent().children("td[name='cantidad']");
            let test3 = $(elem).parent().parent().parent().parent().parent().parent().children("td[name='cantidadVendido']");
            $(test2).text(cantidad);
            $(test3).text(cantidadVendido);
            $inputField.val("");
        }
    }).fail(function (jqXHR) {
        console.log(jqXHR);
    });
}

function borrar(elem) {


}

function editarProveedor(elem) {
    let $firstPart = $(elem).parent();
    let token = $firstPart.siblings("input").attr("value");
    let header = $firstPart.siblings("input").attr("name");
    console.log(token);
    console.log(header);
    let url = $firstPart.parent().attr("action");
    console.log(url);
    let headers = {};
    headers[header] = token;
    let data = {
        // "id":$("#id"),
        nombre: $("#nombre").val(),
        telefono: $("#telefono").val(),
        direccion: $("#direccion").val(),
        descripcion: $("#descripcion").val()
    };
    console.log(data["nombre"]);
    console.log(data["telefono"]);
    console.log(data["direccion"]);
    console.log(data["descripcion"]);

    $.ajax(url, {
        type: "POST",
        headers: headers,
        contentType: "application/json",
        dataType: "json",
        // data: data,
        data: JSON.stringify(data),
        success: function (datas, textStatus, jqXRH) {
            $("#labelNombre").text(datas.nombreP);
            $("#labelDescripcion").text(datas.descripcionP);
            $("#labelTelefono").text(datas.telefono);
            $("#labelDireccion").text(datas.direccion);
            $("#status").show();
            const $alertDiv = $("div>div[role='alert']");
            $alertDiv.text("Proveedor exitosamente actualizado");
            $alertDiv.removeClass("alert-danger").addClass("alert-success");
            console.log(textStatus)
        }
    }).fail(function (jqXHR) {
        console.log("esta imprimiendo esta mierda");
        console.log(jqXHR);
        const $stat = $("#status");
        const $alertDiv = $stat.children().first();
        $stat.show();
        $alertDiv.text("Algo salió terriblementa mal, lamentamos las molestias ocacionadas");
        $alertDiv.removeClass("alert-success").addClass("alert-danger");
    });

}


