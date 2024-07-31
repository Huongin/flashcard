package Main;

import entity.User;
import view.MainMenu;

public class Main {
    public static User LOGGED_IN_USER = null;

    public static void main(String[] args) {
        final MainMenu menuService = new MainMenu();
        menuService.menu();
    }
}
