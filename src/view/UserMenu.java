package view;

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
            System.out.println("1. Xem danh sách thẻ học");
            System.out.println("2. Tạo thẻ học cá nhân");
            System.out.println("3. Xóa thẻ học cá nhân");
            System.out.println("4. Tra từ vựng");
            System.out.println("5. Kiểm tra từ vựng");
            System.out.println("6. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 6, vui lòng nhập lại: ", 1, 6);
            switch (choice) {
                case 1:
                    studyCardList();
                    break;
                case 2:
                    creatPersonalCard();
                    break;
                case 3:
                    deletePersonalCard();
                    break;
                case 4:
                    findCard();
                    break;
                case 5:
                    testWithCard();
                    break;
                case 6:
                    return;
            }
        }
    }

    private void testWithCard() {
    }

    private void findCard() {
    }

    private void deletePersonalCard() {
    }

    private void creatPersonalCard() {
    }

    private void studyCardList() {
        while (true) {
            System.out.println("------------------- PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT-------------------");
            System.out.println("-----------------------DANH SÁCH THẺ HỌC -----------------------");
            System.out.println("1. Danh sách thẻ học chung");
            System.out.println("2. Danh sách thẻ học cá nhân");
            System.out.println("3. Danh sách thẻ đã học");
            System.out.println("4. Danh sách thẻ chưa học");
            System.out.println("5. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 5);
            switch (choice) {
                case 1:
                    studyService.studyCardList();
                    break;
                case 2:
                    studyService.personalCardList();
                    break;
                case 3:
                    studyService.studiedCards();
                    break;
                case 4:
                    studyService.IncomingCards();
                    break;
                case 5:
                    break;
            }
        }
    }
}
