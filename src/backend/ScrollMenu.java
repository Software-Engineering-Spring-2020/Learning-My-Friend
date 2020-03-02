package backend;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.core.PImage;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.Collections;

/**
* A class to handle scrolling through slide thumbnails and changing the current editable slide.
* @deprecated in favor of relocation to backend
*/
class ScrollMenu{
  private PApplet sketch;
  private PGraphics scrollMenu;
  private int offset = 10, thumbnailWidth = 100, thumbnailHeight = 50;
  private int menuWidth, menuHeight, topSlide = -1;
  private int scrollMenuX = 10, scrollMenuY = 10, centerMenuX = 10, centerMenuY = 10, currentSlide = 0;
  private ArrayList<PImage> thumbnails = new ArrayList<PImage>(),
          fullSlides = new ArrayList<PImage>();

  private PImage empty;

  /**
  * @param sketch A reference to a PApplet to allow general functionality of the processing library
  * @param thumbnails A list of slides to make thumbnails of
  */
  public ScrollMenu (PApplet sketch) {
    this.sketch = sketch;
  }

  public ScrollMenu (PApplet sketch, int x, int y, int w, int h) {
    this.sketch = sketch;

    setPos(x, y);
    setSize(w, h);

    if(fullSlides.isEmpty()){
      fullSlides.add(empty);
      thumbnails.add(empty);
    } else{
        convertThumbnails();
      }
    scroll(0);
  }

  public void loadSlides(ArrayList<PImage> fullSlides){
    fullSlides.clear();
    for(PImage slide : fullSlides){
      this.fullSlides.add(slide);
    }
    convertThumbnails();
    scroll(0);
  }


  protected void setPos(int x, int y){
    centerMenuX = x;
    centerMenuY = y;
    scrollMenuX = centerMenuX - menuWidth/2;
    scrollMenuY = centerMenuY - menuHeight/2;
  }

  protected void setSize(int width, int height){
    menuWidth = width;
    menuHeight = height;
    thumbnailWidth = width - 2*offset;
    thumbnailHeight = thumbnailWidth/2;

    scrollMenuX = centerMenuX - menuWidth/2;
    scrollMenuY = centerMenuY - menuHeight/2;

    scrollMenu = sketch.createGraphics(menuWidth, menuHeight);
    createEmpty();
    convertThumbnails();
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
  protected void display() {
    sketch.image(scrollMenu, centerMenuX, centerMenuY);
  }

  /**
  * Change the current editable slide to the slide under the mouse click.
  * @param x sketch.mouseX
  * @param y sketcy.mouseY
  */
  protected int selectSlide(float x, float y){
    PVector pos = new PVector(x, y);
    if(pos.x>scrollMenuX && pos.y>scrollMenuY && pos.x<scrollMenuX+menuWidth && pos.y<scrollMenuY+menuHeight){
      int index = (int)((pos.y - scrollMenuY)/(empty.height+offset));
      if(index >= 0 && index+topSlide < thumbnails.size()) currentSlide = topSlide + index;
    }
    scroll(topSlide);
    return currentSlide;
  }

  protected void selectSlide(int index){
    currentSlide = index;
    scroll(topSlide);
  }

  protected void deleteSlide(int index){
    thumbnails.remove(index);
    fullSlides.remove(index);
  }

  protected void swapSlides(int indexA, int indexB){
    Collections.swap(fullSlides, indexA, indexB);
    Collections.swap(thumbnails, indexA, indexB);
  }

  protected void newSlideAt(int index){
    thumbnails.add(index, empty);
    fullSlides.add(index, empty);
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
      if(slide == currentSlide){
        scrollMenu.push();
        scrollMenu.noFill();
        scrollMenu.stroke(215,165,0);
        scrollMenu.strokeWeight(2);
        scrollMenu.rect(offset/2, i-offset/2, thumbnailWidth+offset, thumbnailHeight+offset);
        scrollMenu.pop();
      }
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
    scroll(topSlide);
  }

  /**
  * Converts a full scale slide into a thumbnail size PImage
  * @return A 100x50 image representing the slide
  */
  private void convertThumbnails(){
    for(int i = 0; i < fullSlides.size(); i++){
      PImage img = fullSlides.get(i);
      img.resize(thumbnailWidth, thumbnailHeight);
      thumbnails.set(i, img);
    }
  }

}
