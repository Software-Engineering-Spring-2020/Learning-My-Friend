package backend.objects;

import backend.Shape;
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;

import java.io.Serializable;
import java.util.ArrayList;


class PollyGon extends Shape implements Serializable {
  private static final long serialVersionUID = 13L;
  private ArrayList<float[]> points = new ArrayList<float[]>();

  PollyGon(PApplet sketch, float x, float y, ArrayList<float[]> points, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    for(float[] point : points){
      this.points.add(point);
    }
    createShape(points);
    setSettings();
    this.pixelWidth = shape.width;
    this.pixelHeight = shape.height;
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
    shape.beginShape();
    for(int i = 0; i<points.size(); i++){
      shape.vertex(points.get(i)[0]-xpos, points.get(i)[1]-ypos);
      xmin = Math.min(points.get(i)[0], xmin);
      ymin = Math.min(points.get(i)[1], ymin);
      xmax = Math.max(points.get(i)[0], xmax);
      ymax = Math.max(points.get(i)[1], ymax);
    }
    pixelWidth = Math.abs(xmax - xmin);
    pixelHeight = Math.abs(ymax - ymin);
    xcenter = (xmin + xmax)/2;
    ycenter = (ymin + ymax)/2;
    //if(points.size()>2) shape.endShape(PConstants.CLOSE);
    //else shape.endShape();
    shape.endShape(PConstants.CLOSE);
  }

}
