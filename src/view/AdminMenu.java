package src.view;

import src.util.InputUtil;

public class AdminMenu {
    public void  menu(){
        while (true) {
            System.out.print("--------PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT--------");
            System.out.println("1.Quản lý danh sách người dùng");
            System.out.println("2.Quản lý thẻ học");
            System.out.println("3.Quản lý bộ thẻ học");
            System.out.println("4.Quản lý bài học ");
            System.out.println("5.Quản lý bài kiểm tra");
            System.out.println("6.Thống kê kết quả bài kiểm tra");
            System.out.println("7.Thoát");
            int choise = InputUtil.chooseOption("Xin mời chọn chức năng: ",
                    "Chức năng là số dương từ 1 đến 7, vui lòng nhập lại: ", 1, 7);
            switch (choise) {
                case 1:
                    userManagementMenu();
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

    private void userManagementMenu() {
        while (true){
            System.out.println("--------PHẦN MỀM HỌC TỪ VỰNG TIẾNG NHẬT--------");
            System.out.println("----------QUẢN LÝ DANH SÁCH NGƯỜI DÙNG----------");
            System.out.println("");
        }
    }

}
