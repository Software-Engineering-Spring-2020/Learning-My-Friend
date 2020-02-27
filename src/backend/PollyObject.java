package backend;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import java.io.Serializable;

/**
* The most basic form of all objects that get added to slides (Back and White)
*/
public abstract class PollyObject implements Serializable {
    private static final long serialVersionUID = 10L;
    transient protected PApplet sketch;
    protected float xpos, ypos, rot = 0, pixelWidth, pixelHeight;
    transient protected float prevRot = 0, ro = 0;
    protected float zoom = 1, offset = 3F, xcenter, ycenter;
    protected String link;

    /**
    * Constructor for PollyObject
    * @param sketch A reference to a PApplet to allow general functionality of the processing library
    * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
    */
    public PollyObject(PApplet sketch, float x, float y) {
        this.sketch = sketch;
        xpos = x;
        ypos = y;
        xcenter = xpos;
        ycenter = ypos;
    }

    /**
    * Function used during the serialization process to restore transient processing library-related variables
    * @param sketch a reference to a PApplet to allow general functionality of the processing library
    */
    protected void init(PApplet sketch){
      this.sketch = sketch;
    }

    /**
    * Get the current xy position of the object
    * @return The current xy position of the oject in slide-relative coordinates
    */
    public float[] getPosition() {
        return new float[]{xpos, ypos};
    }

    /**
    * Set xy position of the object
    * @param x The current x position of the oject in slide-relative coordinates
    * @param y The current y position of the oject in slide-relative coordinates
    */
    public void setPosition(float x, float y) {
        xcenter += (x - xpos);
        ycenter += (y - ypos);
        xpos = x;
        ypos = y;
    }

    protected void setCenter(float x, float y) {
        xpos += (x - xcenter);
        ypos += (y - ycenter);
        xcenter = x;
        ycenter = y;
    }

    /**
    * Set raw rotation angle of the object
    * @default 0 The original and inital rotation of all objects is 0
    * @param r The desired raw rotation angle in degrees (from the original rotation NOT relative from current rotation)
    */
    protected void setRotate(float r) {
        rot = r % 360;
    }

    /**
    * Set relative angle change in rotation of the object
    * @default 0 The original and inital rotation of all objects is 0
    * @param r The desired raw rotation angle in degrees (from the original rotation NOT relative from current rotation)
    */
    protected void setRelativeRotate(float r) {
      ro = r - prevRot;
      if(sketch.abs(ro) > 0){
        rot += ro;
        rot = rot%360;
      }
        prevRot = r;
    }

    /**
    * Get the current dimension and position of the object
    * @return The current xy position of the oject in slide-relative coordinates followed by the width and height, respecively (both in pixels)
    */
    protected float[] getDimensions(){
        return new float[]{xpos, ypos, pixelWidth, pixelHeight};
    }

    /**
    * Checks if the mouse is over the object (accounts for rotation and translation)
    * @param x The x position of the mouse in slide-relative coordinates
    * @param y The y position of the mouse in slide-relative coordinates
    * @return Whether or not the mouse is over the object
    */
    protected boolean withinScope(float x, float y) {
        // approach: rotate the given x, y by the NEGATIVE current rotation
        // this will move it back into the original bounding box (based on width and height)
        // if it is within the shape

        float width = pixelWidth;
        float height = pixelHeight;


        PVector rotatedPoint = rotateAbout(new PVector(x, y), new PVector(xcenter, ycenter), -rot);

        if ((rotatedPoint.x >= xcenter - width / 2 && rotatedPoint.x <= xcenter + width / 2) &&
            (rotatedPoint.y >= ycenter - height / 2 && rotatedPoint.y <= ycenter + height / 2)) {
                return true;
        }
        return false;
    }

    /**
    * Draws an orange rectangle representing the selection field of the object.
    */
    protected void showBoundingBox(){
      sketch.push();
      sketch.noFill();
      sketch.stroke(215,165,0);
      sketch.strokeWeight(2);
      PVector[] vert = getRotatedBoundingBoxPoints(xcenter, ycenter);
      sketch.quad(vert[0].x, vert[0].y, vert[1].x, vert[1].y, vert[2].x, vert[2].y, vert[3].x, vert[3].y);
      sketch.pop();
    }

