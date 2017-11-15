//Si el tex field estaba vacio mandaba un string "" y hacía que spring generara un mensaje de exeption muy feo
//esta función la creé por ahora para evitar esto, hay que hacer algo mejor directamente con spring.
function validatorOfNumbers() {
    let text = document.querySelector("input[placeholder$='salida']").value;
    let text2 = document.querySelector("input[placeholder$='entrada']").value;
    let tex3 = document.querySelector("input[placeholder$='Cantidad']").value;

    if (text === "") {
        document.querySelector("input[placeholder$='salida']").value = 0;
    }
    if (text2 === "") {
        document.querySelector("input[placeholder$='entrada']").value = 0;
    }

    if (tex3 === "") {
        document.querySelector("input[placeholder$='Cantidad']").value = 0;
    }
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

//elimina los ceros iniciales que sabra el putas porqué tenía el formulario.
$(".ceros").val("");

//Hace que la primera letra del proveedor y el producto este capitalizada
$("#agregarUno").click(function () {
    nombreProducto = $("input[placeholder$='producto']").val();
    nombreProveedor = $("input[placeholder^='Proveedor']").val();
    if (nombreProducto.length > 0) {
        nombreProducto = capitalizeFirstLetter(nombreProducto);
        $("input[placeholder$='producto']").val(nombreProducto);
    }
    if (nombreProveedor.length > 0) {
        nombreProveedor = capitalizeFirstLetter(nombreProveedor);
        $("input[placeholder^='Proveedor']").val(nombreProveedor);
    }
});


//TODO: Crear grafíca interactiva en la página principal
//Usar un click event y tomar los datos pasados por java de la base de datos para forma la grafica en tiempo real





