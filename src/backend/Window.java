package backend;

import backend.objects.ObjectFactory;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Window {
    private ArrayList<PollyObject> trash = new ArrayList<PollyObject>();
    private ArrayList<PollyObject> selected = new ArrayList<PollyObject>();
    private ArrayList<PollyObject> copied = new ArrayList<PollyObject>();
    private PApplet sketch;
    private DrawSpace ds;
    private ObjectFactory of;
    private float zoom = 1, strokeWeight = 1;
    private int[] fillColor, boarderColor;
    private float XINIT, YINIT, WIDTH, HEIGHT, gridSpacing = 30;
    private boolean showGrid = false, showComments = false;

    public Window(PApplet sketch, float x, float y, float w, float h) {
        this.sketch = sketch;
        ds = new DrawSpace(sketch, x, y, w, h);
        of = new ObjectFactory(sketch);
        fillColor = new int[]{0, 0, 0};
        boarderColor = new int[]{0, 0, 0};
        XINIT = x;
        YINIT = y;
        WIDTH = w;
        HEIGHT = h;
    }

    private float[] translate(float x, float y){
        return new float[]{(WIDTH/2) * (x / ((WIDTH/2)*zoom)), (HEIGHT/2) * (y / ((HEIGHT/2)*zoom))};
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
        this.ds.display(zoom, showComments, showGrid, gridSpacing);
        sketch.pop();
    }

    public boolean withinCanvas(float x, float y){
        float[] coord = ds.translateCoordinates(x, y, zoom);
        //float[] coord = translateCoordinates(x, y);
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
        gridSpacing = sketch.max(spacing, 2);
    }

    public void toggleComments(){
        if(showComments) showComments = false;
        else showComments = true;
    }

    /*********************************************************
     *
     *
     *          OBJECT CREATION RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

    private boolean createAt(float xpos, float ypos, PollyObject obj){
        if (ds.withinScope(xpos, ypos) && obj != null) {
            trash.clear();
            return ds.addShape(obj);
        }
        return false;
    }

    public boolean createShape(float x, float y, char shape) {
        float[] coord = ds.translateCoordinates(x, y, zoom);
        //float[] coord = translateCoordinates(x, y);
        PollyObject obj = of.createShape(coord[0], coord[1], shape, strokeWeight, fillColor, boarderColor);
        return createAt(coord[0], coord[1], obj);
    }

    public boolean createTextBox(float x, float y, String str, String font, float textSize){
        float[] coord = ds.translateCoordinates(x, y, zoom);
        //float[] coord = translateCoordinates(x, y);
        PollyObject obj = of.createTextBox(coord[0], coord[1], fillColor, boarderColor, str, font, textSize);
        return createAt(coord[0], coord[1], obj);
    }

    public boolean createComment(float x, float y, String str, String font, float textSize){
        float[] coord = ds.translateCoordinates(x, y, zoom);
        //float[] coord = translateCoordinates(x, y);
        PollyObject obj = of.createComment(coord[0], coord[1], fillColor, boarderColor, str, font, textSize);
        return ds.addComment(obj);
    }

    public boolean importImage(float x, float y, String filename, String extension){
        float[] coord = ds.translateCoordinates(x, y, zoom);
        //float[] coord = translateCoordinates(x, y);
        PollyObject obj = of.importImage(coord[0], coord[1], filename, extension);
        return createAt(coord[0], coord[1], obj);
    }

    public boolean importImage(String filename, String extension){
        PollyObject obj = of.importImage(0, 0, filename, extension);
        return createAt(0, 0, obj);
    }

    

    /*********************************************************
     *
     *
     *          TOOLBAR RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

    public void selectedPan(float xo, float yo){
        for(PollyObject obj : selected){
            //float[] coord = ds.relativePan(xo, yo, zoom);
            //shape.pan(coord[0], coord[1]);

            float[] coord = translate(xo, yo);
            obj.pan(coord[0], coord[1]);
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
        multiSelect(x, y);
    }

    public void multiSelect(float x, float y){
        PollyObject obj = ds.getObjectAt(x, y, zoom);
        if(obj != null){
            selected.add(obj);
        }
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
                of.createShape(pos[0]+2, pos[1]+2, 'r', strokeWeight, fill, boarder); //not full copy
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

    public void setThickness(float size){
        strokeWeight = sketch.max(size, 1);
    }


    /*********************************************************
     *
     *
     *          SAVE/LOAD/EXPORT RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

    public void exportAs(String saveName, String extension){
        reCenter();
        float[] dim = ds.getDimensions();
        PImage toSave = sketch.get((int)dim[0], (int)dim[1], (int)dim[2]+1, (int)dim[3]+1);
        toSave.save(saveName+extension);
    }

}
