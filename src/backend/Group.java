package backend;
import backend.PollyObject;
import processing.core.PApplet;
import processing.core.PShape;

import java.io.Serializable;
import java.util.ArrayList;

class Group extends PollyObject implements Serializable {
  private static final long serialVersionUID = 5L;
  ArrayList<PollyObject> members;

  Group(PApplet sketch, float x, float y, ArrayList<PollyObject> members){
    super(sketch, x, y);
    this.members = members;
  }

  protected void pan(float xo, float yo){
    for(PollyObject obj : members){
    	obj.pan(xo, yo);
    }
  }
  protected void resize(float factor){
    for(PollyObject obj : members){
      obj.resize(factor);
    }
  }

}