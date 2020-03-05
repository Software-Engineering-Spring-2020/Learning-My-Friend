package backend.objects;

import backend.Window.TextMode;
import processing.core.PApplet;
import processing.core.PConstants;

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
        sketch.text("You copied:", 0f - pixelWidth / 2, 0f - pixelHeight / 2 - textSize - 5, pixelWidth, pixelHeight + textSize + 1000);
        sketch.text("Press ENTER to confirm.", 0f - pixelWidth / 2, 0f - pixelHeight / 2 + pixelHeight + 5, pixelWidth + 1000, pixelHeight + textSize + 1000);
        isSelected = false;
    }
    
    protected void addCharacter(char c) {
        super.addCharacter(c);
        if (c == PConstants.ENTER || c == PConstants.RETURN) readyForVideo = true;
    }

    public boolean readyForVideo() {
        // on deselect, create a video
        return readyForVideo;
    }

    public String getVID() {
        return str.substring(32);
    }
}