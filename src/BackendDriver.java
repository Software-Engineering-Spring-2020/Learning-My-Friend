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
            } else if (this.keyCode == 40) {
                this.window.zoom(-0.03F);
            }
            else if (this.keyCode == 18){
                this.window.toggleGrid();
            }
        }
    }

    public void mouseDragged() {
        this.window.canvasPan((this.mouseX - this.pmouseX), (this.mouseY - this.pmouseY));
    }

    public void mouseClicked() {
        this.window.createShape(this.mouseX, this.mouseY, 'r');
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
