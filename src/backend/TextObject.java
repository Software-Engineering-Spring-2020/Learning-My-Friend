package backend;
import java.io.Serializable;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PConstants;

/**
* A type of colorful object that strictly deals with text/strings.
*/
public abstract class TextObject extends ColorfulObject implements Serializable {
    private static final long serialVersionUID = 8L;
    protected String str, font;
    protected float textSize;
    transient private PFont pFont;

    /**
    * Constructor for ColorfulObject
    * @param sketch A reference to a PApplet to allow general functionality of the processing library
    * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param strokeWeight Represents the line-thickness
    * @param fillColor The internal color of the object represented by four values ranged 0-255 (Red, Green, Blue, and Alpha respectively)
    * @param boarderColor The outline color of the object represented by three values ranged 0-255 (Red, Green, and Blue respectively)
    * @param str The text to be displayed
    * @param font The font style to display the text as
    * @param textSize The size of text to be displayed in pixels
    */
  public TextObject(PApplet sketch, float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor, String str, String font, float textSize){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    this.str = str;
    this.font = font;
    this.pFont = sketch.createFont(font, 1);
    this.textSize = textSize;
    sketch.textSize(textSize);
    pixelWidth = sketch.textWidth(str);
    pixelHeight = 100;
    fillColor[3] = 255;
  }

  /**
  * Function used during the serialization process to restore transient processing library-related variables
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  */
  protected void init(PApplet sketch){
    super.init(sketch);
    this.pFont = sketch.createFont(font, 1);
  }

  /**
  * Template to draw colored text to the slide
  */
  protected void display(){
        super.display();
    	  sketch.textFont(pFont, textSize);
  }
}
