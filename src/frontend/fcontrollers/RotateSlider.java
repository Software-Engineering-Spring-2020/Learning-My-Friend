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

public class RotateSlider extends FSlider{
  public RotateSlider(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "rotatel", parrent, gui);
    super.slider.plugTo(this);
    super.slider.setMin(-1);
    super.slider.setMax(1);
    //super.slider.setColor(new CColor(0, 0,))
  }


  public void rotatel(float val){
    //System.out.println(val);
    getGUI().setRotate(val);
  }

}
