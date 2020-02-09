package backend;
import backend.Shapes.ShapeFactory;
import processing.core.PApplet;

public class Driver{
    protected PApplet sketch;
    protected DrawSpace ds;
    protected ShapeFactory sf;
    protected float zoom = 1;

    public Driver(PApplet sketch, float x, float y, float w, float h){
        this.sketch = sketch;
        ds = new DrawSpace(sketch, x, y, w, h);
        sf = new ShapeFactory(sketch);
    }

    public void createDrawSpace(PApplet sketch, float x, float y, float w, float h){
        this.ds = new DrawSpace(sketch, x, y, w, h);
    }
    public void zoom(float factor){
        zoom = sketch.max((float) .01, zoom + factor);
    }
    public void display(){
        this.ds.display(zoom);
    }
    public void pan(float xo, float yo){
        this.ds.pan(xo, yo);
    }

    public void reCenter(){
        zoom = 1;
        this.ds.display(zoom);
    }
    public boolean createShape(float x, float y, char shape){
        float[] coord = ds.translateCoordinates(x, y, zoom);
        if(ds.withinScope(coord[0], coord[1])) sketch.println(true);
        else sketch.println(false);
        ds.addShape(sf.createShape(coord[0], coord[1], shape));
        return false;
    }

    public void changeColor(int r, int g, int b){
        ds.setBoarderColor(r, g, b);
    }
}
