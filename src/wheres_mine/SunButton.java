package wheres_mine;

import javax.swing.*;
import java.awt.event.*;

/**
 * The button at the top of the screen with the sun on it.
 * @author not attributable
 */
public class SunButton
    extends JButton
    implements ActionListener

{
  /** State when game is being played. */
  public static final int PLAYING = 0;
  /** State when a square is about to be uncovered. */
  public static final int UNCOVERING = 1;
  /** State when the game is won **/
  public static final int WON = 2;
  /** State when the game is lost **/
  public static final int LOST = 3;
  /** The current game. */
  private MainJFrame board;
  /** Uncovering image. */
  private static ImageIcon uncovering_icon = new ImageIcon("images/uncovering.jpg");
  /** Playing image. */
  private static ImageIcon playing_icon = new ImageIcon("images/playing.jpg");
  /** Won image. */
  private static ImageIcon won_icon = new ImageIcon("images/won.jpg");
  /** Lost image. */
  private static ImageIcon lost_icon = new ImageIcon("images/lost.jpg");


  private int state;

  public SunButton(MainJFrame b) {
    board = b;
    setState(PLAYING);
    addActionListener(this);
  }

  public void setState(int s) {
    state = s;
    switch (state) {
      case PLAYING:
        setIcon(playing_icon);
        break;
      case UNCOVERING:
        setIcon(uncovering_icon);
        break;
      case WON:
        setIcon(won_icon);
        break;
      case LOST:
        setIcon(lost_icon);
        break;
    }
  }

  public void actionPerformed(ActionEvent e) {
    System.out.print("Clicked!");
    board.setup();
  }

}