package backend.objects;
import backend.Shape;
import processing.core.PApplet;
import processing.core.PConstants;
import java.util.ArrayList;

class FreeForm extends Shape {
  private ArrayList<float[]> points = new ArrayList<float[]>();

  FreeForm(PApplet sketch, float x, float y, ArrayList<float[]> points, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    this.points = points;
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

  protected void display(){
    //super.display();
    //sketch.translate(xpos, ypos);
    //sketch.scale(zoom);
    sketch.shape(shape);
  }

  protected void createShape(ArrayList<float[]> points){
  	//shape = createShape();
    sketch.noFill();
    shape.beginShape();
    for(int i = 0; i<points.size(); i++){
      shape.vertex(points.get(i)[0], points.get(i)[1]);
    }
    shape.endShape();
  }

}
