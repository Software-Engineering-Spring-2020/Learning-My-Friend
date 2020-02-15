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
  private float xcenter, ycenter;

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
      point[0] -= xpos;
      point[1] -= ypos;
      this.points.add(point);
      xmin = sketch.min(point[0], xmin);
      ymin = sketch.min(point[1], ymin);
      xmax = sketch.max(point[0], xmax);
      ymax = sketch.max(point[1], ymax);
      pixelWidth = xmax - xmin;
      pixelHeight = ymax - ymin;
      xcenter = xmin + pixelWidth/2;
      ycenter = ymin + pixelHeight/2;
    }
    setFillColor(255,255,255,0);
  }

  protected void display(){
    super.display();
    sketch.bezier(points.get(0)[0], points.get(0)[1], points.get(1)[0], points.get(1)[1], points.get(2)[0], points.get(2)[1], points.get(3)[0], points.get(3)[1]);
  }

}
