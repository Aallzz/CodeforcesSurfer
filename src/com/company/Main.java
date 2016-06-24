package com.company;

import java.awt.*;

public class Main {

    public static MainMenu mainMenu;
    public static UserInfoWindow userInfoWindow;

    public static void main(String[] args) throws Exception {
        mainMenu = new MainMenu();
        userInfoWindow = new UserInfoWindow();
        mainMenu.setVisible(true);
    }

    public static Color getColorByRating(int rating) {
        if (rating <= 1199) {
            return Color.DARK_GRAY;
        }
        if (rating <= 1399) {
            return Color.GREEN;
        }
        if (rating <= 1599) {
            return Color.CYAN;
        }
        if (rating <= 1899) {
            return Color.BLUE;
        }
        if (rating <= 2199) {
            return Color.MAGENTA;
        }
        if (rating <= 2399) {
            return Color.ORANGE;
        }
        return Color.RED;
    }
}
