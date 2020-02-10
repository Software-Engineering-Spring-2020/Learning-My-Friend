import processing.core.PApplet;
import backend.CanvasSupport;

//import frontend.Frontend;

/**
 *<h1> PollyPaint </h1>
 * This is our primary class. Our `main` method is stored here. When launching the program this is what launches.
 *
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.09.2019
 */

public class PollyPaint extends PApplet {






/**
 * [main is what runs by default. This should not be called by any other class.]
 * @param passedArgs [description]
 */
	public static void main(String[] passedArgs) {
		String[] appletArgs = { "PollyPaint" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
    }

/**
 * [settings description]
 */
	public void settings() {
		size(1000, 500);
		smooth();
	}


/**
 * [setup description]
 */
	public void setup(){
		surface.setResizable(true);

	}

/**
 * [draw description]
 */
	public void draw(){
		background(64);
		ellipse(mouseX, mouseY, 20, 20);
	}





}
