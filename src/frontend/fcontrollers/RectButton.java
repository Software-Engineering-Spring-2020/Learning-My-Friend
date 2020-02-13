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
  public RectButton(ControlP5 cp5, String name){
    super(cp5, name);

  }

  public void trigger(int val){
    System.out.println(val);
  }

}
