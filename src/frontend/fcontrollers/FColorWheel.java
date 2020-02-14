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

//make proteced?
public class FColorWheel extends FController{
  public ColorWheel cw;

  public FColorWheel(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "wheel", parrent, gui);
    cw = colorPickerWheel();
  }

  private ColorWheel colorPickerWheel(){
    return cp5.addColorWheel(name).setGroup(parrent.getGroup());
  }



  public void wheel(int i){
    System.out.println(i);
  }


  /**
   * [update updates the ControlP5 object to the FControlers size]
   */
  protected void update(){

    cw.setSize((int)Math.round(parrent.getRealSizeRX()*getSizeX()), (int)Math.round(parrent.getRealSizeRY()*getSizeY()));
    cw.setPosition((int)Math.round(parrent.getRealSizeRX()*getPosX()), (int)Math.round(parrent.getRealSizeRY()*getPosY()));
    cw.update();
  }


}
