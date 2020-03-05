package backend.objects;

import java.io.Serializable;
import java.util.ArrayList;

import backend.Animation;
import backend.ColorfulObject;
import backend.PollyObject;
import processing.core.PApplet;

/**
* Allows for multiple objects to be treated as a unit and fade over time.
*/
public class FadeAnimation extends Animation implements Serializable {
    private static final long serialVersionUID = 17L;
    private int startAlpha;
    private int endAlpha;

    /**
    * Constructor for FadeAnimation
    * @param sketch a reference to a PApplet to allow general functionality of the processing library
    * @param duration the duration in milliseconds of the fade
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
    for (PollyObject obj : members) {
      ColorfulObject cobj;
      if (obj instanceof ColorfulObject) {
          cobj = (ColorfulObject) obj;
          cobj.setAlpha(endAlpha);
      }
    }
  }

  public void reset() {
    super.reset();
    for (PollyObject obj : members) {
      ColorfulObject cobj;
      if (obj instanceof ColorfulObject) {
          cobj = (ColorfulObject) obj;
          cobj.setAlpha(startAlpha);
      }
  }
  }

  public int getEndAlpha() {
    return endAlpha;
  }

  /**
  * Fade each object in the unit over the specified duration time.
  */
  public void display() {
    super.display();
    if (display) {
        for (PollyObject obj : members) {
            ColorfulObject cobj;
            if (obj instanceof ColorfulObject) {
                cobj = (ColorfulObject) obj;
                int startRatio = (int) ((1 - (elapsedTime / duration)) * startAlpha);
                int endRatio = (int) ((elapsedTime / duration) * endAlpha);
                cobj.setAlpha(startRatio + endRatio);
            }
        }
    }
  }


}
