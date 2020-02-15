package backend;

import backend.objects.ObjectFactory;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.IOException;
import java.util.ArrayList;

public class Window {
    private ArrayList<PollyObject> trash = new ArrayList<PollyObject>();
    private ArrayList<PollyObject> selected = new ArrayList<PollyObject>();
    private ArrayList<PollyObject> copied = new ArrayList<PollyObject>();
    private ArrayList<float[]> freePoints = new ArrayList<float[]>();
    private ArrayList<float[]> pollyPoints = new ArrayList<float[]>();
    private ArrayList<float[]> curvePoints = new ArrayList<float[]>();
    private PApplet sketch;
    private DrawSpace ds;
    private ObjectFactory of;
    private float zoom = 1, strokeWeight = 1;
    private int[] fillColor, boarderColor;
    private float XINIT, YINIT, WIDTH, HEIGHT, gridSpacing = 30;
    private boolean showGrid = false, showComments = false;
    private int numberVertex = 0;

    public Window(PApplet sketch, float x, float y, float w, float h) {
        this.sketch = sketch;
        ds = new DrawSpace(sketch, x, y, w, h);
        of = new ObjectFactory(sketch);
        fillColor = new int[] { 0, 0, 0, 0 };
        boarderColor = new int[] { 0, 0, 0 };
        XINIT = x;
        YINIT = y;
        WIDTH = w;
        HEIGHT = h;
    }

    public void display() {
        sketch.push();
        this.ds.display(zoom, showComments, showGrid, gridSpacing);
        for (int i = 0; i < freePoints.size() - 1; i++) {
            if (freePoints.size() > 1)
                sketch.line(freePoints.get(i)[0], freePoints.get(i)[1], freePoints.get(i + 1)[0],
                        freePoints.get(i + 1)[1]);
        }

        sketch.push();
        sketch.strokeWeight(strokeWeight);
        for (float[] v : pollyPoints) {
            sketch.point(v[0], v[1]);
        }
        sketch.pop();

        if (pollyPoints.size() >= numberVertex && numberVertex > 1) {
            ds.addObject(of.createPollyGon(pollyPoints, strokeWeight, fillColor, boarderColor));
            pollyPoints.clear();
            numberVertex = 0;
        }

        if (curvePoints.size() >= 4) {
            ds.addObject(of.createCurve(curvePoints, strokeWeight, fillColor, boarderColor));
            curvePoints.clear();
        }

        for(PollyObject obj : selected){
          obj.showBoundingBox();
        }
        sketch.pop();
    }

    private float[] translate(float x, float y) {
        return new float[] { (WIDTH / 2) * (x / ((WIDTH / 2) * zoom)), (HEIGHT / 2) * (y / ((HEIGHT / 2) * zoom)) };
    }

    /*********************************************************
     *
     *
     * DRAWSPACE RELATED FUNCTIONALITY
     *
     *
     *********************************************************/
    public void createDrawSpace(PApplet sketch, float x, float y, float w, float h) {
        this.ds = new DrawSpace(sketch, x, y, w, h);
    }

    public void zoom(float factor) { // draw offcenter once zoom
        zoom = sketch.max((float) .01, zoom + factor);
    }

    public boolean withinCanvas(float x, float y) {
        float[] coord = ds.translateCoordinates(x, y, zoom);
        // float[] coord = translateCoordinates(x, y);
        return ds.withinScope(coord[0], coord[1]);
    }

    public void canvasPan(float xo, float yo) { // (this.mouseX - this.pmouseX), (this.mouseY - this.pmouseY)
        this.ds.pan(xo, yo);
    }

    public void reCenter() {
        zoom = 1;
        this.ds.setPosition(XINIT, YINIT);
        this.display();
    }

    public void toggleGrid() {
        if (showGrid)
            showGrid = false;
        else
            showGrid = true;
    }

    public void setGridSpacing(float spacing) {
        gridSpacing = sketch.max(spacing, 2);
    }

    public void toggleComments() {
        if (showComments)
            showComments = false;
        else
            showComments = true;
    }

