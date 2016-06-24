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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class UserInfoWindow extends JFrame {

    public static final String NEW_LINE = "<br>";

    private boolean loaded = false;
    private Object lastLoadedObject = null;

    private String infoText = null;

    private JLabel allInfo;
    private JLabel infoLabel;
    private JLabel userAvatar;
    private JLabel handleLabel;
    private JButton okButton;
    private JButton backButton;
    private JButton skillsButton;
    private JTextField jTextField;
    private ImageIcon defaultIcon;

    HashSet<Character> russianLetters;

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
        russianLetters = new HashSet<>();
        for (char c = 'а'; c <= 'я'; c++) {
            russianLetters.add(c);
        }
        for (char c = 'А'; c <= 'Я'; c++) {
            russianLetters.add(c);
        }
    }

    private void addButtons() {
        addBackButton();
        addOkButton();
        addSkillsButton();
    }

    private void addSkillsButton() {
        skillsButton = new JButton("", new ImageIcon("Assets/top-skills.gif"));
        ImagePanel.removeBackround(skillsButton);
        skillsButton.setSize(327, 60);
        skillsButton.setLocation(360, 400);

        skillsButton.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!loaded || lastLoadedObject == null) {
                    JOptionPane.showMessageDialog(Main.userInfoWindow,  "No user profile loaded!", "Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    JSONObject resObj = (JSONObject) lastLoadedObject;
                    try {
                        infoText = infoText + "Top Skills: " + getTopSkills((String)resObj.get("handle")) + NEW_LINE;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    allInfo.setText(infoText + "</html>");
                }
            }
        });
        add(skillsButton);
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
        allInfo.setLocation(150, -30);
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
                boolean haveRussian = false;
                for (int i = 0; i < text.length(); i++) {
                    if (russianLetters.contains(text.charAt(i))) {
                        haveRussian = true;
                        break;
                    }
                }
                if (haveRussian) {
                    JOptionPane.showMessageDialog(Main.userInfoWindow,  "You may not use russian letters!", "Error!", JOptionPane.ERROR_MESSAGE);
                    jTextField.setText("");
                    return;
                }
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
                lastLoadedObject = resObj;
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
                infoText = null;
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
                allInfo.setText(infoText + "</html>");
            }
        }
        loaded = true;
    }

    private String getTopSkills(String handle) throws Exception {
        String result = "";
        HashMap<String, Integer> skills = new HashMap<>();
        String urlRequest = " http://codeforces.com/api/user.status?handle=" + handle + "&from=1&count=1000000";
        HTTPConnection connection = new HTTPConnection(urlRequest);
        ArrayList<String> arrayList = connection.makeRequest();
        JSONParser parser = new JSONParser();
        for (String s : arrayList) {
            JSONObject jsonObject = (JSONObject) parser.parse(s);
            JSONArray jsonArray = (JSONArray)jsonObject.get("result");
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject element = (JSONObject)jsonArray.get(j);
                if (element.get("verdict").equals("OK")) {
                    JSONObject problem = (JSONObject)element.get("problem");
                    JSONArray tags = (JSONArray)problem.get("tags");
                    for (int idx = 0; idx < tags.size(); idx++) {
                        String currentTag = (String)tags.get(idx);
                        int value = 0;
                        if (skills.containsKey(currentTag)) {
                            value = skills.get(currentTag);
                            skills.remove(currentTag);
                        }
                        value++;
                        skills.put(currentTag, value);
                    }
                }
            }
        }
        int maxValue = -1;
        String key = "#";
        for (String s : skills.keySet()) {
            int itsValue = skills.get(s);
            if (itsValue > maxValue) {
                maxValue = itsValue;
                key = s;
            }
        }
        if (maxValue != -1) {
            result = result + key;
            skills.remove(key);
        }
        maxValue = -1;
        key = "#";
        for (String s : skills.keySet()) {
            int itsValue = skills.get(s);
            if (itsValue > maxValue) {
                maxValue = itsValue;
                key = s;
            }
        }
        if (maxValue != -1) {
            result = result + " & " + key;
        }
        skills.clear();
        return result;
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
        loaded = false;
        infoText = null;
        lastLoadedObject = null;
        allInfo.setText("");
        jTextField.setText("");
        handleLabel.setText("");
        userAvatar.setIcon(defaultIcon);
    }

}
