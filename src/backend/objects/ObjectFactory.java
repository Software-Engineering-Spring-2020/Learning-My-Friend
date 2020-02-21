package backend.objects;
import backend.Shape;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PConstants;
import processing.core.PVector;
import java.util.ArrayList;

public class ObjectFactory {
    protected  PApplet sketch;
    public ObjectFactory(PApplet sketch){
        this.sketch = sketch;
    }

    public Image importImage(float x, float y, String filename, String extension){
        return new Image(sketch, x, y, filename, extension);
    }

    public TextBox createTextBox(float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor, String str, String font, float textSize){
        return new TextBox(sketch, x, y, strokeWeight, fillColor, boarderColor, str, font, textSize);
    }

    public InteractiveTextBox createInteractiveTextBox(float x, float y, float x2, float strokeWeight, int[] fillColor, int[] boarderColor, String font, float textSize) {
        return new InteractiveTextBox(sketch, x, y, x2, strokeWeight, fillColor, boarderColor, font, textSize);
    }

    public Comment createComment(float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor, String str, String font, float textSize){
        return new Comment(sketch, x, y, strokeWeight, fillColor, boarderColor, str, font, textSize);
    }

    public Ellipse createEllipse(float x, float y, float w, float h, float strokeWeight, int[] fillColor, int[] boarderColor){
        return new Ellipse(sketch, x, y, w, h, strokeWeight, fillColor, boarderColor);
    }

    public Ellipse createEllipse(float x, float y, float d,  float strokeWeight, int[] fillColor, int[] boarderColor){
        return new Ellipse(sketch, x, y, d, strokeWeight, fillColor, boarderColor);
    } public Ellipse createEllipse(float x, float y,  float strokeWeight, int[] fillColor, int[] boarderColor){
        return new Ellipse(sketch, x, y, strokeWeight, fillColor, boarderColor);
    }


    public Rectangle createRect(float x, float y, float w, float h, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, x, y, w, h, strokeWeight, fillColor, boarderColor);
    }

    public Rectangle createRect(float x, float y, float d, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, x, y, d, strokeWeight, fillColor, boarderColor);
    } public Rectangle createRect(float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, x, y, strokeWeight, fillColor, boarderColor);
    }

    public FreeForm createFreeForm(ArrayList<float[]> points, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new FreeForm(sketch, points.get(0)[0], points.get(0)[1], points, strokeWeight, fillColor, boarderColor);
    }

    public FreeForm createLine(ArrayList<float[]> points, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new FreeForm(sketch, points.get(0)[0], points.get(0)[1], points, strokeWeight, fillColor, boarderColor);
    }

    public PollyGon createPollyGon(ArrayList<float[]> points, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new PollyGon(sketch, points.get(0)[0], points.get(0)[1], points, strokeWeight, fillColor, boarderColor);
    }

    public Curve createCurve(ArrayList<float[]> points, float strokeWeight, int[] fillColor, int[] boarderColor){
      return new Curve(sketch, points.get(0)[0], points.get(0)[1], points, strokeWeight, fillColor, boarderColor);
    }

}
