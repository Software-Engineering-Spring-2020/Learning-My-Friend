package backend.objects;

import java.io.Serializable;
import java.util.ArrayList;

import backend.TextObject;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
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
    private int charactersPerLine;
    transient int cursorIndex;
    transient private PFont pFont;

  public InteractiveTextBox(PApplet sketch, float x, float y, float x2, float strokeWeight, int[] fillColor, int[] boarderColor, String font, float textSize){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    this.font = font;
    this.pFont = sketch.createFont(font, 1);
    this.textSize = textSize;
    this.str = "NEW TEXT BOX";
    sketch.textSize(textSize);
    pixelWidth = x2;
    charactersPerLine = (int) (x2 / sketch.textWidth('o'));
    pixelHeight = (str.length() / charactersPerLine) * textSize;
    cursorIndex = str.length();
    fillColor[3] = 255;
  }

  public void showBoundingBox() {
      super.showBoundingBox();
      sketch.push();
      sketch.stroke(0, 0, 0);
      sketch.strokeWeight(2);
      float[] cursorPos = {xpos, ypos};
      char[] characters = str.toCharArray();
      for (int i = 0; i < characters.length; i++) {
        cursorPos[0] += sketch.textWidth(characters[i]);
        if (i % charactersPerLine == 0) {
            cursorPos[0] = xpos;
            cursorPos[1] = textSize;
        }
      }
      sketch.line(cursorPos[0], cursorPos[1] + textSize / 2, cursorPos[0], cursorPos[1] - textSize / 2);
      sketch.pop();
  }

  /**
   * Adjusts the width and height of the InteractiveTextBox using
   * the supplied width.
   * 
   * @param x2 The new (abolute) width.
   */
  protected void changeWidth(float x2) {
    pixelWidth = x2;
    charactersPerLine = (int) (x2 / sketch.textWidth('o'));
    pixelHeight = (str.length() / charactersPerLine) * textSize;
  }

  /**
   * Moves the text cursor UP, DOWN, LEFT, or RIGHT. Use the `directions` enum.
   * 
   * @param direction The direction to move the text cursor in.
   */
  protected void moveCursor(Direction direction) {
      switch(direction) {
        case DOWN:
            if (cursorIndex + charactersPerLine <= str.length()) {
                cursorIndex += charactersPerLine;
            }
            else {
                cursorIndex = str.length();
            }
			break;
        case LEFT:
            if (cursorIndex > 0) {
                cursorIndex--;
            }
			break;
        case RIGHT:
            if (cursorIndex < str.length()) {
                cursorIndex++;
            }
			break;
        case UP:
            if (cursorIndex - charactersPerLine >= 0) {
                cursorIndex -= charactersPerLine;
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
    char[] characters = str.toCharArray();
    char[] newCharacters = new char[characters.length + 1];
    int j = 0;
    for (int i = 0; i < characters.length + 1; i++) {
        if (i == cursorIndex) {
            newCharacters[i] = c;
        }
        else {
            newCharacters[i] = characters[j];
            j++;
        }
    }
    cursorIndex++;
    str = new String(newCharacters);
    pixelHeight = (str.length() / charactersPerLine) * textSize;
  }

  /**
   * Removes the character before the text cursor.
   */
  protected void removeCharacter() {
    char[] characters = str.toCharArray();
    char[] newCharacters = new char[characters.length - 1];
    int j = 0;
    for (int i = 0; i < characters.length + 1; i++) {
        if (i != cursorIndex) {
            newCharacters[i] = characters[j];
        }
        j++;
    }
    cursorIndex--;
    str = new String(newCharacters);
    pixelHeight = (str.length() / charactersPerLine) * textSize;
  }

  protected void init(PApplet sketch){
    super.init(sketch);
    this.pFont = sketch.createFont(font, 1);
  //  sketch.textSize(textSize);
    //pixelWidth = sketch.textWidth(str);
  }

  protected void handleCharacter(char key, int keyCode) {
    super.handleKey(key, keyCode);
    if (key == sketch.CODED) {
        switch(keyCode) {
            case PConstants.UP:
                break;
            case PConstants.DOWN:
                break;
            case PConstants.LEFT:
                break;
            case PConstants.RIGHT:
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
    System.out.println(str);
  }

  // this function's text() is why this class does NOT extend TextBox
  protected void display(){
        super.display();
        sketch.textFont(pFont, textSize);
        sketch.text(str, 0, 0, pixelWidth, pixelHeight);
        //sketch.text(str, 0, 0);
  }
}
