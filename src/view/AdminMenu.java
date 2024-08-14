package view;

import entity.Card;
import entity.User;
import main.Main;
import service.*;
import util.InputUtil;

import java.util.InputMismatchException;
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
            System.out.println("--------MENU ADMIN MANAGEMENT--------");
            System.out.println("1.Quản lý danh sách người dùng");
            System.out.println("2.Quản lý thẻ học");
            System.out.println("3.Quản lý bộ thẻ học");
            System.out.println("4.Quản lý bài test");
            System.out.println("5.Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng: " ,
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
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng "  ,
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
                    int testId;
                    while (true) {
                        try {
                            System.out.println("Mời bạn nhập ID bài test cần tìm: ");
                            testId = new Scanner(System.in).nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị bạn vừa nhập phải là một số nguyên. Vui lòng nhập lại.");
                            continue;
                        }
                        break;
                    }
                    testDetailService.testResultsByIdTest(testId);
                    break;
                case 5:
                    int userId;
                    while (true) {
                        try {
                            System.out.println("Mời bạn nhập ID người dùng cần tìm kiếm kết quả: ");
                            userId = new Scanner(System.in).nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                            continue;
                        }
                        break;
                    }
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
            System.out.println("5. Gán người dùng vào bộ thẻ");
            System.out.println("6. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng "  ,
                    "Chức năng là số dương từ 1 tới 6, vui lòng nhập lại: ", 1, 6);
            switch (choice) {
                case 1:
                    deckService.createDeck();
                    break;
                case 2:
                    deckService.updateDeckById();
                    break;
                case 3:
                    int deleteId;
                    while (true){
                        try {
                            System.out.println("CẢNH BÁO! Khi xóa bộ thẻ các thẻ học trong bộ thẻ và lịch sử học tập sẽ bị xóa theo." +
                                    " Nếu đồng ý vui lòng nhập ID của bộ thẻ cần xóa: ");
                            deleteId = new Scanner(System.in).nextInt();
                        }catch (InputMismatchException e){
                            System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                            continue;
                        }
                        break;
                    }
                    deckService.deleteDeckById(deleteId);
                    break;
                case 4:
                    deckService.showCardDeckList();
                    break;
                case 5:
                    deckService.assignUserToDeck();
                    break;
                case 6:
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
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng " ,
                    "Chức năng là số dương từ 1 tới 6, vui lòng nhập lại: ", 1, 6);
            switch (choice) {
                case 1:
                    User user = Main.LOGGED_IN_USER;
                    cardService.createCard(user);
                    break;
                case 2:
                    cardService.updateCardInfo();
                case 3:
                    int cardId;
                    while (true) {
                        try {
                            System.out.println("Mời bạn nhập ID của thẻ học cần xóa: ");
                            cardId = new Scanner(System.in).nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                            continue;
                        }
                        break;
                    }
                    cardService.deleteCardById(cardId);
                    break;
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
         User user;
         int idUserLock;
        while (true){
            System.out.println("------------------ PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT------------------");
            System.out.println("------------------QUẢN LÝ DANH SÁCH NGƯỜI DÙNG------------------");
            System.out.println("1.Tìm kiếm người dùng theo tên ");
            System.out.println("2.Tạo mới tài khoản người dùng");
            System.out.println("3.Cập nhật thông tin người dùng ");
            System.out.println("4.Khóa hoạt động của người dùng");
            System.out.println("5.Mở khóa hoạt động của người dùng");
            System.out.println("6.Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng " ,
                    "Chức năng là số dương từ 1 đến 6, Vui lòng nhập lại: ", 1, 6);
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
                    while (true){
                        try {
                            System.out.println("Mời bạn nhập ID của tài khoản muốn khóa: ");
                            idUserLock = new Scanner(System.in).nextInt();
                        }catch (InputMismatchException e){
                            System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                            continue;
                        }
                        user = userService.findUserById(idUserLock);
                        if (user == null){
                            System.out.println("Không tìm thấy ID " + idUserLock + ". Vui lòng nhập lại: " );
                            continue;
                        }
                        break;
                    }
                    userService.lockUserById(idUserLock);
                    break;
                case 5:
                    while (true){
                        try {
                            System.out.println("Mời bạn nhập ID của tài khoản muốn mở khóa: ");
                            idUserLock = new Scanner(System.in).nextInt();
                        }catch (InputMismatchException e){
                            System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                            continue;
                        }
                        user = userService.findUserById(idUserLock);
                        if (user == null){
                            System.out.println("Không tìm thấy ID " + idUserLock + ". Vui lòng nhập lại: " );
                            continue;
                        }
                        break;
                    }
                    userService.unlockedUserById(idUserLock);
                    break;
                case 6:
                    return;
            }
        }
    }
}
