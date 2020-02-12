package backend;

import processing.core.PApplet;
import processing.core.PConstants;

public abstract class PollyObject {
    protected PApplet sketch;
    protected float xpos, ypos, rot = 0, pixelWidth, pixelHeight;
    protected boolean showBoundary;
    protected float[] boundingBox = new float[2]; //represented with centered at 0,0

    public PollyObject(PApplet sketch, float x, float y) {
        this.sketch = sketch;
        xpos = x;
        ypos = y;
    }

    protected float[] getPosition() {
        return new float[]{xpos, ypos};
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

    protected void display() {    }

    protected void pan(float xo, float yo){
        xpos = xpos + xo;
        ypos = ypos + yo;
    }

    protected void enlarge(float factor) {

    }
}