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
  private int offset = 10, thumbnailWidth, thumbnailHeight;
  private int menuWidth, menuHeight, topSlide = 0;
  private int scrollMenuX = 10, scrollMenuY = 10, currentSlide = 0;
  private ArrayList<PImage> thumbnails = new ArrayList<PImage>(),
          fullSlides = new ArrayList<PImage>();

  private PImage empty;

  /**
  * @param sketch A reference to a PApplet to allow general functionality of the processing library
  * @param thumbnails A list of slides to make thumbnails of
  */
  public ScrollMenu (PApplet sketch, int x, int y, int w, int h, ArrayList<PImage> fullSlides) {
    this.sketch = sketch;
    this.fullSlides = fullSlides;

    setPos(x, y);
    setSize(w, h);

    if(thumbnails.isEmpty()) this.thumbnails.add(empty);
    else{
      for(PImage img : fullSlides){
        this.thumbnails.add(convertThumbnail(img));
      }
    }

    scroll(0);
  }



  protected void setPos(int x, int y){
    scrollMenuX = x;
    scrollMenuY = y;
  }

  protected void setSize(int width, int height){
    menuWidth = width;
    menuHeight = height;
    thumbnailWidth = width - 2*offset;
    thumbnailHeight = thumbnailWidth/2;

    scrollMenu = sketch.createGraphics(menuWidth, menuHeight);
    createEmpty();
    scroll(currentSlide);
  }

  private void createEmpty(){
    empty = sketch.createImage(thumbnailWidth,thumbnailHeight, PConstants.RGB);
    empty.loadPixels();
    for (int i = 0; i < empty.pixels.length; i++)
      empty.pixels[i] = sketch.color(255, 255, 255);
    empty.updatePixels();
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
    thumbnails.add(currentSlide+1, empty);
    fullSlides.add(currentSlide+1, empty); //will get over-ridden
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
    img.resize(thumbnailWidth, thumbnailHeight);
    thumbnails.set(index, img);
    fullSlides.set(index, slide);
  }

  /**
  * Converts a full scale slide into a thumbnail size PImage
  * @return A 100x50 image representing the slide
  */
  private PImage convertThumbnail(PImage slide){
    PImage img = slide;
    img.resize(thumbnailWidth, thumbnailHeight);
    return img;
  }
}
