package backend;
import java.io.Serializable;

import processing.core.PApplet;

public abstract class TextObject extends ColorfulObject implements Serializable {
  private static final long serialVersionUID = 9L;
  protected int[] boarderColor = new int[3];
  protected int[] fillColor = new int[4];
  protected float strokeWeight = 1;

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

  protected void init(PApplet sketch){
    super.init(sketch);
    this.strokeWeight = strokeWeight;
    offset += strokeWeight;
    //setBoarderColor(boarderColor[0],boarderColor[1],boarderColor[2]);
    //setFillColor(fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
  }

  protected void handleKey(char key, int keyCode) {

  }

  protected void display(){
    super.display();
  }
}
