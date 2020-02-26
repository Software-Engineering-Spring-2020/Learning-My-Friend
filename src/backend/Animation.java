package backend;

import java.io.Serializable;
import java.util.ArrayList;

import backend.ColorfulObject;
import processing.core.PApplet;

  /**
  * Allows for multiple objects to be treated as a unit and apply changes over time to each of its constiuent members.
  */
public abstract class Animation extends ColorfulObject implements Serializable {
    private static final long serialVersionUID = 16L;
    protected ArrayList<PollyObject> members;
    protected long startTime;
    protected long elapsedTime;
    protected long lastElapsedTime = 0;
    protected float duration;
    protected boolean display;

    /**
    * Constructor for Animation
    * @param sketch a reference to a PApplet to allow general functionality of the processing library
    * @param duration the duration in milliseconds of the animation
    */
  public Animation(PApplet sketch, long duration){
    super(sketch, 0, 0, 1f, new int[] {255, 255, 255, 255}, new int[] {255, 255, 255, 255});
    this.duration = duration;
    members = new ArrayList<PollyObject>();
  }

  /**
  * Add the current annimation sequence to an object.
  * @param newMember The desired object to add the annimation to
  */
  protected void addMember(PollyObject newMember) {
    members.add(newMember);
  }

  /**
  * Remove the current annimation sequence from anobject. The annimation for the other objects in the unit is preserved.
  * @param member The desired object to remove the annimation from
  */
  protected boolean removeMember(PollyObject member) {
    return members.remove(member);
  }

  /**
  * Draw an orange rectangle representing the selection field around each individual object in the group.
  */
  protected void showBoundingBox() {
    for (PollyObject obj : members) {
      obj.showBoundingBox();
    }
  }

/**
* Start the annimation sequence, recording the current starting time to monitor for duration.
*/
  protected void start() {
    elapsedTime = 0;
    lastElapsedTime = 0;
    startTime = System.currentTimeMillis();
    display = true;
  }

  /**
  * Stop the annimation sequence.
  */
  protected void stop() {
    display = false;
  }

  /**
  *
  */
  protected void reset() {

  }

  /**
  * Enact the annimation for each object in the unit for the specified duration
  */
  protected void display() {
    super.display();
    if (display) {
      elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime >= duration) stop();
    }
  }
}
