package backend.objects;

import java.io.Serializable;

import backend.PollyObject;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PConstants;

class Comment extends TextBox implements Serializable {
  private static final long serialVersionUID = 1L;
    private transient PollyObject source;

  public Comment(PApplet sketch, float x, float y, int[] fillColor, int[] boarderColor, String str, String font, float textSize){
    super(sketch, x, y, fillColor, boarderColor, str, font, textSize);
  }

  protected void setSource(PollyObject source){
  	this.source = source;
  }

  protected void display(){
    super.display();
  }
}