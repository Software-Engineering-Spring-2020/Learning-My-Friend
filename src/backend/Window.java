package backend;

import backend.objects.ObjectFactory;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
    private int currentSlide = 0;
    private boolean currentSlideChanged;
    private ObjectFactory of;
    private float zoom = 1, strokeWeight = 3;
    private int[] fillColor, boarderColor;
    private float XINIT, YINIT, WIDTH, HEIGHT, gridSpacing = 30;
    private boolean showGrid = false, showComments = false, ellipse, export = false;
    private int numberVertex = 0, size = 0;
    private String savefile = "";

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

    private float[] translate(float x, float y) {
        return new float[] { (WIDTH / 2) * (x / ((WIDTH / 2) * zoom)), (HEIGHT / 2) * (y / ((HEIGHT / 2) * zoom)) };
    }

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
    /*private void createDrawSpace(PApplet sketch, float x, float y, float w, float h) {
        slides.add(new DrawSpace(sketch, x, y, w, h));
    }*/

    public void zoom(float factor) { // draw offcenter once zoom
        zoom = sketch.max(.001F, zoom + factor);
    }

    public boolean withinCanvas(float x, float y) {
        float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
        return slides.get(currentSlide).withinScope(coord[0], coord[1]);
    }

    public void canvasPan(float xo, float yo) { // (this.mouseX - this.pmouseX), (this.mouseY - this.pmouseY)
        this.slides.get(currentSlide).pan(xo, yo);
    }

    public void reCenter() {
        zoom = 1;
        this.slides.get(currentSlide).setPosition(XINIT, YINIT);
        this.display();
    }

    public void toggleGrid() {
        if (showGrid)
            showGrid = false;
        else
            showGrid = true;
    }

    public void setGridSpacing(float spacing) {
        gridSpacing = sketch.max(spacing, 2F);
    } public void changeGridSpacing(float so) {
        gridSpacing = sketch.max(gridSpacing+so, 2F);
    }

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
     public boolean createEllipse(float x, float y, float w, float h) {
       PollyObject obj = null;
       float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
       if (slides.get(currentSlide).withinScope(coord[0], coord[1]))
          obj = of.createEllipse(coord[0], coord[1], w, h, strokeWeight, fillColor, boarderColor);
        if (obj != null)
            return slides.get(currentSlide).addObject(obj);
        return false;
     }

    public boolean createEllipse(float x, float y, float d) {
        return createEllipse(x, y, d, d);
    }
