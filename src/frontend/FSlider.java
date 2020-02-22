package frontend;
/**
 * <h1>FSlider</h1>
 * FSlider
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 */
import frontend.*;
import frontend.controlP5.*;

//make proteced?
public abstract class FSlider extends FController{
  public Slider slider;

  public FSlider(ControlP5 cp5, String name, FToolbar parrent, GUI gui){
    super(cp5, name, parrent, gui);
    slider = sliderFactory();
  }

  private Slider sliderFactory(){
    return cp5.addSlider(this.name).setGroup(parrent.getGroup());
  }

  public void updateState(int i){
    slider.setValue(i);
  }

  /**
   * [update updates the ControlP5 object to the FControlers size]
   */
  protected void update(){
    slider.setSize((int)Math.round(parrent.getRealSizeRX()*getSizeX()), (int)Math.round(parrent.getRealSizeRY()*getSizeY()));
    slider.setPosition((int)Math.round(parrent.getRealSizeRX()*getPosX()), (int)Math.round(parrent.getRealSizeRY()*getPosY()));

  }


}
