package backend;
import processing.core.PApplet;

import java.util.ArrayList;

class Group extends PollyObject{
  ArrayList<Object> members = new ArrayList<Object>();
  
  Group(PApplet sketch, float x, float y){
    super(sketch, x, y);
  }

  protected void addMembers(ArrayList<Object> prospectives){
    for(Object prop : prospectives){
      members.add(prop);
    }
  }
  
  ArrayList<Object> getMembers(){
    return members;
  }

  protected void setPosition(float x, float y){
    //TODO HERE OR MAYBE ELSEWHERE?
  }
}