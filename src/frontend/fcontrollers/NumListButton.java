package frontend.fcontrollers;
/**
 * <h1>NumListButton</h1>
 * NumListButton
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 */

import frontend.*;
import frontend.controlP5.*;
import backend.Window;

public class NumListButton extends FButton{
  public NumListButton(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "Numbered List", parrent, gui);
    super.button.plugTo(this, "plug");
  }


  public void plug(int val){
    getGUI().setTool('t');
    getGUI().setTextMode(Window.TextMode.NUMBERED);
  }

}
