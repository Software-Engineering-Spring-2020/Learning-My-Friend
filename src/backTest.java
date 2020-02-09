import backend_beta.*;
import processing.core.PApplet;

public class backTest extends PApplet {
    boolean save;
    Driver driver;
    public void setup() {
        this.surface.setResizable(true);
        driver = new Driver(this, 50, 50, 1000, 500);
    }

    public void draw() {
        background(153);
        //rect(50,50,100,100);
        this.driver.display();
        //scale(2);
        //this.ds.fakeDisplay();
    }

    public void keyReleased() {
        if (this.key == Character.MAX_VALUE) {
            if (this.keyCode == 17)
                this.save = true;
            if (this.keyCode == 38) {
                this.driver.zoom(0.03F);
            } else if (this.keyCode == 40) {
                this.driver.zoom(-0.03F);
            }
            if (this.keyCode == 18)
                this.driver.changeColor(70, 80, 200);
        }
    }

    public void mouseDragged() {
        this.driver.pan((this.mouseX - this.pmouseX), (this.mouseY - this.pmouseY));
    }

    public void mouseClicked() {
        this.driver.createShape(this.mouseX, this.mouseY, 'e');
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
