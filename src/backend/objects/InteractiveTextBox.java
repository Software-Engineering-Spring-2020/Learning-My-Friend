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
    private ArrayList<Float> lineLengths;
    private int charactersSinceNewLine;
    private int charactersPerLine;
    transient private PFont pFont;
    transient private int cursorIndex;
    transient private char cursor = '|';
    transient private long cursorStartTime;
    transient private boolean displayCursor = false;
    private final char WIDE_CHAR = 'W';
    private final int LINE_SPACING = 16;
    private final long CURSOR_BLINK_RATE = 530l;
    private final char[] BULLETS = {'-', '+', '*'};
    private final char INDENTATION_CHAR = ' ';
    private final int INDENTATION_SIZE = 4;

  public InteractiveTextBox(PApplet sketch, float x, float y, float width, float strokeWeight, int[] fillColor, int[] boarderColor, String font, float textSize){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    this.font = font;
    this.pFont = sketch.createFont(font, 1);
    this.textSize = textSize;
    this.lineLengths = new ArrayList<Float>();
    this.lineLengths.add(0f);
    this.str = "";
    this.charactersPerLine = (int) (width / sketch.textWidth(WIDE_CHAR));
    this.cursorStartTime = System.currentTimeMillis();
    String example = "NEW TEXT BOX";
    char[] exampleCharacters = example.toCharArray();
    for (char c: exampleCharacters) {
        addCharacter(c);
    }
    sketch.textSize(textSize);
    pixelWidth = width;
    pixelHeight = lineLengths.size() * textSize;
    cursorIndex = str.length();
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
      displayCursor = true;
      sketch.push();
      sketch.stroke(0, 0, 0);
      sketch.strokeWeight(2);
      float[] cursorPos = {xcenter - pixelWidth / 2, ycenter - pixelHeight / 2 + textSize / 2};
      String untilCursor = str.substring(0, cursorIndex);
      char[] characters = untilCursor.toCharArray();
      for (int i = 0; i < characters.length; i++) {
        cursorPos[0] += sketch.textWidth(characters[i]);
        if (cursorPos[0] >= xcenter - pixelWidth / 2 + pixelWidth) {
            cursorPos[0] = xcenter - pixelWidth / 2;
            cursorPos[1] += textSize;
        }
      }
      //sketch.line(cursorPos[0], cursorPos[1] + textSize / 2, cursorPos[0], cursorPos[1] - textSize / 2);
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
    pixelHeight = lineLengths.size() * textSize;
  }

  private void addLine() {
    ycenter += (textSize + LINE_SPACING / 4f) / 2f;
    pixelHeight += textSize + LINE_SPACING / 4f;
    lineLengths.add(0f);
  }

  private void removeLine() {
    ycenter -= (textSize + LINE_SPACING / 4f) / 2f;
    pixelHeight -= textSize + LINE_SPACING / 4f;
    lineLengths.add(0f);
  }

  /**
   * 
   * @return The String starting with the last PConstants.ENTER or PConstants.RETURN, not a Processing line.
   */
  private String getCurrentLine() {
    // TODO: test using cursorIndex
    char[] characters = str.toCharArray();
    String reversedLine = "";
    for (int i = cursorIndex - 1; i >= 0; i--) {
        if (characters[i] == PConstants.ENTER || characters[i] == PConstants.RETURN) break;
        reversedLine += characters[i];
    }
    System.out.println(reversedLine);
    char[] reversedLineCharacters = reversedLine.toCharArray();
    String line = "";
    for (int i = reversedLineCharacters.length - 1; i >= 0; i--) {
        line += reversedLineCharacters[i];
    }
    for (int i = cursorIndex - 1; i < characters.length; i++) {
        if (characters[i] == PConstants.ENTER || characters[i] == PConstants.RETURN) break;
        line += characters[i];
    }
    return line;
  }

  private boolean inBullets() {
    if (str.length() == 0) return false;
    char firstNonWhitespaceCharacter = getFirstNonWhitespaceCharacter();
    for (int i = 0; i < BULLETS.length; i++) if (firstNonWhitespaceCharacter == BULLETS[i]) return true;
    return false;
  }

  private int getIndentationLevel() {
    String line = getCurrentLine();
    if (line.length() == 0) return 0;
    char[] lineCharacters = line.toCharArray();
    int indentation_chars = 0;
    int i = 0;
    while (lineCharacters[i] == INDENTATION_CHAR) {
        indentation_chars++;
        i++;
    }
    if (indentation_chars % INDENTATION_SIZE == 0) return indentation_chars / INDENTATION_SIZE;
    else return 0;
  }

  private void indent() {
      String indentation = "";
      for (int i = 0; i < INDENTATION_SIZE; i++) indentation += INDENTATION_CHAR;
      charactersSinceNewLine += INDENTATION_SIZE + 1;
      str.replace(getCurrentLine(), indentation + getCurrentLine());
  }

  private void unindent() {
    String indentation = "";
    for (int i = 0; i < INDENTATION_SIZE; i++) indentation += INDENTATION_CHAR;
    charactersSinceNewLine -= INDENTATION_SIZE + 1;
    str.replaceFirst(indentation, "");
  }

  private char getFirstNonWhitespaceCharacter() {
    String line = getCurrentLine();
    char[] lineCharacters = line.toCharArray();
    int i = 0;
    while (lineCharacters[i] == INDENTATION_CHAR) {
        i++;
    }
    return lineCharacters[i];
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
            else cursorIndex = 0;
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
    charactersSinceNewLine++;
    if (charactersSinceNewLine % charactersPerLine == 0) addLine();
    cursorIndex++;
    str = new String(newCharacters);
    if (c == PConstants.ENTER || c == PConstants.RETURN) {
        moveCursor(Direction.DOWN);
        if (!(charactersSinceNewLine % charactersPerLine == 0)) addLine();
        if (inBullets()) {
            for (int i = cursorIndex; i < cursorIndex + INDENTATION_SIZE * getIndentationLevel(); i++) {
                addCharacter(INDENTATION_CHAR);
            }
            addCharacter(BULLETS[getIndentationLevel() % BULLETS.length]);
        }
    }
  }

  /**
   * Removes the character before the text cursor.
   */
  protected void removeCharacter() {
    char[] characters = str.toCharArray();
    if (cursorIndex > 0) {
        char[] newCharacters = new char[characters.length - 1];
        cursorIndex--;
        int o = 0;
        System.out.println(cursorIndex);
        for (int i = 0; i < characters.length - 1; i++) {
            if (i == cursorIndex) {
                o = 1;
            }
            newCharacters[i] = characters[i + o];
        }
        str = new String(newCharacters);
        String line = getCurrentLine();
        char[] lineCharacters = line.toCharArray();
        if (inBullets() && (lineCharacters[line.length() - 1] == INDENTATION_CHAR || lineCharacters[line.length() - 2] == INDENTATION_CHAR)) unindent();
        charactersSinceNewLine--;
        if (charactersSinceNewLine % charactersPerLine == 0 || characters[cursorIndex] == '\n' || characters[cursorIndex] == '\r') removeLine();
    }
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
            case PConstants.CONTROL:
                break;
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
            case PConstants.TAB:
                if (inBullets()) indent();
                break;
            // TODO: case SHIFT + TAB for unindent
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
        String finalString = str;
        sketch.textFont(pFont, textSize);
        sketch.textLeading(LINE_SPACING);
        sketch.textAlign(PConstants.LEFT);
        sketch.rectMode(PConstants.CORNER);
        char[] characters = str.toCharArray();
        if (displayCursor) {
            displayCursor = false;
            finalString = "";
            char[] charactersWithCursor = new char[characters.length + 1];
            int offset = 0;
            long nowTime = System.currentTimeMillis();
            if (nowTime - cursorStartTime >= CURSOR_BLINK_RATE) {
                if (cursor == '|') cursor = ' ';
                else cursor = '|';
                cursorStartTime = nowTime;
            }
            if (cursorIndex == characters.length) finalString= str + cursor;
            else {
                for (int i = 0; i < characters.length; i++) {
                    if (i == cursorIndex) {
                        charactersWithCursor[i] = cursor;
                        charactersWithCursor[i + 1] = characters[i];
                        offset = 1;
                    }
                    else charactersWithCursor[i + offset] = characters[i];
                }
            finalString = new String(charactersWithCursor);
            }
        }
        sketch.text(finalString, 0f - pixelWidth / 2, 0f - pixelHeight / 2, pixelWidth, pixelHeight + textSize);
        //sketch.text(str, 0, 0);
  }
}