/*
    public boolean createEllipse(float x, float y){
      return createEllipse(x, y, 20, 20);
    }*/




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

    public boolean createRect(float x, float y, float w, float h) {
        PollyObject obj = null;
        float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
        if (slides.get(currentSlide).withinScope(coord[0], coord[1]))
            obj = of.createRect(coord[0], coord[1], w, h, strokeWeight, fillColor, boarderColor);
        if (obj != null)
            return slides.get(currentSlide).addObject(obj);
        return false;
    }

    public boolean createRect(float x, float y, float d) {
        return createRect(x, y, d, d);
    }

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

    public boolean createTextBox(float x, float y, String str, String font, float textSize) {
        PollyObject obj = null;
        float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
        if (slides.get(currentSlide).withinScope(coord[0], coord[1]))
            obj = of.createTextBox(coord[0], coord[1], strokeWeight, fillColor, boarderColor, str, font, textSize);
        if (obj != null)
            return slides.get(currentSlide).addObject(obj);
        return false;
    }

    public boolean createInteractiveTextBox(float x, float y, float x2, String font, float textSize) {
        PollyObject obj = null;
        float[] startCoord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
        if (slides.get(currentSlide).withinScope(startCoord[0], startCoord[1]))
            obj = of.createInteractiveTextBox(startCoord[0], startCoord[1], x2, strokeWeight, fillColor, boarderColor, font, textSize);
        if (obj != null)
            return slides.get(currentSlide).addObject(obj);
        return false;
    }

    public boolean createComment(float x, float y, String str, String font, float textSize) {
        PollyObject obj = null;
        float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
        if (slides.get(currentSlide).withinScope(coord[0], coord[1]))
            obj = of.createComment(coord[0], coord[1], strokeWeight, fillColor, boarderColor, str, font, textSize);
        if (obj != null)
            return slides.get(currentSlide).addComment(obj);
        return false;
    }

    public boolean importImage(float x, float y, String filename, String extension) {
        PollyObject obj = null;
        float[] coord = slides.get(currentSlide).translateCoordinates(x, y, zoom);
        if (slides.get(currentSlide).withinScope(coord[0], coord[1]))
            obj = of.importImage(coord[0], coord[1], filename, extension);
        if (obj != null)
            return slides.get(currentSlide).addObject(obj);
        return false;
    }

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

     public void resizeSelected(float factor){
       for(PollyObject obj : selected){
         obj.resize(factor);
       }
     }

     public void resize(float factor){
       if(selected.size() == 0) zoom(factor);
       else{
         for(PollyObject obj : selected){
           obj.resize(factor);
         }
       }
     }

     public void toggleGroup(){
       for(PollyObject obj : selected){
         if(obj instanceof Group) slides.get(currentSlide).removeObject(obj);
         else slides.get(currentSlide).addObject(new Group(sketch, 0, 0, selected));
       }
     }

    public void group() {
        slides.get(currentSlide).addObject(new Group(sketch, 0, 0, selected));
    }

    public void unGroup() {
        for (PollyObject obj : selected) {
            if (obj instanceof Group)
                slides.get(currentSlide).removeObject(obj);
        }
    }

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



    public void setFillColor(int r, int g, int b, int a) {
        fillColor[0] = r;
        fillColor[1] = g;
        fillColor[2] = b;
        fillColor[3] = a;
        setSelectedFillColor(r, g, b, a);
    }

    public void setBoarderColor(int r, int g, int b) {
        boarderColor[0] = r;
        boarderColor[1] = g;
        boarderColor[2] = b;
        setSelectedBoarderColor(r, g, b);
    }

    public void setSelectedFillColor(int r, int g, int b, int a) {
        for (PollyObject shape : selected) {
            if (shape instanceof ColorfulObject)
                ((ColorfulObject) shape).setFillColor(r, g, b, a);
        }
    }

    public void setSelectedBoarderColor(int r, int g, int b) {
        for (PollyObject shape : selected) {
            if (shape instanceof ColorfulObject)
                ((ColorfulObject) shape).setBoarderColor(r, g, b);
        }
    }

    public ArrayList<int[]> getSelectedFillColors() {
        ArrayList<int[]> colors = new ArrayList<int[]>();
        for (PollyObject shape : selected) {
            if (shape instanceof ColorfulObject)
                colors.add(((ColorfulObject) shape).getFillColor());
        }
        return colors;
    }

    public ArrayList<int[]> getSelectedBoarderColors() {
        ArrayList<int[]> colors = new ArrayList<int[]>();
        for (PollyObject shape : selected) {
            if (shape instanceof ColorfulObject)
                colors.add(((ColorfulObject) shape).getBoarderColor());
        }
        return colors;
    }

    public void rotate(float ao) {
        for (PollyObject shape : selected) {
            shape.setRotate(ao);
        }
    }

    public void singleSelect(float x, float y) {
        selected.clear();
        multiSelect(x, y);
    }

    public void multiSelect(float x, float y) {
        PollyObject obj = slides.get(currentSlide).getObjectAt(x, y, zoom);
        if (obj != null){
          if(selected.contains(obj)) selected.remove(obj);
          else selected.add(obj);
        }
    }

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

    public boolean deleteSelected() {
        boolean successful = true;
        for (PollyObject shape : selected) {
            trash.add(shape);
            if (!slides.get(currentSlide).removeObject(shape))
                successful = false;
        }
        return successful;
    }

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

    public boolean deleteLast() {
        if(slides.get(currentSlide).getNumObjects() <= 0) return false;
        return trash.add(slides.get(currentSlide).removeObject(slides.get(currentSlide).getNumObjects()-1));
    }

    public boolean restoreLast() {
        sketch.println(trash.size());
        if (trash.isEmpty()) return false;
        return slides.get(currentSlide).addObject(trash.remove(trash.size()-1));
    }

    public void clear(){
        for(PollyObject shape : slides.get(currentSlide).getAllObjects()){
            sketch.println(slides.get(currentSlide).getNumObjects()+" : "+trash.size());
            trash.add(shape);
        }
        slides.get(currentSlide).clear();
        sketch.println(slides.get(currentSlide).getNumObjects()+" : "+trash.size());
    }

    public boolean copy(){
        boolean sucess = true;
        copied.clear();
        for(PollyObject shape : selected){
            if(!copied.add(shape)) sucess = false;
        }
        return sucess;
    }

    public boolean cut(){
        boolean sucess = true;
        if(!copy()) sucess = false;
        clear();
        return sucess;
    }

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
     * Takes the key pressed to feed it to any InteractiveTextBoxes.
     * While backspace is supported, delete is not (yet).
     * @param key
     */
    public void keyPressed(char key, int keyCode) {
        for (PollyObject obj : selected) {
            if (obj instanceof TextObject) {
                TextObject tobj = (TextObject) obj;
                tobj.handleKey(key, keyCode);
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

    public void setThickness(float size){
        strokeWeight = sketch.max(size, 1);
    }
    public void changeThickness(float so){
        strokeWeight = sketch.max(strokeWeight+so, 1);
    }

    public void freeDraw(float pmousex, float pmousey){ //must call createFreeForm() on mouseRelease()
        float[] coord = slides.get(currentSlide).translateCoordinates(pmousex, pmousey, zoom);
        float[] v = new float[]{coord[0], coord[1]};
        freePoints.add(v);
    }

    public void createPollyGon(float pmousex, float pmousey, int numberVertex){
        this.numberVertex = numberVertex;
        this.size = slides.get(currentSlide).getNumObjects();
        float[] coord = slides.get(currentSlide).translateCoordinates(pmousex, pmousey, zoom);
        float[] v = new float[]{coord[0], coord[1]};
        pollyPoints.add(v);
    }

    public void createCurve(float pmousex, float pmousey){
        this.size = slides.get(currentSlide).getNumObjects();
        float[] coord = slides.get(currentSlide).translateCoordinates(pmousex, pmousey, zoom);
        float[] v = new float[]{coord[0], coord[1]};
        curvePoints.add(v);
    }

    public void createLine(float pmouseX, float pmouseY){
        createPollyGon(pmouseX, pmouseY, 2);
    }

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

    public void nextSlide() {
        currentSlide++;
    }

    public void previousSlide() {
        currentSlide--;
    }

    /**
     * Creates a slide beneath the selected slide.
     */
    public void createSlideAt() {
        DrawSpace ds = slides.get(currentSlide);
        slides.add(new DrawSpace(sketch, ds.xpos, ds.ypos, ds.pixelWidth, ds.pixelHeight));
    }

    public void moveSlideUp() {
        if (currentSlide - 1 < 0) {
            Collections.swap(slides, currentSlide, currentSlide - 1);
        }
    }

    public void moveSlideDown() {
        if (currentSlide + 1 < slides.size()) {
            Collections.swap(slides, currentSlide, currentSlide + 1);
        }
    }

    public void deleteSlide() {
        if (slides.size() > 0) {
            slides.remove(currentSlide);
            currentSlide--;
        }
    }

    public void selectSlide(int index) {
        currentSlide = index;
    }

    /**
     * Get the slide thumbnails for use in scroll menus.
     * @return A list of visual representations of slides with correct indices.
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

    public void exportAs(String saveName, String extension){
        reCenter();
        export = true;
        savefile = saveName+extension;
    }

    public void save(String filename) throws IOException {
      SerialManager.saveSlides(slides, filename);
    }

    public void open(String filename) throws IOException, ClassNotFoundException {
      slides = SerialManager.openSlides(sketch, filename);
    }

}
