package backend.objects;
import java.io.Serializable;

import backend.ColorfulObject;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PConstants;

class TextBox extends ColorfulObject implements Serializable {
    private static final long serialVersionUID = 8L;
    private String str; //save me
    private float textSize; //save me
    private PFont font; //save me

  public TextBox(PApplet sketch, float x, float y, int[] fillColor, int[] boarderColor, String str, String font, float textSize){
    super(sketch, x, y, fillColor, boarderColor);
    this.str = str;
    this.font = sketch.createFont(font, 1);
    this.textSize = textSize;
    sketch.textSize(textSize);
    pixelWidth = sketch.textWidth(str);
    //pixelHeight = sketch.textHeight();
    pixelHeight = 100;
    fillColor[3] = 255;
  }

  protected void display(){
        super.display();
    	  sketch.textFont(font, textSize);
        sketch.text(str, xpos, ypos);
  }
}