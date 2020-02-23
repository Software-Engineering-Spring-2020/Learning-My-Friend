package backend;
import backend.PollyObject;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.io.Serializable;
import java.util.ArrayList;

/**
* Allows for multiple objects to be treated as a unit and have any operation/function applied to each of its constiuent members.
*/
class Group extends PollyObject implements Serializable {
  private static final long serialVersionUID = 5L;
  private ArrayList<PollyObject> members = new ArrayList<PollyObject>();
  private float xmin = Float.MAX_VALUE, ymin = Float.MAX_VALUE, xmax = Float.MIN_VALUE, ymax = Float.MIN_VALUE;

  /**
  * Constructor for Group
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param members A list of all objects to be grouped together
  */
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

  /**
  * Draw an orange rectangle representing the selection field around each individual object in the group.
  */
  protected void showBoundingBox(){
    for(PollyObject obj : members){
      obj.showBoundingBox();
    }
  }

  /**
  * Checks if the mouse is over any object in the group (accounts for rotation and translation)
  * @param x The x position of the mouse in slide-relative coordinates
  * @param y The y position of the mouse in slide-relative coordinates
  * @return Whether or not the mouse is over at least one object in the group
  */
  protected boolean withinScope(float x, float y){
    for(PollyObject obj : members){
      if(obj.withinScope(x, y)) {
        return true;
      }
    } return false;
  }

  /**
  * Move all objects in the group as a unit (Accounts for any zoom factor)
  * @param xo Relative amount to move the objects in the x dimension (in slide-related pixel amounts)
  * @param yo Relative amount to move the objects in the y dimension (in slide-related pixel amounts)
  */
  protected void pan(float xo, float yo){
    for(PollyObject obj : members){
    	obj.pan(xo, yo);
    }
  }

  /**
  * Alter the size of all objects in the goup from their relative center. If any change should decrease the zoom below 10%, it is set to 10% instead. Objectstarts at 100%
  * @param factor The amount to scale the objects (in percentage) from the current size
  */
  protected void resize(float factor){
    for(PollyObject obj : members){
      obj.resize(factor);
    }
  }

  /**
  * Set relative angle change in rotation of all objects in the group
  * @default 0 The original and inital rotation of all objects is 0
  * @param r The desired raw rotation angle in degrees (from the original rotation NOT relative from current rotation)
  */
  protected void setRelativeRotate(float ro){
    for(PollyObject obj : members)
      obj.setRelativeRotate(ro);
  }
}
