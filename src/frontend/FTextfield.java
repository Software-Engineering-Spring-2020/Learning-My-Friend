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
public abstract class FTextfield extends FController{
  public Textfield textfield;

  public FTextfield(ControlP5 cp5, String name, FToolbar parrent, GUI gui){
    super(cp5, name, parrent, gui);
    textfield = textfieldFactory();
  }

  private Textfield textfieldFactory(){
    return cp5.addTextfield(this.name).setGroup(parrent.getGroup());
  }

  /**
   * [update updates the ControlP5 object to the FControlers size]
   */
  protected void update(){
    textfield.setSize((int)Math.round(parrent.getRealSizeRX()*getSizeX()), (int)Math.round(parrent.getRealSizeRY()*getSizeY()));
    textfield.setPosition((int)Math.round(parrent.getRealSizeRX()*getPosX()), (int)Math.round(parrent.getRealSizeRY()*getPosY()));
  }


}
