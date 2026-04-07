package com.modex.exporter;

import javax.swing.*;
import java.awt.*;

public class Toast {

    public static void show(String message) {
        JWindow window = new JWindow();
        JLabel label = new JLabel(message);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        window.add(label);
        window.pack();

        // position bottom-right
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(screen.width - window.getWidth() - 20,
                           screen.height - window.getHeight() - 50);

        window.setVisible(true);

        // auto close
        new Timer(2000, e -> window.dispose()).start();
    }
}
