package wheres_mine;

import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;

/**
 * The display and functions for the game timer.
 * @author Jason Vroustouris
 */

public class TimerLabel extends JLabel implements ActionListener {
  private Timer timer = null;
  private int count = 0;

  /** Creates a new timer display. */
  public TimerLabel() {
    timer = new Timer(1000,this);
  }

  /** Starts the game timer. */
  public void start() {
    count = 0;
    setText(" Time: " + count);
    timer.start();
  }

  /** Stop the game timer. */
  public void stop() {
    setText(" Time: " + count);
    timer.stop();
  }

  /** Stop the game timer. */
  public void clear() {
    count = 0;
    setText(" Time: " + count);
    timer.stop();
  }

  /** Gets the current counter value.  */
  public int getTime() {
    return count;
  }

  /** Updates the display every second. */
  public void actionPerformed(ActionEvent actionEvent) {
    count++;
    setText(" Time: " + count);
  }
}