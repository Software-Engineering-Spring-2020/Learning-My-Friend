package backend;
import java.io.Serializable;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PConstants;

public abstract class TextObject extends ColorfulObject implements Serializable {
    private static final long serialVersionUID = 8L;
    protected String str, font;
    protected float textSize;
    transient private PFont pFont;

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

  protected void init(PApplet sketch){
    super.init(sketch);
    this.pFont = sketch.createFont(font, 1);
  }

  protected void display(){
        super.display();
    	  sketch.textFont(pFont, textSize);
        //delete this line so you can specify where it will be drawn
        //sketch.text(str, 0, 0);
  }
}
