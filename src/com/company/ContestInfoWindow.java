package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class ContestInfoWindow extends JFrame {

    private boolean visited = false;
    private JButton okButton;
    private JButton backButton;
    private HashMap<String, String> contestsIdByName;

    public ContestInfoWindow() throws Exception {
        super("Codeforces Surfer Contest Info");
        setBounds(200, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage backgroundImage = ImageIO.read(new File("Assets/any-background.jpg"));
        setContentPane(new ImagePanel(backgroundImage));
        contestsIdByName = new HashMap<>();
        addButtons();
        addBoxWithContestList();
    }

    private void addBoxWithContestList() throws Exception {
        Vector comboBoxItems = new Vector();
        HTTPConnection httpConnection = new HTTPConnection("http://codeforces.com/api/contest.list?gym=false");
        ArrayList<String> result = httpConnection.makeRequest();
        JSONParser parser = new JSONParser();
        for (String resultString : result) {
            JSONObject parsedObject = (JSONObject) parser.parse(resultString);
            JSONArray resultArray = (JSONArray) parsedObject.get("result");
            if (resultArray != null) {
                for (int i = 0; i < resultArray.size(); i++) {
                    Object object = resultArray.get(i);
                    JSONObject arrayEntry = (JSONObject) object;
                    String phase = (String) arrayEntry.get("phase");
                    if (phase.equals("BEFORE")) {
                        continue;
                    } else if (phase.equals("FINISHED")) {
                        String contestName = (String)arrayEntry.get("name");
                        Long contestId = (Long)arrayEntry.get("id");
                        contestsIdByName.put(contestName, contestId.toString());
                        comboBoxItems.add(contestName);
                    }
                }
            }
        }
        final DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
        JComboBox comboBox = new JComboBox(model);
        comboBox.setLocation(0, 10);
        comboBox.setSize(300, 40);
        add(comboBox);

    }

    public void visit() {
        if (visited) {
            return;
        }
        visited = true;
        JOptionPane.showMessageDialog(this, "Choose contest and click OK!", "Information message!", JOptionPane.OK_OPTION);
    }

    public void clear() {

    }

    private void addButtons() {
        addOkButton();
        addBackButton();
    }

    private void addOkButton() {
        okButton = new JButton("", new ImageIcon("Assets/ok-button.gif"));
        ImagePanel.removeBackround(okButton);
        okButton.setSize(85, 60);
        okButton.setLocation(310, 0);
        add(okButton);
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
