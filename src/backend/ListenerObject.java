package backend;

/**
* An interface for handling user input.
*/
public interface ListenerObject {

  /**
  * Handles user input.
  * @param key
  * @param keyCode
  */
  public abstract void handleKey(char key, int keyCode);
}
