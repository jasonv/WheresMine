package wheres_mine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class AboutDialog extends JDialog implements ActionListener {
    /** About Dialog Box **/
        public AboutDialog(JFrame f) {
                super(f,"About Where's Mine",true);
                setSize(300,200);
                setLocation(100,100);
                String text = "Where's Mine 1.0\n" +
                    "Created By Jason Vroustouris\n" +
                    "CS422 - User Inteface Design\n" +
                    "Prof. Pat Troy\n" +
                    "January 29, 2004";


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
