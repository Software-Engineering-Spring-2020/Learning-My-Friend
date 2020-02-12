package backend.objects;
import backend.PollyObject;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PConstants;

class TextBox extends ColorfulObject{
    private String str;
    private float textSize;
    private PFont font;

  public TextBox(PApplet sketch, float x, float y, int[] fillColor, int[] boarderColor, String str, String font, float textSize){
    super(sketch, x, y, fillColor, boarderColor);
    this.str = str;
    this.font = createFont(font, 1);
    this.textSize = textSize;
    sketch.textSize(textSize);
    pixelWidth = sketch.textWidth(str);
    pixelHeight = sketch.textHeight(str);
    sketch.textAlign(PConstants.CENTER);
  }

  protected void setBoarderColor(int r, int g, int b){
    super.setBoarderColor(r, g, b);
  }

  protected void setFillColor(int r, int g, int b){
    super.setFillColor(r, g, b);
  }

  protected void display(){
        super.display();
    	sketch.textFont(font, textSize);
        sketch.fill(sketch.color(fillColor[0], fillColor[1], fillColor[2]));
        sketch.text(str, xpos, ypos);
  }
}