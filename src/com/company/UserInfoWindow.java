package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class UserInfoWindow extends JFrame {

    private JLabel infoLabel;
    private JLabel userAvatar;
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
        addTextField();
        addOkButton();
        addUserAvatar();
    }

    private void addUserAvatar() {
        userAvatar = new JLabel();
        userAvatar.setSize(300, 300);
        userAvatar.setLocation(500, 0);
        add(userAvatar);
    }

    private void addTextField() {
        jTextField = new JTextField();
        jTextField.setSize(250, 30);
        jTextField.setLocation(0, 150);
        add(jTextField);
    }

    private void addOkButton() {
        okButton = new JButton("", new ImageIcon("Assets/ok-button.gif"));
        ImagePanel.removeBackround(okButton);
        okButton.setSize(85, 60);
        okButton.setLocation(0, 200);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = jTextField.getText();
                if (text.length() == 0) {
                    JOptionPane.showMessageDialog(Main.userInfoWindow,  "Field username can not be empty!", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        loadUserInfo(text);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        add(okButton);
    }

    private void loadUserInfo(String handle) throws Exception {
        String urlRequest = " http://codeforces.com/api/user.info?handles=" + handle;
        HTTPConnection connection = new HTTPConnection(urlRequest);
        ArrayList<String> result = connection.makeRequest();
        JSONParser parser = new JSONParser();
        for (String jSonQuery : result) {
            //System.out.println(jSonQuery);
            Object obj = parser.parse(jSonQuery);
            JSONObject jSon = (JSONObject) obj;
            String status = (String)jSon.get("status");
            if (status.equals("FAILED")) {
                JOptionPane.showMessageDialog(Main.userInfoWindow,  jSon.get("comment"), "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                JSONArray res = (JSONArray)jSon.get("result");
                JSONObject resObj = (JSONObject)res.get(0);
                userAvatar.setIcon(ImagePanel.readFromURL((String)resObj.get("titlePhoto")));
                userAvatar.setVisible(true);
            }
            //System.out.println(jSon.get("status"));
        }
    }

    private void addInfoLabel() {
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Serif", Font.BOLD, 30));
        infoLabel.setText("Enter a username:");
        infoLabel.setSize(250, 30);
        infoLabel.setLocation(0, 100);
        add(infoLabel);
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
