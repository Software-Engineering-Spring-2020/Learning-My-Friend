package frontend.fcontrollers;
/**
 * <h1>PresentButton</h1>
 * PresentButton
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 */

import frontend.*;
import frontend.controlP5.*;

public class PresentButton extends FButton{
  public PresentButton(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "present", parrent, gui);
    super.button.plugTo(this);
  }


  public void present(int val){
    getGUI().present();
  }

}
