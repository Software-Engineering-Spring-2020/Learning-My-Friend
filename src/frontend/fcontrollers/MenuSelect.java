package frontend.fcontrollers;
/**
 * <h1>MenuSelect</h1>
 * MenuSelect is the class responsable for displaying the top buttonbar responsable for chaning topChoice in the GUI menu.
 * MenuSelect is a buttonbar whith diffrent menu options, selecting an option will display a diffrenttoolbar menu below it.
 *
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 */

import frontend.*;
import frontend.controlP5.*;

public class MenuSelect extends FButtonBar{
  public MenuSelect(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "MenuSelect", parrent, gui);
    String items[] = {"File", "Text", "Draw", "Animate", "Present"};
    super.bb.addItems(items);
    super.bb.plugTo(this);
  }


  public void MenuSelect(int val){
    getGUI().setActiveToolbar(val);
    //sets the tool back to select for imporved usability suggested by issue #2
    getGUI().tool = 's';
  }

}
