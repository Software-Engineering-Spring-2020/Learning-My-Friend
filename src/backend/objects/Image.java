package backend.objects;
import backend.PollyObject;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.Serializable;

class Image extends PollyObject implements Serializable {
  private static final long serialVersionUID = 6L;
  String[] file;
  transient PImage img;
  Image(PApplet sketch, float x, float y, String filename, String extension){
    super(sketch, x, y);
    file = new String[]{filename, extension};
    //img = sketch.requestImage(filename+extension);
    img = sketch.loadImage(filename+extension);
    if(img != null){
      pixelWidth = img.width;
      pixelHeight = img.height;
    }
  }

  protected void init(PApplet sketch){
    super.init(sketch);
    img = sketch.requestImage(file[0]+file[1]);
  }

  protected void display(){
    if(img != null) sketch.image(img, xpos, ypos);
  	//if(img.width != 0) sketch.image(img, xpos, ypos);
  }

  protected void resize(float factor){
    super.resize(factor);
    img.resize((int)((1+factor)*pixelWidth), 0);
  }
  protected void resize(float xfactor, float yfactor){
    //img.resize(xfactor, yfactor);
  }

}
