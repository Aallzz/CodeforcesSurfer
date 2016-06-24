package com.company;

public class Main {

    public static MainMenu mainMenu;
    public static UserInfoWindow userInfoWindow;

    public static void main(String[] args) throws Exception {
        mainMenu = new MainMenu();
        userInfoWindow = new UserInfoWindow();
        mainMenu.setVisible(true);
    }
}
