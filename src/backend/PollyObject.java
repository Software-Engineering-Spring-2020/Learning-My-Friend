package backend;
import processing.core.PApplet;

public abstract class PollyObject {
  protected PApplet sketch;
  protected float xpos, ypos, rot = 0;
  protected boolean showBoundary;
  protected  float[] boundingBox = new float[2];
  
  public PollyObject(PApplet sketch, float x, float y){
    this.sketch = sketch;
    xpos = x;
    ypos = y;
  }

  protected void setPosition(float x, float y){
    xpos = x;
    ypos = y;
  }
  protected void setRotation(float r){
    rot = r; 
  }
  protected float[] getPosition(){
    return new float[]{xpos, ypos}; 
  }
  protected float getRotation(){
    return rot; 
  }
  protected float[] getDimensions(){
    return new float[]{-1, -1};
  }
  protected boolean withinScope(float x, float y){
    if(x < xpos + boundingBox[0] && x > xpos && y < ypos+boundingBox[1] && y > ypos) return true;
    return false;
  }
  protected void display(){  }
}