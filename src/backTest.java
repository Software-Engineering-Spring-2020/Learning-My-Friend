import backend_beta.*;
import processing.core.PApplet;

public class backTest extends PApplet {
    boolean save;
    DrawSpace ds;

    public void setup() {
        this.surface.setResizable(true);
        this.ds = new DrawSpace(50.0F, 50.0F, 1000.0F, 500.0F);
    }

    public void draw() {
        background(0);
        this.ds.display();
    }

    public void keyReleased() {
        if (this.key == Character.MAX_VALUE) {
            if (this.keyCode == 17)
                this.save = true;
            if (this.keyCode == 38) {
                this.ds.zoom(0.03F);
            } else if (this.keyCode == 40) {
                this.ds.zoom(-0.03F);
            }
            if (this.keyCode == 18)
                this.ds.createEllipse(this.mouseX, this.mouseY);
        }
    }

    public void mouseDragged() {
        this.ds.pan((this.mouseX - this.pmouseX), (this.mouseY - this.pmouseY));
    }

    public void mouseClicked() {
        this.ds.createEllipse(this.mouseX, this.mouseY);
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
