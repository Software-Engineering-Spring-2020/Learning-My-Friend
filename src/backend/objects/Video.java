package backend.objects;

import java.io.File;
import java.io.IOException;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioVideoFormat;
import com.github.kiulian.downloader.model.formats.Format;


import backend.ColorfulObject;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.video.*;

/**
* Class which supports the download and display of videos.
*/
public class Video extends ColorfulObject {
  private static final long serialVersionUID = 21L;
  Movie pvideo;
  String videoPath;
  String[] file;
  boolean downloaded;

  /**
  * Constructor for Video
  * @param sketch A reference to a PApplet to allow general functionality of the processing library
  * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param vid YouTube's video ID.
  * @param filepath The directory to store the video in.
  */
  Video(PApplet sketch, float x, float y, float width, float height, String vid, String filepath){
    super(sketch, x, y, 1, new int[4], new int[4]);
    fillColor[3] = 255;
    boarderColor[3] = 255;
    videoPath = filepath;
    YoutubeDownloader downloader = new YoutubeDownloader();
    try {
        YoutubeVideo video = downloader.getVideo(vid);
        System.out.println(videoPath);
        File f = new File(videoPath);
        for (Format format : video.formats()) {
            // Itag.i17 is the lowest resolution available
            if (format instanceof AudioVideoFormat) {
                video.download(format, f);
                pvideo = new Movie(sketch, videoPath + "/" + video.details().title() + ".mp4");
                pixelWidth = width;
                pixelHeight = height;
                break;
            }
        }
    } catch (YoutubeException | IOException e) {
        e.printStackTrace();
    }
  }

  /**
  * Function used during the serialization process to restore transient processing library-related variables
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  */
  protected void init(PApplet sketch){
    super.init(sketch);
    //img = sketch.requestImage(file[0]+file[1]);
  }

  public boolean isPlaying() {
      if (pvideo != null) return pvideo.isPlaying();
      return false;
  }

  public void play() {
    pvideo.play();
  }

  public void stop() {
    pvideo.stop();
  }

  /**
  * Draw the video's current frame to the slide
  */
  protected void display(){
    super.display();
    if (pvideo.available()) {    
        pvideo.read();  
    }
    sketch.push();
    sketch.translate(-xpos, -ypos);
    sketch.tint(255, fillColor[3]);
    //System.out.println(pvideo);
    if (pvideo.isPlaying()){
        sketch.image(pvideo.get(), xcenter, ycenter, pixelWidth, pixelHeight);
    }
    else sketch.rect(xpos, ypos, pixelWidth, pixelHeight);
    sketch.pop();
  }

}