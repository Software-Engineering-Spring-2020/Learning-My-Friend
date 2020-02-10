package backend;
import backend_beta.Shapes.ShapeFactory;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class CanvasSupport {
    private ArrayList<PollyObject> trash = new ArrayList<PollyObject>();
    private ArrayList<PollyObject> selected = new ArrayList<PollyObject>();
    private PApplet sketch;
    private DrawSpace ds;
    private ShapeFactory sf;
    private float zoom = 1;

    public CanvasSupport(PApplet sketch, float x, float y, float w, float h){
        this.sketch = sketch;
        ds = new DrawSpace(sketch, x, y, w, h);
        sf = new ShapeFactory(sketch);
    }

    /*********************************************************
     *
     *
     *          DRAWSPACE RELATED FUNCTIONALITY
     *
     *
     *********************************************************/
    public void createDrawSpace(PApplet sketch, float x, float y, float w, float h){
        this.ds = new DrawSpace(sketch, x, y, w, h);
    }
    public void zoom(float factor){ //draw offcenter once zoom
        zoom = sketch.max((float) .01, zoom + factor);
    }
    public void display(){
        this.ds.display(zoom);
    }
    public void pan(float xo, float yo){ //(this.mouseX - this.pmouseX), (this.mouseY - this.pmouseY)
        this.ds.pan(xo, yo);
    }

    public void reCenter(){
        zoom = 1;
        this.ds.display(zoom);
    }
    public boolean createShape(float x, float y, char shape){
        float[] coord = ds.translateCoordinates(x, y, zoom);
        if(ds.withinScope(coord[0], coord[1])) {
            selected.add(sf.createShape(coord[0], coord[1], 'r'));
            sketch.println(selected.size());
            return ds.addShape(sf.createShape(coord[0], coord[1], shape));
        }
        return false;
    }

    /*********************************************************
     *
     *
     *          TOOLBAR RELATED FUNCTIONALITY
     *
     *
     *********************************************************/
    public void rotate(float ao){
        for(PollyObject shape : selected){
            shape.setRelativeRotate(ao);
        }
    }
    public void changeColor(int r, int g, int b){
        ds.setBoarderColor(r, g, b);
    }

    public boolean deleteSelected(){
        boolean successful = true;
        for(PollyObject shape : selected){
            if(shape instanceof ColorfulObject) {
                ((ColorfulObject) shape).setAlpha(0);
            }
            trash.add(shape);
            successful = successful && ds.removeShape(shape);
        } return successful;
    }

    public boolean undo(){
        return trash.add(ds.removeShape(ds.getNumObjects()));
    }
    public boolean redo(){
        if(trash.isEmpty()) return false;
        return ds.addShape(trash.remove(0));
    }

    /*********************************************************
     *
     *
     *          SAVE/LOAD/IMPORT/EXPORT RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

}
