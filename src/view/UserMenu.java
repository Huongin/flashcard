package view;

import service.UserService;
import util.InputUtil;

public class UserMenu {

    private final UserService userService;

    public UserMenu(UserService userService){
        this.userService = userService;
    }
    public void menu() {
        while (true) {
            System.out.println("-------PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT-------");
            System.out.println("1. Danh sách thẻ học mới ");
            System.out.println("2. Tra từ vựng");
            System.out.println("3. Học với thẻ học");
            System.out.println("4. Test từ vựng");
            System.out.println("5. Tự tạo thẻ học");
            System.out.println("6. Xem lại danh sách từ đã học");
            System.out.println("7. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 7, vui lòng nhập lại: ", 1, 7);
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    return;
            }
        }
    }
}
