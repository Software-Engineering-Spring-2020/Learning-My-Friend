package backend.Shapes;
import backend.ColorfulObject;
import processing.core.PApplet;

class Ellipse extends ColorfulObject {
  float w = 10, h = 10;
  Ellipse(PApplet sketch, float x, float y, float w, float h){
    super(sketch, x, y);
    this.w = w;
    this.h = h;
  }
  Ellipse(PApplet sketch, float x, float y, float d){
    super(sketch, x, y);
    this.w = d;
    this.h = d;
  }
  Ellipse(PApplet sketch, float x, float y){
    super(sketch, x, y);
  }
  
  protected void display(){
    super.display();
    sketch.ellipse(xpos, ypos, w, h);
  }
}