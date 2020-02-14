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

    public TextBox createTextBox(float x, float y, int[] fillColor, int[] boarderColor, String str, String font, float textSize){
        return new TextBox(sketch, x, y, fillColor, boarderColor, str, font, textSize);
    }

    public Comment createComment(float x, float y, int[] fillColor, int[] boarderColor, String str, String font, float textSize){
        return new Comment(sketch, x, y, fillColor, boarderColor, str, font, textSize);
    }

    public Shape createShape(float x, float y, char shape, float strokeWeight, int[] fillColor, int[] boarderColor){
        if(shape == 'e') return createCircle(x, y, strokeWeight, fillColor, boarderColor);
        else if(shape == 'r') return createRect(x, y, 100, 50, strokeWeight, fillColor, boarderColor);
        return null;
    }

    public Ellipse createEllipse(float x, float y, float w, float h, float strokeWeight, int[] fillColor, int[] boarderColor){
        return new Ellipse(sketch, x, y, w, h, strokeWeight, fillColor, boarderColor);
    }

    public Ellipse createCircle(float x, float y, float d,  float strokeWeight, int[] fillColor, int[] boarderColor){
        return new Ellipse(sketch, x, y, d, strokeWeight, fillColor, boarderColor);
    } public Ellipse createCircle(float x, float y,  float strokeWeight, int[] fillColor, int[] boarderColor){
        return new Ellipse(sketch, x, y, strokeWeight, fillColor, boarderColor);
    }


    public Rectangle createRect(float x, float y, float w, float h, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, x, y, w, h, strokeWeight, fillColor, boarderColor);
    }

    public Rectangle createSquare(float x, float y, float d, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, x, y, d, strokeWeight, fillColor, boarderColor);
    } public Rectangle createRect(float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, x, y, strokeWeight, fillColor, boarderColor);
    }

    public FreeForm createFreeForm(float x, float y, ArrayList<PVector> points, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new FreeForm(sketch, x, y, points, strokeWeight, fillColor, boarderColor);
    }

    public FreeForm createLine(float x, float y, ArrayList<PVector> points, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new FreeForm(sketch, x, y, points, strokeWeight, fillColor, boarderColor);
    }

    public PollyGon createPollyGon(float x, float y, ArrayList<PVector> points, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new PollyGon(sketch, x, y, points, strokeWeight, fillColor, boarderColor);
    }

}
