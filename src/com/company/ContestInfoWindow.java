package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

public class ContestInfoWindow extends JFrame {

    private JButton backButton;

    public ContestInfoWindow() throws Exception {
        super("Codeforces Surfer Contest Info");
        setBounds(200, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage backgroundImage = ImageIO.read(new File("Assets/any-background.jpg"));
        setContentPane(new ImagePanel(backgroundImage));
        addButtons();
        addBoxWithContestList();
    }

    private void addBoxWithContestList() {
        Vector comboBoxItems = new Vector();

        final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
        JComboBox comboBox = new JComboBox(model);
        comboBox.setLocation(200, 200);
        comboBox.setSize(200, 200);
        add(comboBox);

    }

    public void clear() {

    }

    private void addButtons() {
        addBackButton();
    }

    private void addBackButton() {
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
