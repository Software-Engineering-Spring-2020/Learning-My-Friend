package backend.objects;

import backend.Window.TextMode;
import processing.core.PApplet;

public class YouTubeTextBox extends InteractiveTextBox {

    private static final long serialVersionUID = 24L;

    transient private boolean wasSelected;
    transient private boolean isSelected;

    private boolean readyForVideo;

    public YouTubeTextBox(PApplet sketch, float x, float y, float width, float strokeWeight, int[] fillColor, int[] boarderColor, String font, float textSize, TextMode m, String start) {
        super(sketch, x, y, width, strokeWeight, fillColor, boarderColor, font, textSize, m, start);
    }

    public void showBoundingBox(float r, float g, float b) {
        super.showBoundingBox(r, g, b);
        wasSelected = true;
        isSelected = true;
    }

    public void display() {
        super.display();
        if (wasSelected && !isSelected && str.length() > 32) {
            readyForVideo = true;
            wasSelected = false;
            System.out.println(getVID());
        }
        isSelected = false;
    }

    public boolean readyForVideo() {
        // on deselect, create a video
        return readyForVideo;
    }

    public String getVID() {
        return str.substring(32);
    }
}