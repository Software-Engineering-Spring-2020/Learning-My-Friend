package backend;

/**
* An interface for handling user input.
*/
public interface ListenerObject {

  /**
  * Handles user input.
  * @param key A character representation of the key currently pressed (capital and lower case letters are different, all function keys are the same)
  * @param keyCode Represents the value of they key currently pressed (capital and lower case letters are the same, all function keys are different)
  */
  public abstract void handleKey(char key, int keyCode);
}
