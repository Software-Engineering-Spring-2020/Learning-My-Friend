package backend.Shapes;
import backend.ColorfulObject;
import processing.core.PApplet;

public class ShapeFactory {
    protected  PApplet sketch;
    public ShapeFactory(PApplet sketch){
        this.sketch = sketch;
    }

    public ColorfulObject createShape(float x, float y, char shape, int[] fillColor, int[] boarderColor){
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
