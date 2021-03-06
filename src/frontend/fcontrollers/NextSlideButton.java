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

public class NextSlideButton extends FButton{
  public NextSlideButton(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "Next", parrent, gui);
    super.button.plugTo(this, "next");
  }


  public void next(int val){
    //getGUI().nextSlide();
    //doenst have to be called with the way the backend handles presenting
  }

}
