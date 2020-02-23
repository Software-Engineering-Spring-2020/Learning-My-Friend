package backend;
import java.io.Serializable;

import processing.core.PApplet;

/**
* A template class for all text objects
*/
public abstract class TextObject extends ColorfulObject implements Serializable {
  private static final long serialVersionUID = 9L;
  protected int[] boarderColor = new int[3];
  protected int[] fillColor = new int[4];
  protected float strokeWeight = 1;

  /**
  * Constructor for TextObject
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param strokeWeight Represents the line-thickness
  * @param fillColor The internal color of the object represented by four values ranged 0-255 (Red, Green, Blue, and Alpha respectively)
  * @param boarderColor The outline color of the object represented by three values ranged 0-255 (Red, Green, and Blue respectively)
  */
  public TextObject(PApplet sketch, float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
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
  * Function used during the serialization process to restore transient processing library-related variables
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  */
  protected void init(PApplet sketch){
    super.init(sketch);
    offset += strokeWeight;
    //setBoarderColor(boarderColor[0],boarderColor[1],boarderColor[2]);
    //setFillColor(fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
  }

/**
* @deprecated Please deprecate and only keep in InteractiveTextBox
*/
  protected void handleKey(char key, int keyCode) {

  }
}
