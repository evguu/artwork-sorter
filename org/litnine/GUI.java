package org.litnine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI
{
    private final JLabel label;

    public GUI()
    {
        JFrame frame = new JFrame();

        label = new JLabel("0", SwingConstants.CENTER);

        JButton button = new JButton("Click me");
        button.addActionListener(new ActionListener() {
            private int count = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText(Integer.toString(++count));
            }
        });

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));

        panel.add(label);
        panel.add(button);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("File Sorter v"+Config.VERSION);
        frame.pack();
        frame.setVisible(true);

    }
}
