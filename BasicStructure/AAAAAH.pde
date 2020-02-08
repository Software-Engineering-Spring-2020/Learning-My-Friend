abstract class Object{
  float xpos, ypos, xinit, yinit, rot = 0;
  boolean showBoundary;
  
  Object(float x, float y){
    xinit = x;
    yinit = y;
    xpos = xinit;
    ypos = yinit;
  }
  
  void setPosition(float x, float y){
    xpos = x;
    ypos = y;
  }
  void setRotation(float r){ 
    rot = r; 
  }
  float[] getPosition(){ 
    return new float[]{xpos, ypos}; 
  }
  float getRotation(){ 
    return rot; 
  }
  
  void display(float[] pos){}
}
