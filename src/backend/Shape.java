package backend;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PConstants;

public abstract class Shape extends ColorfulObject{
    protected PShape shape;
    protected float strokeWeight; //save me

  public Shape(PApplet sketch, float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, fillColor, boarderColor);
    //shape = sketch.createShape(PConstants.POINT, 0, 0);
    shape = sketch.createShape();
    this.strokeWeight = strokeWeight;
  }

  protected void setSettings(){
    shape.setFill(sketch.color(fillColor[0], fillColor[1], fillColor[2]));
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

  protected void setFillColor(int r, int g, int b){
    super.setFillColor(r, g, b);
    shape.setFill(sketch.color(fillColor[0], fillColor[1], fillColor[2]));
  }

  protected void display(){
        super.display();
        sketch.translate(xpos, ypos);
        sketch.shape(shape);
  }

  /*protected void pan(float xo, float yo){
    super.pan(xo, yo);
    shape.translate(xo, yo);
  }
*/
  protected void setRelativeRotate(float ro) {
    super.setRelativeRotate(ro);
    shape.rotate(sketch.radians(ro));
  }
}