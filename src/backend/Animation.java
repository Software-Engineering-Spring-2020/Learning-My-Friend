package backend;

import java.io.Serializable;
import java.util.ArrayList;

import backend.ColorfulObject;
import processing.core.PApplet;

/**
 * An abstract Animation that applies changes to objects over time.
 */
public abstract class Animation extends ColorfulObject implements Serializable {
    private static final long serialVersionUID = 16L;
    protected ArrayList<PollyObject> members;
    protected long startTime;
    protected long elapsedTime;
    protected float duration;
    protected boolean display;

    /**
    * Constructor for Shape
    * @param sketch a reference to a PApplet to allow general functionality of the processing library
    * @param duration the duration in milliseconds of the animation
    * @param strokeWeight Represents the line-thickness
    * @param fillColor The internal color of the object represented by four values ranged 0-255 (Red, Green, Blue, and Alpha respectively)
    * @param boarderColor The outline color of the object represented by three values ranged 0-255 (Red, Green, and Blue respectively)
    */
  public Animation(PApplet sketch, long duration){
    super(sketch, 0, 0, 1f, new int[] {255, 255, 255, 255}, new int[] {255, 255, 255, 255});
    this.duration = duration;
    members = new ArrayList<PollyObject>();
  }

  protected void addMember(PollyObject newMember) {
    members.add(newMember);
  }

  protected boolean removeMember(PollyObject member) {
    return members.remove(member);
  }

  protected void showBoundingBox() {
    for (PollyObject obj : members) {
      obj.showBoundingBox();
    }
  }

  protected void start() {
    startTime = System.currentTimeMillis();
    display = true;
  }

  protected void stop() {
    display = false;
  }

  protected void undo() {
    
  }

  protected void display() {
    super.display();
    if (display) elapsedTime = System.currentTimeMillis() - startTime;
    if (elapsedTime >= duration) stop();
  }

  
}