package backend;
//import processing.core.*;

import processing.core.PApplet;

public abstract class ColorfulObject extends PollyObject{
  protected int[] boarderColor = new int[3];
  protected int[] fillColor = new int[3];
  protected float alpha = 1;

  public ColorfulObject(PApplet sketch, float x, float y){
    super(sketch, x, y);
  }

  protected void setBoarderColor(int r, int g, int b){
    boarderColor[0] = r;
    boarderColor[1] = g;
    boarderColor[2] = b;
  }

  protected void setFillColor(int r, int g, int b){
    fillColor[0] = r;
    fillColor[1] = g;
    fillColor[2] = b;
  }

  protected int[] getBoarderColor(){
    return boarderColor;
  }
  protected int[] getFillColor(){
    return fillColor;
  }

  protected void setAlpha(float a){
    this.alpha = a;
  }

  protected void display(){
    sketch.fill(sketch.color(fillColor[0], fillColor[1], fillColor[2]));
    sketch.stroke(sketch.color(boarderColor[0], boarderColor[1], boarderColor[2]));
  }
}