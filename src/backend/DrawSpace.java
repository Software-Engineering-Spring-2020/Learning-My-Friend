package backend;
import java.util.ArrayList;
import processing.core.PApplet;
//import processing.core.*;

class DrawSpace extends ColorfulObject{
  protected float pixelWidth, pixelHeight;
  protected ArrayList<PollyObject> objects = new ArrayList<PollyObject>();

  DrawSpace(PApplet sketch, float x, float y, float w, float h){
    super(sketch, x, y);
    pixelWidth = w;
    pixelHeight = h;
    int[] white = {255,255,255};
    fillColor = white;
    boarderColor = white;
    boundingBox[0] = pixelWidth;
    boundingBox[1] = pixelHeight;
  }

  void setRotation(int r){}

  ArrayList<PollyObject> getAllObjects(){
    return objects;
  }

  Object getObjectAt(float x, float y, float zoom){
    float[] pos = translateCoordinates(x, y, zoom);
    return null;
  }

  float[] translateCoordinates(float x, float y, float zoom){
    float[] coord = new float[]{x-xpos, y-ypos}; //translates from coordinates to relational fraction
    coord[0] = pixelWidth * (coord[0] / (pixelWidth*zoom));
    coord[1] = pixelHeight * (coord[1] / (pixelHeight*zoom));
    return coord;
  }

  void pan(float xo, float yo){
    xpos = xpos + xo;
    ypos = ypos + yo;
  }

  protected void display(float zoom){
    super.display();
    sketch.translate(xpos, ypos);
    sketch.scale(zoom);
    sketch.rect(0, 0, pixelWidth, pixelHeight);
    for(PollyObject obj : objects){
      obj.display();
    }
  }

  protected boolean addShape(PollyObject shape){
    return objects.add(shape);
  }

  protected float[] getDimensions(){
   return new float[]{pixelWidth, pixelHeight};
  }
}