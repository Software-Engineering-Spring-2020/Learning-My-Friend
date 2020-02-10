package backend.Shapes;
import backend.ColorfulObject;
import processing.core.PApplet;

class Rectangle extends ColorfulObject {
    float pixelWidth, pixelHeight;
    Rectangle(PApplet sketch, float x, float y, float w, float h, int[] fillColor, int[] boarderColor){
        super(sketch, x, y, fillColor, boarderColor);
        this.pixelWidth = w;
        this.pixelHeight = h;
    }
    Rectangle(PApplet sketch, float x, float y, float d, int[] fillColor, int[] boarderColor){
        this(sketch, x, y, d, d, fillColor, boarderColor);
    }
    Rectangle(PApplet sketch, float x, float y, int[] fillColor, int[] boarderColor){
        this(sketch, x, y, 50, 25, fillColor, boarderColor);
    }

    protected void display(){
        super.display();
        sketch.rect(xpos, ypos, pixelWidth, pixelHeight);
    }
}