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
  public Button button;


  public FButton(ControlP5 cp5, String name, FToolbar parrent, GUI gui){
    super(cp5, name, parrent, gui);
    button = buttonFactory();
    //button.setColorBackground(getGUI().returnColor(30,80,255));
    //button.setColorForeground(getGUI().returnColor(30,80,255));
  }

  private Button buttonFactory(){
    return cp5.addButton(this.name).setGroup(parrent.getGroup());
  }

  /**
   * [update updates the ControlP5 object to the FControlers size]
   */
  protected void update(){
    button.setSize((int)Math.round(parrent.getRealSizeRX()*getSizeX()), (int)Math.round(parrent.getRealSizeRY()*getSizeY()));
    button.setPosition((int)Math.round(parrent.getRealSizeRX()*getPosX()), (int)Math.round(parrent.getRealSizeRY()*getPosY()));

  //  parrent.boarder((int)Math.round(parrent.getRealSizeRX()*getPosX()), (int)Math.round(parrent.getRealSizeRY()*getPosY()), (int)Math.round(parrent.getRealSizeRX()*getSizeX()), (int)Math.round(parrent.getRealSizeRY()*getSizeY()));

  }


}
