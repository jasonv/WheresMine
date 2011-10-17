package wheres_mine;

import javax.swing.*;

/**
 * The display for the current number of flags placed.
 * @author not attributable
 */

public class CounterLabel extends JLabel {

  /**
   * Creates a new display for the current number of flags placed.
   */
  public CounterLabel() {
    clear();
  }

  /**
   * Sets the counter to zero.
   */
  public void clear() {
    counter = 0;
    setText(" Flags: " + counter);
  }

  /**
   * Subtracts one from the counter.
   */
  public void decrement() {
    counter--;
    setText(" Flags: " + counter);
  }

  /**
   * Adds one to the counter.
   */
  public void increment() {
    counter++;
    setText(" Flags: " + counter);
  }

  /** The current number of flags placed. */
  private int counter;



}