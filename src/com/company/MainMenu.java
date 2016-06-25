package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainMenu extends JFrame {

    private JLabel codeforfesLogo;
    private JButton getUserInfoButton;
    private JButton getContestInfoButton;

    public MainMenu() throws Exception {
        super("Codeforces Surfer Main Menu");
        setBounds(200, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage backgroundImage = ImageIO.read(new File("Assets/main-menu-background.jpg"));
        setContentPane(new ImagePanel(backgroundImage));
        addCodeforcesLogo();
        addGetUserInfoButton();
        addGetContestInfoButton();
    }

    private void addGetContestInfoButton() {
        getContestInfoButton = new JButton("", new ImageIcon("Assets/get-contest-info.gif"));
        ImagePanel.removeBackround(getContestInfoButton);
        getContestInfoButton.setSize(379, 60);
        getContestInfoButton.setLocation(0, 170);
        getContestInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Main.contestInfoWindow.clear();
                Main.contestInfoWindow.setVisible(true);
            }
        });
        add(getContestInfoButton);
    }

   private void addCodeforcesLogo() {
        codeforfesLogo = new JLabel();
        codeforfesLogo.setIcon(new ImageIcon("Assets/cflogo.png"));
        codeforfesLogo.setSize(350, 50);
        codeforfesLogo.setLocation(0, 0);
        add(codeforfesLogo);
    }

    private void addGetUserInfoButton() {
        getUserInfoButton = new JButton("", new ImageIcon("Assets/get-user-info.gif"));
        ImagePanel.removeBackround(getUserInfoButton);
        getUserInfoButton.setSize(198, 47);
        getUserInfoButton.setLocation(0, 100);
        getUserInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Main.userInfoWindow.clear();
                Main.userInfoWindow.setVisible(true);
            }
        });
        add(getUserInfoButton);
    }

}
