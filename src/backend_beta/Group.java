package backend_beta;
import java.util.ArrayList;
//import processing.core.*;

class Group extends PollyObject{
  ArrayList<Object> members = new ArrayList<Object>();
  
  Group(float x, float y){
    super(x, y);
  }
  
  void addMembers(ArrayList<Object> prospectives){
    for(Object prop : prospectives){
      members.add(prop);
    }
  }
  
  ArrayList<Object> getMembers(){
    return members;
  }
  
  void setPosition(float x, float y){
    //TODO HERE OR MAYBE ELSEWHERE?
  }
}