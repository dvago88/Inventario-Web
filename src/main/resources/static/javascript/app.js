/*TODO: Implementar confirmacion botón borrar*/
/*TODO: Implementar onhover para el fondo del mas y el menos*/




//Esto solo funciona para la primera fila...
document.getElementById("tableRow").onmouseover = function () {
    mouseOver()
};
document.getElementById("tableRow").onmouseout = function () {
    mouseOut()
};
function mouseOver(){
    document.getElementById("mas").style.background = "#f5f5f5";
    document.getElementById("menos").style.background = "#f5f5f5";
}

function mouseOut() {
    document.getElementById("mas").style.background = "#fff";
    document.getElementById("menos").style.background = "#fff";
}


