package backend_beta;
import java.util.ArrayList;
import processing.core.PApplet;
//import processing.core.*;

public class DrawSpace extends ColorfulObject{
  float pixelWidth, pixelHeight;
  float zoom;
  ArrayList<PollyObject> objects = new ArrayList<PollyObject>();

  public DrawSpace(float x, float y, float w, float h){
    super(x, y);
    pixelWidth = w;
    pixelHeight = h;
    int[] white = {255,255,255};
    fillColor = white;
    boarderColor = white;
  }

  void setRotation(int r){}
  ArrayList<PollyObject> getAllObjects(){
    return objects;
  }
  Object getObjectAt(float x, float y){
    float[] pos = fromWindowCoordinates(x, y);
    return null;
  }

  float[] fromWindowCoordinates(float x, float y){
    return new float[]{x-xpos, y-ypos}; //translates from coordinates to relational fraction
  }

  public void fakeDisplay(){
    translate(xpos, ypos);
    rect(0, 0, pixelWidth, pixelHeight);
    ellipse(pixelWidth/2, pixelHeight/2, 40, 40);
    println("mouseX:\t"+mouseX);
    println("mouseY:\t"+mouseY);
    println("xpos:\t"+xpos);
    println("ypos:\t"+ypos);
  }

  void pan(){
    xpos = xpos + (mouseX - pmouseX);
    ypos = ypos + (mouseY - pmouseY);
  }

  public void pan(float xo, float yo){
    xpos = xpos + xo;
    ypos = ypos + yo;
  }

  public void display(){
    translate(xpos, ypos);
    scale(zoom);
    rect(0, 0, pixelWidth, pixelHeight);
    for(PollyObject obj : objects){
      obj.display();
    }
  }

  public void zoom(float factor){ zoom += factor; }

  void reset(){
    zoom = 1;
    display();
  }

  public void createEllipse(float x, float y){
    float[] coord = fromWindowCoordinates(x, y);
    println(coord[0]);
    objects.add(new Ellipse(coord[0], coord[1], 70, 70));
  }
}