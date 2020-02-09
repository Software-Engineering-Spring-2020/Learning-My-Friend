package backend_beta;
//import processing.core.*;

abstract class ColorfulObject extends PollyObject{
  int[] boarderColor = new int[3];
  int[] fillColor = new int[3];
  
  ColorfulObject(float x, float y){
    super(x, y);
  }
  
  void setBoarderColor(int r, int g, int b){
    boarderColor[0] = r;
    boarderColor[1] = g;
    boarderColor[2] = b;
  }
  
  void setFillColor(int r, int g, int b){
    fillColor[0] = r;
    fillColor[1] = g;
    fillColor[2] = b;
  }
  
  int[] getBoarderColor(){ 
    return boarderColor; 
  }
  int[] getFillColor(){ 
    return fillColor; 
  }
}