package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainMenu extends JFrame {

    private JLabel codeforfesLogo;
    private JButton getUserInfoButton;

    public MainMenu() throws Exception {
        super("Main Menu");
        setBounds(50, 50, 1200, 800);
        setResizable(false);
        BufferedImage backgroundImage = ImageIO.read(new File("Assets/main-menu-background.jpg"));
        setContentPane(new ImagePanel(backgroundImage));
        addCodeforcesLogo();
        addGetUserInfoButton();
    }

    void addCodeforcesLogo() {
        codeforfesLogo = new JLabel();
        codeforfesLogo.setIcon(new ImageIcon("Assets/cflogo.png"));
        codeforfesLogo.setSize(350, 50);
        codeforfesLogo.setLocation(0, 0);
        add(codeforfesLogo);
    }

    void addGetUserInfoButton() {
        getUserInfoButton = new JButton();
    }

}
