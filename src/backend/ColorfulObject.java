package backend;
import java.io.Serializable;

import processing.core.PApplet;

public abstract class ColorfulObject extends PollyObject implements Serializable {
  private static final long serialVersionUID = 9L;
  protected int[] boarderColor = new int[3]; 
  protected int[] fillColor = new int[4]; 

  public ColorfulObject(PApplet sketch, float x, float y, int[] fillColor, int[] boarderColor){
    super(sketch, x, y);
    this.boarderColor[0] = boarderColor[0];
    this.boarderColor[1] = boarderColor[1];
    this.boarderColor[2] = boarderColor[2];
    this.fillColor[0] = fillColor[0];
    this.fillColor[1] = fillColor[1];
    this.fillColor[2] = fillColor[2];
    this.fillColor[3] = fillColor[3];
  }

  protected void setBoarderColor(int r, int g, int b){
    boarderColor[0] = r;
    boarderColor[1] = g;
    boarderColor[2] = b;
  }
  protected void setFillColor(int r, int g, int b, int a){
    fillColor[0] = r;
    fillColor[1] = g;
    fillColor[2] = b;
    fillColor[3] = a;
  }
  protected void setAlpha(int alpha){
    fillColor[3] = alpha;
  }

  protected int[] getBoarderColor(){
    return boarderColor;
  }
  protected int[] getFillColor(){
    return fillColor;
  }

  protected void display(){
    super.display();
    sketch.fill(sketch.color(fillColor[0], fillColor[1], fillColor[2]), fillColor[3]);
    sketch.stroke(sketch.color(fillColor[0], fillColor[1], fillColor[2]));
  }
}