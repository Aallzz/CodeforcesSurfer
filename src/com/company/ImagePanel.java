package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

class ImagePanel extends JComponent {
    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    public static ImageIcon readFromURL(String stringURL) throws Exception {
        //System.out.println("!! " + stringURL);
        URL url = new URL(stringURL);
        Image image = ImageIO.read(url);
        return new ImageIcon(image);
    }

    public static void removeBackround(JButton jButton) {
        jButton.setOpaque(false);
        jButton.setContentAreaFilled(false);
    }

}