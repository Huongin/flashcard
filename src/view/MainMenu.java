package view;

import main.Main;
import constant.UserRole;
import entity.User;
import service.*;
import util.InputUtil;

public class MainMenu {

    //Khai báo tất cả service
    private final UserService userService = new UserService();
    private final DeckService deckService = new DeckService(userService);
    private final CardService cardService = new CardService(userService, deckService);
    private final StudyService studyService = new StudyService(userService, deckService, cardService);
    private final TestService testService = new TestService(userService,deckService,cardService);
    private final TestDetailService testDetailService = new TestDetailService(userService,deckService,cardService,testService);


    //Khai báo tất cả các menu của các role khác nhau (Đi kèm các service cần thiết)
    private final UserMenu userMenu = new UserMenu(userService, deckService, cardService, studyService, testService, testDetailService);
    private final AdminMenu adminMenu = new AdminMenu(userService, cardService, deckService, studyService, testService, testDetailService );

    public void menu() {
        while (true) {
            System.out.println("==================================================================");
            System.out.println("------- PHẦN MỀM QUẢN LÝ HỌC TỪ VỰNG TIẾNG NHẬT --------");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký");
            System.out.println("3. Thoát");
            int choice = InputUtil.chooseOption( "Xin mời chọn chức năng  ",
                    "Chức năng là số dương từ 1 tới 3, vui lòng nhập lại: ", 1, 3);
            switch (choice) {
                case 1:
                    User loggedInUser = userService.login();
                    if (loggedInUser == null) {
                        System.out.println("Đăng nhập thất bại!!!");
                        break;
                    }
                    Main.LOGGED_IN_USER = loggedInUser;
                    if (loggedInUser.getRole().equals(UserRole.USER)) {
                        userMenu.menu();
                        break;
                    }
                    adminMenu.menu();
                    break;
                case 2:
                    User registeredUser = userService.register();
                    if (registeredUser == null) {
                        System.out.println("Dừng đăng ký tài khoản!!!");
                        break;
                    }
                    System.out.println("Đăng ký tài khoản thành công, vui lòng đăng nhập để tiếp tục sử dụng phần mềm.");
                    break;
                case 3:
                    return;
            }
        }
    }
    public void initializeData(){
        userService.setUsers();
        userService.createDefaultAdminUser();
        userService.findCurrentAutoId();

        deckService.setDecks();
        deckService.findCurrentAutoId();

        cardService.setCards();
        cardService.findCurrentAutoId();

        studyService.setStudy();

        testService.setTests();
        testService.findCurrentAutoId();

        testDetailService.setTestDetails();
    }
}