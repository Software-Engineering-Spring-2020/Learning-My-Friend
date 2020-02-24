package backend.objects;

import java.io.Serializable;
import java.util.ArrayList;

import backend.Animation;
import backend.ColorfulObject;
import backend.PollyObject;
import processing.core.PApplet;

/**
* Allows for multiple objects to be treated as a unit and move over time.
*/
public class TranslateAnimation extends Animation implements Serializable {
    private static final long serialVersionUID = 18L;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private float[] distance = new float[2];
    private long lastElapsedTime = 0;

    /**
    * Constructor for FadeAnimation
    * @param sketch a reference to a PApplet to allow general functionality of the processing library
    * @param duration the duration in milliseconds of the translation
    * @param startAlpha Represents the initial visibility (0-255)
    * @param endAlpha Represents the final visibility (0-255)
    */
  public TranslateAnimation(PApplet sketch, long duration, float endX, float endY){
    super(sketch, duration);
    this.endX = endX;
    this.endY = endY;
  }

  /**
  * Start the annimation sequence, recording the current starting time to monitor for duration. Records the distance to travel.
  */
  protected void start() {
    super.start();
    distance[0] = endX - startX;
    distance[1] = endY - startY;
  }

  /**
  * Stop the annimation sequence.
  */
  protected void stop() {
    super.stop();
    for (PollyObject obj : members) {
        //obj.pan(-distance[0], -distance[1]);
    }
  }

  protected void undo() {

  }

  /**
  * Move each object in the unit over the specified duration time.
  */
  public void display() {
    super.display();
    if (display) {
        for (PollyObject obj : members) {
            float ratio = (elapsedTime - lastElapsedTime) / duration;
            obj.pan(distance[0] * ratio, distance[1] * ratio);
            lastElapsedTime = elapsedTime;
        }
    }
  }


}
