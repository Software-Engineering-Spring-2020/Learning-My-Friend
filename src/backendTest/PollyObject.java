package backend;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PConstants;
import processing.core.PVector;
import java.io.Serializable;

/**
* The most basic form of all objects that get added to slides (Back and White)
*/
public abstract class PollyObject implements Serializable {
    private static final long serialVersionUID = 10L;
    transient protected PApplet sketch;

    transient protected PGraphics graphic;

    protected float xpos, ypos, rot = 0, pixelWidth, pixelHeight;
    transient protected float prevRot = 0, ro = 0;
    protected float zoom = 1, offset = 3F, xcenter = xpos, ycenter = ypos;

    /**
    * Constructor for PollyObject
    * @param sketch A reference to a PApplet to allow general functionality of the processing library
    * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
    */
    public PollyObject(PApplet sketch, PGraphics graphic, float x, float y) {
        this.sketch = sketch;
        this.graphic = graphic;
        xpos = x;
        ypos = y;
        xcenter = xpos;
        ycenter = ypos;
    }

    /**
    * Template to draw an object to the slide
    */
    protected void display() {
        graphic.translate(xcenter, ycenter);
        graphic.rotate((float) Math.toRadians(rot));
        graphic.scale(zoom);
     }

}
