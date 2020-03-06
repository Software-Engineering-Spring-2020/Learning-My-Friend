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

public class SizeSlider extends FSlider{
  public SizeSlider(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "Size", parrent, gui);
    super.slider.plugTo(this, "size");
    super.slider.setMin(-1);
    super.slider.setMax(5);
    //super.slider.setColor(new CColor(0, 0,))
  }


  public void size(float val){
    //System.out.println(val);
    getGUI().setSize(val);
  }

}
