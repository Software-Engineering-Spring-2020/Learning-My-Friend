package backend;

import backend.shapes.ShapeFactory;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Stack;

public class Window {
    //private Stack<PollyObject> trash = new Stack<PollyObject>();
    private ArrayList<PollyObject> trash = new ArrayList<PollyObject>();
    private ArrayList<PollyObject> selected = new ArrayList<PollyObject>();
    private ArrayList<PollyObject> copied = new ArrayList<PollyObject>();
    private PApplet sketch;
    private DrawSpace ds;
    private ShapeFactory sf;
    private float zoom = 1;
    private int[] fillColor, boarderColor;
    private float XINIT, YINIT, gridSpacing = 30;
    private boolean showGrid = false;

    public Window(PApplet sketch, float x, float y, float w, float h) {
        this.sketch = sketch;
        ds = new DrawSpace(sketch, x, y, w, h);
        sf = new ShapeFactory(sketch);
        fillColor = new int[]{0, 0, 0};
        boarderColor = new int[]{0, 0, 0};
        XINIT = x;
        YINIT = y;
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
        sketch.push();
        this.ds.display(zoom, showGrid, gridSpacing);
        sketch.pop();
    }

    public boolean withinCanvas(float x, float y){
        float[] coord = ds.translateCoordinates(x, y, zoom);
        return ds.withinScope(coord[0], coord[1]);
    }

    public void canvasPan(float xo, float yo) { //(this.mouseX - this.pmouseX), (this.mouseY - this.pmouseY)
        this.ds.pan(xo, yo);
    }

    public void reCenter() {
        zoom = 1;
        this.ds.setPosition(XINIT, YINIT);
        this.display();
    }

    public void toggleGrid(){
        if(showGrid) showGrid = false;
        else showGrid = true;
    }
    public void setGridSpacing(float spacing){
        gridSpacing = spacing;
    }

      /*********************************************************
     *
     *
     *          TOOLBAR RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

    public boolean createShape(float x, float y, char shape) {
        float[] coord = ds.translateCoordinates(x, y, zoom);
        if (ds.withinScope(coord[0], coord[1])) {
            trash.clear();
            PollyObject obj = sf.createShape(coord[0], coord[1], shape, fillColor, boarderColor);
            if(obj != null){
                selected.add(obj);
                return ds.addShape(obj);
            }
        }
        return false;
    }

    public void selectedPan(float xo, float yo){
        for(PollyObject shape : selected){
            //float[] coord = ds.relativePan(xo, yo, zoom);
            //shape.pan(coord[0], coord[1]);
            shape.pan(xo, yo);
        }
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

    public boolean deleteSelected() {
        boolean successful = true;
        for (PollyObject shape : selected) {
            trash.add(shape);
            if(!ds.removeShape(shape)) successful = false;
        }
        return successful;
    }

    public boolean deleteLast() {
        if(ds.getNumObjects() <= 0) return false;
        return trash.add(ds.removeShape(ds.getNumObjects()-1));
    }

    public boolean restoreLast() {
        sketch.println(trash.size());
        if (trash.isEmpty()) return false;
        return ds.addShape(trash.remove(trash.size()-1));
    }

    public void clear(){
        for(PollyObject shape : ds.getAllObjects()){
            sketch.println(ds.getNumObjects()+" : "+trash.size());
            trash.add(shape);
        }
        ds.clear();

        sketch.println(ds.getNumObjects()+" : "+trash.size());
    }

    public boolean copy(){
        boolean sucess = true;
        copied.clear();
        for(PollyObject shape : selected){
            sucess = sucess && copied.add(shape);
        }
        return sucess;
    }

    public void paste(){
        for(PollyObject shape : copied){
            float[] pos = shape.getPosition();
            if(shape instanceof ColorfulObject){
                int[] fill = ((ColorfulObject) shape).getFillColor();
                int[] boarder = ((ColorfulObject) shape).getBoarderColor();
                sf.createShape(pos[0]+2, pos[1]+2, 'r', fill, boarder); //not full copy
            } else{
                //ds.addShape();
            }
        }
    }

    /*********************************************************
     *
     *
     *          DRAWING RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

    public void setThickness(int size){
        int weight = sketch.max(size, 1);
        sketch.strokeWeight(weight);
    }


    /*********************************************************
     *
     *
     *          SAVE/LOAD/IMPORT/EXPORT RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

}
