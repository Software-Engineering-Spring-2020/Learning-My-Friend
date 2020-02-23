package backend.objects;

import java.io.Serializable;

import backend.PollyObject;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PConstants;

/**
* Type of text which only displays to the slide if comment-mode is enabled (Does not support interactivity).
*/
public class Comment extends TextBox implements Serializable {
  private static final long serialVersionUID = 1L;
    private PollyObject source;

    /**
    * Constructor for Comment
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
  public Comment(PApplet sketch, float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor, String str, String font, float textSize){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor, str, font, textSize);
  }

  /**
  * Link the comment with another object on the slide
  */
  protected void setSource(PollyObject source){
  	this.source = source;
  }
}
