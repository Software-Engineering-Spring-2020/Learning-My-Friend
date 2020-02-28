package backend.objects;

import java.util.ArrayList;

import backend.TextObject;
import backend.ListenerObject;

import processing.core.PApplet;
import processing.core.PConstants;

/**
* Class to display dynamic text to the slide, (Supports interactivity).
*/
public class InteractiveTextBox extends TextObject implements ListenerObject {
    private static final long serialVersionUID = 14L;
    public enum Directions {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    private int charactersSinceNewLine;
    private int charactersPerLine;
    transient private int cursorIndex;
    transient private char cursor = '|';
    transient private long cursorStartTime;
    transient private boolean displayCursor = false;
    private final char WIDE_CHAR = 'W';
    private final int LINE_SPACING = 20;
    private final long CURSOR_BLINK_RATE = 530l;
    private final char[] BULLETS = {'-', '+', '*'};
    private final char[] NUMBERS = {'1', 'a'};
    private final char INDENTATION_CHAR = ' ';
    private final int INDENTATION_SIZE = 4;
    private enum Modes {
        NUMBERED,
        BULLETED,
        PLAIN
    }
    private final Modes mode;

    /**
    * Constructor for InteractiveTextBox
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
  public InteractiveTextBox(PApplet sketch, float x, float y, float width, float strokeWeight, int[] fillColor, int[] boarderColor, String font, float textSize){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor, "", font, textSize);
    this.str = "";
    this.charactersPerLine = (int) (width / sketch.textWidth(WIDE_CHAR));
    this.cursorStartTime = System.currentTimeMillis();
    this.mode = Modes.PLAIN;
    String example = "NEW TEXT BOX";
    char[] exampleCharacters = example.toCharArray();
    for (char c: exampleCharacters) {
        addCharacter(c);
    }
    sketch.textSize(textSize);
    pixelWidth = width;
    pixelHeight = textSize;
    cursorIndex = str.length();
    fillColor[3] = 255;
  }

   /**
   * Draws an orange rectangle representing the selection field of the object as well as a text cursor.
   */
  public void showBoundingBox() {
      super.showBoundingBox();
      displayCursor = true;
  }

  /**
   * Adjusts the width of the InteractiveTextBox.
   * @param width The new (abolute) width.
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

  /**
  *
  */
  private void addLine() {
    ycenter += (textSize + LINE_SPACING / 4f) / 2f;
    pixelHeight += textSize + LINE_SPACING / 4f;
  }

  /**
  *
  */
  private void removeLine() {
    ycenter -= (textSize + LINE_SPACING / 4f) / 2f;
    pixelHeight -= textSize + LINE_SPACING / 4f;
  }

  /**       ASK FOR CLARITY
   * Replace the line the cursor is currently on.
   * @param newCurrentLine
   */
  private void setCurrentLine(String newCurrentLine) {
    char[] characters = str.toCharArray();
    int startIndex = 0;
    int endIndex = 0;
    int i = 0;
    ArrayList<Character> charactersAl = new ArrayList<Character>();
    for (i = 0; i < characters.length; i++) {
        charactersAl.add(characters[i]);
    }
    for (i = cursorIndex - 1; i >= 0; i--) {
        if (i > 0) if (characters[i] == PConstants.ENTER || characters[i] == PConstants.RETURN) {
            i++;
            break;
        }
    }
    startIndex = i;
    for (i = cursorIndex; i < characters.length; i++) {
        if (i > 0) if (characters[i] == PConstants.ENTER || characters[i] == PConstants.RETURN) {
            break;
        }
    }
    //if (i > 0) i--;
    endIndex = i;
    while (endIndex - startIndex > 0) {
        charactersAl.remove(startIndex);
        endIndex--;
    }
    char[] newCharacters = newCurrentLine.toCharArray();
    int j = 0;
    for (i = startIndex; i < startIndex + newCharacters.length; i++) {
        charactersAl.add(i, newCharacters[j]);
        j++;
    }
    str = "";
    for (i = 0; i < charactersAl.size(); i++) str += charactersAl.get(i);
  }

