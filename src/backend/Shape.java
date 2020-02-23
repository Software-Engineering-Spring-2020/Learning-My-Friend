package backend;
import java.io.Serializable;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PConstants;

/**
* A type of ColorfulObject that allows for the utilization of Shapes from the processing library.
*/
public abstract class Shape extends ColorfulObject implements Serializable {
    private static final long serialVersionUID = 11L;
    transient protected PShape shape;

    /**
    * Constructor for Shape
    * @param sketch a reference to a PApplet to allow general functionality of the processing library
    * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param strokeWeight Represents the line-thickness
    * @param fillColor The internal color of the object represented by four values ranged 0-255 (Red, Green, Blue, and Alpha respectively)
    * @param boarderColor The outline color of the object represented by three values ranged 0-255 (Red, Green, and Blue respectively)
    */
  public Shape(PApplet sketch, float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    shape = sketch.createShape();
  }

  /**
  * Function used during the serialization process to restore transient processing library-related variables
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  */
  protected void init(PApplet sketch){
    super.init(sketch);
    shape = sketch.createShape();
    setSettings();
    setRelativeRotate(rot);
  }

  /**
  * Set the initial color and stroke settings for shapes. Use in constructor of instanciable sub-classes.
  */
  protected void setSettings(){
    shape.setFill(sketch.color(sketch.color(fillColor[0], fillColor[1], fillColor[2]), fillColor[3]));
    shape.setStroke(sketch.color(boarderColor[0], boarderColor[1], boarderColor[2]));
    setStrokeWeight(strokeWeight);
  }

  /**
  * Set the current stroke thickness for the objects
  * @param wgt The desired thickness (in pixels)
  */
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

/**
* Set the outline/boarder of the object to the specified color
* @param r Represents the red value in color combinations (range between 0 - 255)
* @param g Represents the green value in color combinations (range between 0 - 255)
* @param b Represents the blue value in color combinations (range between 0 - 255)
*/
  protected void setBoarderColor(int r, int g, int b){
    super.setBoarderColor(r, g, b);
    shape.setStroke(sketch.color(boarderColor[0], boarderColor[1], boarderColor[2]));
  }

  /**
  * Set the internal/fill of the object to the specified color
  * @param r Represents the red value in color combinations (range between 0 - 255)
  * @param g Represents the green value in color combinations (range between 0 - 255)
  * @param b Represents the blue value in color combinations (range between 0 - 255)
  * @param a Represents the alpha (transparent) value for the color (range between 0 - 255)
  */
  protected void setFillColor(int r, int g, int b, int a){
    super.setFillColor(r, g, b, a);
    shape.setFill(sketch.color(sketch.color(fillColor[0], fillColor[1], fillColor[2]), fillColor[3]));
  }

  /**
  * Set relative angle change in rotation of the object
  * @default 0 The original and inital rotation of all objects is 0
  * @param r The desired raw rotation angle in degrees (from the original rotation NOT relative from current rotation)
  */
  protected void setRelativeRotate(float r) {
    super.setRelativeRotate(r);
    shape.rotate((float)Math.toRadians(ro));
  }
}
