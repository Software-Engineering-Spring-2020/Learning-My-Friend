package backend.objects;
import backend.Shape;
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PConstants;
import java.util.ArrayList;

class FreeForm extends Shape {
  private ArrayList<float[]> points = new ArrayList<float[]>();
  private float xcenter, ycenter;

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
    shape.beginShape();
    for(int i = 0; i<points.size(); i++){
      float[] pos = new float[]{points.get(i)[0]-xpos, points.get(i)[1]-ypos};
      shape.vertex(pos[0], pos[1]);
      xmin = sketch.min(pos[0], xmin);
      ymin = sketch.min(pos[1], ymin);
      xmax = sketch.max(pos[0], xmax);
      ymax = sketch.max(pos[1], ymax);
    }
    shape.endShape();
    pixelWidth = xmax - xmin;
    pixelHeight = ymax - ymin;
    xcenter = xmin + pixelWidth/2;
    ycenter = ymin + pixelHeight/2;
  }

  protected void display(){
    super.display();
    sketch.push();
    sketch.strokeWeight(3);
    sketch.point(xcenter, ycenter);
    sketch.pop();
  }

  /*protected PVector[] getBoundingBoxPoints() {
    PVector[] points = super.getBoundingBoxPoints();
  }*/

}
