package wheres_mine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class DirectionsDialog extends JDialog implements ActionListener {
    /** About Dialog Box **/
        public DirectionsDialog(JFrame f) {
                super(f,"About Where's Mine",true);
                setSize(500,600);
                setLocation(100,100);
                String text = "Objective: \n\n"+
"Cover each bomb in the field with a flag and uncover all the squares that are not bombs.  \n\n" +
"Left clicking on a square will reveal a single number or a blank space.  " +
                  "The number indicates the number of bombs abound the square.  " +
                  "If the square is empty, there are no bombs around the square.  " +
                  "Use the numbers to guess where the bombs are.  " +
                  "When all the squares except the bombs are uncovered the game is won.  " +
                  "If a bomb is clicked the game is lost.  " +
                  "Right clicking will place a flag or a question mark.  " +
                  "Place a flag to mark the location of a bomb.  " +
                  "Place a question make for a location that might be a bomb.  ";

                JTextPane jTextPane = new JTextPane();
                jTextPane.setText(text);
                jTextPane.setEditable(false);
                jTextPane.setSelectionColor(Color.WHITE);
                jTextPane.select(0,1);
                JButton jButton = new JButton("Ok");
                jButton.addActionListener(this);
                getContentPane().setLayout(new BorderLayout());
                getContentPane().add(new JScrollPane(jTextPane),BorderLayout.CENTER);
                getContentPane().add(jButton,BorderLayout.SOUTH);
                show();
        }

        /** Handel ActionEvents from the OK JButton **/
        public void actionPerformed(ActionEvent a) {
                dispose();
        }
}
