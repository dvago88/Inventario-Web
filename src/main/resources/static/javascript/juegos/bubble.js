function Bubble(x, y, diameter) {
    this.x = x;
    this.y = y;
    if (diameter > 150 || diameter < 5) {
        this.diameter = 48;
    } else {
        this.diameter = diameter;
    }

    this.clicked = function (moux, mouy) {
        const distancia = dist(moux, mouy, this.x, this.y);
        if (distancia < this.diameter / 2) {
            return true;
        }
        return false;
    }

    this.display = function () {
        stroke(255);
        fill(255, 155);
        ellipse(this.x, this.y, this.diameter, this.diameter);
    }

    this.move = function () {
        this.x = this.x + random(-2, 2);
        this.y = this.y + random(-2, 2);
    }
}
