
//Si el tex field estaba vacio mandaba un string "" y hacía que spring generara un mensaje de exeption muy feo
//esta función la creé por ahora para evitar esto, hay que hacer algo mejor directamente con spring.
function validatorOfNumbers() {
    let text=document.querySelector("input[placeholder$='salida']").value;
    let text2=document.querySelector("input[placeholder$='entrada']").value;

    if(text===""){
        document.querySelector("input[placeholder$='salida']").value=0;
    }
    if (text2 === "") {
        document.querySelector("input[placeholder$='entrada']").value = 0;
    }
}

//TODO: Escribir metodo que capitalice siempre el texto que entra del formulario




