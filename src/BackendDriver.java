import backend.*;
import processing.core.PApplet;

public class BackendDriver extends PApplet {
    boolean save;
    int count = 0;
    Window canvas;
    public void setup() {
        this.surface.setResizable(true);
        canvas = new Window(this, 50, 50, 1000, 500);
    }

    public void draw() {
        background(153);
        this.canvas.display();
    }

    public void keyPressed() {     
        if (this.key == Character.MAX_VALUE) {
            if (this.keyCode == 17)
                //this.save = true;
                this.canvas.redo();
            if (this.keyCode == 38) {
                this.canvas.zoom(0.03F);
            } else if (this.keyCode == 40) {
                this.canvas.zoom(-0.03F);
            }
            else if (this.keyCode == 18){
                //this.canvasSupport.setFillColor((count*10)%255, 80, 200);
                this.canvas.undo();
                //this.canvasSupport.deleteSelected();
            }
        }
    }

    public void mouseDragged() {
        this.canvas.canvasPan((this.mouseX - this.pmouseX), (this.mouseY - this.pmouseY));
    }

    public void mouseClicked() {
        this.canvas.createShape(this.mouseX, this.mouseY, 'r');
        count++;
    }

    public void settings() {
        size(400, 400);
    }

    public static void main(String[] passedArgs) {
        String[] appletArgs = { "backTest" };
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}
