package wheres_mine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * The main window displayed by the program.
 * @author Jason Vroustouris
 *  */
public class MainJFrame
    extends JFrame {
  /* =====================================================================
   * Private Fields
   * ================================================================== */
  /** Number of squares in mine field across. **/
  private static final int BOARD_WIDTH = 8;
  /** Number of squares in mine field across. **/
  private static final int BOARD_HEIGHT = 8;
  /** Number of bombs on screen. */
  private static final int BOARD_BOMBS = 10;
  /** The menu bar at the top of the screen. */
  private MainJMenuBar mainJMenuBar = new MainJMenuBar(this);
  /** Logical and visual representation of the game board. **/
  private Square square[][] = new Square[BOARD_WIDTH][BOARD_HEIGHT];
  /** The reset button with picture of a sun on it. */
  private SunButton sunButton;
  /** The timer for the game */
  private TimerLabel timer = null;
  /** The timer for the game */
  private CounterLabel counter = null;
  /** True before users first click. */
  private boolean first_click;
  /** The total number of uncovered squares. */
  private int total_uncovered;
  /** Fastest time. **/
  private int fastest_time;

  public MainJFrame() {
    super("Where's Mine");

    // Fastest time
    fastest_time = 99999;

    // Screen Layout
    getContentPane().setLayout(new BorderLayout());

    /* Counter */
    counter = new CounterLabel();

    /* Sun Button */
    sunButton = new SunButton(this);

    /* Timer */
    timer = new TimerLabel();

    /* Game Board */
    JPanel board = new JPanel(new GridLayout(BOARD_HEIGHT, BOARD_WIDTH));
    board.setBackground(new Color(150,150,150));
    for (int y = 0; y < BOARD_HEIGHT; y++) {
      for (int x = 0; x < BOARD_WIDTH; x++) {
        square[x][y] = new Square(x, y, this);
        board.add(square[x][y]);
      }
    }
    getContentPane().add(board, BorderLayout.CENTER);

    /* NORTH J Panel */
    JPanel northJPanel = new JPanel(new GridLayout(1, 3));
    northJPanel.add(counter);
    northJPanel.add(sunButton);
    northJPanel.add(timer);
    getContentPane().add(northJPanel, BorderLayout.NORTH);

    // JFrame
    setJMenuBar(mainJMenuBar);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(350, 410);
    show();
    setup();
  }

  /**
   * Gets the sun button on the top of the screen.
   * @return
   */
  public SunButton getSunButton() {
    return sunButton;
  }

  /**
   * Gets the counter on the top of the screen.
   * @return
   */
  public CounterLabel getCounterLabel() {
    return counter;
  }

  public int getFastestTime() {
    return fastest_time;
  }

  public void setup() {
    /* Set the sun button to playing. */
    sunButton.setState(SunButton.PLAYING);

    /* Reset all the squares. */
    for (int y = 0; y < BOARD_HEIGHT; y++) {
      for (int x = 0; x < BOARD_WIDTH; x++) {
        square[x][y].setup();
      }
    }
    first_click = true;
    total_uncovered = 0;
    timer.clear();
    counter.clear();
  }

  private void firstClick(int x_loc, int y_loc) {

    /* Place bombs is random squares. */
    Random random = new Random();
    int i = 0;
    while (i < BOARD_BOMBS) {
      int x_rand = random.nextInt(BOARD_WIDTH);
      int y_rand = random.nextInt(BOARD_HEIGHT);
      boolean skip = false;
      skip |= (x_rand == x_loc);
      skip |= (y_rand == y_loc);
      skip |= (square[x_rand][y_rand].isBomb());
      if (skip) {
        System.out.print("Skip!");
      } else {
        square[x_rand][y_rand].setBomb();
        i++;
      }
    }

    /* Calculate numbers of adjacent bombs in squares. */
    for (int y = 0; y < BOARD_HEIGHT; y++) {
      for (int x = 0; x < BOARD_WIDTH; x++) {
        square[x][y].setNumber(getNumber(x, y));
      }
    }

    /* Start the timer. */
    timer.start();
  }

  /**
   * Uncover the given square and the ones around it that should be.
   * */
  public void uncoverSquare(int x, int y) {
    if (isValidSquare(x, y) == false) {
      System.out.print("Invalid Square");
      return;
    }
    if (first_click) {
      first_click = false;
      firstClick(x, y);
    }
    if (square[x][y].isBomb()) {
      lost();
    }
    cascade(x, y);
    if (total_uncovered == ((BOARD_WIDTH * BOARD_HEIGHT)-BOARD_BOMBS)) {
      won();
      return;
    }
  }

  private static boolean isValidSquare(int x, int y) {
    if (x < 0) {
      return false;
    }
    if (y < 0) {
      return false;
    }
    if (x >= BOARD_WIDTH) {
      return false;
    }
    if (y >= BOARD_HEIGHT) {
      return false;
    }
    return true;
  }

  /** Count the number of bombs in a square. Can be 0 or 1.*/
  private int getBombs(int x, int y) {
    if (isValidSquare(x, y)) {
      return square[x][y].getBombs();
    }
    else {
      return 0;
    }
  }

  /** Count the numbers of bombs around a square. */
  private int getNumber(int x, int y) {
    int number = 0;
    number += getBombs(x + 1, y + 1);
    number += getBombs(x + 1, y + 0);
    number += getBombs(x + 1, y - 1);
    number += getBombs(x + 0, y + 1);
    number += getBombs(x + 0, y - 1);
    number += getBombs(x - 1, y + 1);
    number += getBombs(x - 1, y + 0);
    number += getBombs(x - 1, y - 1);
    return number;
  }

  /** Open surounding empty squares.  */
  private void cascade(int x, int y) {
    if (isValidSquare(x, y) == false) {
      return;
    }
    if (square[x][y].isOpenable() == false) {
      return;
    }
    total_uncovered++;
    square[x][y].setState(Square.SQUARE_UNCOVERED);
    if (square[x][y].isOpener() == false) {
      return;
    }

    // South
    cascade(x - 1, y + 1);
    cascade(x, y + 1);
    cascade(x + 1, y + 1);

    // East and West
    cascade(x - 1, y);
    cascade(x + 1, y);

    // North
    cascade(x - 1, y - 1);
    cascade(x, y - 1);
    cascade(x + 1, y - 1);
  }

  /**
   * Locks and cleans up the board after loss.
   */
  private void lost() {
    sunButton.setState(SunButton.LOST);
    timer.stop();
    uncoverBoard();
  }

  /**
   * Locks and cleans up the board after sin.
   */
  private void won() {
    sunButton.setState(SunButton.WON);
    timer.stop();
    if (timer.getTime() < this.fastest_time) {
      fastest_time = timer.getTime();
      JOptionPane.showMessageDialog(this,"You have the new fastest time: " + getFastestTime());
    }
    uncoverBoard();
  }

  /** Uncover all the squares on the board. */
  private void uncoverBoard() {
    for (int y = 0; y < BOARD_HEIGHT; y++) {
      for (int x = 0; x < BOARD_WIDTH; x++) {
        if (square[x][y].getState() != Square.SQUARE_FLAGED) {
          if (square[x][y].isBomb()) {
            square[x][y].setState(Square.SQUARE_UNCOVERED);
          }
        }
        square[x][y].setLocked();
      }
    }
  }

}