package backend;
import processing.core.PApplet;

class Ellipse extends ColorfulObject{
  float w, h;
  Ellipse(PApplet sketch, float x, float y, float w, float h){
    super(sketch, x, y);
    this.w = w;
    this.h = h;
  }
  
  void display(){
    super.display();
    sketch.ellipse(xpos, ypos, w, h);
  }
}