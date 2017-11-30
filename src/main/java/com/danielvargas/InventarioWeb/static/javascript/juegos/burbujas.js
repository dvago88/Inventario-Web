let bubbles = [];
let x = 0;
let y = 0;
let w = 1200;
let h = 600;
let spacing = parseInt(prompt("Introduce el valor deseado:\nSe recomiendan valores entre 5 y 150"));
if (isNaN(spacing)) {
    spacing = 20;
}
let enJuego = true;
let noGanaste = true;

function setup() {
    createCanvas(w, h);
    background(55);

    for (let i = 0; i < 10; i++) {
        let randx = random(width / 2, width);
        let randy = random(height);
        bubbles.push(new Bubble(randx, randy, spacing));
    }
}

function draw() {
    if (enJuego && noGanaste) {
        tenPrint();
        fill(55);
        rect(width / 2, 0, width / 2, height);
        for (let i = 0; i < bubbles.length; i++) {
            bubbles[i].move();
            bubbles[i].display();
        }
    } else {
    }
    if (bubbles.length > 60) {
        enJuego = false;
        background(0);
    }
    if (bubbles.length === 0) {
        noGanaste = false;
        background(255)
    }
}

function mousePressed() {
    for (let i = 0; i < bubbles.length; i++) {
        const muerto = bubbles[i].clicked(mouseX, mouseY);
        if (muerto) {
            bubbles.splice(i, 1);
        }
    }
}

function tenPrint() {
    stroke(255);

    if (random() < 0.5) {
        line(x, y, x + spacing, y + spacing);
    } else {
        line(x, y + spacing, x + spacing, y);
    }

    if (random() < 0.5) {
        line(-x + width / 2, -y + height, -x + width / 2 - spacing, -y + height - spacing);
    } else {
        line(-x + width / 2, -y + height - spacing, -x + width / 2 - spacing, -y + height);
    }

    x += spacing;
    if (x > width / 2) {
        y += spacing;
        x = 0;
    }
    if (y > height) {
        setup();
        y = 0;
        x = 0;
    }
}

document.getElementById("replay").onclick = () => {
    enJuego = true;
    noGanaste = true;
    bubbles = [];
    x = 0;
    y = 0;
    spacing = parseInt(prompt("Introduce el valor deseado:\nSe recomiendan valores entre 5 y 150"));
    if (isNaN(spacing)) {
        spacing = 20;
    }
    setup();
};
