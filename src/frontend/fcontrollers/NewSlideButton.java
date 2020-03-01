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

public class NewSlideButton extends FButton{
  public NewSlideButton(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "new", parrent, gui);
    super.button.plugTo(this, "newSlide");
  }


  public void newSlide(int val){
    //System.out.println("newSlide");
    getGUI().newSlide();
  }

}
