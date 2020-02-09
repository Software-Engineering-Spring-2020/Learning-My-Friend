package backend;
//import processing.core.*;

import processing.core.PApplet;

abstract class ColorfulObject extends PollyObject{
  int[] boarderColor = new int[3];
  int[] fillColor = new int[3];
  
  ColorfulObject(PApplet sketch, float x, float y){
    super(sketch, x, y);
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

  void display(){
    sketch.fill(sketch.color(fillColor[0], fillColor[1], fillColor[2]));
    sketch.stroke(sketch.color(boarderColor[0], boarderColor[1], boarderColor[2]));
  }
}