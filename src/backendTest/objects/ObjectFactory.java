package backend.objects;
import backend.Shape;
import processing.core.PApplet;
import processing.core.Graphics;
import processing.core.PShape;
import processing.core.PConstants;
import processing.core.PVector;
import java.util.ArrayList;

public class ObjectFactory {
    protected  PApplet sketch;
    public ObjectFactory(PApplet sketch){
        this.sketch = sketch;
    }


    public Rectangle createRect(PGraphics graphic, float x, float y, float w, float h, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, graphic, x, y, w, h, strokeWeight, fillColor, boarderColor);
    }
}
