package backend;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

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

    /* description: checks if the provided x, y are within the pivoted bounding box of this
    shape */
    protected boolean withinScope(float x, float y) {
        // approach: rotate the given x, y by the NEGATIVE current rotation
        // this will move it back into the original bounding box (based on width and height)
        // if it is within the shape

        float width = boundingBox[0];
        float height = boundingBox[1];


        PVector rotatedPoint = rotateAbout(new PVector(x, y), new PVector(xpos, ypos), -rot);

        if ((rotatedPoint.x >= xpos - width / 2 && rotatedPoint.x <= xpos + width / 2) && 
            (rotatedPoint.y >= ypos - height / 2 && rotatedPoint.y <= ypos + height / 2)) {
                return true;
        }
        return false;
    }

    protected PVector[] getBoundingBoxPoints() {
        float width = boundingBox[0];
        float height = boundingBox[1];

        // this uses the existing bounding box system as much as possible
        // using width and height to generate four points
        // these points are NOT rotated per this PollyObject's rotation

        // represented with four PVectors: topLeft, topRight, bottomRight, bottomLeft (like NESW)
        PVector[] boundingBoxPoints = new PVector[4];
        // topLeft
        boundingBoxPoints[0] = new PVector(-width/2 + xpos, -height/2 + ypos);
        // topRight
        boundingBoxPoints[1] = new PVector(width/2 + xpos, -height/2 + ypos);
        // bottomRight
        boundingBoxPoints[2] = new PVector(width/2 + xpos, height/2 + ypos);
        // bottomLeft
        boundingBoxPoints[3] = new PVector(-width/2 + xpos, height/2 + ypos);
        return boundingBoxPoints;
    }

    protected PVector[] getRotatedBoundingBoxPoints() {
        float rot = this.rot;
        PVector[] rotatedBoundingBoxPoints = new PVector[4];
        PVector[] boundingBoxPoints = getBoundingBoxPoints();
        PVector center = new PVector(xpos, ypos);
        for (int i = 0; i < 4; i++) {
            rotatedBoundingBoxPoints[i] = rotateAbout(boundingBoxPoints[i], center, rot);
        }
        return rotatedBoundingBoxPoints;
    }

    protected PVector rotateAbout(PVector rotatePoint, PVector aboutPoint, float degrees) {
        PVector finalPoint = new PVector(0, 0);
        float radians = (float) (Math.PI / 180) * degrees;

        // make finalPoint like rotatePoint as if aboutPoint were the origin
        finalPoint.x = rotatePoint.x - aboutPoint.x;
        finalPoint.y = rotatePoint.y - aboutPoint.y;

        // rotate finalPoint about the origin
        float tempX = finalPoint.x * (float) Math.cos(radians) -  finalPoint.y * (float) Math.sin(radians);
        float tempY = finalPoint.x * (float) Math.sin(radians) +  finalPoint.y * (float) Math.cos(radians);

        // translate finalPoint back to where it should be based on aboutPoint
        finalPoint.x = tempX + aboutPoint.x;
        finalPoint.y = tempY + aboutPoint.y;

        return finalPoint;
    }

    protected void display() {    }

    protected void pan(float xo, float yo){
        xpos = xpos + xo;
        ypos = ypos + yo;
    }

    protected void resize(float factor) {

    }
}