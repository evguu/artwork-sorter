package org.litnine;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        SorterForm sf = new SorterForm();
        sf.init();
        frame.setContentPane(sf.panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("File Sorter v" + Config.VERSION);
        frame.pack();
        frame.setVisible(true);
    }
}
