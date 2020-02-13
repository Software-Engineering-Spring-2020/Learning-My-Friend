package backend.objects;
import backend.Shape;
import processing.core.PApplet;
import processing.core.PConstants;

class Ellipse extends Shape {
  Ellipse(PApplet sketch, float x, float y, float w, float h, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    this.pixelWidth = w;
    this.pixelHeight = h;
    shape = sketch.createShape(PConstants.ELLIPSE, this.xpos, this.ypos, this.pixelWidth, this.pixelHeight);
    setSettings();
  }
  Ellipse(PApplet sketch, float x, float y, float d, float strokeWeight,  int[] fillColor, int[] boarderColor){
    this(sketch, x, y, d, d, strokeWeight, fillColor, boarderColor);
  }
  Ellipse(PApplet sketch, float x, float y, float strokeWeight,  int[] fillColor, int[] boarderColor){
    this(sketch, x, y, 50, 25, strokeWeight, fillColor, boarderColor);
  }
}