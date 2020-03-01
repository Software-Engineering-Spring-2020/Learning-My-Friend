package frontend.fcontrollers;
/**
 * <h1>NextSlideButton</h1>
 * FButton
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 */

import frontend.*;
import frontend.controlP5.*;

public class ExitSlideButton extends FButton{
  public ExitSlideButton(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "esc", parrent, gui);
    super.button.plugTo(this, "exitSlide");
  }


  public void exitSlide(int val){
    getGUI().exitPresentMode();

  }

}
