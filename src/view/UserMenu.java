package view;

import Main.Main;
import service.CardService;
import service.DeckService;
import service.StudyService;
import service.UserService;
import util.InputUtil;

public class UserMenu {

    private final UserService userService;
    private final DeckService deckService;
    private final CardService cardService;
    private final StudyService studyService;

    public UserMenu(UserService userService, DeckService deckService, CardService cardService, StudyService studyService) {
        this.userService = userService;
        this.deckService = deckService;
        this.cardService = cardService;
        this.studyService = studyService;
    }

    public void menu() {
        while (true) {
            System.out.println("-------PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT-------");
            System.out.println("1. Học cùng thẻ học chung");
            System.out.println("2. Quản lý thẻ học cá nhân");
            System.out.println("3. Tra từ vựng");
            System.out.println("4. Test");
            System.out.println("5. Thống kê kết quả học tập");
            System.out.println("6. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 6, vui lòng nhập lại: ", 1, 6);
            switch (choice) {
                case 1:
                    studyWithCard();
                    break;
                case 2:
                    PersonalCardManagementMenu();
                    break;
                case 3:
                    //findCard();
                    break;
                case 4:
                    //test();
                    break;
                case 5:
                    //showLearningStatistics();
                    break;
                case 6:
                    return;
            }
        }
    }

    private void PersonalCardManagementMenu() {
        while (true) {
            System.out.println("------------------- PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT-------------------");
            System.out.println("-----------------------QUẢN LÝ THẺ HỌC CÁ NHÂN -----------------------");
            System.out.println("1. Tạo bộ thẻ cá nhân");
            System.out.println("2. Cập nhật thông tin bộ thẻ ");
            System.out.println("3. Xóa bộ thẻ");
            System.out.println("4. Tạo thẻ học cá nhân");
            System.out.println("5. Cập nhật thông tin thẻ học ");
            System.out.println("6. Xóa bộ thẻ");
            System.out.println("7. Học cùng thẻ học cá nhân");
            System.out.println("8. Thoát  ");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 8, vui lòng nhập lại: ", 1, 8);
            switch (choice) {
                case 1:
                    deckService.createDeck();
                    break;
                case 2:
                    deckService.updateDeckById();
                    break;
                case 3:
                    deckService.deleteDeckById(Main.LOGGED_IN_USER.getId());
                    break;
                case 4:
                    cardService.createCard();
                    break;
                case 5:
                    cardService.updateCardInfo();
                    break;
                case 6:
                    cardService.deleteCardById(Main.LOGGED_IN_USER.getId());
                    break;
                case 7:
                    studyService.studyWithPersonalCards();
                    break;
                case 8:
                    break;
            }
        }
    }


    private void studyWithCard() {
        while (true) {
            System.out.println("------------------- PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT-------------------");
            System.out.println("-----------------------DANH SÁCH THẺ HỌC -----------------------");
            System.out.println("1. Danh sách thẻ học chung");
            System.out.println("2. Danh sách thẻ đã học");
            System.out.println("3. Danh sách thẻ chưa học");
            System.out.println("4. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 4, vui lòng nhập lại: ", 1, 4);
            switch (choice) {
                case 1:
                    studyService.studyWithPublicCards();
                    break;
                case 2:
                    studyService.studiedCards();
                    break;
                case 3:
                    studyService.IncomingCards();
                    break;
                case 4:
                    return;
            }
        }
    }
}
