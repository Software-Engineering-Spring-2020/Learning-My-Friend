package backend.objects;

import backend.ColorfulObject;
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;

import java.io.Serializable;
import java.util.ArrayList;


class Curve extends ColorfulObject implements Serializable{
  private static final long serialVersionUID = 12L;
  private ArrayList<float[]> points = new ArrayList<float[]>();

  Curve(PApplet sketch, float x, float y, ArrayList<float[]> points, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    setSettings(points);
  }

  protected void init(PApplet sketch){
    super.init(sketch);
    setSettings(points);
  }

  protected void setSettings(ArrayList<float[]> points){
    float xmin = Float.MAX_VALUE, ymin = Float.MAX_VALUE, xmax = Float.MIN_VALUE, ymax = Float.MIN_VALUE;
    for(float[] point : points){
      xmin = Math.min(point[0], xmin);
      ymin = Math.min(point[1], ymin);
      xmax = Math.max(point[0], xmax);
      ymax = Math.max(point[1], ymax);
      pixelWidth = Math.abs(xmax - xmin);
      pixelHeight = Math.abs(ymax - ymin);
      xcenter = (xmin + xmax)/2;
      ycenter = (ymin + ymax)/2;
      point[0] -= xcenter;
      point[1] -= ycenter;
      this.points.add(point);
    }
    setFillColor(255,255,255,0);
  }

  protected void display(){
    super.display();
    sketch.bezier(points.get(0)[0], points.get(0)[1], points.get(1)[0], points.get(1)[1], points.get(2)[0], points.get(2)[1], points.get(3)[0], points.get(3)[1]);
  }
/*
  protected boolean withinScope(float x, float y) {
    boolean yes = super.withinScope(x, y);
    System.out.println(shape.contains(x-xpos, y-ypos));
    return yes;
  }
*/
}
