package backend;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PConstants;

public abstract class Shape extends ColorfulObject{
    protected PShape shape;

  public Shape(PApplet sketch, float x, float y, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, fillColor, boarderColor);
    shape = sketch.createShape(PConstants.POINT, xpos, ypos);
  }

  protected void setColor(){
    shape.setFill(sketch.color(fillColor[0], fillColor[1], fillColor[2]));
    shape.setStroke(sketch.color(boarderColor[0], boarderColor[1], boarderColor[2]));
  }

  protected void setBoarderColor(int r, int g, int b){
    super.setBoarderColor(r, g, b);
    shape.setStroke(sketch.color(boarderColor[0], boarderColor[1], boarderColor[2]));
  }

  protected void setFillColor(int r, int g, int b){
    super.setFillColor(r, g, b);
    shape.setFill(sketch.color(fillColor[0], fillColor[1], fillColor[2]));
  }

  protected void display(){
        super.display();
        sketch.shape(shape);
  }

  protected void pan(float xo, float yo){
    super.pan(xo, yo);
    shape.translate(xo, yo);
  }
}