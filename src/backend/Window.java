package backend;

import backend.objects.ObjectFactory;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
* Acts as a conduit between the backend functionality and everything external to the backend package.
*/
public class Window {
    private ArrayList<PollyObject> trash = new ArrayList<PollyObject>();
    private ArrayList<PollyObject> selected = new ArrayList<PollyObject>();
    private ArrayList<PollyObject> copied = new ArrayList<PollyObject>();
    private ArrayList<float[]> freePoints = new ArrayList<float[]>();
    private ArrayList<float[]> pollyPoints = new ArrayList<float[]>();
    private ArrayList<float[]> curvePoints = new ArrayList<float[]>();
    private ArrayList<float[]> shapePoints = new ArrayList<float[]>();
    private PApplet sketch;
    private ArrayList<DrawSpace> slides;
    private float zoom = 1, strokeWeight = 3;
    private int[] fillColor, boarderColor;
    private float XINIT, YINIT, WIDTH, HEIGHT, gridSpacing = 30;
    private boolean showGrid = false, showComments = false, ellipse, export = false;
    private int numberVertex = 0, size = 0;
    private String savefile = "";

    private int currentSlide = 0;
    private boolean currentSlideChanged;
    private float[] editingPosition;
    private float editingZoom;
    private boolean presenting;
    private ObjectFactory of;

    /**
    * Class Constructor to initialize an ArrayList of DrawSpace objects for storing the slides and set the default parameters for color and dimensions
    * @param sketch a reference to a PApplet to allow general functionality of the processing library
    * @param x a float to represent the initial x starting position (in pixels) of the DrawSpaces
    * @param y a float to represent the initial y starting position (in pixels) of the DrawSpaces
    * @param w a float to represent the width (in pixels) of each DrawSpace (slide)
    * @param h a float to represent the height (in pixels) of each DrawSpace (slide)
    */
    public Window(PApplet sketch, float x, float y, float w, float h) {
        this.sketch = sketch;
        this.slides = new ArrayList<DrawSpace>();
        slides.add(new DrawSpace(sketch, x, y, w, h));
        of = new ObjectFactory(sketch);
        fillColor = new int[] { 0, 0, 0, 0 };
        boarderColor = new int[] { 0, 0, 0 };
        XINIT = x;
        YINIT = y;
        WIDTH = w;
        HEIGHT = h;
    }

