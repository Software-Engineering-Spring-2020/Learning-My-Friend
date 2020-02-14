package backend;
import java.io.Serializable;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PConstants;

public abstract class Shape extends ColorfulObject implements Serializable {
    private static final long serialVersionUID = 11L;
    transient protected PShape shape;
    protected float strokeWeight; //save me

  public Shape(PApplet sketch, float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, fillColor, boarderColor);
    this.strokeWeight = strokeWeight;
    shape = sketch.createShape();
  }

  protected void init(PApplet sketch){
    super.init(sketch);
    shape = sketch.createShape();
  }

  protected void setSettings(){
    shape.setFill(sketch.color(fillColor[0], fillColor[1], fillColor[2]), fillColor[3]);
    shape.setStroke(sketch.color(boarderColor[0], boarderColor[1], boarderColor[2]));
    setStrokeWeight(strokeWeight);
  }

  protected void setStrokeWeight(float wgt) {
    try {
      java.lang.reflect.Field f = shape.getClass().getDeclaredField("strokeWeight");
      f.setAccessible(true);
      f.setFloat(shape, wgt);
      //return f.getFloat(shape);
    }
    catch (final ReflectiveOperationException e) {
      e.printStackTrace();
      throw new InternalError("strokeWeight doesn't exist in class PShape anymore!");
    }
}

  protected void setBoarderColor(int r, int g, int b){
    super.setBoarderColor(r, g, b);
    shape.setStroke(sketch.color(boarderColor[0], boarderColor[1], boarderColor[2]));
  }

  protected void setFillColor(int r, int g, int b, int a){
    super.setFillColor(r, g, b, a);
    shape.setFill(sketch.color(fillColor[0], fillColor[1], fillColor[2]), fillColor[3]);
  }

  protected void display(){
    super.display();
    sketch.translate(xpos, ypos);
    sketch.shape(shape);
  }

  protected void setRelativeRotate(float ro) {
    super.setRelativeRotate(ro);
    shape.rotate(sketch.radians(ro));
  }
}
