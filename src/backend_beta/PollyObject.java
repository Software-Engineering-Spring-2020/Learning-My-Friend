package backend;
import processing.core.PApplet;

abstract class PollyObject extends PApplet {
  float xpos, ypos, rot = 0;
  boolean showBoundary;
  
  PollyObject(float x, float y){
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
  
  void display(){}
}