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
public class FColorPicker extends FController{
  public ColorPicker cp;

  public FColorPicker(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "color", parrent, gui);
    cp = colorPickerFactory();
  }

  private ColorPicker colorPickerFactory(){
    return cp5.addColorPicker(name).setGroup(parrent.getGroup());
  }



    public void color(int i){
      System.out.println(i);
    }




  /**
   * [update updates the ControlP5 object to the FControlers size]
   */
  protected void update(){

    cp.setSize((int)Math.round(parrent.getRealSizeRX()*getSizeX()), (int)Math.round(parrent.getRealSizeRY()*getSizeY()));
    cp.setPosition((int)Math.round(parrent.getRealSizeRX()*getPosX()), (int)Math.round(parrent.getRealSizeRY()*getPosY()));
    cp.update();
  }


}
