package com.company;

import javax.swing.*;
import java.awt.*;

class ImagePanel extends JComponent {
    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    public static void removeBackround(JButton jButton) {
        jButton.setOpaque(false);
        jButton.setContentAreaFilled(false);
    }

}