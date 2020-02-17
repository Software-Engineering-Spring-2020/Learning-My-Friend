package backend.objects;
import backend.Shape;
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;

import java.io.Serializable;
import java.util.ArrayList;

class FreeForm extends Shape implements Serializable {
  private static final long serialVersionUID = 14L;
  private ArrayList<float[]> points = new ArrayList<float[]>();

  FreeForm(PApplet sketch, float x, float y, ArrayList<float[]> points, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    for(float[] point : points){
      this.points.add(point);
    }
    createShape(points);
    setSettings();
  }

  protected void init(PApplet sketch){
    super.init(sketch);
    createShape(points);
    setSettings();
  }

  protected void setSettings(){
    setFillColor(255,255,255,0);
    shape.setStroke(sketch.color(boarderColor[0], boarderColor[1], boarderColor[2]));
    setStrokeWeight(strokeWeight);
  }

  protected void createShape(ArrayList<float[]> points){
    float xmin = Float.MAX_VALUE, ymin = Float.MAX_VALUE, xmax = Float.MIN_VALUE, ymax = Float.MIN_VALUE;

    for(int i = 0; i<points.size(); i++){
      float[] pos = new float[]{points.get(i)[0], points.get(i)[1]};
      xmin = Math.min(pos[0], xmin);
      ymin = Math.min(pos[1], ymin);
      xmax = Math.max(pos[0], xmax);
      ymax = Math.max(pos[1], ymax);
    }

    pixelWidth = Math.abs(xmax - xmin);
    pixelHeight = Math.abs(ymax - ymin);
    xcenter = (xmin + xmax)/2;
    ycenter = (ymin + ymax)/2;

    shape.beginShape();
    for(int i = 0; i<points.size(); i++){
      float[] pos = new float[]{points.get(i)[0], points.get(i)[1]};
      shape.vertex(pos[0]-xcenter, pos[1]-ycenter);
    }
    shape.endShape();
  }

  protected boolean withinScope(float x, float y) {
    boolean yes = super.withinScope(x, y);
    System.out.println(shape.contains(x-xpos, y-ypos));
    return yes;
  }
}
