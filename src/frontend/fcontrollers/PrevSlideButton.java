package frontend.fcontrollers;
/**
 * <h1>PrevSlideButton</h1>
 * FButton
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 */

import frontend.*;
import frontend.controlP5.*;

public class PrevSlideButton extends FButton{
  public PrevSlideButton(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "prev", parrent, gui);
    super.button.plugTo(this);
  }


  public void prev(int val){
    getGUI().previousSlide();

  }

}
