package src;
import processing.core.PApplet;

public class PollyPaint extends PApplet {

	public static void main(String[] passedArgs) {
		String[] appletArgs = { "PollyPaint" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		} 
    }
		
	public void settings() {
		size(500, 500);
	}

	public void draw(){
		background(64);
		ellipse(mouseX, mouseY, 20, 20);
	}

}
