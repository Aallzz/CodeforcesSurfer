package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class UserInfoWindow extends JFrame {

    private JLabel infoLabel;
    private JButton okButton;
    private JButton backButton;
    private JTextField jTextField;

    public UserInfoWindow() throws Exception {
        super("Codeforces Surfer User Info");
        setBounds(200, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage backgroundImage = ImageIO.read(new File("Assets/any-background.jpg"));
        setContentPane(new ImagePanel(backgroundImage));
        addInfoLabel();
        addBackButton();
    }

    void addInfoLabel() {
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Serif", Font.BOLD, 30));
        infoLabel.setText("Enter a username:");
        infoLabel.setSize(250, 30);
        infoLabel.setLocation(0, 100);
        add(infoLabel);
    }

    void addBackButton() {
        backButton = new JButton("", new ImageIcon("Assets/back-button.gif"));
        ImagePanel.removeBackround(backButton);
        backButton.setSize(134, 60);
        backButton.setLocation(0, 500);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Main.mainMenu.setVisible(true);
            }
        });
        add(backButton);
    }

}
