package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class UserInfoWindow extends JFrame {

    public static final String NEW_LINE = "<br>";

    private JLabel allInfo;
    private JLabel infoLabel;
    private JLabel userAvatar;
    private JLabel handleLabel;
    private JButton okButton;
    private JButton backButton;
    private JTextField jTextField;
    private ImageIcon defaultIcon;

    public UserInfoWindow() throws Exception {
        super("Codeforces Surfer User Info");
        setBounds(200, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage backgroundImage = ImageIO.read(new File("Assets/any-background.jpg"));
        setContentPane(new ImagePanel(backgroundImage));
        addLabels();
        addButtons();
        addTextField();
    }

    private void addButtons() {
        addBackButton();
        addOkButton();
    }

    private void addLabels() throws Exception {
        addProfileInfo();
        addHandleLabel();
        addUserAvatar();
        addInfoLabel();
     }

    private void addProfileInfo() {
        allInfo = new JLabel();
        allInfo.setSize(300, 600);
        allInfo.setLocation(150, 0);
        allInfo.setFont(new Font("Serif", Font.PLAIN, 17));
        allInfo.setText("");
        add(allInfo);
    }

    private void addHandleLabel() {
        handleLabel = new JLabel();
        handleLabel.setFont(new Font("Serif", Font.CENTER_BASELINE, 20));
        handleLabel.setText("");
        handleLabel.setSize(250, 30);
        handleLabel.setLocation(500, 80);
        add(handleLabel);
    }

    private void addUserAvatar() throws Exception {
        userAvatar = new JLabel();
        userAvatar.setSize(230, 260);
        userAvatar.setLocation(450, 110);
        Border border = BorderFactory.createLineBorder(Color.ORANGE, 5, true);
        userAvatar.setBorder(border);
        Image image = ImageIO.read(new File("Assets/no-avatar.jpg"));
        image = ImagePanel.getScaledImage(image, 230, 260);
        defaultIcon = new ImageIcon(image);
        userAvatar.setIcon(defaultIcon);
        add(userAvatar);
    }

    private void addTextField() {
        jTextField = new JTextField();
        jTextField.setSize(250, 30);
        jTextField.setLocation(0, 150);
        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                justDoIt();
            }
        });
        add(jTextField);
    }

    private void justDoIt() {
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

    private void addOkButton() {
        okButton = new JButton("", new ImageIcon("Assets/ok-button.gif"));
        ImagePanel.removeBackround(okButton);
        okButton.setSize(85, 60);
        okButton.setLocation(0, 200);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                justDoIt();
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
            Object obj = parser.parse(jSonQuery);
            JSONObject jSon = (JSONObject) obj;
            String status = (String)jSon.get("status");
            if (status.equals("FAILED")) {
                JOptionPane.showMessageDialog(Main.userInfoWindow,  jSon.get("comment"), "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                JSONArray res = (JSONArray)jSon.get("result");
                JSONObject resObj = (JSONObject)res.get(0);
                userAvatar.setIcon(ImagePanel.readFromURL((String)resObj.get("titlePhoto")));
                handleLabel.setText("<html><div style='text-align: center;'>" + resObj.get("handle") + "</html>");
                int r = 0;
                Object rating = resObj.get("rating");
                if (rating != null) {
                    r = (int)( (long)resObj.get("rating") );
                    handleLabel.setForeground(Main.getColorByRating(r));
                } else {
                    handleLabel.setForeground(Main.getColorByRating(0));
                }
                Object object = null;
                String infoText = null;
                infoText = "<html>Rating: " + r + NEW_LINE;
                object = resObj.get("rank");
                if (object != null) {
                    infoText = infoText + "Rank: " + object + NEW_LINE;
                }
                object = resObj.get("country");
                if (object != null) {
                    infoText = infoText + "Country: " + object + NEW_LINE;
                }
                object = resObj.get("city");
                if (object != null) {
                    infoText = infoText + "City: " + object + NEW_LINE;
                }
                object = resObj.get("organization");
                if (object != null) {
                    infoText = infoText + "Organization: " + object + NEW_LINE;
                }
                object = resObj.get("firstName");
                String name = "#";
                if (object != null) {
                    name = (String)object;
                }
                object = resObj.get("lastName");
                if (object != null) {
                    if (name.equals("#")) {
                        name = (String)object;
                    }
                    name = name + " " + (String)object;
                }
                infoText = infoText + "Name: " + name + NEW_LINE;
                infoText += "</html>";
                allInfo.setText(infoText);
            }
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

    public void clear() {
        allInfo.setText("");
        jTextField.setText("");
        handleLabel.setText("");
        userAvatar.setIcon(defaultIcon);
    }

}
