package backend.objects;

import backend.ColorfulObject;
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;

import java.io.Serializable;
import java.util.ArrayList;

/**
* Handles creation and display of bezier curves.
*/
class Curve extends ColorfulObject implements Serializable{
  private static final long serialVersionUID = 12L;
  private ArrayList<float[]> points = new ArrayList<float[]>();

  /**
  * Constructor for Curve
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param points The four points of the bezier curve
  * @param strokeWeight Represents the line-thickness
  * @param fillColor The internal color of the object represented by four values ranged 0-255 (Red, Green, Blue, and Alpha respectively)
  * @param boarderColor The outline color of the object represented by three values ranged 0-255 (Red, Green, and Blue respectively)
  */
  Curve(PApplet sketch, float x, float y, ArrayList<float[]> points, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    setFillColor(255,255,255,0);
    setSettings(points);
  }

  /**
  * Function used during the serialization process to restore transient processing library-related variables
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  */
  protected void init(PApplet sketch){
    super.init(sketch);
    for (float[] point : points) {
      point[0] += xpos;
      point[1] += ypos;
    }
    setSettings((ArrayList<float[]>)this.points.clone());
  }

  /**
  * Calculates and intializes the center of the object and saves the points of the bezier curve.
  */
  protected void setSettings(ArrayList<float[]> points){
    float xmin = Float.MAX_VALUE, ymin = Float.MAX_VALUE, xmax = Float.MIN_VALUE, ymax = Float.MIN_VALUE;
    this.points = new ArrayList<float[]>();
    for(float[] point : points){
      xmin = Math.min(point[0], xmin);
      ymin = Math.min(point[1], ymin);
      xmax = Math.max(point[0], xmax);
      ymax = Math.max(point[1], ymax);
      pixelWidth = Math.abs(xmax - xmin);
      pixelHeight = Math.abs(ymax - ymin);
      xcenter = (xmin + xmax)/2;
      ycenter = (ymin + ymax)/2;
      point[0] -= xpos;
      point[1] -= ypos;
      this.points.add(point);
    }
  }

  /**
  * Draw the bezier curve to the slide
  */
  protected void display(){
    super.display();
    sketch.translate(-xcenter+xpos, -ycenter+ypos);
    sketch.bezier(points.get(0)[0], points.get(0)[1], points.get(1)[0], points.get(1)[1], points.get(2)[0], points.get(2)[1], points.get(3)[0], points.get(3)[1]);
  }
}
