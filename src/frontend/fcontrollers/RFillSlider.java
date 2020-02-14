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

public class RFillSlider extends FSlider{
  public RFillSlider(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "Rfill", parrent, gui);
    super.slider.plugTo(this);
    super.slider.setMax(255);
  }


  public void Rfill(int val){
    //System.out.println(val);
    getGUI().setFill(val, 'r');
  }

}
