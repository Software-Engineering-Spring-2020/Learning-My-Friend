package frontend;
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

//make proteced?
public abstract class FButton extends FController{
  Button button;

  public FButton(ControlP5 cp5, String name){
    super(cp5, name);
    button = buttonFactory();
    button.plugTo(this);
  }

  private Button buttonFactory(){
    return cp5.addButton(this.name);
  }


}
