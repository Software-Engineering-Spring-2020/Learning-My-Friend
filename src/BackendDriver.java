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

    boolean ctrl = false, shift = false;

    public void keyReleased(){
      if(this.keyCode == 17) ctrl = false;
      if(this.keyCode == 16) shift = false;
    }

    public void keyPressed(){
      //System.out.println(this.key+ ", "+this.keyCode + " : "+ctrl);
      if(this.keyCode == 17)  ctrl = true;
      if(this.keyCode == 16) shift = true;

      if(this.key == 'z' && ctrl) this.window.deleteLast();
      else if(this.key == 'y' && ctrl) this.window.restoreLast();
      else if(this.key == 'c' && ctrl) this.window.copy();
      else if(this.key == 'x' && ctrl) this.window.cut();
      else if(this.key == 'v' && ctrl) this.window.paste();

      else if(this.key == 'n' && ctrl) this.window.clear();
      else if(this.key == 'o' && ctrl){
        try {
					this.window.open("C:/Users/Mei ^.^/Desktop/drawing.polly");
				} catch (ClassNotFoundException | IOException e) {
					System.out.println(e);
					e.printStackTrace();
				}
      }
      else if(this.key == 's' && ctrl){
        try {
					this.window.save("C:/Users/Mei ^.^/Desktop/drawing.polly");
				} catch (IOException e) {
					System.out.println(e);
					e.printStackTrace();
				}
      }

      else if(this.key == 'e' && ctrl) this.window.exportAs("test", ".png");
      else if(this.key == 'g' && ctrl) this.window.toggleGroup();
      else if(this.key == 'a' && ctrl) this.window.toggleComments(); //Toggles Annotations
      else if(this.key == 'b' && ctrl) this.window.toggleGrid();  //Toggles boxes on screen (grid)

      else if(this.key == '<') this.window.changeThickness(-0.1F);
      else if(this.key == '>') this.window.changeThickness(0.1F);
      else if(this.key == '{') this.window.changeGridSpacing(-5);
      else if(this.key == '}') this.window.changeGridSpacing(5);
      else if(this.key == '|') this.window.createLine(this.mouseX, this.mouseY);
      else if(this.key == 'P') this.window.createPollyGon(this.mouseX, this.mouseY, 3);


      else if(this.key == 'R') this.window.createRect(this.mouseX, this.mouseY);
      else if(this.key == 'T') this.window.createTextBox(this.mouseX, this.mouseY, "This is Text!", "arial", 12);
      else if(this.key == 'A') this.window.createComment(this.mouseX, this.mouseY, "This is Comment!", "arial", 12);
      else if(this.key == 'C') this.window.createEllipse(this.mouseX, this.mouseY);

      else if(this.keyCode == 127) this.window.deleteSelected();  //delete
      else if(this.keyCode == 37) this.window.rotate(-1); //left
      else if(this.keyCode == 39) this.window.rotate(1);  //right
      else if(this.keyCode == 38) this.window.resize(0.01F);  //up
      else if(this.keyCode == 40) this.window.resize(-0.01F); //down
      else if(this.keyCode == 155) this.window.importImage("grayscrunchie",".png");

      else if(this.keyCode == 10) this.window.reCenter();   //enter
      else if(this.keyCode == 192) this.window.createCurve(this.mouseX, this.mouseY); //tilde


    }

  /*  public void keyReleased() {

        if (this.key == Character.MAX_VALUE) {
            if (this.keyCode == 17)
                //this.save = true;
                this.window.restoreLast();
            if (this.keyCode == 38) {
                this.window.zoom(0.03F);
                //this.window.resizeSelected(0.03F);
            } if (this.keyCode == 40) {
                this.window.zoom(-0.03F);
                //this.window.resizeSelected(-0.03F);
            }
            else if (this.keyCode == 18){
                this.window.toggleGrid();
            }
        }

			if (this.key == 'h'){
				this.window.setFillColor(255, 50, 100, 10);
			}
			if (this.key == 'j'){
				this.window.setBoarderColor(200, 50, 200);
			}
			if (this.key == 's'){
				this.window.setThickness(25);
			}
			// saving and opening. Currently using absolute paths to demonstrate how this could
			// work with a choose file/choose folder system
			if (this.key == 'x'){
				try {
					this.window.save("C:/Users/isaac/Desktop/drawing.polly");
				} catch (IOException e) {
					System.out.println(e);
					e.printStackTrace();
				}
			}
			if (this.key == 'y'){
				try {
					this.window.open("C:/Users/isaac/Desktop/drawing.polly");
				} catch (ClassNotFoundException | IOException e) {
					System.out.println(e);
					e.printStackTrace();
				}
			}
			if (this.key == 'z') {
				this.window.duplicateSelected();
			}
			if (this.key == '['){ // Note: we can use punctuation marks to test more functions. Pressing shift for keys doesn't work (so we can't use A or >, for example). Numbers also didn't work for me.
				this.window.setSelectedFillColor(10, 50, 50, 100);
			}
			if (this.key == ']'){
				this.window.setSelectedBoarderColor(10, 200, 100);
			}
			if (this.key == ';'){
				System.out.println(this.window.getSelectedFillColors());
			}
			if (this.key == '\''){ // this is the apostrophe punctuation mark
				System.out.println(this.window.getSelectedBoarderColors());
			}
			if (this.key == '.'){
				this.window.resizeSelected(0.1F);
			}
			if (this.key == '/'){
				this.window.resizeSelected(-0.1F);
			}
			if (this.key == '`'){
				this.window.createCurve(this.mouseX, this.mouseY);
			}
    }
*/
    public void mouseDragged() {
      if(shift) this.window.pan(this.mouseX, this.mouseY, this.pmouseX, this.pmouseY);
      else this.window.freeDraw(this.mouseX, this.mouseY);
    }

   public void mouseClicked() {
        this.window.select(this.mouseX, this.mouseY);
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
