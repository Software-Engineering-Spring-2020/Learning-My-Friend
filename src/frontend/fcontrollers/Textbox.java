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

public class Textbox extends FTextfield{
  public Textbox(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "Textbox", parrent, gui);
    textfield.setText("input text here then click <ENTER>");
  }

}
