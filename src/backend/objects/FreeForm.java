package backend.objects;
import backend.Shape;
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;
import processing.core.PShape;

import java.io.Serializable;
import java.util.ArrayList;

/**
* Handles user free-hand drawn line design.
*/
class FreeForm extends Shape implements Serializable {
  private static final long serialVersionUID = 14L;
  private ArrayList<float[]> points = new ArrayList<float[]>();

  /**
  * Constructor for FreeForm
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param points The points along the user-drawn free-hand drawn line design
  * @param strokeWeight Represents the line-thickness
  * @param fillColor The internal color of the object represented by four values ranged 0-255 (Red, Green, Blue, and Alpha respectively)
  * @param boarderColor The outline color of the object represented by three values ranged 0-255 (Red, Green, and Blue respectively)
  */
  FreeForm(PApplet sketch, float x, float y, ArrayList<float[]> points, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    for(float[] point : points){
      this.points.add(point);
    }
    createShape(points);
    setFillColor(255,255,255,0);
    setSettings();
  }

  /**
  * Function used during the serialization process to restore transient processing library-related variables
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  */
  protected void init(PApplet sketch){
    super.init(sketch);
    createShape(points);
    setSettings();
  }

  protected void pan(float xo, float yo) {
    super.pan(xo, yo);
    for (float[] point : points) {
      point[0] += xo;
      point[1] += yo;
    }
  }

/**
* Creates and saves all the points as a single shape object (Effectively treats them as one unit and connects the dots)
* @param points The points along the user-drawn free-hand drawn line design
*/
  private void createShape(ArrayList<float[]> points){
    float xmin = Float.MAX_VALUE, ymin = Float.MAX_VALUE, xmax = Float.MIN_VALUE, ymax = Float.MIN_VALUE;

    shape = sketch.createShape(PShape.PATH);
    shape.beginShape();
    for(int i = 0; i<points.size(); i++){
      float[] pos = new float[]{points.get(i)[0], points.get(i)[1]};
      shape.vertex(pos[0]-xpos, pos[1]-ypos);
      xmin = Math.min(pos[0], xmin);
      ymin = Math.min(pos[1], ymin);
      xmax = Math.max(pos[0], xmax);
      ymax = Math.max(pos[1], ymax);
    }
    shape.endShape();

    pixelWidth = Math.abs(xmax - xmin);
    pixelHeight = Math.abs(ymax - ymin);
    xcenter = (xmin + xmax)/2;
    ycenter = (ymin + ymax)/2;
  }

  /**
  * Draw the user line design to the screen
  */
  protected void display() {
      super.display();
      sketch.translate(-xcenter+xpos, -ycenter+ypos);
      sketch.shape(shape);
   }
}
