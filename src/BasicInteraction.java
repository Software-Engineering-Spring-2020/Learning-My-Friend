import processing.core.PApplet;

public class BasicInteraction extends PApplet {
  float xo;

  float yo;

  float zoom = 1.0F;

  float angle = 0.0F;

  int d = 40;

  public void setup() {
    this.xo = (this.width / 2);
    this.yo = (this.height / 2);
    noStroke();
  }

  public void draw() {
    background(-14713957);
    translate(this.xo, this.yo);
    scale(this.zoom);
    rotate(this.angle);
    fill(120);
    ellipse(-200.0F, 0.0F, this.d, this.d);
    fill(180);
    ellipse(-100.0F, 0.0F, this.d, this.d);
    fill(220);
    ellipse(0.0F, 0.0F, this.d, this.d);
    fill(180);
    ellipse(100.0F, 0.0F, this.d, this.d);
    fill(120);
    ellipse(200.0F, 0.0F, this.d, this.d);
  }

  public void keyPressed() {
    if (this.key == Character.MAX_VALUE)
      if (this.keyCode == 38) {
        this.zoom += 0.03F;
      } else if (this.keyCode == 40) {
        this.zoom -= 0.03F;
      } else if (this.keyCode == 37) {
        this.angle -= 0.03F;
      } else if (this.keyCode == 39) {
        this.angle += 0.03F;
      }
    if (this.key == ' ') {
      this.angle = 0.0F;
      this.zoom = 1.0F;
      this.xo = (this.width / 2);
      this.yo = (this.height / 2);
    }
  }

  public void mouseDragged() {
    this.xo += (this.mouseX - this.pmouseX);
    this.yo += (this.mouseY - this.pmouseY);
  }

  public void settings() {
    size(600, 200);
    smooth();
  }

  public static void main(String[] passedArgs) {
    String[] appletArgs = { "BasicInteraction" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
