package frontend;


/**
  *
  * @author Hunter Chasens
  * @version 1.0
  * @since 02.09.2019
 */
import processing.core.PApplet;
import frontend.controlP5.*;

public class GUI{
  int guiHeight;
  int guiWidth;
  ControlP5 cp5;
  PApplet sketch;
  Slider sl;
  ColorPicker cp;
  public GUI(PApplet sketch){
    this.sketch = sketch;
    cp5 = new ControlP5(sketch);
  }


  public void setup(){
    sl = cp5.addSlider("Slider").setPosition(sketch.width-500,sketch.height-500).setSize(100, 20);
    cp = cp5.addColorPicker("picker")
          .setPosition(60, 100)
          //.setSize(1000,100)
          .setColorValue(sketch.color(128, 128, 128, 128))
          ;
    cp5.addNumberbox("n1")
      .setValue(0)
      .setPosition(20, 20)
      .setSize(100, 20)
      .setMin(0)
      .setMax(255)
      .setId(1);
  }


  public void update(){
    sl.setPosition(0, 0).setSize(sketch.width/10, sketch.height/20);
    cp.setPosition(60, 100).setSize(sketch.width/10, sketch.height/20);
  }


  public void display(){
    if (guiHeight != sketch.height || guiWidth!= sketch.width)
      update();
    guiHeight = sketch.height;
    guiWidth = sketch.width;
    //testing cp5 right now, not final

  }

}
