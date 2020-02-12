package backend.objects;
import backend.PollyObject;
import processing.core.PApplet;
import processing.core.PImage;

class Image extends PollyObject {
  String[] file;
  PImage img;
  Image(PApplet sketch, float x, float y, String filename, String extension){
    super(sketch, x, y);
    file = new String[]{filename, extension};
    img = sketch.requestImage(filename, extension);
  }
  
  protected void display(){
  	if(img.width != 0) sketch.image(img, xpos, ypos);
  }

  protected void resize(float factor){
    img.resize((int)factor*img.width, 0);
  }
  protected void resize(float xfactor, float yfactor){
    //img.resize(xfactor, yfactor);
  }

}