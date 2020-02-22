package frontend;
/**
 * <h1>FButtonbar</h1>
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
public abstract class FButtonBar extends FController{
  public ButtonBar bb;

  public FButtonBar(ControlP5 cp5, String name, FToolbar parrent, GUI gui){
    super(cp5, name, parrent, gui);
    bb = buttonbarFactory();
  }

  private ButtonBar buttonbarFactory(){
    return cp5.addButtonBar(this.name).setGroup(parrent.getGroup());
  }

  /**
   * [update updates the ControlP5 object to the FControlers size]
   */
  protected void update(){
    bb.setSize((int)Math.round(parrent.getRealSizeRX()*getSizeX()), (int)Math.round(parrent.getRealSizeRY()*getSizeY()));
    bb.setPosition((int)Math.round(parrent.getRealSizeRX()*getPosX()), (int)Math.round(parrent.getRealSizeRY()*getPosY()));

  }


}
