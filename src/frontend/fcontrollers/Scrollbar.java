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

public class Scrollbar extends FSlider{
  public Scrollbar(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "Scrollbar", parrent, gui);
    super.slider.plugTo(this, "scrollbar");
    setMin(-10);
    setMax(-1);
    super.slider.snapToTickMarks(true);
  }


  public void scrollbar(int val){
    getGUI().scrollTo(val+1);
  }

}
