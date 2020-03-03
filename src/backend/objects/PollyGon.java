package backend.objects;

import backend.Shape;
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;
import processing.core.PShape;

import java.io.Serializable;
import java.util.ArrayList;


class PollyGon extends Shape implements Serializable {
  private static final long serialVersionUID = 13L;
  private ArrayList<float[]> points = new ArrayList<float[]>();

  PollyGon(PApplet sketch, float x, float y, ArrayList<float[]> points, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    xpos = x;
    ypos = y;
    float xmin = Float.MAX_VALUE, ymin = Float.MAX_VALUE, xmax = Float.MIN_VALUE, ymax = Float.MIN_VALUE;
    for(float[] point : points){
      point[0] -= xpos;
      point[1] -= ypos;
      xmin = Math.min(point[0], xmin);
      ymin = Math.min(point[1], ymin);
      xmax = Math.max(point[0], xmax);
      ymax = Math.max(point[1], ymax);
    }
    this.points = points;
    pixelWidth = Math.abs(xmax - xmin);
    pixelHeight = Math.abs(ymax - ymin);
    xcenter = (xmin + xmax)/2 + xpos;
    ycenter = (ymin + ymax)/2 + ypos;
    createShape();
    setFillColor(255,255,255,0);
    setSettings();
  }

  protected void init(PApplet sketch){
    super.init(sketch);
    float xmin = Float.MAX_VALUE, ymin = Float.MAX_VALUE, xmax = Float.MIN_VALUE, ymax = Float.MIN_VALUE;
    for(float[] point : points){
      xmin = Math.min(point[0], xmin);
      ymin = Math.min(point[1], ymin);
      xmax = Math.max(point[0], xmax);
      ymax = Math.max(point[1], ymax);
    }
    pixelWidth = Math.abs(xmax - xmin);
    pixelHeight = Math.abs(ymax - ymin);
    createShape();
    setSettings();
  }

  public void pan(float xo, float yo) {
    super.pan(xo, yo);
    for (float[] point : points) {
      point[0] += xo;
      point[1] += yo;
    }
  }

  protected void createShape(){
    shape = sketch.createShape();
    shape.beginShape();
    shape.strokeWeight(strokeWeight);
    shape.fill(fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
    shape.stroke(boarderColor[0], boarderColor[1], boarderColor[2], boarderColor[3]);
    this.points = (ArrayList<float[]>) this.points.clone();
    for(int i = 0; i<points.size(); i++){

      shape.vertex(points.get(i)[0], points.get(i)[1]);
    }

    if(points.size()>2) shape.endShape(PConstants.CLOSE);
    else shape.endShape();
  }

  public void setAlpha(int alpha) {
    super.setAlpha(alpha);
    createShape();
  }

  /*
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
    xcenter = (xmin + pixelWidth)/2;
    ycenter = (ymin + pixelHeight)/2;

    shape.beginShape();
    for(int i = 0; i<points.size(); i++){
      float[] pos = new float[]{points.get(i)[0], points.get(i)[1]};
      shape.vertex(pos[0]-xcenter, pos[1]-ycenter);
    }
    if(points.size()>2) shape.endShape(PConstants.CLOSE);
    else shape.endShape();
  }
  */
/*
  protected boolean withinScope(float x, float y) {
    boolean yes = super.withinScope(x, y);
    System.out.println(shape.contains(-xcenter+xpos, -ycenter+ypos));
    return yes;
  }
*/
  protected void display() {
      super.display();
      sketch.fill(0, 255);
      sketch.translate(-xcenter+xpos, -ycenter+ypos);
      sketch.shape(shape);
   }

}
