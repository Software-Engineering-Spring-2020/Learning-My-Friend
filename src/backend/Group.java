package backend;
import backend.PollyObject;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.io.Serializable;
import java.util.ArrayList;

class Group extends PollyObject implements Serializable {
  private static final long serialVersionUID = 5L;
  private ArrayList<PollyObject> members = new ArrayList<PollyObject>();
  private float xmin = Float.MAX_VALUE, ymin = Float.MAX_VALUE, xmax = Float.MIN_VALUE, ymax = Float.MIN_VALUE;

  Group(PApplet sketch, float x, float y, ArrayList<PollyObject> members){
    super(sketch, x, y);
    for(PollyObject obj : members){
      this.members.add(obj);
      float[] pos = obj.getPosition();
      xmin = sketch.min(pos[0], xmin);
      ymin = sketch.min(pos[1], ymin);
      xmax = sketch.max(pos[0], xmax);
      ymax = sketch.max(pos[1], ymax);
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

  /*protected void setRelativeRotate(float ro){
    for(PollyObject obj : members){
      PVector[] rotatedBoundingBoxPoints = new PVector[4];
      PVector[] boundingBoxPoints = obj.getBoundingBoxPoints();
      PVector center = new PVector(xmin+(xmax-xmin)/2,ymin+(ymax-ymin)/2);
      for (int i = 0; i < 4; i++) {
          rotatedBoundingBoxPoints[i] = obj.rotateAbout(boundingBoxPoints[i], center, rot);
      }
    }
  }*/

  protected void setRelativeRotate(float ro){
    for(PollyObject obj : members)
      obj.setRelativeRotate(ro);
  }

  /*protected void display(){
    super.display();
      sketch.push();
      sketch.strokeWeight(5);


      sketch.point(xmin+(xmax-xmin)/2,ymin+(ymax-ymin)/2);
      sketch.pop();
  }*/
}
