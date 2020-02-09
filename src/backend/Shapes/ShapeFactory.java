package backend.Shapes;
import backend.ColorfulObject;
import processing.core.PApplet;

public class ShapeFactory {
    protected  PApplet sketch;
    public ShapeFactory(PApplet sketch){
        this.sketch = sketch;
    }

    public ColorfulObject createShape(float x, float y, char shape){
        if(shape == 'e') return createCircle(x, y);
        return null;
    }

    public Ellipse createEllipse(float x, float y, float w, float h){
        return new Ellipse(sketch, x, y, w, h);
    }

    public Ellipse createCircle(float x, float y, float d){
        return new Ellipse(sketch, x, y, d);
    } public Ellipse createCircle(float x, float y){
        return new Ellipse(sketch, x, y);
    }


}