    /*********************************************************
     *
     *
     * OBJECT CREATION RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

    public boolean createEllipse(float x, float y, float w, float h) {
        PollyObject obj = null;
        float[] coord = ds.translateCoordinates(x, y, zoom);
        if (ds.withinScope(coord[0], coord[1]))
            obj = of.createEllipse(coord[0], coord[1], w, h, strokeWeight, fillColor, boarderColor);
        if (obj != null)
            return ds.addObject(obj);
        return false;
    }

    public boolean createEllipse(float x, float y, float d) {
        return createEllipse(x, y, d, d);
    }

    public boolean createEllipse(float x, float y) {
        return createEllipse(x, y, 100, 50);
    }

    public boolean createRect(float x, float y, float w, float h) {
        PollyObject obj = null;
        float[] coord = ds.translateCoordinates(x, y, zoom);
        if (ds.withinScope(coord[0], coord[1]))
            obj = of.createRect(coord[0], coord[1], w, h, strokeWeight, fillColor, boarderColor);
        if (obj != null)
            return ds.addObject(obj);
        return false;
    }

    public boolean createRect(float x, float y, float d) {
        return createRect(x, y, d, d);
    }

    public boolean createRect(float x, float y) {
        return createRect(x, y, 100, 50);
    }

    public boolean createTextBox(float x, float y, String str, String font, float textSize) {
        PollyObject obj = null;
        float[] coord = ds.translateCoordinates(x, y, zoom);
        if (ds.withinScope(coord[0], coord[1]))
            obj = of.createTextBox(coord[0], coord[1], strokeWeight, fillColor, boarderColor, str, font, textSize);
        if (obj != null)
            return ds.addObject(obj);
        return false;
    }

    public boolean createComment(float x, float y, String str, String font, float textSize) {
        PollyObject obj = null;
        float[] coord = ds.translateCoordinates(x, y, zoom);
        if (ds.withinScope(coord[0], coord[1]))
            obj = of.createComment(coord[0], coord[1], strokeWeight, fillColor, boarderColor, str, font, textSize);
        if (obj != null)
            return ds.addComment(obj);
        return false;
    }

    public boolean importImage(float x, float y, String filename, String extension) {
        PollyObject obj = null;
        float[] coord = ds.translateCoordinates(x, y, zoom);
        if (ds.withinScope(coord[0], coord[1]))
            obj = of.importImage(coord[0], coord[1], filename, extension);
        if (obj != null)
            return ds.addObject(obj);
        return false;
    }

    public boolean importImage(String filename, String extension) {
        PollyObject obj = of.importImage(0, 0, filename, extension);
        if (obj != null)
            return ds.addObject(obj);
        return false;
    }

    /*********************************************************
     *
     *
     * TOOLBAR RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

     public void resizeSelected(float factor){
       for(PollyObject obj : selected){
         obj.resize(factor);
       }
     }

    public void group() {
        ds.addObject(new Group(sketch, 0, 0, selected));
    }

    public void unGroup() {
        for (PollyObject obj : selected) {
            if (obj instanceof Group)
                ds.removeObject(obj);
        }
    }

    public void selectedPan(float xo, float yo) {
        for (PollyObject obj : selected) {
            float[] coord = translate(xo, yo);
            obj.pan(coord[0], coord[1]);
        }
    }

    public void selectedPan(float mouseX, float mouseY, float pmouseX, float pmouseY) {
        for (PollyObject obj : selected) {
          float[] coord = ds.translateCoordinates(mouseX, mouseY, zoom);
          if(obj.withinScope(coord[0], coord[1])){
            coord = translate(mouseX-pmouseX, mouseY-pmouseY);
            selectedPan(coord[0], coord[1]);
          }
        }
    }

    public void setFillColor(int r, int g, int b, int a) {
        fillColor[0] = r;
        fillColor[1] = g;
        fillColor[2] = b;
        fillColor[3] = a;
    }

    public void setBoarderColor(int r, int g, int b) {
        boarderColor[0] = r;
        boarderColor[1] = g;
        boarderColor[2] = b;
    }

    public void setSelectedFillColor(int r, int g, int b, int a) {
        for (PollyObject shape : selected) {
            if (shape instanceof ColorfulObject)
                ((ColorfulObject) shape).setFillColor(r, g, b, a);
        }
    }

    public void setSelectedBoarderColor(int r, int g, int b) {
        for (PollyObject shape : selected) {
            if (shape instanceof ColorfulObject)
                ((ColorfulObject) shape).setBoarderColor(r, g, b);
        }
    }

    public ArrayList<int[]> getSelectedFillColors() {
        ArrayList<int[]> colors = new ArrayList<int[]>();
        for (PollyObject shape : selected) {
            if (shape instanceof ColorfulObject)
                colors.add(((ColorfulObject) shape).getFillColor());
        }
        return colors;
    }

    public ArrayList<int[]> getSelectedBoarderColors() {
        ArrayList<int[]> colors = new ArrayList<int[]>();
        for (PollyObject shape : selected) {
            if (shape instanceof ColorfulObject)
                colors.add(((ColorfulObject) shape).getBoarderColor());
        }
        return colors;
    }

    public void rotate(float ao) {
        for (PollyObject shape : selected) {
            shape.setRelativeRotate(ao);
        }
    }

    public void singleSelect(float x, float y) {
        selected.clear();
        multiSelect(x, y);
    }

    public void multiSelect(float x, float y) {
        PollyObject obj = ds.getObjectAt(x, y, zoom);
        if (obj != null && !selected.contains(obj))
            selected.add(obj);
    }

    public boolean deleteSelected() {
        boolean successful = true;
        for (PollyObject shape : selected) {
            trash.add(shape);
            if (!ds.removeObject(shape))
                successful = false;
        }
        return successful;
    }

    public void duplicateSelected() {
        for (PollyObject shape : selected) {
            try {
                ds.addObject(SerialManager.deepClonePollyObject(sketch, shape));
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
        }
    }

    public boolean deleteLast() {
        if(ds.getNumObjects() <= 0) return false;
        return trash.add(ds.removeObject(ds.getNumObjects()-1));
    }

    public boolean restoreLast() {
        sketch.println(trash.size());
        if (trash.isEmpty()) return false;
        return ds.addObject(trash.remove(trash.size()-1));
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
            if(!copied.add(shape)) sucess = false;
        }
        return sucess;
    }

    public void paste(){    //not working yet
      for (PollyObject shape : copied) {
          try {
              ds.addObject(SerialManager.deepClonePollyObject(sketch, shape));
    } catch (ClassNotFoundException | IOException e) {
      System.out.println(e);
      e.printStackTrace();
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

    public void freeDraw(float pmousex, float pmousey){ //must call createFreeForm() on mouseRelease()
        float[] coord = ds.translateCoordinates(pmousex, pmousey, zoom);
        float[] v = new float[]{coord[0], coord[1]};
        freePoints.add(v);
    }

    public void createPollyGon(float pmousex, float pmousey, int numberVertex){
        this.numberVertex = numberVertex;
        float[] coord = ds.translateCoordinates(pmousex, pmousey, zoom);
        float[] v = new float[]{coord[0], coord[1]};
        pollyPoints.add(v);
    }

    public void createCurve(float pmousex, float pmousey){
        float[] coord = ds.translateCoordinates(pmousex, pmousey, zoom);
        float[] v = new float[]{coord[0], coord[1]};
        curvePoints.add(v);
    }

    public void createLine(float pmousex, float pmousey){ //make exta thick???
        this.numberVertex = 2;
        float[] coord = ds.translateCoordinates(pmousex, pmousey, zoom);
        float[] v = new float[]{coord[0], coord[1]};
        pollyPoints.add(v);
    }

    public void createFreeForm(){ //must be called on the mouseReleased()
        if(!freePoints.isEmpty()) ds.addObject(of.createFreeForm(freePoints, strokeWeight, fillColor, boarderColor));
        freePoints.clear();
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

    public void save(String filename) throws IOException {
      SerialManager.saveDrawSpace(ds, filename);
    }

    public void open(String filename) throws IOException, ClassNotFoundException {
      ds = SerialManager.openDrawSpace(sketch, filename);
    }


    public void test(){
      PImage img = sketch.requestImage("grayscrunchie.png");
    }

}
