package view;

import entity.Card;
import entity.User;
import main.Main;
import service.*;
import util.InputUtil;

import java.util.Scanner;


public class AdminMenu {

    private final UserService userService;
    private final CardService cardService;
    private final DeckService deckService;
    private final TestService testService;
    private final TestDetailService testDetailService;

    public AdminMenu(UserService userService, CardService cardService, DeckService deckService,TestService testService, TestDetailService testDetailService) {
        this.userService = userService;
        this.cardService = cardService;
        this.deckService = deckService;
        this.testService = testService;
        this.testDetailService = testDetailService;
    }

    public void  menu() {
        while (true) {
            System.out.print("--------MENU ADMIN MANAGEMENT--------");
            System.out.println("1.Quản lý danh sách người dùng");
            System.out.println("2.Quản lý thẻ học");
            System.out.println("3.Quản lý bộ thẻ học");
            System.out.println("4.Quản lý bài test");
            System.out.println("5.Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng: ",
                    "Chức năng là số dương từ 1 đến 5, vui lòng nhập lại: ", 1, 5);
            switch (choice) {
                case 1:
                    userManagementMenu();
                    break;
                case 2:
                    flashcardManagementMenu();
                    break;
                case 3:
                    deckManagementMenu();
                    break;
                case 4:
                    testManagementMenu();
                    break;
                case 5:
                    return;
            }
        }
    }

    private void testManagementMenu() {
        while (true){
            System.out.println("------------------- PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT-------------------");
            System.out.println("-----------------------QUẢN LÝ BÀI TEST-----------------------");
            System.out.println("1. Tạo bài test mới");
            System.out.println("2. Danh sách bài test");
            System.out.println("3. Chỉnh sửa bài test");
            System.out.println("4. Xem kết quả kiểm tra theo ID bài test");
            System.out.println("5. Xem kết quả kiểm tra theo tên User");
            System.out.println("6. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng" ,
                    "Chức năng là số dương từ 1 tới 6, vui lòng nhập lại: ", 1, 6);
            switch (choice) {
                case 1:
                    testService.createNewTest();
                    break;
                case 2:
                    testService.showTestList();
                    break;
                case 3:
                    testService.updateTestById();
                    break;
                case 4:
                    System.out.println("Nhập ID bài test cần tìm: ");
                    int testId = new Scanner(System.in).nextInt();
                    testDetailService.testResultsByIdTest(testId);
                    break;
                case 5:
                    System.out.println("Nhập ID người dùng cần tìm kiếm kết quả: ");
                    int userId = new Scanner(System.in).nextInt();
                    testDetailService.testResultsByUserId(userId);
                    break;
                case 6:
                    return;
            }
        }
    }

    public void deckManagementMenu() {
        while (true) {
            System.out.println("------------------- PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT-------------------");
            System.out.println("-----------------------QUẢN LÝ DANH MỤC BỘ THẺ HỌC-----------------------");
            System.out.println("1. Thêm mới danh mục bộ thẻ");
            System.out.println("2. Cập nhật thông tin danh mục");
            System.out.println("3. Xóa danh mục");
            System.out.println("4. Xem danh sách các danh mục trong bộ thẻ đang có");
            System.out.println("5. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng" ,
                    "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 5);
            switch (choice) {
                case 1:
                    deckService.createDeck();
                    break;
                case 2:
                    deckService.updateDeckById();
                    break;
                case 3:
                    System.out.println("CẢNH BÁO! Khi xóa bộ thẻ các thẻ học trong bộ thẻ và lịch sử học tập sẽ bị xóa theo." +
                            "Nếu đồng ý vui lòng nhập ID bộ thẻ cần xóa");
                    int deleteId = new Scanner(System.in).nextInt();
                    deckService.deleteDeckById(deleteId);
                    break;
                case 4:
                    deckService.showCardDeckList();
                    break;
                case 5:
                    return;
            }
        }
    }

    private void  flashcardManagementMenu() {
        while (true) {
            System.out.println("------------------ PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT------------------");
            System.out.println("------------------QUẢN LÝ DANH SÁCH THẺ HỌC------------------");
            System.out.println("1. Thêm mới thẻ học");
            System.out.println("2. Cập nhật thông tin thẻ học");
            System.out.println("3. Xóa thẻ học");
            System.out.println("4. Tìm kiếm thẻ học theo từ");
            System.out.println("5. Tìm kiếm thẻ học theo chủ đề");
            System.out.println("6. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 6, vui lòng nhập lại: ", 1, 6);
            switch (choice) {
                case 1:
                    User user = Main.LOGGED_IN_USER;
                    cardService.createCard(user);
                    break;
                case 2:
                    cardService.updateCardInfo();
                case 3:
                    System.out.println("Mời bạn nhập ID của thẻ học cần xóa: ");
                    int cardId = new Scanner(System.in).nextInt();
                    cardService.deleteCardById(cardId);
                case 4:
                    cardService.findCardByWord();
                    break;
                case 5:
                    cardService.findCardByNameTopic();
                    break;
                case 6:
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
            System.out.println("5.Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng" ,
                    "Chức năng là số dương từ 1 đến 5, Vui lòng nhập lại: ", 1, 5);
            switch (choice) {
                case 1:
                    userService.searchUserByName();
                    break;
                case 2:
                    userService.creatUserInfo();
                    break;
                case 3:
                    userService.updateUserInformationByAdmin();
                case 4:
                    System.out.println("Mời bạn nhập Email của tài khoản cần khóa: ");
                    String lockEmail = new Scanner(System.in).nextLine();

                    userService.lockUserByEmail(lockEmail);
                case 5:
                    return;
            }
        }
    }
}
