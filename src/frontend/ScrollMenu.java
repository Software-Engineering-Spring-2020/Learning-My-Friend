package frontend;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.core.PImage;
import processing.core.PConstants;

import java.util.ArrayList;

/**
* A class to handle scrolling through slide thumbnails and changing the current editable slide.
*/
class ScrollMenu{
  private PApplet sketch;
  private PGraphics scrollMenu;
  private int offset = 10, thumbnailWidth = 100, thumbnailHeight = 50;
  private int menuWidth = 100+2*offset, menuHeight = 400, topSlide = 0;
  protected int scrollMenuX = 10, scrollMenuY = 10, currentSlide = 0;
  private ArrayList<PImage> thumbnails = new ArrayList<PImage>();

  private PImage empty;

  /**
  * @param sketch A reference to a PApplet to allow general functionality of the processing library
  * @param thumbnails A list of slides to make thumbnails of
  */
  void init(PApplet sketch, ArrayList<PImage> thumbnails) {
    this.sketch = sketch;
    scrollMenu = sketch.createGraphics(scrollMenuX, scrollMenuY);

    empty = sketch.createImage(100,50, PConstants.RGB);
    empty.loadPixels();
    for (int i = 0; i < empty.pixels.length; i++)
      empty.pixels[i] = sketch.color(255, 255, 255);
    empty.updatePixels();

    scroll(0);

    if(thumbnails.isEmpty()) this.thumbnails.add(empty);
    else{
      for(PImage img : thumbnails){
        this.thumbnails.add(convertThumbnail(img));
      }
    }
  }

  /**
  * Draw the menu of slides to the screen.
  */
  void display() {
    sketch.image(scrollMenu, scrollMenuX, scrollMenuY);
  }

  /**
  * Add an empty slide to the slide menu.
  */
  protected void newSlide(){
    thumbnails.add(empty);
  }

  /**
  * Change the current editable slide to the slide under the mouse click.
  */
  protected int selectSlide(){
    PVector pos = new PVector(sketch.mouseX, sketch.mouseY);
    if(pos.x>scrollMenuX && pos.y>scrollMenuY && pos.x<scrollMenuX+menuWidth && pos.y<scrollMenuY+menuHeight){
      int index = (int)((pos.y - scrollMenuY)/(empty.height+offset));
      if(index >= 0 && index+topSlide < thumbnails.size()) currentSlide = topSlide + index;
    }
    return currentSlide;
  }

  /**
  * Call this function to actively scroll through the thumbnails of slides
  * @param startSlide The topmost slide on the menu
  */
  protected void scroll(int startSlide) {
    scrollMenu.beginDraw();
    scrollMenu.background(100);
    scrollMenu.rectMode(PConstants.CORNER);
    int slide = startSlide;
    topSlide = startSlide;
    for(int i = offset; i<scrollMenu.height; i+=empty.height+offset){
      if(slide>= thumbnails.size()) break;
      scrollMenu.image(thumbnails.get(slide), offset, i);
      slide ++;
    }
    scrollMenu.endDraw();
  }

  /**
  * Update the thumbnail for the slide just finished editing
  * @param index The location of the thumbnail to change
  * @param slide The new slide to be saved into a thumbnail.
  */
  protected void updateThumbnail(int index, PImage slide){
    PImage img = slide;
    img.resize(100, 50);
    thumbnails.set(index, img);
  }

  /**
  * Converts a full scale slide into a thumbnail size PImage
  * @return A 100x50 image representing the slide
  */
  private PImage convertThumbnail(PImage slide){
    PImage img = slide;
    img.resize(100, 50);
    return img;
  }
}
