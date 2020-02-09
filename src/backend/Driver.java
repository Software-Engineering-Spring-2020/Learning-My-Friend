package backend;
import processing.core.PApplet;

public class Driver{
    DrawSpace ds;

    public Driver(PApplet sketch, float x, float y, float w, float h){
        ds = new DrawSpace(sketch, x, y, w, h);
    }

    public void createDrawSpace(PApplet sketch, float x, float y, float w, float h){
        this.ds = new DrawSpace(sketch, x, y, w, h);
    }
    public void zoom(float factor){
        this.ds.zoom(factor);
    }
    public void display(){
        this.ds.display();
    }
    public void pan(float xo, float yo){
        this.ds.pan(xo, yo);
    }
    public void createShape(float x, float y, char shape){
        this.ds.createEllipse(x, y);
    }

    public void changeColor(int r, int g, int b){
        ds.setBoarderColor(r, g, b);
    }
}
