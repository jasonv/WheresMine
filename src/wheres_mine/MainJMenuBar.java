package wheres_mine;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Menu bar at top of the screen.
 * @author Jason Vroustouris
 */

public class MainJMenuBar extends JMenuBar implements java.awt.event.ActionListener {
  private JMenu gameJMenu = new JMenu("Game");
  private JMenuItem newGameJMenuItem = new JMenuItem("New Game");
  private JMenuItem fastestTimeJMenuItem = new JMenuItem("Fastest Time");
  private JMenuItem exitJMenuItem = new JMenuItem("Exit");
  private JMenu helpJMenu = new JMenu("Help");
  private JMenuItem directionsJMenuItem = new JMenuItem("Directions");
  private JMenuItem aboutJMenuItem = new JMenuItem("About");
  private MainJFrame board;
  public MainJMenuBar(MainJFrame b) {
    board = b;
    // Game
    newGameJMenuItem.addActionListener(this);
    gameJMenu.add(newGameJMenuItem);
    fastestTimeJMenuItem.addActionListener(this);
    gameJMenu.add(fastestTimeJMenuItem);
    exitJMenuItem.addActionListener(this);
    gameJMenu.add(exitJMenuItem);
    add(gameJMenu);

    // Help
    directionsJMenuItem.addActionListener(this);
    helpJMenu.add(directionsJMenuItem);
    aboutJMenuItem.addActionListener(this);
    helpJMenu.add(aboutJMenuItem);
    add(helpJMenu);
  }

  public void actionPerformed(ActionEvent actionEvent) {
    if (actionEvent.getSource() == newGameJMenuItem) {
      board.setup();
      return;
    }
    if (actionEvent.getSource() == exitJMenuItem) {
      System.exit(0);
    }
    if (actionEvent.getSource() == aboutJMenuItem) {
      AboutDialog ad = new AboutDialog(board);
    }
    if (actionEvent.getSource() == directionsJMenuItem) {
      DirectionsDialog dd = new DirectionsDialog(board);
    }
    if (actionEvent.getSource() == fastestTimeJMenuItem) {
      JOptionPane.showMessageDialog(board,"The fastest time is: " + board.getFastestTime());

    }
  }
}