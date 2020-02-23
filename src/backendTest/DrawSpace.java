package backend;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;
import backend.objects.Comment;

class DrawSpace extends ColorfulObject{
    private float xcenter, ycenter;
    private ArrayList<PollyObject> objects = new ArrayList<PollyObject>();
    private ArrayList<PollyObject> comments = new ArrayList<PollyObject>();
    transient boolean showComments;

    DrawSpace(PApplet sketch, PGraphics graphics, float x, float y, float w, float h){
        super(sketch, graphics, x, y, 0, new int[] {255, 255, 255, 255}, new int[] {255, 255, 255});

        this.graphic = sketch.createGraphics(w, h);


        pixelWidth = w;
        pixelHeight = h;
        xcenter = xpos+pixelWidth/2;
        ycenter = ypos+pixelHeight/2;
        graphic.rectMode(PConstants.CENTER);
        graphic.ellipseMode(PConstants.CENTER);
        graphic.imageMode(PConstants.CENTER);
        graphic.textMode(PConstants.MODEL);
        graphic.textAlign(PConstants.CENTER, PConstants.CENTER);
        //sketch.shapeMode(PConstants.CENTER);
    }

    protected PGraphics getGraphic(){
      return graphic;
    }
/*
    protected void init(PApplet sketch){
      super.init(sketch);
      for(PollyObject obj : objects){
        obj.init(sketch);
      }
      for(PollyObject obj : comments){
        obj.init(sketch);
      }
    }
*/
    protected ArrayList<PollyObject> getAllObjects(){
        return objects;
    }

    protected PollyObject getObjectAt(float x, float y, float zoom){
        float[] pos = translateCoordinates(x, y, zoom);
        if(showComments){
          for(int i = comments.size()-1; i>=0; i--){
              PollyObject obj = comments.get(i);
              if(obj.withinScope(pos[0], pos[1])) return obj;
          }
        }

        for(int i = objects.size()-1; i>=0; i--){
            PollyObject obj = objects.get(i);
            if(obj.withinScope(pos[0], pos[1])) return obj;
        }
        return null;
    }

    protected boolean withinScope(float x, float y){
        if (x < pixelWidth/2 && x > -pixelWidth/2 && y < pixelHeight/2 && y > -pixelHeight/2) return true;
        return false;
    }

    protected int getNumObjects(){
        return objects.size();
    }

    protected float[] translateCoordinates(float x, float y, float zoom){
        float[] coord = new float[]{x-xcenter, y-ycenter}; //translates from coordinates to relational fraction
        coord[0] = (pixelWidth/2) * (coord[0] / ((pixelWidth/2)*zoom));
        coord[1] = (pixelHeight/2) * (coord[1] / ((pixelHeight/2)*zoom));
        return coord;
    }

    protected float[] relativePan(float xo, float yo, float zoom){
        float[] coord = new float[]{xo, yo}; //translates from coordinates to relational fraction
        coord[0] = (pixelWidth/2) * (coord[0] / ((pixelWidth/2)*zoom));
        coord[1] = (pixelHeight/2) * (coord[1] / ((pixelHeight/2)*zoom));
        return coord;
    }

    protected void pan(float xo, float yo){
        super.pan(xo, yo);
        xcenter = xpos+pixelWidth/2;
        ycenter = ypos+pixelHeight/2;
    }

    protected void setPosition(float x, float y){
        super.setPosition(x, y);
        xcenter = xpos+pixelWidth/2;
        ycenter = ypos+pixelHeight/2;
    }

    protected void display(float zoom, boolean showComments, boolean showGrid, float gridSpacing){
      graphic.beginDraw();
        this.showComments = showComments;
        //graphic.translate(xcenter, ycenter);

        graphic.background(graphic.color(fillColor[0], fillColor[1], fillColor[2]), fillColor[3]);
        //sketch.rect(0, 0, pixelWidth, pixelHeight);
        if(showGrid) displayGrid(gridSpacing);
        for(PollyObject obj : objects){
            graphic.push();
            obj.display();
            graphic.pop();
        }
        if(showComments){
            for(PollyObject obj : comments){
                graphic.push();
                obj.display();
                graphic.pop();
            }
        }
        graphic.endDraw();
        sketch.scale(zoom);
        sketch.image(graphic, xcenter, ycenter);
    }

    protected boolean addObject(PollyObject shape){
        return objects.add(shape);
    }

    protected boolean removeObject(PollyObject shape){
      if(shape instanceof Comment) return removeComment(shape);
        return objects.remove(shape);
    } protected PollyObject removeObject(int i){
        return objects.remove(i);
    } protected void clear(){
        objects.clear();
    }

    protected int indexOf(PollyObject shape){
        return objects.indexOf(shape);
    }

    protected PollyObject getShape(int i){
        return objects.get(i);
    }

    protected boolean addComment(PollyObject shape){
        return comments.add(shape);
    }

    protected boolean removeComment(PollyObject shape){
        return comments.remove(shape);
    } protected PollyObject removeComment(int i){
        return comments.remove(i);
    } protected void clearComments(){
        comments.clear();
    }

    protected PollyObject getComment(int i){
        return comments.get(i);
    }

    protected void displayGrid(float spacing){
        for(float x = -pixelWidth/2; x<pixelWidth/2; x+=spacing) sketch.line(x, -pixelHeight/2, x, pixelHeight/2);
        for(float y = -pixelHeight/2; y<pixelHeight/2; y+=spacing) sketch.line(-pixelWidth/2, y, pixelWidth/2, y);
    }
}
