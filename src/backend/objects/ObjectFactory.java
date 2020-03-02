package backend.objects;

import backend.Shape;
import backend.Window.TextMode;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PConstants;
import processing.core.PVector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
* Factory to allow for object creation and instanciation.
*/
public class ObjectFactory {
    protected  PApplet sketch;

    /**
    * Constructor for ObjectFactory
    * @param sketch A reference to a PApplet to allow general functionality of the processing library
    */
    public ObjectFactory(PApplet sketch){
        this.sketch = sketch;
    }

    /**
    * Instanciate an Image
    * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param filename The file (path + name) of the image to display
    * @param extension The image file type (the . is still necessary)
    */
    public Image importImage(float x, float y, String filename, String extension){
        try {
            if (Files.probeContentType(Paths.get(filename + extension)).equals("image/gif")) {
                return new AnimatedImage(sketch, x, y, filename, extension, 10);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Image(sketch, x, y, filename, extension);
    }

    public Video importVideo(float x, float y, String vid, String filepath) {
        return new Video(sketch, x, y, 256, 144, vid, filepath);
    }

    /**
    * Instanciate a TextBox
    * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param strokeWeight Represents the line-thickness
    * @param fillColor The internal color of the object represented by four values ranged 0-255 (Red, Green, Blue, and Alpha respectively)
    * @param boarderColor The outline color of the object represented by three values ranged 0-255 (Red, Green, and Blue respectively)
    * @param str The text to be displayed
    * @param font The font style to display the text as
    * @param textSize The size of text to be displayed in pixels
    */
    public TextBox createTextBox(float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor, String str, String font, float textSize){
        return new TextBox(sketch, x, y, strokeWeight, fillColor, boarderColor, str, font, textSize);
    }

    /**
    * Instanciate an InteractiveTextBox
    * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param width A float representing the desired width of the text box in pixels (text-wrapping NOT enabled)
    * @param strokeWeight Represents the line-thickness
    * @param fillColor The internal color of the object represented by four values ranged 0-255 (Red, Green, Blue, and Alpha respectively)
    * @param boarderColor The outline color of the object represented by three values ranged 0-255 (Red, Green, and Blue respectively)
    * @param str The text to be displayed
    * @param font The font style to display the text as
    * @param textSize The size of text to be displayed in pixels
    */
    public InteractiveTextBox createInteractiveTextBox(float x, float y, float width, float strokeWeight, int[] fillColor, int[] boarderColor, String font, float textSize, TextMode m) {
        return new InteractiveTextBox(sketch, x, y, width, strokeWeight, fillColor, boarderColor, font, textSize, m);
    }

    /**
    * Instanciate a Comment
    * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param strokeWeight Represents the line-thickness
    * @param fillColor The internal color of the object represented by four values ranged 0-255 (Red, Green, Blue, and Alpha respectively)
    * @param boarderColor The outline color of the object represented by three values ranged 0-255 (Red, Green, and Blue respectively)
    * @param str The text to be displayed
    * @param font The font style to display the text as
    * @param textSize The size of text to be displayed in pixels
    */
    public Comment createComment(float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor, String str, String font, float textSize){
        return new Comment(sketch, x, y, strokeWeight, fillColor, boarderColor, str, font, textSize);
    }

    public Ellipse createEllipse(float x, float y, float w, float h, float strokeWeight, int[] fillColor, int[] boarderColor){
        return new Ellipse(sketch, x, y, w, h, strokeWeight, fillColor, boarderColor);
    }

    public Ellipse createEllipse(float x, float y, float d,  float strokeWeight, int[] fillColor, int[] boarderColor){
        return new Ellipse(sketch, x, y, d, strokeWeight, fillColor, boarderColor);
    } public Ellipse createEllipse(float x, float y,  float strokeWeight, int[] fillColor, int[] boarderColor){
        return new Ellipse(sketch, x, y, strokeWeight, fillColor, boarderColor);
    }


    public Rectangle createRect(float x, float y, float w, float h, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, x, y, w, h, strokeWeight, fillColor, boarderColor);
    }

    public Rectangle createRect(float x, float y, float d, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, x, y, d, strokeWeight, fillColor, boarderColor);
    } public Rectangle createRect(float x, float y, float strokeWeight, int[] fillColor, int[] boarderColor){
        return new Rectangle(sketch, x, y, strokeWeight, fillColor, boarderColor);
    }

    /**
    * Instanciate a FreeForm
    * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param points The points along the user-drawn free-hand drawn line design
    * @param strokeWeight Represents the line-thickness
    * @param fillColor The internal color of the object represented by four values ranged 0-255 (Red, Green, Blue, and Alpha respectively)
    * @param boarderColor The outline color of the object represented by three values ranged 0-255 (Red, Green, and Blue respectively)
    */
    public FreeForm createFreeForm(ArrayList<float[]> points, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new FreeForm(sketch, points.get(0)[0], points.get(0)[1], points, strokeWeight, fillColor, boarderColor);
    }

    public FreeForm createLine(ArrayList<float[]> points, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new FreeForm(sketch, points.get(0)[0], points.get(0)[1], points, strokeWeight, fillColor, boarderColor);
    }

    public PollyGon createPollyGon(ArrayList<float[]> points, float strokeWeight,  int[] fillColor, int[] boarderColor){
        return new PollyGon(sketch, points.get(0)[0], points.get(0)[1], points, strokeWeight, fillColor, boarderColor);
    }

    /**
    * Instanciate a Curve
    * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
    * @param points The four points of the bezier curve
    * @param strokeWeight Represents the line-thickness
    * @param fillColor The internal color of the object represented by four values ranged 0-255 (Red, Green, Blue, and Alpha respectively)
    * @param boarderColor The outline color of the object represented by three values ranged 0-255 (Red, Green, and Blue respectively)
    */
    public Curve createCurve(ArrayList<float[]> points, float strokeWeight, int[] fillColor, int[] boarderColor){
      return new Curve(sketch, points.get(0)[0], points.get(0)[1], points, strokeWeight, fillColor, boarderColor);
    }

}
