import java.io.IOException;

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
                //this.window.zoom(0.03F);
                this.window.resizeSelected(0.03F);
            } if (this.keyCode == 40) {
                this.window.zoom(-0.03F);
                //this.window.resizeSelected(-0.03F);
            }
            else if (this.keyCode == 18){
                this.window.toggleGrid();
            }
        }

			if (this.key == 'a'){
				 this.window.createRect(this.mouseX, this.mouseY);
			}
			if (this.key == 'b'){
				this.window.reCenter();
			}
			if (this.key == 'c'){
				this.window.setGridSpacing(-10);
			}
			if (this.key == 'd'){
				this.window.createEllipse(this.mouseX, this.mouseY);
			}
			if (this.key == 'e'){
				this.window.createTextBox(this.mouseX, this.mouseY, "This is Text!", "arial", 12);
			}
			if (this.key == 'f'){
				this.window.createComment(this.mouseX, this.mouseY, "This is Comment!", "arial", 12);
			}
			if (this.key == 'g'){
				//this.window.importImage(this.mouseX, this.mouseY, "grayscrunchie", ".png");
        this.window.group();
			}
			if (this.key == 'h'){
				this.window.setFillColor(300, 500, 0, 255);
			}
			if (this.key == 'j'){
				this.window.setBoarderColor(-450, 300, 60);
			}
			if (this.key == 'l'){
				this.window.rotate(123);
			}
			if (this.key == 'm'){
				this.window.deleteSelected();
			}
			if (this.key == 'n'){
				this.window.deleteLast();
			}
			if (this.key == 'o'){
				this.window.restoreLast();
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
				this.window.setThickness(25);
			}
			if (this.key == 't'){
				this.window.exportAs("testName", ".png");
			}
			if (this.key == 'u'){
				this.window.importImage("grayscrunchie",".png");
			}
			if (this.key == 'v'){
				this.window.toggleComments();
			}
			if (this.key == 'w') {
				//this.window.freeDraw();
        this.window.unGroup();
			}

			// saving and opening. Currently using absolute paths to demonstrate how this could
			// work with a choose file/choose folder system
			if (this.key == 'x'){
				try {
					this.window.save("C:/Users/isaac/Documents/Git/notebook/school/spring-2020/software-engineering/drawing.polly");
				} catch (IOException e) {
					System.out.println(e);
					e.printStackTrace();
				}
			}
			if (this.key == 'y'){
				try {
					this.window.open("C:/Users/isaac/Documents/Git/notebook/school/spring-2020/software-engineering/drawing.polly");
				} catch (ClassNotFoundException | IOException e) {
					System.out.println(e);
					e.printStackTrace();
				}
			}
			if (this.key == 'z') {
				this.window.duplicateSelected();
			}
    }

    public void mouseDragged() {
        //this.window.selectedPan((this.mouseX - this.pmouseX), (this.mouseY - this.pmouseY));
        this.window.freeDraw(this.mouseX, this.mouseY);
    }

    public void mouseClicked() {
        if(count%3 == 0) this.window.singleSelect(this.mouseX, this.mouseY);
        else this.window.multiSelect(this.mouseX, this.mouseY);
        count++;
        //this.window.createPollyGon(this.mouseX, this.mouseY, 4);
    }

    public void mouseReleased(){
      this.window.createFreeForm();
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
