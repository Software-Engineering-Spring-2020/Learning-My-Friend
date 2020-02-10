package frontend;

import processing.core.PApplet;
import frontend.controlP5.*;

public class Frontend{
  int guiHeight;
  int guiWidth;
  ControlP5 cp5;
  PApplet sketch;
  Slider sl;
  public Frontend(PApplet sketch){
    this.sketch = sketch;
    cp5 = new ControlP5(sketch);
  }


  public void setup(){
    sl = cp5.addSlider("Slider").setPosition(sketch.width-500,sketch.height-500).setSize(100, 20);
  }


  public void update(){
    sl.setPosition(0, 0).setSize(sketch.width/10, sketch.width/20);
  }



  public void display(){
    if (guiHeight != sketch.height || guiWidth!= sketch.width)
      update();
    guiHeight = sketch.height;
    guiWidth = sketch.width;
    //testing cp5 right now, not final

  }

}
