package backend.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import backend.Animation;
import backend.ColorfulObject;
import backend.PollyObject;
import processing.core.PApplet;

/**
* Allows for multiple objects to be treated as a unit and move over time.
*/
public class TranslateAnimation extends Animation implements Serializable {
    private static final long serialVersionUID = 18L;
    private HashMap<PollyObject, float[]> distances;
    private HashMap<PollyObject, float[]> starts;
    private float[] end = new float[2];

    /**
    * Constructor for FadeAnimation
    * @param sketch a reference to a PApplet to allow general functionality of the processing library
    * @param duration the duration in milliseconds of the translation
    * @param startAlpha Represents the initial visibility (0-255)
    * @param endAlpha Represents the final visibility (0-255)
    */
  public TranslateAnimation(PApplet sketch, long duration, float endX, float endY){
    super(sketch, duration);
    starts = new HashMap<PollyObject, float[]>();
    distances = new HashMap<PollyObject, float[]>();
    end[0] = endX;
    end[1] = endY;
  }

  protected void showBoundingBox(float r, float g, float b) {
    super.showBoundingBox(r, g, b);
    for (PollyObject obj : members) {
      sketch.push();
      sketch.strokeWeight(3);
      sketch.stroke(255, 255, 0);
      sketch.line(obj.getPosition()[0], obj.getPosition()[1], end[0], end[1]);
      sketch.pop();
    }
  }

  protected void addMember(PollyObject newMember) {
    super.addMember(newMember);
    starts.put(newMember, newMember.getPosition());
  }

  /**
  * Start the annimation sequence, recording the current starting time to monitor for duration. Records the distance to travel.
  */
  protected void start() {
    super.start();
    for (PollyObject obj : members) {
      float[] start = obj.getPosition();
      float[] distance = new float[2];
      distance[0] = end[0] - start[0];
      distance[1] = end[1] - start[1];
      distances.put(obj, distance);
    }
  }

  /**
  * Stop the annimation sequence.
  */
  protected void stop() {
    super.stop();
    for (PollyObject obj : members) {
      obj.setPosition(end[0], end[1]);
    }
  }

  protected void reset() {
    super.reset();
    for (PollyObject obj : members) {
      obj.setPosition(starts.get(obj)[0], starts.get(obj)[1]);
    }
  }

  /**
  * Move each object in the unit over the specified duration time.
  */
  public void display() {
    super.display();
    if (display) {
        for (PollyObject obj : members) {
            float ratio = (elapsedTime - lastElapsedTime) / duration;
            float[] pos = obj.getPosition();
            float[] distance = distances.get(obj);
            obj.setPosition(distance[0] * ratio + pos[0], distance[1] * ratio + pos[1]);
            lastElapsedTime = elapsedTime;
        }
    }
  }


}
