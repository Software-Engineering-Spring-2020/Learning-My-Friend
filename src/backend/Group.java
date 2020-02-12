package backend;
import backend.PollyObject;
import processing.core.PApplet;
import processing.core.PShape;
import java.util.ArrayList;

class Group extends PollyObject {
  ArrayList<PollyObject> members;
  Group(PApplet sketch, float x, float y, ArrayList<PollyObject> members){
    super(sketch, x, y);
    this.members = members;
  }
  
  protected void display(){  }

  protected void pan(float xo, float yo){
    for(PollyObject obj : members){
    	obj.pan(xo, yo);
    }
  }
  protected void resize(float xfactor, float yfactor){
    //img.resize(xfactor, yfactor);
  }

}