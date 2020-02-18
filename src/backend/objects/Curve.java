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
    setFillColor(255,255,255,0);
    setSettings(points);
  }

  protected void init(PApplet sketch){
    super.init(sketch);
    for(float[] point : points){
      point[0] += xpos;
      point[1] += ypos;
    }
    setSettings((ArrayList<float[]>)this.points.clone());
  }

  protected void setSettings(ArrayList<float[]> points){
    float xmin = Float.MAX_VALUE, ymin = Float.MAX_VALUE, xmax = Float.MIN_VALUE, ymax = Float.MIN_VALUE;
    for(float[] point : points){
      point[0] -= xpos;
      point[1] -= ypos;
      xmin = Math.min(point[0], xmin);
      ymin = Math.min(point[1], ymin);
      xmax = Math.max(point[0], xmax);
      ymax = Math.max(point[1], ymax);
      pixelWidth = Math.abs(xmax - xmin);
      pixelHeight = Math.abs(ymax - ymin);
      xcenter = (xmin + xmax)/2;
      ycenter = (ymin + ymax)/2;
      this.points.add(point);
    }
  }

  protected void display(){
    super.display();
    sketch.translate(-xcenter+xpos, -ycenter+ypos);
    sketch.bezier(points.get(0)[0], points.get(0)[1], points.get(1)[0], points.get(1)[1], points.get(2)[0], points.get(2)[1], points.get(3)[0], points.get(3)[1]);
  }
}
