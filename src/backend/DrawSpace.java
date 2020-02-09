package backend;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PGraphics;
//import processing.core.*;

class DrawSpace extends ColorfulObject{
  float pixelWidth, pixelHeight;
  float zoom = 1;
  ArrayList<PollyObject> objects = new ArrayList<PollyObject>();

  DrawSpace(PApplet sketch, float x, float y, float w, float h){
    super(sketch, x, y);
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

  void pan(float xo, float yo){
    xpos = xpos + xo;
    ypos = ypos + yo;
  }

  void display(){
    super.display();
    sketch.translate(xpos, ypos);
    sketch.scale(zoom);
    sketch.rect(0, 0, pixelWidth, pixelHeight);
    for(PollyObject obj : objects){
      obj.display();
    }
  }

  void zoom(float factor){ zoom += factor; }

  void reset(){
    zoom = 1;
    display();
  }

  void createEllipse(float x, float y){
    float[] coord = fromWindowCoordinates(x, y);
    if(coord[0] < pixelWidth*zoom && coord[0] > 0 && coord[1] > 0 && coord[1] < pixelHeight*zoom) sketch. println(coord[0]);

    coord[0] = pixelWidth * (coord[0] / (pixelWidth*zoom));
    coord[1] = pixelHeight * (coord[1] / (pixelHeight*zoom));
    sketch.ellipse(coord[0], coord[1], 40, 40);
    objects.add(new Ellipse(sketch, coord[0], coord[1], 70, 70));
    sketch.println(objects.size());
  }
}