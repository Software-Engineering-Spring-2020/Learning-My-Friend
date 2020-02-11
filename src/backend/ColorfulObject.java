package backend;
import processing.core.PApplet;

public abstract class ColorfulObject extends PollyObject{
  protected int[] boarderColor = new int[3];
  protected int[] fillColor = new int[3];
  protected float alpha = 1;

  public ColorfulObject(PApplet sketch, float x, float y, int[] fillColor, int[] boarderColor){
    super(sketch, x, y);
    this.boarderColor[0] = boarderColor[0];
    this.boarderColor[1] = boarderColor[1];
    this.boarderColor[2] = boarderColor[2];
    this.fillColor[0] = fillColor[0];
    this.fillColor[1] = fillColor[1];
    this.fillColor[2] = fillColor[2];
  }

  protected void setColor(){
    shape.setFill(sketch.color(fillColor[0], fillColor[1], fillColor[2]));
    shape.setStroke(sketch.color(boarderColor[0], boarderColor[1], boarderColor[2]));
  }

  protected void setBoarderColor(int r, int g, int b){
    boarderColor[0] = r;
    boarderColor[1] = g;
    boarderColor[2] = b;
    shape.setStroke(sketch.color(boarderColor[0], boarderColor[1], boarderColor[2]));
  }

  protected void setFillColor(int r, int g, int b){
    fillColor[0] = r;
    fillColor[1] = g;
    fillColor[2] = b;
    shape.setFill(sketch.color(fillColor[0], fillColor[1], fillColor[2]));
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
}