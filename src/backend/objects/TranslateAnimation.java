package backend.objects;

import java.io.Serializable;
import java.util.ArrayList;

import backend.Animation;
import backend.ColorfulObject;
import backend.PollyObject;
import processing.core.PApplet;

/**
 * An abstract Animation that applies changes to objects over time.
 */
public class TranslateAnimation extends Animation implements Serializable {
    private static final long serialVersionUID = 18L;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private long lastElapsedTime = 0;

    /**
    * Constructor for FadeAnimation
    * @param sketch a reference to a PApplet to allow general functionality of the processing library
    * @param duration the duration in milliseconds of the animation
    * @param startAlpha Represents the initial visibility (0-255)
    * @param endAlpha Represents the final visibility (0-255)
    */
  public TranslateAnimation(PApplet sketch, long duration, float endX, float endY){
    super(sketch, duration);
    this.endX = endX;
    this.endY = endY;
  }

  public void start() {
    super.start();
  }

  public void stop() {
    super.stop();
  }

  public void display() {
    super.display();
    if (display) {
        for (PollyObject obj : members) {
            float ratio = (elapsedTime - lastElapsedTime) / duration;
            obj.pan((endX - startX) * ratio, (endY - startY) * ratio);
            lastElapsedTime = elapsedTime;
        }
    }
  }

  
}