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

public class PolyCountSlider extends FSlider{
  public PolyCountSlider(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "polyCount", parrent, gui);
    super.slider.plugTo(this);
    super.slider.setMin(3);
    super.slider.setMax(12);
    super.slider.setNumberOfTickMarks(9);
    super.slider.snapToTickMarks(true);
  }


  public void polyCount(int val){
    getGUI().setPolyCount(val);
  }

}
