package backend.objects;

import java.io.Serializable;
import java.util.ArrayList;

import backend.TextObject;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.core.PConstants;

public class InteractiveTextBox extends TextObject implements Serializable {
    private static final long serialVersionUID = 14L;
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    private String str, font;
    private float textSize;
    private ArrayList<String> lines;
    private ArrayList<Float> lineWidths;
    transient int[] cursorCell = {0, 0};
    transient float[] cursorPos;
    transient private PFont pFont;

  public InteractiveTextBox(PApplet sketch, float x, float y, float width, float strokeWeight, int[] fillColor, int[] boarderColor, String font, float textSize){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    this.font = font;
    this.pFont = sketch.createFont(font, 1);
    this.textSize = textSize;
    this.lines = new ArrayList<String>();
    this.lines.add("");
    this.lineWidths = new ArrayList<Float>();
    this.lineWidths.add(0f);
    this.str = "";
    String example = "NEW TEXT BOX";
    char[] exampleCharacters = example.toCharArray();
    for (char c: exampleCharacters) {
        addCharacter(c);
    }
    sketch.textSize(textSize);
    pixelWidth = width;
    pixelHeight = lineWidths.size() * textSize;
    this.cursorCell = new int[2];
    this.cursorCell[0] = lines.get(lines.size() - 1).length() - 1;
    this.cursorCell[1] = lines.size() - 1;
    System.out.println(textSize);
    System.out.println(pixelWidth);
    System.out.println(pixelHeight);
    fillColor[3] = 255;
  }

  /**
   * Displays the changed bounding box and the text cursor.
   */
  public void showBoundingBox() {
      super.showBoundingBox();
      sketch.push();
      sketch.stroke(0, 0, 0);
      sketch.strokeWeight(2);
      float[] cursorPos = {xpos - pixelWidth / 2, ypos - pixelHeight / 2};
      System.out.println(ypos);
      char[] characters = str.toCharArray();
      for (int i = 0; i < characters.length; i++) {
        cursorPos[0] += sketch.textWidth(characters[i]);
        if (cursorPos[0] >= pixelWidth) {
            cursorPos[0] = xpos - pixelWidth / 2;
            cursorPos[1] = textSize;
        }
      }
      sketch.line(cursorPos[0], cursorPos[1] + textSize / 2, cursorPos[0], cursorPos[1] - textSize / 2);
      sketch.pop();
  }

  protected PVector[] getBoundingBoxPoints(float xcenter, float ycenter) {
    float width = pixelWidth*zoom;
    float height = pixelHeight*zoom;
    offset = 3;
    // this uses the existing bounding box system as much as possible
    // using width and height to generate four points
    // these points are NOT rotated per this PollyObject's rotation

    // represented with four PVectors: topLeft, topRight, bottomRight, bottomLeft (like NESW)
    PVector[] boundingBoxPoints = new PVector[4];
    // topLeft
    boundingBoxPoints[0] = new PVector(-width/2 + xcenter - offset, -height/2 + ycenter - offset);
    // topRight
    boundingBoxPoints[1] = new PVector(width/2 + xcenter + offset, -height/2 + ycenter - offset);
    // bottomRight
    boundingBoxPoints[2] = new PVector(width/2 + xcenter + offset, height/2 + ycenter + offset);
    // bottomLeft
    boundingBoxPoints[3] = new PVector(-width/2 + xcenter - offset, height/2 + ycenter + offset);
    return boundingBoxPoints;
}

  /**
   * Adjusts the width and height of the InteractiveTextBox using
   * the supplied width.
   * 
   * @param x2 The new (abolute) width.
   */
  protected void changeWidth(float width) {
    pixelWidth = width;
    char[] temp = str.toCharArray();
    str = "";
    pixelHeight = 0;
    for (int i = 0; i < temp.length; i++) {
        addCharacter(temp[i]);
    }
  }

  private void addLine() {
    pixelHeight += textSize;
    lineWidths.add(cursorCell[1], 0f);
    lines.add(cursorCell[1], "");
    pixelHeight = lineWidths.size() * textSize;
  }

  private void removeLine() {
    pixelHeight -= textSize;
    lineWidths.remove(cursorCell[1]);
    lines.remove(cursorCell[1]);
    pixelHeight = lineWidths.size() * textSize;
  }

  /**
   * @param s The string to measure.
   * @return A measurement (in pixels) of the width of the String.
   */
  private float getStringWidth(String s) {
      char[] sCharacters = s.toCharArray();
      float width = 0;
      for (int i = 0; i < sCharacters.length; i++) {
        width += sketch.textWidth(sCharacters[i]);
      }
      return width;
  }