  /**
   *  ASK FOR CLARIFICATION
   * @return The String starting with the last PConstants.ENTER or PConstants.RETURN, not a Processing line.
   */
  private String getCurrentLine() {
    char[] characters = str.toCharArray();
    String reversedLine = "";
    if (characters.length == 0) return "";
    for (int i = cursorIndex - 1; i >= 0; i--) {
        if (characters[i] == PConstants.ENTER || characters[i] == PConstants.RETURN) {
             break;
        } 
        reversedLine += characters[i];
    }
    char[] reversedLineCharacters = reversedLine.toCharArray();
    String line = "";
    for (int i = reversedLineCharacters.length - 1; i >= 0; i--) {
        line += reversedLineCharacters[i];
    }
    for (int i = cursorIndex; i < characters.length && i >= 0; i++) {
        if (characters[i] == PConstants.ENTER || characters[i] == PConstants.RETURN) break;
        line += characters[i];
    }
    return line;
  }

  private boolean inBullets() {
    if (getCurrentLine().length() == 0) return false;
    if (!lineHasNonWhitespaceCharacter()) return false;
    char firstNonWhitespaceCharacter = getCharacterAfterWhitespace(getCurrentLine(), 0);
    for (int i = 0; i < BULLETS.length; i++) if (firstNonWhitespaceCharacter == BULLETS[i]) return true;
    return false;
  }

  private boolean inNumbers() {
    String line = getCurrentLine();
    if (line.length() < 2) return false;
    if (!lineHasNonWhitespaceCharacter()) return false;
    char firstNonWhitespaceCharacter = getCharacterAfterWhitespace(line, 0);
    if (Character.isAlphabetic(firstNonWhitespaceCharacter) && Character.isLowerCase(firstNonWhitespaceCharacter)) {
        if (getIndentationLevel() % 2 == 1) return true;
    }
    if (Character.isDigit(firstNonWhitespaceCharacter)) {
        if (getIndentationLevel() % 2 == 0) return true;
    }
    return false;
  }

  private int getIndentationLevel() {
    String line = getCurrentLine();
    if (line.length() == 0) return 0;
    char[] lineCharacters = line.toCharArray();
    int indentationChars = 0;
    int i = 0;
    while (lineCharacters[i] == INDENTATION_CHAR) {
        indentationChars++;
        i++;
    }
    if (indentationChars % INDENTATION_SIZE == 0) return indentationChars / INDENTATION_SIZE;
    else return 0;
  }

  private void indent() {
    String indentation = "";
    for (int i = 0; i < INDENTATION_SIZE; i++) indentation += INDENTATION_CHAR;
    charactersSinceNewLine += INDENTATION_SIZE + 1;
    String line = getCurrentLine();
    String newLine = "";
    if (inBullets()) {
        String lastBullet = Character.toString(getCharacterAfterWhitespace(line, 0));
        String newBullet = Character.toString(BULLETS[(getIndentationLevel() + 1) % BULLETS.length]);
        newLine = (indentation + line).replaceFirst("\\" + lastBullet, newBullet);
    }
    else if (inNumbers()) {
        int nextIndex = 0;
        char lastNumber = getCharacterAfterWhitespace(line, nextIndex);
        String nextNumber = "a";

        // TODO: the following will not preserve past numbers if you outindent and then indent back
        // to the bottom of a list.
        // TODO: make the below a helper method. It will come up at LEAST 4 times in total.
        
        /*if (Character.isAlphabetic(lastNumber)) nextNumber = Character.toString('1');
        else {
            String lastNumberCombined = "";
            while (Character.isDigit(lastNumber)) {
                lastNumberCombined += lastNumber;
                nextIndex++;
                lastNumber = getCharacterAfterWhitespace(getCurrentLine(), nextIndex);
            }
            nextNumber = Integer.toString(Integer.parseInt(lastNumberCombined) + 1);
        char newNumber;
        if (Character.isDigit(getCharacterAfterWhitespace(line, 0))) newNumber = NUMBERS[1];
        else newNumber = NUMBERS[0];*/
        newLine = (indentation + line).replaceFirst("\\" + lastNumber, nextNumber);
    }
    setCurrentLine(newLine);
    cursorIndex += INDENTATION_SIZE;
  }

