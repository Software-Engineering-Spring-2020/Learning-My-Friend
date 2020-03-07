package backend.objects;

import java.io.File;
import java.io.IOException;

import com.github.kiulian.downloader.OnYoutubeDownloadListener;
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
  private static final long serialVersionUID = 29L;
  transient Movie pvideo;
  String videoPath;
  String vid;
  transient String[] file;
  transient YouTubeTextBox yt;
  transient boolean done;
  boolean downloaded;
  String title;
  String hashedTitle;
  private boolean broken;

  /**
  * Constructor for Video
  * @param sketch A reference to a PApplet to allow general functionality of the processing library
  * @param x A float to represent the initial x starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param y A float to represent the initial y starting position (in pixels) of the object, should be a slide-relative coordinate
  * @param vid YouTube's video ID.
  * @param filepath The directory to store the video in.
  */
  Video(PApplet sketch, float x, float y, float width, float height, String vid, String filepath, YouTubeTextBox yt){
    super(sketch, x, y, 1, new int[4], new int[4]);
    fillColor[3] = 255;
    boarderColor[3] = 255;
    pixelWidth = width;
    pixelHeight = height;
    videoPath = filepath;
    this.yt = yt;
    this.vid = vid;
    File vf = new File(videoPath + "/" + title + ".mp4");
    if (!vf.exists()) downloadVideo();
  }

  public void setYTB(YouTubeTextBox yt) {
    this.yt = yt;
  }

  public YouTubeTextBox getYTB() {
    return yt;
  }

  public boolean isDone() {
    return done;
  }

  private void downloadVideo() {
    YoutubeDownloader downloader = new YoutubeDownloader();
    try {
        YoutubeVideo video = downloader.getVideo(vid);
        System.out.println("Downloading " + video.details().title() + " to " + videoPath);
        File f = new File(videoPath);
        for (Format format : video.formats()) {
            // Itag.i17 is the lowest resolution available
            if (format instanceof AudioVideoFormat) {
                video.downloadAsync(format, f, new OnYoutubeDownloadListener() {
                  @Override
                  public void onDownloading(int progress) {
                    System.out.println("Downloading: " + Integer.toString(progress) + "%");
                  }
                          
                  @Override
                  public void onFinished(File file) {
                    pvideo = new Movie(sketch, videoPath + "/" + hashedTitle + ".mp4");
                    done = true;
                  }
              
                  @Override
                  public void onError(Throwable throwable) {
                    System.out.println("Error: " + throwable.getLocalizedMessage());
                  }
                });
                title = video.details().title();
                hashedTitle = Integer.toString(title.hashCode());
                break;
            }
        }
    } catch (YoutubeException | IOException e) {
        broken = true;
        e.printStackTrace();
    }
  }

  /**
  * Function used during the serialization process to restore transient processing library-related variables
  * @param sketch a reference to a PApplet to allow general functionality of the processing library
  */
  protected void init(PApplet sketch){
    super.init(sketch);
    File vf = new File(videoPath + "/" + hashedTitle + ".mp4");
    if (!vf.exists()) downloadVideo();
    else {
      pvideo = new Movie(sketch, videoPath + "/" + hashedTitle + ".mp4");
      done = true;
    }
  }

  public boolean isPlaying() {
      if (pvideo != null) return pvideo.isPlaying();
      return false;
  }

  public void play() {
    if (pvideo != null) pvideo.play();
  }

  public void stop() {
    if (pvideo != null) pvideo.stop();
  }

  public boolean broken() {
    return broken;
  }

  /**
  * Draw the video's current frame to the slide
  */
  protected void display(){
    super.display();
    if (pvideo != null) {
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
}
