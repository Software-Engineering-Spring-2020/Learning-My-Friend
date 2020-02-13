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

  public FButton(ControlP5 cp5, String name, FToolbar parrent){
    super(cp5, name, parrent);
    button = buttonFactory();
    button.plugTo(this);
  }

  private Button buttonFactory(){
    return cp5.addButton(this.name);
  }

  /**
   * [update updates the ControlP5 object to the FControlers size]
   */
  protected void update(){
    button.setSize((int)Math.round(parrent.getSizeX()*getSizeX()), (int)Math.round(parrent.getSizeY()*getSizeY()));
    button.setPosition((int)Math.round(parrent.getSizeX()*getPosX()), (int)Math.round(parrent.getSizeY()*getPosY()));

  }


}
