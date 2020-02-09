package backend;
import processing.core.PApplet;

abstract class PollyObject {
  PApplet sketch;
  float xpos, ypos, rot = 0;
  boolean showBoundary;
  
  PollyObject(PApplet sketch, float x, float y){
    this.sketch = sketch;
    xpos = x;
    ypos = y;
  }
  
  void setPosition(float x, float y){
    xpos = x;
    ypos = y;
  }
  void setRotation(float r){ 
    rot = r; 
  }
  float[] getPosition(){ 
    return new float[]{xpos, ypos}; 
  }
  float getRotation(){ 
    return rot; 
  }
  
  void display(){  }
}