package backend;

import processing.core.PApplet;
import processing.core.PShape;

public abstract class PollyObject extends PShape {
    protected PApplet sketch;
    protected PShape shape;
    protected float xpos, ypos, rot = 0;
    protected boolean showBoundary;
    protected float[] boundingBox = new float[2]; //represented with centered at 0,0

    public PollyObject(PApplet sketch, float x, float y) {
        this.sketch = sketch;
        xpos = x;
        ypos = y;
    }

    protected void setPosition(float x, float y) {
        xpos = x;
        ypos = y;
    }

    protected void setRelativeRotate(float ro) {
        rot += ro;
    }

    protected float[] getDimensions() {
        return new float[]{-1, -1};
    }

    protected boolean withinScope(float x, float y) {
        if (x < boundingBox[0] && x > -boundingBox[0] && y < boundingBox[1] && y > -boundingBox[1]) return true;
        return false;
    }

    protected void display() {
        sketch.rotate(sketch.radians(rot));
    }

    protected void pan(float xo, float yo){
        xpos = xpos + xo;
        ypos = ypos + yo;
    }

    protected void enlarge(float factor) {

    }
}