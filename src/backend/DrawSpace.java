package backend;
import java.util.ArrayList;
//import processing.core.*;

class DrawSpace extends ColorfulObject{
  float wBasic, hBasic, wPix, hPix;
  ArrayList<Object> objects = new ArrayList<Object>();
  
  DrawSpace(float x, float y, float w, float h){
    super(x, y);
    wPix = w;
    hPix = h;
    wBasic = wPix / min(wPix, hPix);
    hBasic = hPix / min(wPix, hPix);
    int[] white = {255,255,255};
    fillColor = white;
    boarderColor = white;
  }
  
  void setRotation(int r){}
  ArrayList<Object> getAllObjects(){
    return objects;
  }
  Object getObjectAt(){
    float[] pos = fromCoordinates();
    return null;
  }
  
  float[] toCoordinates(float[] pos){
    return new float[]{pos[0]*wPix + xpos, pos[1]*hPix + ypos}; //translates from relational fracction to coordinates
  }
  
  float[] fromCoordinates(){
    return new float[]{(mouseX - xpos)/wPix, (mouseY - ypos)/hPix}; //translates from coordinates to relational fraction
  }
  
  void display(){
    for(Object obj : objects){
      obj.display(toCoordinates(obj.getPosition()));
    }
  }
}