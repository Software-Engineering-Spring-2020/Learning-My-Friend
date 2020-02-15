package backend.objects;
import backend.Shape;
import processing.core.PApplet;;
import processing.core.PVector;
import processing.core.PConstants;
import java.util.ArrayList;


class Curve extends Shape {
  private ArrayList<float[]> points = new ArrayList<float[]>();

  Curve(PApplet sketch, float x, float y, ArrayList<float[]> points, float strokeWeight, int[] fillColor, int[] boarderColor){
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
    shape.beginShape();
    for(int i = 0; i<points.size(); i++){
      shape.curveVertex(points.get(i)[0]-xpos, points.get(i)[1]-ypos);
    }
    shape.endShape();
  }

}
