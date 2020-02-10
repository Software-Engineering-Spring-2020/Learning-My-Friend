package frontend;

import processing.core.PApplet;
import frontend.controlP5.*;

public class Frontend{

  ControlP5 cp5;
  PApplet sketch;

  public Frontend(PApplet sketch){
    this.sketch = sketch;
    cp5 = new ControlP5(sketch);
  }


  public void display(){
    //testing cp5 right now, not final
    cp5.addSlider("mySlider");
  }

}