    /**
    * Displays the current slide and all of it's elements to the screen
    */
    public void display() {
        // TODO: only call display "recursively" down the heirarchy if we do not have a cached PGraphic
        sketch.push();
        this.slides.get(currentSlide).display(zoom, showComments, showGrid, gridSpacing);

        sketch.push();
        sketch.fill(fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
        sketch.stroke(boarderColor[0], boarderColor[1], boarderColor[2]);
        sketch.strokeWeight(strokeWeight);
        if(size != slides.get(currentSlide).getNumObjects()) newToolSelection();

        for (int i = 0; i < freePoints.size() - 1; i++) {
            if (freePoints.size() > 1)
                sketch.line(freePoints.get(i)[0], freePoints.get(i)[1], freePoints.get(i + 1)[0],
                freePoints.get(i + 1)[1]);
        }

        float[] coord = slides.get(currentSlide).translateCoordinates(sketch.mouseX, sketch.mouseY, zoom);
        for (int i = 0; i < curvePoints.size(); i++) {
          sketch.line(curvePoints.get(curvePoints.size()-1)[0], curvePoints.get(curvePoints.size()-1)[1], coord[0], coord[1]);
          if (i >= 1)
              sketch.line(curvePoints.get(i-1)[0], curvePoints.get(i-1)[1], curvePoints.get(i)[0],
              curvePoints.get(i)[1]);
        }

        for (int i = 0; i < pollyPoints.size(); i++) {
          sketch.line(pollyPoints.get(pollyPoints.size()-1)[0], pollyPoints.get(pollyPoints.size()-1)[1], coord[0], coord[1]);
          if (i >= 1)
              sketch.line(pollyPoints.get(i-1)[0], pollyPoints.get(i-1)[1], pollyPoints.get(i)[0],
              pollyPoints.get(i)[1]);
        }

        sketch.push();
        sketch.noFill();

        for (int i = 0; i < shapePoints.size(); i++) {
          if(ellipse) sketch.ellipse(shapePoints.get(0)[0], shapePoints.get(0)[1], 2*Math.abs(coord[0]-shapePoints.get(0)[0]), 2*Math.abs(coord[1]-shapePoints.get(0)[1]));
          else sketch.rect(shapePoints.get(0)[0], shapePoints.get(0)[1], 2*Math.abs(coord[0]-shapePoints.get(0)[0]), 2*Math.abs(coord[1]-shapePoints.get(0)[1]));
        }
        sketch.pop();
        sketch.pop();

        if (pollyPoints.size() >= numberVertex && numberVertex > 1) {
          PollyObject obj = of.createPollyGon(pollyPoints, strokeWeight, fillColor, boarderColor);
            //slides.get(currentSlide).addObject(of.createPollyGon(pollyPoints, strokeWeight, fillColor, boarderColor));
            slides.get(currentSlide).addObject(obj);
            //selected.add(obj);
            pollyPoints.clear();
            numberVertex = 0;
        }

        if (curvePoints.size() >= 4) {
            slides.get(currentSlide).addObject(of.createCurve(curvePoints, strokeWeight, fillColor, boarderColor));
            curvePoints.clear();
        }

        for(PollyObject obj : selected){
          obj.showBoundingBox();
        }

        if(export && slides.get(currentSlide).getPosition()[0] == XINIT && slides.get(currentSlide).getPosition()[1]==YINIT){
          PImage toSave = sketch.get((int)XINIT+1, (int)YINIT+1, (int)WIDTH-1, (int)HEIGHT-1);
          toSave.save(savefile);
          export = false;
        }

        sketch.pop();
    }

    /**
    * Translates the window-relative x,y coordinates of the mouse to slide-relative coordinates with the origin point at the top left
    * @param x raw X position of the mouse (given in window-relative coordinates)
    * @param y raw Y position of the mouse (given in window-relative coordinates)
    * @return an array containing two floats representing an x and y position that accounts for any pan and/or zoom
    */
    private float[] translate(float x, float y) {
        return new float[] { (WIDTH / 2) * (x / ((WIDTH / 2) * zoom)), (HEIGHT / 2) * (y / ((HEIGHT / 2) * zoom)) };
    }

    /**
    * Clears the line drawn between a point on the canvas and the mouse position when a new tool is selected. Used when there is no desire to complete a shape
    */
    public void newToolSelection(){
      pollyPoints.clear();
      curvePoints.clear();
      shapePoints.clear();
    }

    /*********************************************************
     *
     *
     * DRAWSPACE RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

     /**
     * Alter the size of the slide from its center. If any change should decrease the zoom below 0.1%, it is set to 0.1% instead. Slide starts at 100%
     * @param factor The amount to change the current zoom (in percentage) from the current zoom amount
     */
    public void zoom(float factor) { // draw offcenter once zoom
        zoom = sketch.max(.001F, zoom + factor);
    }

    /**
    * Determines if the current mouse position is over the slide
    * @param x raw X position of the mouse
    * @param y raw Y position of the mouse
    * @return a boolean designating whether the position lies over the slide
    */
    public boolean withinCanvas(float x, float y) {
        float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
        return slides.get(currentSlide).withinScope(coord[0], coord[1]);
    }

    /**
    * @deprecated
    * @see pan
    */
    public void canvasPan(float xo, float yo) { // (this.mouseX - this.pmouseX), (this.mouseY - this.pmouseY)
        this.slides.get(currentSlide).pan(xo, yo);
    }

    /**
    * Resets the current shown slide back to the inital starting position and inital zoom amount
    */
    public void reCenter() {
        zoom = 1;
        this.slides.get(currentSlide).setPosition(XINIT, YINIT);
        this.display();
    }

    /**
    * Show/Unshow a grid overlaying the slide
    */
    public void toggleGrid() {
        if (showGrid)
            showGrid = false;
        else
            showGrid = true;
    }

    /**
    * Alter the sizing of the grid squares, minimum of 2 always set
    * @default 30 Starting grid space of 30 pixels
    * @param spacing A float representing the desired size (in pixels) of the squares
    */
    public void setGridSpacing(float spacing) {
        gridSpacing = sketch.max(spacing, 2F);
    } public void changeGridSpacing(float so) {
        gridSpacing = sketch.max(gridSpacing+so, 2F);
    }

    /**
    * Show/Unshow comments on the slide
    */
    public void toggleComments() {
        if (showComments)
            showComments = false;
        else
            showComments = true;
    }

    /*********************************************************
     *
     *
     * OBJECT CREATION RELATED FUNCTIONALITY
     *
     *
     *********************************************************/
     /**
     * Creates and draws an ellipse on the current slide
     * @param x Raw X position of the mouse
     * @param y Raw Y position of the mouse
     * @param w A float to represent the width (in pixels) of the ellipse
     * @param h A float to represent the height (in pixels) of the ellipse
     * @return Whether or not the object was created and added to the slide sucessfully
     */
     public boolean createEllipse(float x, float y, float w, float h) {
       PollyObject obj = null;
       float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
       if (slides.get(currentSlide).withinScope(coord[0], coord[1]))
          obj = of.createEllipse(coord[0], coord[1], w, h, strokeWeight, fillColor, boarderColor);
        if (obj != null)
            return slides.get(currentSlide).addObject(obj);
        return false;
     }

     /**
     * Creates and draws a circle on the current slide
     * @param x Raw X position of the mouse
     * @param y Raw Y position of the mouse
     * @param d A float to represent the diameter (in pixels) of the circle
     * @return Whether or not the object was created and added to the slide sucessfully
     */
    public boolean createEllipse(float x, float y, float d) {
        return createEllipse(x, y, d, d);
    }

    /**
    * Starts the creation of an ellipse at first mouse click, finalizes the ellipse at the second click. Between the first and second click, the ellipse shape is dynamic, with the edge following the mouse position.
    * @param x Raw X position of the mouse
    * @param y Raw Y position of the mouse
    * @return Whether or not the object was created and added to the slide successfully
    */
    public boolean createEllipse(float x, float y) {
      ellipse = true;
      this.numberVertex = 2;
      this.size = slides.get(currentSlide).getNumObjects();
      float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
      shapePoints.add(coord);

      if(shapePoints.size() >= numberVertex){
        PollyObject obj = of.createEllipse(shapePoints.get(0)[0], shapePoints.get(0)[1],
                  2*Math.abs(shapePoints.get(1)[0]-shapePoints.get(0)[0]), 2*Math.abs(shapePoints.get(1)[1]-shapePoints.get(0)[1]),
                  strokeWeight, fillColor, boarderColor);
        return slides.get(currentSlide).addObject(obj);

      }
        return false;
    }

    /**
    * Creates and draws an rectangle on the current slide
    * @param x Raw X position of the mouse
    * @param y Raw Y position of the mouse
    * @param w A float to represent the width (in pixels) of the rectangle
    * @param h A float to represent the height (in pixels) of the rectangle
    * @return Whether or not the object was created and added to the slide successfully
    */
    public boolean createRect(float x, float y, float w, float h) {
        PollyObject obj = null;
        float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
        if (slides.get(currentSlide).withinScope(coord[0], coord[1]))
            obj = of.createRect(coord[0], coord[1], w, h, strokeWeight, fillColor, boarderColor);
        if (obj != null)
            return slides.get(currentSlide).addObject(obj);
        return false;
    }

    /**
    * Creates and draws a square on the current slide
    * @param x Raw X position of the mouse
    * @param y Raw Y position of the mouse
    * @param d A float to represent the diameter (in pixels) of the rectangle
    * @return Whether or not the object was created and added to the slide successfully
    */
    public boolean createRect(float x, float y, float d) {
        return createRect(x, y, d, d);
    }

    /**
    * Starts the creation of a Rectangle at first mouse click, finalizes the rectangle at the second click. Between the first and second click, the rectangle shape is dynamic, with the bottom right corner following the mouse position.
    * @param x Raw X position of the mouse
    * @param y Raw Y position of the mouse
    * @return Whether or not the object was created and added to the slide successfully
    */
    public boolean createRect(float x, float y) {
      ellipse = false;
      this.numberVertex = 2;
      this.size = slides.get(currentSlide).getNumObjects();
      float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
      shapePoints.add(coord);

      if(shapePoints.size() >= numberVertex){
        PollyObject obj = of.createRect(shapePoints.get(0)[0], shapePoints.get(0)[1],
                  2*Math.abs(shapePoints.get(1)[0]-shapePoints.get(0)[0]), 2*Math.abs(shapePoints.get(1)[1]-shapePoints.get(0)[1]),
                  strokeWeight, fillColor, boarderColor);
        return slides.get(currentSlide).addObject(obj);

      }
        return false;
    }

    /**
    * Creates and draws text to the slide
    * @param x Raw X position of the mouse
    * @param y Raw Y position of the mouse
    * @param str The desired text to be displayed
    * @param font The desired font of the text to be displayed
    * @param textSize The desired size of the text to be displayed
    * @return Whether or not the object was created and added to the slide successfully
    */
    public boolean createTextBox(float x, float y, String str, String font, float textSize) {
        PollyObject obj = null;
        float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
        if (slides.get(currentSlide).withinScope(coord[0], coord[1]))
            obj = of.createTextBox(coord[0], coord[1], strokeWeight, fillColor, boarderColor, str, font, textSize);
        if (obj != null)
            return slides.get(currentSlide).addObject(obj);
        return false;
    }

    /**
    * Establishes a text box to allow for interactive input from the user onto the slide. Text will wrap around the width of the box.
    * @param x Raw X position of the mouse
    * @param y Raw Y position of the mouse
    * @param x2 Set the starting width of the text box
    * @param font The desired font of the text to be displayed
    * @param textSize The desired size of the text to be displayed
    * @return Whether or not the object was created and added to the slide successfully
    */
    public boolean createInteractiveTextBox(float x, float y, float x2, String font, float textSize) {
        PollyObject obj = null;
        float[] startCoord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
        if (slides.get(currentSlide).withinScope(startCoord[0], startCoord[1]))
            obj = of.createInteractiveTextBox(startCoord[0], startCoord[1], x2, strokeWeight, fillColor, boarderColor, font, textSize);
        if (obj != null)
            return slides.get(currentSlide).addObject(obj);
        return false;
    }

    /**
    * Creates and draws a comment to the slide. Comments can be hidden.
    * @param x Raw X position of the mouse
    * @param y Raw Y position of the mouse
    * @param str The desired text to be displayed
    * @param font The desired font of the text to be displayed
    * @param textSize The desired size of the text to be displayed
    * @return Whether or not the object was created and added to the slide successfully
    */
    public boolean createComment(float x, float y, String str, String font, float textSize) {
        PollyObject obj = null;
        float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
        if (slides.get(currentSlide).withinScope(coord[0], coord[1]))
            obj = of.createComment(coord[0], coord[1], strokeWeight, fillColor, boarderColor, str, font, textSize);
        if (obj != null)
            return slides.get(currentSlide).addComment(obj);
        return false;
    }

    /**
    * Imports an image from the users computer into the program and displays it at a specified position
    * @param x Raw X position (relative to the window) to display image
    * @param y Raw Y position (relative to the window) to display image
    * @param filename The file (path + name) of the image to display
    * @param extension The image file type (the . is still necessary)
    * @return Whether or not the object was created and added to the slide successfully
    */
    public boolean importImage(float x, float y, String filename, String extension) {
        PollyObject obj = null;
        float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
        if (slides.get(currentSlide).withinScope(coord[0], coord[1]))
            obj = of.importImage(coord[0], coord[1], filename, extension);
        if (obj != null)
            return slides.get(currentSlide).addObject(obj);
        return false;
    }

    /**
    * Imports an image from the users computer into the program and displays it at the center of the slide
    * @param filename The file (path + name) of the image to display
    * @param extension The image file type (the . is still necessary)
    * @return Whether or not the object was created and added to the slide successfully
    */
    public boolean importImage(String filename, String extension) {
        PollyObject obj = of.importImage(0, 0, filename, extension);
        if (obj != null)
            return slides.get(currentSlide).addObject(obj);
        return false;
    }

    /*********************************************************
     *
     *
     * TOOLBAR RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

     /**
     * Alter the size of each selected object from the relative center of each object
     * @default 1 Slide starts at 100%
     * @param factor A float representing the amount to change (in percentage) from the current Size
     */
     public void resizeSelected(float factor){
       for(PollyObject obj : selected){
         obj.resize(factor);
       }
     }

     /**
     * Alter the size of each selected object from the relative center of each object. If no object is selected, the slide is zoomed instead
     * @param factor The amount to scale the object (in percentage) from the current size
     */
     public void resize(float factor){
       if(selected.size() == 0) zoom(factor);
       else{
         for(PollyObject obj : selected){
           obj.resize(factor);
         }
       }
     }

     /**
     * Groups selected objects if not already a group, otherwise ungroups the grouped objects
     * @deprecated Grouping is not a supported mechanic in slides
     */
     public void toggleGroup(){
       for(PollyObject obj : selected){
         if(obj instanceof Group) slides.get(currentSlide).removeObject(obj);
         else slides.get(currentSlide).addObject(new Group(sketch, 0, 0, selected));
       }
     }

     /**
     * Groups all the currently selected objects, making it so selecting one selects all in the group
     * @deprecated Grouping is not a supported mechanic in slides
     */
    public void group() {
        slides.get(currentSlide).addObject(new Group(sketch, 0, 0, selected));
    }

    /**
    * Splits up all the objects in all the groups currently selected, if any
    * @deprecated Grouping is not a supported mechanic in slides
    */
    public void unGroup() {
        for (PollyObject obj : selected) {
            if (obj instanceof Group)
                slides.get(currentSlide).removeObject(obj);
        }
    }

    /**
    * Shifts all current selected objects the same amount if mouse is over at least one. If no object is selected, the slide is shfited instead.
    * @param mouseX Current raw X position of the mouse
    * @param mouseY Current raw Y position of the mouse
    * @param pmouseX Previous raw X position of the mouse
    * @param pmouseY Previous raw Y position of the mouse
    */
    public void pan(float mouseX, float mouseY, float pmouseX, float pmouseY) {
      boolean pan = false;
      float[] coord = slides.get(currentSlide).translateCoordinates(mouseX, mouseY, zoom);
      float[] translation = translate(mouseX-pmouseX, mouseY-pmouseY);

      if(selected.size() == 0 && withinCanvas(mouseX, mouseY)) this.slides.get(currentSlide).pan(translation[0], translation[1]);
      else{
        for (PollyObject obj : selected) {
          if(obj.withinScope(coord[0], coord[1])) pan = true;
        }
        if(pan){
          for(PollyObject obj : selected) obj.pan(translation[0], translation[1]);
        }
      }
    }


    /**
    * Changes the current fill color of all selected elements and any future elements created
    * @param r Represents the red value in color combinations (range between 0 - 255)
    * @param g Represents the green value in color combinations (range between 0 - 255)
    * @param b Represents the blue value in color combinations (range between 0 - 255)
    * @param a Represents the alpha (transparent) value for the color (range between 0 - 255)
    */
    public void setFillColor(int r, int g, int b, int a) {
        fillColor[0] = r;
        fillColor[1] = g;
        fillColor[2] = b;
        fillColor[3] = a;
        setSelectedFillColor(r, g, b, a);
    }

    /**
    * Changes the current boarder color of all selected elements and any future elements created
    * @param r Represents the red value in color combinations (range between 0 - 255)
    * @param g Represents the green value in color combinations (range between 0 - 255)
    * @param b Represents the blue value in color combinations (range between 0 - 255)
    */
    public void setBoarderColor(int r, int g, int b) {
        boarderColor[0] = r;
        boarderColor[1] = g;
        boarderColor[2] = b;
        setSelectedBoarderColor(r, g, b);
    }

    /**
    * Changes the current fill color of all selected objects
    * @deprecated Combined the functionality of this method with setting the fill color of all future objects created
    * @see setFillColor(int r, int g, int b, int a)
    */
    public void setSelectedFillColor(int r, int g, int b, int a) {
        for (PollyObject shape : selected) {
            if (shape instanceof ColorfulObject)
                ((ColorfulObject) shape).setFillColor(r, g, b, a);
        }
    }

    /**
    * Changes the current boarder color of all selected objects
    * @deprecated Combined the functionality of this method with setting the boarder color of all future objects created
    * @see setBoarderColor(int r, int g, int b)
    */
    public void setSelectedBoarderColor(int r, int g, int b) {
        for (PollyObject shape : selected) {
            if (shape instanceof ColorfulObject)
                ((ColorfulObject) shape).setBoarderColor(r, g, b);
        }
    }

    /**
    * Gets the fill color of all currently selected objects
    * @return An ArrayList of int arrays representing the fill color of all currently selected objects
    */
    public ArrayList<int[]> getSelectedFillColors() {
        ArrayList<int[]> colors = new ArrayList<int[]>();
        for (PollyObject shape : selected) {
            if (shape instanceof ColorfulObject)
                colors.add(((ColorfulObject) shape).getFillColor());
        }
        return colors;
    }

    /**
    * Gets the boarder color of all currently selected objects
    * @return An ArrayList of int arrays representing the boarder color of all currently selected objects
    */
    public ArrayList<int[]> getSelectedBoarderColors() {
        ArrayList<int[]> colors = new ArrayList<int[]>();
        for (PollyObject shape : selected) {
            if (shape instanceof ColorfulObject)
                colors.add(((ColorfulObject) shape).getBoarderColor());
        }
        return colors;
    }

    /**
    * Rotate each selected object to the desired angle in degrees
    * @param a A float representing the desired angle of rotation from the original position for each selected object in degrees
    */
    public void rotate(float a) {
        for (PollyObject shape : selected) {
            shape.setRotate(a);
        }
    }

    /**
    * Select object below current mouse position, only allows for one object selected at a time
    * @deprecated
    * @see select(float x, float y)
    */
    public void singleSelect(float x, float y) {
        selected.clear();
        multiSelect(x, y);
    }

    /**
    * Select object below current mouse position, allows for multiple objects selected at a time
    * @deprecated
    * @see select(float x, float y)
    */
    public void multiSelect(float x, float y) {
        PollyObject obj = slides.get(currentSlide).getObjectAt(x, y, zoom);
        if (obj != null){
          if(selected.contains(obj)) selected.remove(obj);
          else selected.add(obj);
        }
    }

    /**
    * Select object below current mouse position, allows for multiple objects selected at a time. If a selected object is clicked again, it is unselected. If the slide is clicked, the selection is cleared.
    * @param x Raw X position of the mouse
    * @param y Raw Y position of the mouse
    */
    public void select(float x, float y) {
      PollyObject obj = slides.get(currentSlide).getObjectAt(x, y, zoom);
      if (obj != null){
        if(selected.contains(obj))
          selected.remove(obj);
        else
          selected.add(obj);
      }
      else if(withinCanvas(x, y))
          selected.clear();
    }

    /**  DO A CHANGE HERE SLKFGAPOFGNSDFDFNLKSDF MSDFKLHF SD,FHLSDK ***************************************
    * Remove each object currently selected from being displayed on the slide. Objects are restorable until new object is added to the slide.
    * @return Whether or not the objects were sucessfully removed from the slide and still restorable
    */
    public boolean delete() {
        boolean successful = true;
        for (PollyObject shape : selected) {
            trash.add(shape);
            if (!slides.get(currentSlide).removeObject(shape))
                successful = false;
        }
        selected.clear();
        return successful;
    }

    /**  DO A CHANGE HERE SLKFGAPOFGNSDFDFNLKSDF MSDFKLHF SD,FHLSDK ***************************************
    * Remove each object currently selected from being displayed on the slide. Objects are restorable until new object is added to the slide.
    * @deprecated
    * @see delete()
    * @return Whether or not the objects were sucessfully removed from the slide and still restorable
    */
    public boolean deleteSelected() {
        boolean successful = true;
        for (PollyObject shape : selected) {
            trash.add(shape);
            if (!slides.get(currentSlide).removeObject(shape))
                successful = false;
        }
        return successful;
    }

    /**
    * Create a deep clone of all currently selected objects and display them on the slide, overlaying the originals. Throws error if unsucessful.
    * @deprecated
    * @see paste()
    */
    public void duplicateSelected() {
        for (PollyObject shape : selected) {
            try {
                slides.get(currentSlide).addObject(SerialManager.deepClonePollyObject(sketch, shape));
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
        }
    }

    /**
    * Create a deep clone of all currently selected objects and display them on the slide,overlaying the originals. Throws error if unsucessful.
    * @deprecated
    * @see paste()
    */
    public void duplicate() {
        for (PollyObject shape : selected) {
            try {
                slides.get(currentSlide).addObject(SerialManager.deepClonePollyObject(sketch, shape));
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
        }
    }

    /**
    * Remove the most recently created object from the slide. This object is restoreable.
    * @return Whether or not the deleted object is restorable
    */
    public boolean deleteLast() {
        if(slides.get(currentSlide).getNumObjects() <= 0) return false;
        return trash.add(slides.get(currentSlide).removeObject(slides.get(currentSlide).getNumObjects()-1));
    }

    /**
    * Restore the most recently trashed (deleted) object back onto the slide.
    * @return Whether or not the deleted object was sucessfully restored
    */
    public boolean restoreLast() {
        sketch.println(trash.size());
        if (trash.isEmpty()) return false;
        return slides.get(currentSlide).addObject(trash.remove(trash.size()-1));
    }

    /**
    * Remove all objects from the slide. Each object is restorable in reverse order from which they were first added.
    */
    public void clear(){
        for(PollyObject shape : slides.get(currentSlide).getAllObjects()){
            sketch.println(slides.get(currentSlide).getNumObjects()+" : "+trash.size());
            trash.add(shape);
        }
        slides.get(currentSlide).clear();
        sketch.println(slides.get(currentSlide).getNumObjects()+" : "+trash.size());
    }

    /**  DO A CHANGE HERE SLKFGAPOFGNSDFDFNLKSDF MSDFKLHF SD,FHLSDK ***************************************
    * All currently selected slides are flagged for deep cloning later. Each new selection flagging overrides and clears the previous flags.
    * @return Whether or not each selected was sucessfully flagged for cloning later
    */
    public boolean copy(){
        boolean sucess = true;
        copied.clear();
        for(PollyObject shape : selected){
            if(!copied.add(shape)) sucess = false;
        }
        return sucess;
    }

    /**  DO A CHANGE HERE SLKFGAPOFGNSDFDFNLKSDF MSDFKLHF SD,FHLSDK ***************************************
    * Combines the functionality of copy and delete
    * @return Whether or not the objects were sucessfully removed from the slide and still restorable
    * @see copy()
    * @see delete()
    */
    public boolean cut(){
        boolean sucess = true;
        if(!copy()) sucess = false;
        clear();
        return sucess;
    }

    /**
    * Create a deep clone of all currently selected objects and display them on the slide, slightly offset from the original objects. Throws error if unsucessful.
    */
    public void paste(){
      for (PollyObject shape : copied) {
          try {
              //shape.pan(10, 10);
              slides.get(currentSlide).addObject(SerialManager.deepClonePollyObject(sketch, shape));
              //shape.pan(-10, -10);
              slides.get(currentSlide).getShape(slides.get(currentSlide).getNumObjects()-1).pan(10, 10);
    } catch (ClassNotFoundException | IOException e) {
      System.out.println(e);
      e.printStackTrace();
    }
      }
    }

    /**
     * Feeds keyboard input into all currently selected interactive textboxes - allows for interactive typing of text. Delete is not yet supported.
     * @param key A character representation of the key currently pressed (capital and lower case letters are different, all function keys are the same)
     * @param keyCode Represents the value of they key currently pressed (capital and lower case letters are the same, all function keys are different)
     */
    public void keyPressed(char key, int keyCode) {
        for (PollyObject obj : selected) {
            if (obj instanceof ListenerObject) {
                ListenerObject lobj = (ListenerObject) obj;
                lobj.handleKey(key, keyCode);
            }
        }
    }

    /*********************************************************
     *
     *
     *          DRAWING RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

     /**
     * Set the current stroke thickness for drawn objects
     * @param size The desired thickness (in pixels)
     */
    public void setThickness(float size){
        strokeWeight = sketch.max(size, 1);
    }

    /**
    * Set the current stroke thickness for drawn objects. If any change should decrease the stroke below 1, it is set to 1 instead.
    * @param so The amount to change the current thickness (in pixels)
    */
    public void changeThickness(float so){
        strokeWeight = sketch.max(strokeWeight+so, 1);
    }

    /**
    * Draw a connected free form line which follows the mouse position accross the screen. Intended for call during a mouse drag. Call createFreeForm() to render it to the screen and save it to the slide when the mouse is released.
    * @param pmousex
    * @param pmouseY
    * @see createFreeForm()
    * @see mouseRelease()
    */
    public void freeDraw(float pmousex, float pmousey){ //must call createFreeForm() on mouseRelease()
        float[] coord = slides.get(currentSlide).translateCoordinates(pmousex, pmousey, zoom);
        float[] v = new float[]{coord[0], coord[1]};
        freePoints.add(v);
    }

    /**     DO A CHANGE *********************************
    * Create and display an in-progress pollygon. The verticies of the polygon coorespond to the position of the each mouse click.
    * @param pmousex raw X position of the mouse
    * @param pmousey raw Y position of the mouse
    * @param numberVertex Number of verticies the polygon will have
    */
    public void createPollyGon(float pmousex, float pmousey, int numberVertex){
        this.numberVertex = numberVertex;
        this.size = slides.get(currentSlide).getNumObjects();
        float[] coord = slides.get(currentSlide).translateCoordinates(pmousex, pmousey, zoom);
        float[] v = new float[]{coord[0], coord[1]};
        pollyPoints.add(v);
    }

    /**     DO A CHANGE *********************************
    * Create and display an in-progress curve. The points of the curve coorespond to the position of the each mouse click.
    * @param pmousex raw X position of the mouse
    * @param pmousey raw Y position of the mouse
    */
    public void createCurve(float pmousex, float pmousey){
        this.size = slides.get(currentSlide).getNumObjects();
        float[] coord = slides.get(currentSlide).translateCoordinates(pmousex, pmousey, zoom);
        float[] v = new float[]{coord[0], coord[1]};
        curvePoints.add(v);
    }

    /**     DO A CHANGE *********************************
    * Create and display an in-progress stragiht line. The start and stop points of the line coorespond to the position two mouse clicks.
    * @param pmousex raw X position of the mouse
    * @param pmousey raw Y position of the mouse
    * @see addCuve
    */
    public void createLine(float pmouseX, float pmouseY){
        createPollyGon(pmouseX, pmouseY, 2);
    }

    /**
    * Render the free drawn line to the slide and save it to the slide as one object. Intended for call during mouse release.
    * @see freeDraw(float pmousex, float pmousey)
    * @see mouseRelease()
    */
    public void createFreeForm(){ //must be called on the mouseReleased()
        if(!freePoints.isEmpty()) slides.get(currentSlide).addObject(of.createFreeForm(freePoints, strokeWeight, fillColor, boarderColor));
        freePoints.clear();
    }

    /*********************************************************
     *
     *
     *          SLIDE RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

    /**
     * Go to the slide after the current slide, edit mode allows for slide modification
     */
    public void nextSlide() {
        if (currentSlide < slides.size() - 1) {
            float[] currentSlidePosition = slides.get(currentSlide).getPosition();
            slides.get(currentSlide + 1).setPosition(currentSlidePosition[0], currentSlidePosition[1]);
            currentSlide++;
        }
    }

    /**
     * Go to the slide before the current slide, edit mode allows for slide modification
     */
    public void previousSlide() {
        if (currentSlide > 0) {
            float[] currentSlidePosition = slides.get(currentSlide).getPosition();
            slides.get(currentSlide - 1).setPosition(currentSlidePosition[0], currentSlidePosition[1]);
            currentSlide--;
        }
    }

    /**
     * Creates a new blank slide after the current slide.
     */
    public void createSlideAt() {
        DrawSpace ds = slides.get(currentSlide);
        currentSlide++;
        slides.add(currentSlide, new DrawSpace(sketch, ds.xpos, ds.ypos, ds.pixelWidth, ds.pixelHeight));
    }

     /**
      * Alter the order of the slides by swapping the current slide with the one before it.
      */
    public void moveSlideUp() {
        if (currentSlide - 1 < 0) {
            Collections.swap(slides, currentSlide, currentSlide - 1);
        }
    }

    /**
     * Alter the order of the slides by swapping the current slide with the one after it.
     */
    public void moveSlideDown() {
        if (currentSlide + 1 < slides.size()) {
            Collections.swap(slides, currentSlide, currentSlide + 1);
        }
    }

    /**
     * Delete the current slide. This slide is not able to be restored.
     */
    public void deleteSlide() {
        if (slides.size() > 1) {
            slides.remove(currentSlide);
            currentSlide--;
        }
    }

    /**
    * Go to the the specified slide, edit mode allows for slide modification
    * @param index The index value (where the slide is located in the order) of the desired slide, values start at 0.
    */
    public void selectSlide(int index) {
        currentSlide = index;
    }

    /**
    * Enter presentation mode. Slides cannot be edited in this mode. Display slides to full screen.
    */
    public void present() {
        if (!presenting) {
            presenting = true;
            editingPosition = slides.get(currentSlide).getPosition();
            editingZoom = zoom;
            reCenter();
            slides.get(currentSlide).setPosition(sketch.width / 2, sketch.height / 2);
            zoom = sketch.width / slides.get(currentSlide).pixelWidth;
            // TODO: allow animations to trigger
        }
    }

    /**
    * Exit presentation mode to allow for slide modification (editing). Slides displayed at normal size.
    */
    public void endPresent() {
        if (presenting) {
            slides.get(currentSlide).setPosition(editingPosition[0], editingPosition[1]);
            zoom = editingZoom;
        }
        presenting = false;
    }

    /**
     * Get all currernt slides for use in the slide scroll menu.
     * @return A list of buffered images of all the slides in order
     */
    public ArrayList<PGraphics> getSlideThumbnails() {
        ArrayList<PGraphics> thumbs = new ArrayList<PGraphics>();
        for (DrawSpace slide : slides) {
            // TODO: changing to PGraphics is more complicated than I expected
            // this is because the size of the current slide is relative to
            // pixelWidth and pixelHeight
        }
        return thumbs;
    }

    /*********************************************************
     *
     *
     *          SAVE/LOAD/EXPORT RELATED FUNCTIONALITY
     *
     *
     *********************************************************/

     /**
      * Export the current slide as an image
      * @param saveName
      * @param extension
      */
    public void exportAs(String saveName, String extension){
        reCenter();
        export = true;
        savefile = saveName+extension;
    }

    /**
     * Save all slides to a file that can be opened and restored later.
     * @param filename The desired name for the file to be saved under
     * @throws IOException
     */
    public void save(String filename) throws IOException {
      SerialManager.saveSlides(slides, filename);
    }

     /**
      * Open and restore a previous slide project to continue editing.
      * @param filename The name for the slide project to be restored
      * @throws IOException
      */
    public void open(String filename) throws IOException, ClassNotFoundException {
      slides = SerialManager.openSlides(sketch, filename);
    }

}
