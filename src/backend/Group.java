package backend;
import backend.PollyObject;
import processing.core.PApplet;
import processing.core.PShape;

import java.io.Serializable;
import java.util.ArrayList;

class Group extends PollyObject implements Serializable {
  private static final long serialVersionUID = 5L;
  ArrayList<PollyObject> members = new ArrayList<PollyObject>();

  Group(PApplet sketch, float x, float y, ArrayList<PollyObject> members){
    super(sketch, x, y);
    for(PollyObject obj : members){
      this.members.add(obj);
    }
  }

  protected void showBoundingBox(){
    for(PollyObject obj : members){
      obj.showBoundingBox();
    }
  }

  protected boolean withinScope(float x, float y){
    for(PollyObject obj : members){
      if(obj.withinScope(x, y)) {
        return true;
      }
    } return false;
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
