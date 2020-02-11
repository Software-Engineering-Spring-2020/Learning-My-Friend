package backend;

import backend.Shapes.ShapeFactory;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Stack;

public class Window {
    private Stack<PollyObject> trash = new Stack<PollyObject>();
    private ArrayList<PollyObject> selected = new ArrayList<PollyObject>();
    private PApplet sketch;
    private DrawSpace ds;
    private ShapeFactory sf;
    private float zoom = 1;
    private int[] fillColor, boarderColor;

    public Window(PApplet sketch, float x, float y, float w, float h) {
        this.sketch = sketch;
        ds = new DrawSpace(sketch, x, y, w, h);
        sf = new ShapeFactory(sketch);
        fillColor = new int[]{0, 0, 0};
        boarderColor = new int[]{0, 0, 0};
    }

    /*********************************************************
     *
     *
     *          DRAWSPACE RELATED FUNCTIONALITY
     *
     *
     *********************************************************/
    public void createDrawSpace(PApplet sketch, float x, float y, float w, float h) {
        this.ds = new DrawSpace(sketch, x, y, w, h);
    }

    public void zoom(float factor) { //draw offcenter once zoom
        zoom = sketch.max((float) .01, zoom + factor);
    }

    public void display() {
        this.ds.display(zoom);
    }

    public void canvasPan(float xo, float yo) { //(this.mouseX - this.pmouseX), (this.mouseY - this.pmouseY)
        this.ds.pan(xo, yo);
    }

    public void reCenter() {
        zoom = 1;
        this.ds.display(zoom);
    }

    public boolean createShape(float x, float y, char shape) {
        float[] coord = ds.translateCoordinates(x, y, zoom);
        if (ds.withinScope(coord[0], coord[1])) {
            trash.clear();
            selected.add((sf.createShape(coord[0], coord[1], shape, fillColor, boarderColor)));
            return ds.addShape(sf.createShape(coord[0], coord[1], shape, fillColor, boarderColor));
        }
        return false;
    }

    public void setFillColor(int r, int g, int b) {
        fillColor[0] = r;
        fillColor[1] = g;
        fillColor[2] = b;
    }

    public void setBoarderColor(int r, int g, int b) {
        boarderColor[0] = r;
        boarderColor[1] = g;
        boarderColor[2] = b;
    }

    /*********************************************************
     *
     *
     *          TOOLBAR RELATED FUNCTIONALITY
     *
     *
     *********************************************************/
    public void rotate(float ao) {
        for (PollyObject shape : selected) {
            shape.setRelativeRotate(ao);
        }
    }

    public void singleSelect(float x, float y){
        selected.clear();
        selected.add(ds.getObjectAt(x, y, zoom));
    }

    public void multiSelect(float x, float y){
        selected.add(ds.getObjectAt(x, y, zoom));
    }

    public boolean deleteSelected() { //not removing when pass in shape object
        boolean successful = false;
        for (PollyObject shape : selected) {
            trash.add(shape);
            successful = successful || true;
            sketch.println(ds.indexOf(shape));
        }
        return successful;
    }

    public boolean undo() {
        if(ds.getNumObjects() <= 0) return false;
        //return trash.push(ds.removeShape(ds.getNumObjects()-1));
        return false;
    }

    public boolean redo() {
        if (trash.isEmpty()) return false;
        return ds.addShape(trash.pop());
    }

    /*********************************************************
     *
     *
     *          SAVE/LOAD/IMPORT/EXPORT RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

}
