import backend.*;
import processing.core.PApplet;

public class BackendDriver extends PApplet {
    boolean save;
    int count = 0;
    Window window;
    public void setup() {
        this.surface.setResizable(true);
        window = new Window(this, 50, 50, 1000, 500);
    }

    public void draw() {
        background(153);
        this.window.display();
    }

    public void keyReleased() {     
        if (this.key == Character.MAX_VALUE) {
            if (this.keyCode == 17)
                //this.save = true;
                this.window.restoreLast();
            if (this.keyCode == 38) {
                this.window.zoom(0.03F);
            } if (this.keyCode == 40) {
                this.window.zoom(-0.03F);
            }
            else if (this.keyCode == 18){
                this.window.toggleGrid();
            }
        }
		
			if (this.key == 'a'){ 
				 this.window.createShape(this.mouseX, this.mouseY, 'r');
			}
			if (this.key == 'b'){
				this.window.reCenter();
			}
			if (this.key == 'c'){
				this.window.setGridSpacing(50);
			}
			if (this.key == 'd'){
				this.window.createShape(this.mouseX, this.mouseY, 'e');
			}
			if (this.key == 'e'){
				this.window.createTextBox(this.mouseX, this.mouseY, "Hello world!", "arial", 12);
			}
			if (this.key == 'f'){
				this.window.createComment(this.mouseX, this.mouseY, "Hello world!", "arial", 12);
			}
			if (this.key == 'g'){
				this.window.importImage(this.mouseX, this.mouseY, "grayscrunchie", ".png");
			}
			if (this.key == 'h'){
				this.window.setFillColor(90, 50, 250);
			}
			if (this.key == 'j'){
				this.window.setBoarderColor(100, 200, 60);
			}
			if (this.key == 'l'){
				this.window.rotate(90);
			}
			if (this.key == 'm'){
				this.window.deleteSelected();
			}
			if (this.key == 'n'){
				this.window.deleteLast();
			}
			if (this.key == 'o'){
				this.window.deleteSelected();
			}
			if (this.key == 'p'){
				this.window.clear();
			}
			if (this.key == 'q'){
				this.window.copy();
			}
			if (this.key == 'r'){
				this.window.paste();
			}
			if (this.key == 's'){
				this.window.setThickness(5);
			}
			if (this.key == 't'){
				this.window.exportAs("testName", ".png");
			}
			if (this.key == 'u'){
				System.out.println(this.window.withinCanvas(this.mouseX, this.mouseY));
			}
			if (this.key == 'u'){
				
			}
    }

    public void mouseDragged() {
        this.window.selectedPan((this.mouseX - this.pmouseX), (this.mouseY - this.pmouseY));
    }

    public void mouseClicked() {
        this.window.multiSelect(this.mouseX, this.mouseY);
        count++;
    }

    public void settings() {
        size(400, 400);
    }

    public static void main(String[] passedArgs) {
        String[] appletArgs = { "BackendDriver" };
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
