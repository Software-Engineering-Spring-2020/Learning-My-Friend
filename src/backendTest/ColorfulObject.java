package backend;
import java.io.Serializable;

import processing.core.PApplet;
import processing.core.PGraphics;

/**
* A basic form of objects that adds color functionality
*/
public abstract class ColorfulObject extends PollyObject implements Serializable {
  private static final long serialVersionUID = 9L;
  protected int[] boarderColor = new int[3];
  protected int[] fillColor = new int[4];
  protected float strokeWeight = 1;

  /**
  * Constructor for ColorfulObject
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param strokeWeight Represents the line-thickness
  * @param fillColor The internal color of the object represented by four values ranged 0-255 (Red, Green, Blue, and Alpha respectively)
  * @param boarderColor The outline color of the object represented by three values ranged 0-255 (Red, Green, and Blue respectively)
  */
  public ColorfulObject(PApplet sketch, PGraphics graphic, float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, graphic, x, y);
    this.strokeWeight = strokeWeight;
    offset += strokeWeight;
    this.boarderColor[0] = boarderColor[0];
    this.boarderColor[1] = boarderColor[1];
    this.boarderColor[2] = boarderColor[2];
    this.fillColor[0] = fillColor[0];
    this.fillColor[1] = fillColor[1];
    this.fillColor[2] = fillColor[2];
    this.fillColor[3] = fillColor[3];
  }

  /**
  * Set the outline/boarder of the object to the specified color
  * @param r Represents the red value in color combinations (range between 0 - 255)
  * @param g Represents the green value in color combinations (range between 0 - 255)
  * @param b Represents the blue value in color combinations (range between 0 - 255)
  */
  protected void setBoarderColor(int r, int g, int b){
    boarderColor[0] = r;
    boarderColor[1] = g;
    boarderColor[2] = b;
  }

  /**
  * Set the internal/fill of the object to the specified color
  * @param r Represents the red value in color combinations (range between 0 - 255)
  * @param g Represents the green value in color combinations (range between 0 - 255)
  * @param b Represents the blue value in color combinations (range between 0 - 255)
  * @param a Represents the alpha (transparent) value for the color (range between 0 - 255)
  */
  protected void setFillColor(int r, int g, int b, int a){
    fillColor[0] = r;
    fillColor[1] = g;
    fillColor[2] = b;
    fillColor[3] = a;
  }

  /**
  * Template to draw a colored object to the slide
  */
  protected void display(){
    super.display();
    graphic.fill(graphic.color(fillColor[0], fillColor[1], fillColor[2]), fillColor[3]);
    graphic.stroke(graphic.color(boarderColor[0], boarderColor[1], boarderColor[2]));
    graphic.strokeWeight(strokeWeight);
  }
}
