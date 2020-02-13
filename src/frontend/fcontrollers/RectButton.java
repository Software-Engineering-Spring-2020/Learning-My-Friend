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

public class RectButton extends FButton{
  public RectButton(ControlP5 cp5, String name, FToolbar parrent, GUI gui){
    super(cp5, name, parrent, gui);
    super.button.plugTo(this);
  }


  public void rect(int val){
    System.out.println(val);
    getGUI().tool = 'r';

  }

}
