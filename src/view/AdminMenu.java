package view;

import Main.Main;
import entity.Card;
import entity.User;
import service.CardService;
import service.UserService;
import util.InputUtil;



public class AdminMenu {

    private final UserService userService;
    private final CardService cardService;

    public AdminMenu(UserService userService, CardService cardService) {
        this.userService = userService;
        this.cardService = cardService;
    }

    public void  menu() {
        while (true) {
            System.out.print("--------MENU ADMIN MANAGEMENT--------");
            System.out.println("1.Quản lý danh sách người dùng");
            System.out.println("2.Quản lý thẻ học");
            System.out.println("3.Quản lý bộ thẻ học");
            System.out.println("4.Quản lý bài Test ");
            System.out.println("5.Quản lý kết quả học");
            System.out.println("6.Thoát");
            int choise = InputUtil.chooseOption("Xin mời chọn chức năng: ",
                    "Chức năng là số dương từ 1 đến 6, vui lòng nhập lại: ", 1, 6);
            switch (choise) {
                case 1:
                    userManagementMenu();
                    break;
                case 2:
                    flashcardMenu();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    return;
            }
        }
    }

    private void flashcardMenu() {
        while (true) {
            System.out.println("------------------ PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT------------------");
            System.out.println("------------------QUẢN LÝ DANH SÁCH THẺ HỌC------------------");
            System.out.println("1. Thêm mới thẻ học");
            System.out.println("2. Cập nhật thông tin thẻ học");
            System.out.println("3. Tìm kiếm thẻ học theo từ");
            System.out.println("4. Danh sách các thẻ học đang có");
            System.out.println("5. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 5);
            switch (choice) {
                case 1:
                    cardService.createCard();
                    break;
                case 2:
                    cardService.updateCardInfo();
                case 3:
                    cardService.searchCardByWord();
                    break;
                case 4:
                    cardService.listOfFlashcards();
                    break;
                case 5:
                    return;
            }
        }
    }

    public void userManagementMenu() {
        while (true){
            System.out.println("------------------ PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT------------------");
            System.out.println("------------------QUẢN LÝ DANH SÁCH NGƯỜI DÙNG------------------");
            System.out.println("1.Tìm kiếm người dùng theo tên ");
            System.out.println("2.Tạo mới tài khoản người dùng");
            System.out.println("3.Cập nhật thông tin người dùng ");
            System.out.println("4.Khóa hoạt động người dùng");
            System.out.println("6.Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng" ,
                    "Chức năng là số dương từ 1 đến 5, Vui lòng nhập lại: ", 1, 5);
            switch (choice) {
                case 1:
                    userService.searchUserByName();
                    break;
                case 2:
                    userService.createUser();
                    break;
                case 3:
                    userService.updateUserInformation(Main.LOGGED_IN_USER.getId());
                case 4:
                    userService.lockUserById();
                case 5:
                    return;
            }
        }
    }
}
