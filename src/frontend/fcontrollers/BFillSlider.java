package frontend.fcontrollers;
/**
 * <h1>FButton</h1>
 * FButton
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 */

import frontend.*;
import frontend.controlP5.*;

public class BFillSlider extends FSlider{
  public BFillSlider(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "Bfill", parrent, gui);
    super.slider.plugTo(this);
    super.slider.setMax(255);
    //super.slider.setColor(PApplet.color(100, 0, 0));
    //super.slider.setColor(new CColor(0, 0,))
  }


  public void Bfill(int val){

    getGUI().setFill(val, 'b');
  }

}
