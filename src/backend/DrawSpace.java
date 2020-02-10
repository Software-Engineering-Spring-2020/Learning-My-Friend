package backend;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;
//import processing.core.*;

class DrawSpace extends ColorfulObject{
    private float pixelWidth, pixelHeight, xcenter, ycenter;
    private ArrayList<PollyObject> objects = new ArrayList<PollyObject>();

    DrawSpace(PApplet sketch, float x, float y, float w, float h){
        super(sketch, x, y);
        pixelWidth = w;
        pixelHeight = h;
        int[] white = {255,255,255};
        fillColor = white;
        boarderColor = white;
        boundingBox[0] = pixelWidth/2;
        boundingBox[1] = pixelHeight/2;
        xcenter = xpos+pixelWidth/2;
        ycenter = ypos+pixelHeight/2;
        sketch.rectMode(PConstants.CENTER);
    }

    protected ArrayList<PollyObject> getAllObjects(){
        return objects;
    }

    protected Object getObjectAt(float x, float y, float zoom){
        float[] pos = translateCoordinates(x, y, zoom);
        return null;
    }

    protected int getNumObjects(){
        return objects.size();
    }

    /*protected float[] translateCoordinates(float x, float y, float zoom){
        float[] coord = new float[]{x-xpos, y-ypos}; //translates from coordinates to relational fraction
        sketch.println(coord[0]+ " : "+coord[1]);


        coord[0] = (pixelWidth * (coord[0] / (pixelWidth*zoom))) - pixelWidth/2;
        coord[1] = (pixelHeight * (coord[1] / (pixelHeight*zoom))) - pixelHeight/2;
        return coord;
    } */

    protected float[] translateCoordinates(float x, float y, float zoom){
        float[] coord = new float[]{x-xcenter, y-ycenter}; //translates from coordinates to relational fraction
        sketch.println(coord[0]+ " : "+coord[1]);


        coord[0] = (pixelWidth/2) * (coord[0] / ((pixelWidth/2)*zoom));
        coord[1] = (pixelHeight/2) * (coord[1] / ((pixelHeight/2)*zoom));
        return coord;
    }

    protected void pan(float x, float y){
        super.pan(x, y);
        xcenter = xpos+pixelWidth/2;
        ycenter = ypos+pixelHeight/2;
    }

    protected void display(float zoom){
        super.display();
        sketch.translate(xcenter, ycenter);
        sketch.scale(zoom);
        sketch.rect(0, 0, pixelWidth, pixelHeight);
        for(PollyObject obj : objects){
            sketch.pushMatrix();
            obj.display();
            sketch.popMatrix();
        }
    }

    protected boolean addShape(PollyObject shape){
        return objects.add(shape);
    }

    protected boolean removeShape(PollyObject shape){
        return objects.remove(shape);
    } protected PollyObject removeShape(int i){
        return objects.remove(i);
    }

    protected float[] getDimensions(){
        return new float[]{pixelWidth, pixelHeight};
    }
}