    /**
    * Generate four points representing the verticies of the rectangular selection field (Does not account for rotation)
    * @param xcenter The x position representing the center of rotation (in slide-relative coordinates)
    * @param ycenter The y position representing the center of rotation (in slide-relative coordinates)
    * @return Array of the vertex positions in slide-relative coordinates
    */
    protected PVector[] getBoundingBoxPoints(float xcenter, float ycenter) {
        float width = pixelWidth*zoom;
        float height = pixelHeight*zoom;
        offset = 3;
        // this uses the existing bounding box system as much as possible
        // using width and height to generate four points
        // these points are NOT rotated per this PollyObject's rotation

        // represented with four PVectors: topLeft, topRight, bottomRight, bottomLeft (like NESW)
        PVector[] boundingBoxPoints = new PVector[4];
        // topLeft
        boundingBoxPoints[0] = new PVector(-width/2 + xcenter - offset, -height/2 + ycenter - offset);
        // topRight
        boundingBoxPoints[1] = new PVector(width/2 + xcenter + offset, -height/2 + ycenter - offset);
        // bottomRight
        boundingBoxPoints[2] = new PVector(width/2 + xcenter + offset, height/2 + ycenter + offset);
        // bottomLeft
        boundingBoxPoints[3] = new PVector(-width/2 + xcenter - offset, height/2 + ycenter + offset);
        return boundingBoxPoints;
    }

    /**
    * Generate four points representing the verticies of the rectangular selection field (Accounts for rotation)
    * @param xcenter The x position representing the center of rotation (in slide-relative coordinates)
    * @param ycenter The y position representing the center of rotation (in slide-relative coordinates)
    * @return Array of the vertex positions in slide-relative coordinates
    */
    protected PVector[] getRotatedBoundingBoxPoints(float xcenter, float ycenter) { //Can Display
        float rot = this.rot;
        PVector[] rotatedBoundingBoxPoints = new PVector[4];
        PVector[] boundingBoxPoints = getBoundingBoxPoints(xcenter, ycenter);
        PVector center = new PVector(xcenter, ycenter);
        for (int i = 0; i < 4; i++) {
            rotatedBoundingBoxPoints[i] = rotateAbout(boundingBoxPoints[i], center, rot);
        }
        return rotatedBoundingBoxPoints;
    }

    /**
    * Calculate the new position of a rotated point
    * @param rotatePoint The point being rotated (in slide-relative coordinates)
    * @param anchor The point to rotate about (in slide-relative coordinates)
    * @param degrees The desired rotation amount in degrees
    * @return The new xy position in slide-relative coordinates
    */
    protected PVector rotateAbout(PVector rotatePoint, PVector anchor, float degrees) {
        PVector finalPoint = new PVector(0, 0);
        float radians = (float) (Math.PI / 180) * degrees;

        // make finalPoint like rotatePoint as if aboutPoint were the origin
        finalPoint.x = rotatePoint.x - anchor.x;
        finalPoint.y = rotatePoint.y - anchor.y;

        // rotate finalPoint about the origin
        float tempX = finalPoint.x * (float) Math.cos(radians) -  finalPoint.y * (float) Math.sin(radians);
        float tempY = finalPoint.x * (float) Math.sin(radians) +  finalPoint.y * (float) Math.cos(radians);

        // translate finalPoint back to where it should be based on aboutPoint
        finalPoint.x = tempX + anchor.x;
        finalPoint.y = tempY + anchor.y;

        return finalPoint;
    }

    /**
    * Template to draw an object to the slide
    */
    protected void display() {
        sketch.translate(xcenter, ycenter);
        sketch.rotate((float) Math.toRadians(rot));
        sketch.scale(zoom);
     }

     /**
     * Move the object (Accounts for any zoom factor)
     * @param xo Relative amount to move the object in the x dimension (in slide-related pixel amounts)
     * @param yo Relative amount to move the object in the y dimension (in slide-related pixel amounts)
     */
    public void pan(float xo, float yo){
        xpos = xpos + xo;
        ypos = ypos + yo;
        xcenter += xo;
        ycenter += yo;
    }

    /**
    * Alter the size of the object from its center. If any change should decrease the zoom below 10%, it is set to 10% instead. Objectstarts at 100%
    * @param factor The amount to scale the object (in percentage) from the current size
    */
    protected void resize(float factor) {
        //pixelHeight *= (1+factor);
        //pixelWidth *= (1+factor);
        zoom = Math.max(.1F, 1 + factor);
        //pixelHeight *= zoom;
        //pixelWidth *= zoom;
    }
}
