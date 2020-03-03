package frontend;
/**
 * <h1>FDropdown</h1>
 * FButton
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 03.02.2020
 *
 */
import frontend.*;
import frontend.controlP5.*;
import java.util.*;

//make proteced?
public abstract class FDropdown extends FController{
  public ScrollableList dropdown;


  public FDropdown(ControlP5 cp5, String name, FToolbar parrent, GUI gui){
    super(cp5, name, parrent, gui);
    dropdown = dropdownFactory();
  }

  private ScrollableList dropdownFactory(){
    return cp5.addScrollableList(this.name).setGroup(parrent.getGroup());
  }

  public void setItems(String[] theItems){
    dropdown.addItems(theItems);
  }

  /**
   * [update updates the ControlP5 object to the FControlers size]
   */
  protected void update(){
    dropdown.setSize((int)Math.round(parrent.getRealSizeRX()*getSizeX()), 4*((int)Math.round(parrent.getRealSizeRY()*getSizeY())));
    dropdown.setPosition((int)Math.round(parrent.getRealSizeRX()*getPosX()), (int)Math.round(parrent.getRealSizeRY()*getPosY()));
    //dropdown.setItemHeight((int)4*Math.round(parrent.getRealSizeRY()*getSizeY()));
    dropdown.setBarHeight((int)Math.round(parrent.getRealSizeRY()*getSizeY()));
    dropdown.close();
    //dropdown.setItemHeight(20);
  }


}
