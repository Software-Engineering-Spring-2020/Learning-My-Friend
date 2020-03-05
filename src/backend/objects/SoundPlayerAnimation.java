package backend.objects;

import java.util.HashMap;

import backend.Animation;
import backend.PollyObject;
import processing.core.PApplet;
import processing.sound.*;

/**
* Allows for sounds to be played as animations.
*/
public class SoundPlayerAnimation extends Animation {
    private static final long serialVersionUID = 19L;
    transient private SoundFile sf;
    String filename;
    String extension;

    /**
    * Constructor for SoundPlayerAnimation
    * @param sketch a reference to a PApplet to allow general functionality of the processing library
    * @param duration the duration in milliseconds of the sound.
    * @param startAlpha Represents the initial visibility (0-255)
    * @param endAlpha Represents the final visibility (0-255)
    */
  public SoundPlayerAnimation(PApplet sketch, long duration, String filename, String extension){
    super(sketch, duration);
    this.filename = filename;
    this.extension = extension;
    sf = new SoundFile(sketch, filename + extension);
  }

  protected void init(PApplet sketch) {
    super.init(sketch);
    sf = new SoundFile(sketch, filename + extension);
  }

  /**
  * Start the animation sequence.
  */
  protected void start() {
    super.start();
    sf.play();
  }

  /**
  * Stop the animation sequence.
  */
  protected void stop() {
    super.stop();
    sf.stop();
  }

  protected void display() {
      if (elapsedTime >= duration) stop();
  }
}
