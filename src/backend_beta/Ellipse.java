package backend_beta;
import processing.core.PApplet;

class Ellipse extends ColorfulObject{
  float w, h;
  Ellipse(float x, float y, float w, float h){
    super(x, y);
    this.w = w;
    this.h = h;
  }
  
  void display(){
    ellipse(xpos, ypos, w, h);
  }
}