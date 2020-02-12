package backend.shapes;
import backend.ColorfulObject;
import processing.core.PApplet;

class Ellipse extends ColorfulObject {
  float pixelWidth, pixelHeight;
  Ellipse(PApplet sketch, float x, float y, float w, float h, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, fillColor, boarderColor);
    this.pixelWidth = w;
    this.pixelHeight = h;
    shape = sketch.createShape(ELLIPSE, this.xpos, this.ypos, this.pixelWidth, this.pixelHeight);
    setColor();
  }
  Ellipse(PApplet sketch, float x, float y, float d, int[] fillColor, int[] boarderColor){
    this(sketch, x, y, d, d, fillColor, boarderColor);
  }
  Ellipse(PApplet sketch, float x, float y, int[] fillColor, int[] boarderColor){
    this(sketch, x, y, 50, 25, fillColor, boarderColor);
  }
}
