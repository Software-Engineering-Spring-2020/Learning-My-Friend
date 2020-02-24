package backend.objects;

import backend.PollyObject;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.Serializable;

/**
* Class which supports the import, save, and display of images.
*/
class Image extends PollyObject implements Serializable {
  private static final long serialVersionUID = 6L;
  String[] file;
  transient PImage img;

  /**
  * Constructor for Image
  * @param sketch A reference to a PApplet to allow general functionality of the processing library
  * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param filename The file (path + name) of the image to display
  * @param extension The image file type (the . is still necessary)
  */
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

  /**
  * Function used during the serialization process to restore transient processing library-related variables
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  */
  protected void init(PApplet sketch){
    super.init(sketch);
    img = sketch.requestImage(file[0]+file[1]);
  }

  /**
  * Draw the image to the slide
  */
  protected void display(){
    super.display();
    sketch.translate(-xpos, -xpos);
    if(img != null) sketch.image(img, xcenter, ycenter);
  	//if(img.width != 0) sketch.image(img, xpos, ypos);
  }

}
