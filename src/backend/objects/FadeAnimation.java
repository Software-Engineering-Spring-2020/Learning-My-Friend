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
public class FadeAnimation extends Animation implements Serializable {
    private static final long serialVersionUID = 17L;
    private int startAlpha;
    private int endAlpha;

    /**
    * Constructor for FadeAnimation
    * @param sketch a reference to a PApplet to allow general functionality of the processing library
    * @param duration the duration in milliseconds of the animation
    * @param startAlpha Represents the initial visibility (0-255)
    * @param endAlpha Represents the final visibility (0-255)
    */
  public FadeAnimation(PApplet sketch, long duration, int startAlpha, int endAlpha){
    super(sketch, duration);
    this.startAlpha = startAlpha;
    this.endAlpha = endAlpha;
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
            ColorfulObject cobj;
            if (obj instanceof ColorfulObject) {
                cobj = (ColorfulObject) obj;
                int startRatio = (int) (((duration - elapsedTime) / elapsedTime) * startAlpha);
                int endRatio = (int) ((duration / elapsedTime) * endAlpha);
                cobj.setAlpha(startRatio + endRatio);
            }
        }
    }
  }

  
}