  /**
   * Moves the text cursor UP, DOWN, LEFT, or RIGHT. Use the `directions` enum.
   * 
   * @param direction The direction to move the text cursor in.
   */
  protected void moveCursor(Direction direction) {
      switch(direction) {
        case DOWN:
            if (cursorCell[1] != lines.size() - 1) {
                if (cursorCell[1] >= lines.get(cursorCell[1] + 1).length()) {
                    cursorCell[0] = lines.get(cursorCell[1] + 1).length() - 1;
                    cursorCell[1]++;
                }
                else {
                    cursorCell[1]++;
                }
            }
			break;
        case LEFT:
            if (cursorCell[0] - 1 < 0) {
                cursorCell[0] = 0;
                if (cursorCell[1] != 0) cursorCell[1]--;
            }
            else {
                cursorCell[1]++;
            }
			break;
        case RIGHT:
            if (cursorCell[0] + 1 == lines.get(cursorCell[1]).length()) {
                cursorCell[0] = 0;
                cursorCell[1]++;
            }
            else {
                cursorCell[0]++;
            }
			break;
        case UP:
            if (cursorCell[1] > 0) {
                if (cursorCell[1] >= lines.get(cursorCell[1] - 1).length()) {
                    cursorCell[0] = lines.get(cursorCell[1] - 1).length() - 1;
                    cursorCell[1]++;
                }
                else {
                    cursorCell[1]++;
                }
            }
            break;
		default:
			break;
      }
  }

  /**
   * Adds the specified character to the position in the InteractiveTextBox
   * defined by the current cursor position.
   * 
   * @param c The character to add to the InteractiveTextBox.
   */
  protected void addCharacter(char c) {
    if (c == PConstants.ENTER || c == PConstants.RETURN) {
        addLine();
        cursorCell[0] = 0;
        cursorCell[1]++;
    }
    else {
        float newLineWidth = getStringWidth(lines.get(cursorCell[1]) + c);
        if (newLineWidth > pixelWidth) {
            cursorCell[0] = 0;
            cursorCell[1]++;
            if (!(cursorCell[1] - 1 == lines.size() - 1)) {
                char lastCharacterOfNextLine = lines.get(cursorCell[1]).charAt(lines.get(cursorCell[1]).length() - 1);
                lines.set(cursorCell[1], lines.get(cursorCell[1]).substring(0, lines.get(cursorCell[1]).length() - 2));
                addCharacter(lastCharacterOfNextLine);
                return;
            }
            else {
                addLine();
            }
        }
        char[] characters = lines.get(cursorCell[1]).toCharArray();
        char[] newCharacters = new char[characters.length + 1];
        int j = 0;
        for (int i = 0; i < characters.length + 1; i++) {
            if (i == cursorCell[0]) {
                newCharacters[i] = c;
            }
            else {
                newCharacters[i] = characters[j];
                j++;
            }
        }
        lines.set(cursorCell[1], new String(newCharacters));
        lineWidths.set(cursorCell[1], getStringWidth(lines.get(cursorCell[1])));
        str = "";
        for (String line : lines) str += line;
    }
  }

  /**
   * Removes the character before the text cursor.
   */
  protected void removeCharacter() {
    char[] characters = lines.get(cursorCell[1]).toCharArray();
    char[] newCharacters;
    int j = 0;
    if (cursorCell[0] - 1 < 0 && cursorCell[1] > 0) {
        removeLine();
        cursorCell[0] = lines.get(cursorCell[1] - 1).length();
        cursorCell[1]--;
        removeCharacter();
        return;
    }
    newCharacters = new char[characters.length - 1];
    if (newCharacters.length > 0) {
        for (int i = 0; i < characters.length; i++) {
            if (i != cursorCell[0] - 1) {
                newCharacters[i] = characters[j];
                j++;
            }
        }
    }
    String remainderToReAdd = "";
    for (int i = cursorCell[1]; i < lines.size(); i++) remainderToReAdd += lines.get(i);
    char[] charactersToReAdd = remainderToReAdd.toCharArray();
    for (int i = 0; i < charactersToReAdd.length; i++) addCharacter(charactersToReAdd[i]);
    lines.set(cursorCell[1], new String(newCharacters));
    lineWidths.set(cursorCell[1], getStringWidth(lines.get(cursorCell[1])));
    str = "";
    for (String line : lines) str += line;
  }

  protected void init(PApplet sketch){
    super.init(sketch);
    this.pFont = sketch.createFont(font, 1);
  //  sketch.textSize(textSize);
    //pixelWidth = sketch.textWidth(str);
  }

  protected void handleKey(char key, int keyCode) {
    super.handleKey(key, keyCode);
    if (key == sketch.CODED) {
        switch(keyCode) {
            case PConstants.UP:
                moveCursor(Direction.UP);
                break;
            case PConstants.DOWN:
                moveCursor(Direction.DOWN);
                break;
            case PConstants.LEFT:
                moveCursor(Direction.LEFT);
                break;
            case PConstants.RIGHT:
                moveCursor(Direction.RIGHT);
                break;
        }
    }
    else {
        switch(key) {
            case PConstants.BACKSPACE:
                removeCharacter();
                break;
            case PConstants.DELETE:
                // TODO
                break;
            default:
                addCharacter(key);
                break;
        }
    }
  }

  // this function's text() is why this class does NOT extend TextBox
  protected void display(){
        super.display();
        sketch.textFont(pFont, textSize);
        sketch.textAlign(PConstants.LEFT);
        sketch.rectMode(PConstants.CORNER);
        int lidx = 0;
        for (String line : lines) {
            sketch.text(line, 0, lidx * textSize);
            lidx++;
        }
        //sketch.text(str, 0, 0);
  }
}