  private void unindent() {
    String indentation = "";
    for (int i = 0; i < INDENTATION_SIZE; i++) indentation += INDENTATION_CHAR;
    charactersSinceNewLine -= INDENTATION_SIZE + 1;
    String line = getCurrentLine();
    String bullet = Character.toString(getCharacterAfterWhitespace(line, 0));
    String newBullet = Character.toString(BULLETS[(getIndentationLevel() - 1 + 3) % BULLETS.length]);
    String newLine = line.replaceFirst(indentation, "").replaceFirst("\\" + bullet, newBullet);
    setCurrentLine(newLine);
    cursorIndex -= INDENTATION_SIZE;
  }

  private boolean lineHasNonWhitespaceCharacter() {
    char[] sCharacters = getCurrentLine().toCharArray();
    int i = 0;
    while (i < sCharacters.length - 1 && sCharacters[i] == INDENTATION_CHAR) {
        i++;
    }
    return !(sCharacters[i] == INDENTATION_CHAR);
  }

  private char getCharacterAfterWhitespace(String s, int index) {
    char[] sCharacters = s.toCharArray();
    int i = 0;
    while (sCharacters[i] == INDENTATION_CHAR && i < sCharacters.length) {
        i++;
    }
    return sCharacters[i + index];
  }

  /**
   * Moves the text cursor UP, DOWN, LEFT, or RIGHT. Use the `Directionss` enum.
   * @param Directions The Directions to move the text cursor in.
   * @see Directionss
   */
  protected void moveCursor(Directions Directions) {
      switch(Directions) {
        case DOWN:
            int index = scan(Directions.RIGHT) + 1;
            if (index <= str.length()) cursorIndex = index;
            else cursorIndex = str.length();
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
            index = scan(Directions.LEFT) - 1;
            if (index >= 0) cursorIndex = index;
            else cursorIndex = 0;
            break;
		default:
			break;
      }
  }

  /**
   * Scans in the specified Directions for a newline or the beginning/end of the string
   * in this text box, starting at the cursor.
   * @param d Directions.LEFT or Directions.RIGHT. The direction to scan in.
   * @return The index of the first encountered newline, or the beginning of the string,
   * or the end of the string.
   */
  private int scan(Directions d) {
    int index = cursorIndex;
    if (d == Directions.LEFT) index--;
    else if (d == Directions.RIGHT) index++;
    char[] strCharacters = str.toCharArray();
    while (index >= 1 && index < strCharacters.length &&
        !(strCharacters[index] == PConstants.RETURN) && !(strCharacters[index] == PConstants.ENTER)) {
            if (d == Directions.LEFT) index--;
            else if (d == Directions.RIGHT) index++;
    }
    return index;
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
    boolean inBullets = inBullets();
    boolean inNumbers = inNumbers();
    int indentationLevel = 0;
    if (inBullets) indentationLevel = getIndentationLevel();
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
        if (!(charactersSinceNewLine % charactersPerLine == 0)) addLine();
        if (inBullets || inNumbers) {
			if (inBullets) {
                addCharacter(BULLETS[indentationLevel % BULLETS.length]);
                addCharacter(' ');
            }
            else if (inNumbers) {
                int cachedCursorIndex = cursorIndex;
                moveCursor(Directions.UP);
                int nextIndex = 0;
                char lastNumber = getCharacterAfterWhitespace(getCurrentLine(), nextIndex);
                String nextNumber = "a";
                if (lastNumber == 'z') nextNumber = Character.toString('a');
                else {
                    String lastNumberCombined = "";
                    while (Character.isDigit(lastNumber)) {
                        lastNumberCombined += lastNumber;
                        nextIndex++;
                        lastNumber = getCharacterAfterWhitespace(getCurrentLine(), nextIndex);
                    }
                    nextNumber = Integer.toString(Integer.parseInt(lastNumberCombined) + 1);
                }
                cursorIndex = cachedCursorIndex;
                char[] nextNumberChars = nextNumber.toCharArray();
                for (int i = 0; i < nextNumberChars.length; i++) addCharacter(nextNumberChars[i]);
                addCharacter('.');
                addCharacter(' ');
            }
            for (int i = 0; i < indentationLevel; i++) {
                indent();
            }
        }
    }
  }

