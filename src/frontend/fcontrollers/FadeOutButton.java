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

public class FadeOutButton extends FButton{
  public FadeOutButton(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "Fade Out", parrent, gui);
    super.button.plugTo(this, "FadeOut");
  }


  public void FadeOut(int val){
    //System.out.println("Elip " + val);
    getGUI().animate(0);
    getGUI().setTool('g');

  }

}
