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

public class UpSlideButton extends FButton{
  public UpSlideButton(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "Up", parrent, gui);
    super.button.plugTo(this, "up");
  }


  public void up(int val){
    getGUI().upSlide();
  }

}
