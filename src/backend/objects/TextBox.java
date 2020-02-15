package backend.objects;
import java.io.Serializable;

import backend.ColorfulObject;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PConstants;

class TextBox extends ColorfulObject implements Serializable {
    private static final long serialVersionUID = 8L;
    private String str, font;
    private float textSize;
    transient private PFont pFont;

  public TextBox(PApplet sketch, float x, float y, int[] fillColor, int[] boarderColor, String str, String font, float textSize){
    super(sketch, x, y, fillColor, boarderColor);
    this.str = str;
    this.font = font;
    this.pFont = sketch.createFont(font, 1);
    this.textSize = textSize;
    sketch.textSize(textSize);
    pixelWidth = sketch.textWidth(str);
    //pixelHeight = sketch.textHeight();
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
        //sketch.text(str, xpos, ypos);
        sketch.text(str, 0, 0);
  }
}
