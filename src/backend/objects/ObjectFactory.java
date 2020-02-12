package backend.objects;
import backend.Shape;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PConstants;

public class ObjectFactory {
    protected  PApplet sketch;
    public ObjectFactory(PApplet sketch){
        this.sketch = sketch;
    }

    public Image importImage(float x, float y, String filename, String extension){
        return new Image(sketch, x, y, filename, extension);
    }

    

    public Shape createShape(float x, float y, char shape, int[] fillColor, int[] boarderColor){
        if(shape == 'e') return createCircle(x, y, fillColor, boarderColor);
        else if(shape == 'r') return createRect(x, y, fillColor, boarderColor);
        return null;
    }

    public Ellipse createEllipse(float x, float y, float w, float h, int[] fillColor, int[] boarderColor){
        return new Ellipse(sketch, x, y, w, h, fillColor, boarderColor);
    }

    public Ellipse createCircle(float x, float y, float d, int[] fillColor, int[] boarderColor){
        return new Ellipse(sketch, x, y, d, fillColor, boarderColor);
    } public Ellipse createCircle(float x, float y, int[] fillColor, int[] boarderColor){
        return new Ellipse(sketch, x, y, fillColor, boarderColor);
    }


    public Rectangle createRect(float x, float y, float w, float h, int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, x, y, w, h, fillColor, boarderColor);
    }

    public Rectangle createSquare(float x, float y, float d, int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, x, y, d, fillColor, boarderColor);
    } public Rectangle createRect(float x, float y, int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, x, y, fillColor, boarderColor);
    }


}
