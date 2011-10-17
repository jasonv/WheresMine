package wheres_mine;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.net.*;

/**
 * A single square on the board.
 * @author Jason Vroustouris
 */
public class Square
    extends JLabel
    implements MouseListener {
  /* =====================================================================
   * Public Fields
   * ================================================================== */
  /** State when square is not clicked. **/
  public static final int SQUARE_COVERED = 0;
  /** State when square is flaged. **/
  public static final int SQUARE_FLAGED = 1;
  /** State when square has been clicked or uncovered in a cascade.*/
  public static final int SQUARE_UNCOVERED = 2;
  /** State when square may have a bomb. */
  public static final int SQUARE_QUESTION_MARKED = 3;
  /* =====================================================================
   * Private Fields
   * ================================================================== */
  /** Flag icon */
  private static ImageIcon flag_icon = new ImageIcon("images/flag.gif");
  /** Flag icon */
  private static ImageIcon covered_icon = new ImageIcon("images/covered.gif");
  /** Flag icon */
  private static ImageIcon bomb_icon = new ImageIcon("images/bomb.gif");
  /** Flag icon */
  private static ImageIcon question_icon = new ImageIcon("images/question.gif");
  /** Flag icon */
  private static ImageIcon empty_icon = new ImageIcon("images/empty.gif");
  /** Flag icon */
  private static ImageIcon one_icon = new ImageIcon("images/1.gif");
  /** Flag icon */
  private static ImageIcon two_icon = new ImageIcon("images/2.gif");
  /** Flag icon */
  private static ImageIcon three_icon = new ImageIcon("images/3.gif");
  /** Flag icon */
  private static ImageIcon four_icon = new ImageIcon("images/4.gif");
  /** Flag icon */
  private static ImageIcon five_icon = new ImageIcon("images/5.gif");
  /** Flag icon */
  private static ImageIcon six_icon = new ImageIcon("images/6.gif");
  /** Flag icon */
  private static ImageIcon seven_icon = new ImageIcon("images/7.gif");
  /** Flag icon */
  private static ImageIcon eight_icon = new ImageIcon("images/8.gif");
  private int state;
  private int x_location;
  private int y_location;
  private int number_of_adjacent_bombs;
  private boolean bomb;
  private boolean locked;
  private MainJFrame board;
  /* =====================================================================
   * Public Constructors
     ================================================================== */

  /**
   * Creates new square at location on the board.
   * */
  public Square(int x, int y, MainJFrame b) {
    x_location = x;
    y_location = y;
    board = b;
    addMouseListener(this);
  }

  public void setup() {
    setState(SQUARE_COVERED);
    bomb = false;
    locked = false;
    number_of_adjacent_bombs = 0;
    setText("");
  }

  /* =====================================================================
   * Get Members
   * ================================================================== */

  /**
   * Gets the number of bombs in this square.
   * @return 0 or 1
   * */
  public int getBombs() {
    if (bomb) {
      return 1;
    }
    else {
      return 0;
    }
  }

  /**
   * Gets the number of bombs adjacent to this square.
   * @returns (0-8)
   * */
  public int getNumber() {
    return number_of_adjacent_bombs;
  }

  /**
   * Gets the current state of the square.
   * @returns SQUARE_COVERED, SQUARE_FLAGED, SQUARE_UNCOVERED
   * */
  public int getState() {
    return state;
  }

  /* =====================================================================
   * Is Members
   * ================================================================== */
  /**
   * Returns true if square should be opened in cascade.
   * */
  public boolean isOpenable() {
    if (getBombs() == 1) {
      return false;
    }
    if (getState() == Square.SQUARE_FLAGED) {
      return false;
    }
    if (getState() == Square.SQUARE_UNCOVERED) {
      return false;
    }
    return true;
  }

  /**
   * Returns true is square should open other squares in cascade.
   * */
  public boolean isOpener() {
    if (getNumber() != 0) {
      return false;
    }
    return true;
  }

  /**
   * Returns true if square contains a bomb.
   * */
  public boolean isBomb() {
    return bomb;
  }

  /* =====================================================================
   * Mouse Handelers
   * ================================================================== */

  /** Handels scared sun expression. */
  public void mousePressed(MouseEvent e) {
    if (locked) {
      return;
    }
    if (state != SQUARE_COVERED) {
      return;
    }
    switch (e.getButton()) {
      case MouseEvent.BUTTON1:
        board.getSunButton().setState(SunButton.UNCOVERING);
        break;
      case MouseEvent.BUTTON3:
        break;
    }
  }

  /** Not Used */
  public void mouseEntered(MouseEvent e) {}

  /** Not Used */
  public void mouseExited(MouseEvent e) {}

  /** Handels left and right mouse clicks on this button. */
  public void mouseClicked(MouseEvent e) {
    if (locked) {
      return;
    }
    switch (e.getButton()) {
      case MouseEvent.BUTTON1:
        sendLeftClick();
        break;
      case MouseEvent.BUTTON3:
        sendRightClick();
        break;
    }
  }

  /** Handels removal of scared sun expression. */
  public void mouseReleased(MouseEvent e) {
    if (locked) {
      return;
    }
    if (state != SQUARE_COVERED) {
      return;
    }
    switch (e.getButton()) {
      case MouseEvent.BUTTON1:
        board.getSunButton().setState(SunButton.PLAYING);
        break;
      case MouseEvent.BUTTON3:
        break;
    }
  }

  /**
   * Method called by left click.
   * */
  public void sendLeftClick() {
    switch (state) {
      case SQUARE_COVERED:
        board.uncoverSquare(x_location, y_location);
        setState(SQUARE_UNCOVERED);
        break;
      case SQUARE_FLAGED:

        // nothing
        break;
      case SQUARE_UNCOVERED:

        // nothing
        break;
      case SQUARE_QUESTION_MARKED:

        // nothing
        break;
    }
  }

  /**
   * Method called by right click.
   * */
  public void sendRightClick() {
    switch (state) {
      case SQUARE_COVERED:
        board.getCounterLabel().increment();
        setState(SQUARE_FLAGED);
        break;
      case SQUARE_FLAGED:
        board.getCounterLabel().decrement();
        setState(SQUARE_QUESTION_MARKED);
        break;
      case SQUARE_UNCOVERED:

        // nothing
        break;
      case SQUARE_QUESTION_MARKED:
        setState(SQUARE_COVERED);
        break;
    }
  }

  /* =====================================================================
   * Set Members
   * ================================================================== */

  /**
   * Place a bomb in this square.
   * @return
   * */
  public void setBomb() {
    bomb = true;
    setText("b");
  }

  /**
   * Sets the number of adjacent bombs.
   * */
  public void setNumber(int num) {
    number_of_adjacent_bombs = num;
  }

  /**
   * Sets the square to covered, flaged or uncovered.
   * */
  public void setState(int s) {
    state = s;
    switch (state) {
      case SQUARE_COVERED:
        setIcon(covered_icon);
        break;
      case SQUARE_FLAGED:
        setIcon(flag_icon);
        break;
      case SQUARE_UNCOVERED:
        if (bomb) {
          setIcon(bomb_icon);
        }
        else {
          setIconNumber(number_of_adjacent_bombs);
        }
        break;
      case SQUARE_QUESTION_MARKED:
        setIcon(question_icon);
        break;
    }
  }

  /** Set the icon to a number 1 to 8 */
  private void setIconNumber(int i) {
    if (i < 0 || i > 8) {
      return;
    }
    switch (i) {
      case 0:
        setIcon(empty_icon);
        break;
      case 1:
        setIcon(one_icon);
        break;
      case 2:
        setIcon(two_icon);
        break;
      case 3:
        setIcon(three_icon);
        break;
      case 4:
        setIcon(four_icon);
        break;
      case 5:
        setIcon(five_icon);
        break;
      case 6:
        setIcon(six_icon);
        break;
      case 7:
        setIcon(seven_icon);
        break;
      case 8:
        setIcon(eight_icon);
        break;
    }
    return;
  }

  /** Locks this square so that it can't be clicked. */
  public void setLocked() {
    locked = true;
  }
}