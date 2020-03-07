package backend.objects;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;

import backend.ColorfulObject;
import processing.core.PApplet;
import processing.core.PImage;

/**
* Class which supports the import, save, and display of images.
*/
class AnimatedImage extends Image {
  private static final long serialVersionUID = 22L;
  String[] file;
  transient ArrayList<BufferedImage> frames;
  transient ArrayList<PImage> images;
  protected long lastElapsedTime = 0;
  protected long lastTimeCheck = 0;
  protected long timeSinceFrameChange = 0;
  protected int fps;
  protected int currentFrame;

  /**
  * Constructor for AnimatedImage
  * @param sketch A reference to a PApplet to allow general functionality of the processing library
  * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param filename The file (path + name) of the image to display
  * @param extension The image file type (the . is still necessary)
  * @param fps The frames per second to display the GIF at.
  */
  AnimatedImage(PApplet sketch, float x, float y, String filename, String extension, int fps){
    super(sketch, x, y, filename, extension);
    this.fps = fps;
    prepareGif(filename, extension);
    file = new String[2];
    file[0] = filename;
    file[1] = extension;

    display = false;
  }

  /**
  * Function used during the serialization process to restore transient processing library-related variables
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  */
  protected void init(PApplet sketch){
    super.init(sketch);
    prepareGif(file[0], file[1]);
    display = false;
  }

  private void prepareGif(String filename, String extension) {
    file = new String[]{filename, extension};
    frames = new ArrayList<BufferedImage>();
    images = new ArrayList<PImage>();
    try {
        File gif = new File(filename + extension);
        ImageInputStream gifStream = ImageIO.createImageInputStream(gif);
        ImageReader gifReader = ImageIO.getImageReadersByFormatName("gif").next();
        gifReader.setInput(gifStream);

        int numFrames = gifReader.getNumImages(true);
        for (int i = 0; i < numFrames; i++) {
            BufferedImage frame = gifReader.read(i);
            PImage image = new PImage(frame);
            frames.add(frame);
            images.add(image);
        }
    }
    catch (IOException e) {
        System.err.println(e);
    }

    if(images.get(0) != null){
      pixelWidth = images.get(0).width;
      pixelHeight = images.get(0).height;
    }
  }

  /**
  * Draw the image to the slide
  */
  protected void display(){
    super.display();
    sketch.push();
    sketch.translate(-xpos, -ypos);
    sketch.tint(255, fillColor[3]);
    lastElapsedTime = System.currentTimeMillis() - lastTimeCheck;
    timeSinceFrameChange += lastElapsedTime;
    if (timeSinceFrameChange >= 1000l / fps) {
        currentFrame = (currentFrame + 1) % images.size();
        timeSinceFrameChange = 0;
    }
    if(images.get(currentFrame) != null) sketch.image(images.get(currentFrame), xcenter, ycenter);
    sketch.pop();
    lastTimeCheck = System.currentTimeMillis();
  	//if(img.width != 0) sketch.image(img, xpos, ypos);
  }

}
