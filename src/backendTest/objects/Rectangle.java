package backend.objects;
import java.io.Serializable;

import backend.Shape;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PConstants;

class Rectangle extends Shape implements Serializable {
    private static final long serialVersionUID = 7L;
    Rectangle(PApplet sketch, PGraphics graphic, float x, float y, float w, float h, float strokeWeight, int[] fillColor, int[] boarderColor){
        super(sketch, graphic, x, y, strokeWeight, fillColor, boarderColor);
        this.pixelWidth = w;
        this.pixelHeight = h;
        shape = graphic.createShape(PConstants.RECT, 0, 0, this.pixelWidth, this.pixelHeight);
        setFillColor(255,255,255,0);
        setSettings();
    }

    Rectangle(PApplet sketch, PGraphics graphic, float x, float y, float d, float strokeWeight,  int[] fillColor, int[] boarderColor){
        this(sketch, graphic, x, y, d, d, strokeWeight, fillColor, boarderColor);
    }
    Rectangle(PApplet sketch, PGraphics graphic, float x, float y, float strokeWeight,  int[] fillColor, int[] boarderColor){
        this(sketch, graphic, x, y, 50, 25, strokeWeight, fillColor, boarderColor);
    }

    protected void display(){
      super.display();
      graphic.shape(shape);
    }
}