  /**
   * Removes the character before the text cursor.
   */
  protected void removeCharacter() {
    char[] characters = str.toCharArray();
    if (cursorIndex > 0) {
        String line = getCurrentLine();
        char[] lineCharacters = line.toCharArray();
        int lineCursorIndex = cursorIndex - (str.length() - line.length());
        boolean cursorCharIsBullet = false;
        if (lineCursorIndex > 0) {
            for (int i = 0; i < BULLETS.length; i++)
            if (BULLETS[i] == lineCharacters[lineCursorIndex - 1]) cursorCharIsBullet = true;
        }
        if (inBullets() && cursorCharIsBullet && getIndentationLevel() > 0) {
            unindent();
            return;
        }
        char[] newCharacters = new char[characters.length - 1];
        cursorIndex--;
        int o = 0;
        for (int i = 0; i < characters.length - 1; i++) {
            if (i == cursorIndex) {
                o = 1;
            }
            newCharacters[i] = characters[i + o];
        }
        str = new String(newCharacters);
        charactersSinceNewLine--;
        if (charactersSinceNewLine % charactersPerLine == 0 || characters[cursorIndex] == '\n' || characters[cursorIndex] == '\r') removeLine();
    }
  }

  protected void setMode(Modes m) {
    if (m == Modes.PLAIN) {

    }
    if (m == Modes.BULLETED) {

    }
    if (m == Modes.NUMBERED) {
        
    }
  }

  /**
  * Handles user input to add text and move the cursor around.
  * @param key A character representation of the key currently pressed (capital and lower case letters are different, all function keys are the same)
  * @param keyCode Represents the value of they key currently pressed (capital and lower case letters are the same, all function keys are different)
  */
  public void handleKey(char key, int keyCode) {
    if (key == PConstants.CODED) {
        switch(keyCode) {
            case PConstants.CONTROL:
                break;
            case PConstants.UP:
                moveCursor(Directions.UP);
                break;
            case PConstants.DOWN:
                moveCursor(Directions.DOWN);
                break;
            case PConstants.LEFT:
                moveCursor(Directions.LEFT);
                break;
            case PConstants.RIGHT:
                moveCursor(Directions.RIGHT);
                break;
        }
    }
    else {
        switch(key) {
            case PConstants.TAB:
                if (inBullets() || inNumbers()) indent();
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

  /**
  * Draw colored user-inputted text to the slide
  */
  protected void display(){
        super.display();
        String finalString = str;
        sketch.textLeading(LINE_SPACING);
        sketch.textAlign(PConstants.LEFT);  //overrides the current default of CENTER, CENTER
        sketch.rectMode(PConstants.CORNER);  //overrides the current default of CENTER
        char[] characters = str.toCharArray();
        if (displayCursor) {
            displayCursor = false;
            finalString = "";
            char[] charactersWithCursor;
            int offset = 0;
            long nowTime = System.currentTimeMillis();
            if (nowTime - cursorStartTime >= CURSOR_BLINK_RATE) {
                if (cursor == '|') cursor = '\u202F';
                else cursor = '|';
                cursorStartTime = nowTime;
            }
            charactersWithCursor = new char[characters.length + 1];
            if (cursorIndex == characters.length) finalString = str + cursor;
            else {
                for (int i = 0; i < characters.length; i++) {
                    if (i == cursorIndex) {
                        charactersWithCursor[i + 1] = cursor;
                        offset = 1;
                    }
                    charactersWithCursor[i + offset] = characters[i];
                }
            finalString = new String(charactersWithCursor);
            }
        }
        sketch.text(finalString, 0f - pixelWidth / 2, 0f - pixelHeight / 2, pixelWidth, pixelHeight + textSize);
        //sketch.text(str, 0, 0);
  }
}
