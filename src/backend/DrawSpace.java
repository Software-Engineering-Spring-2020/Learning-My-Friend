package backend;
import java.util.ArrayList;
import processing.core.PApplet;
//import processing.core.*;

class DrawSpace extends ColorfulObject{
  float wBasic, hBasic, wPix, hPix;
  ArrayList<PollyObject> objects = new ArrayList<PollyObject>();
  
  DrawSpace(float x, float y, float w, float h){
    super(x, y);
    wPix = w;
    hPix = h;
    wBasic = wPix / Math.min(wPix, hPix);
    hBasic = hPix / Math.min(wPix, hPix);
    int[] white = {255,255,255};
    fillColor = white;
    boarderColor = white;
  }
  
  void setRotation(int r){}
  ArrayList<PollyObject> getAllObjects(){
    return objects;
  }
  Object getObjectAt(float mouseX, float mouseY){
    float[] pos = fromCoordinates(mouseX, mouseY);
    return null;
  }
  
  float[] toCoordinates(float[] pos){
    return new float[]{pos[0]*wPix + xpos, pos[1]*hPix + ypos}; //translates from relational fracction to coordinates
  }
  
  float[] fromCoordinates(float mouseX, float mouseY){
    return new float[]{(mouseX - xpos)/wPix, (mouseY - ypos)/hPix}; //translates from coordinates to relational fraction
  }
  
  void display(){
    for(PollyObject obj : objects){
      obj.display(toCoordinates(obj.getPosition()));
    }
  }
}