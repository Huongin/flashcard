package service;


import Main.Main;
import constant.Regex;
import constant.UserRole;

import util.InputUtil;
import entity.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserService {

    private static final List<User> users = new ArrayList<>();

    private static final int MAX_LOGIN_TIMES = 5;

    //Admin mặc định
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "Admin123";



    public User login() {
        int loginCount = 1;
        User user = null;
        do {
            System.out.println("Nhập email (nhập 'exit' để thoát): ");
            String email = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(email)) {
                return null;
            }
            if (!email.matches(Regex.EMAIL_REGEX)) {
                System.out.println("Email không đúng định dạng vui lòng nhập lại");
                continue;
            }
            System.out.println("Nhập mật khẩu (nhập 'exit' để thoát): ");
            String password = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(password)) {
                return null;
            }
            if (!password.matches(Regex.PASSWORD_REGEX)) {
                System.out.println("Mật khẩu không đúng định dạng vui lòng nhập lại.");
                continue;
            }
            user = findUserByEmailAndPassword(email, password);
            if (user != null) {
                break;
            }
            loginCount++;
            if (loginCount == MAX_LOGIN_TIMES) {
                System.out.println("Bạn đã vượt quá số lần đăng nhập tối đa (5 lần), vui lòng thử lại sau");
                break;
            }
            System.out.println("Thông tin đăng nhập không chính xác, vui lòng thử lại: ");
        } while (true) ;
        return user;
    }
    
    //Lưu dữ liệu người dùng
    private void saveUserData(User user){
        for (int j = 0; j< users.size(); j++){
            if(users.get(j) == null){
                users.set(j,user);
                break;
            }
        }
    }

    //Tạo mới tài khoản người dùng
    public void createUser() {
        User user = creatUserInfo();
        System.out.println("Chọn quyền của user:");
        System.out.println("1.User");
        System.out.println("2.Admin");
        int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                "Chức năng là số dương từ 1 đến 2, Vui lòng nhập lại: ", 1, 2);
        user.setRole(choice == 1 ? UserRole.USER : UserRole.ADMIN);
        users.add(user);
        showUser(user);
        saveUserData(user);
    }

    // Đăng ký người dùng mới
    public User register() {
        User user = creatUserInfo();
        user.setRole(UserRole.USER);
        users.add(user);
        return user;
    }

    //Tìm kiếm người dùng bằng mail
    private User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    //Tìm kiếm người dùng bằng mail và mật khẩu
    private User findUserByEmailAndPassword(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    //Tìm kiếm người dùng bằng ID
    public static User findUserById(int idUser) {
        for (User user : users) {
            if (user.getId() == idUser) {
                return user;
            }
        }
        return null;
    }
    // Khai báo thông tin người dùng
    private User creatUserInfo() {
        String email;
        String password;
        //Nhập email
        while (true) {
            System.out.println("Nhập email (nhập 'exit' để thoát): ");
            email = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(email)) {
                return null;
            }
            if (!email.matches(Regex.EMAIL_REGEX)) {
                System.out.println("Email không đúng định dạng vui lòng nhập lại");
                continue;
            }
            boolean coTrungEmailKhong = false;
            for (User user1 : users) {
                if (email.equalsIgnoreCase(user1.getEmail())) {
                    System.out.println("Email đã tồn tại vui lòng nhập lại.");
                    coTrungEmailKhong = true;
                    break;
                }
            }
            if (!coTrungEmailKhong) {
                break;
            }
        }
        //Nhập mật khẩu
        while (true) {
            System.out.println("Nhập mật khẩu (nhập 'exit' để thoát): ");
            password = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(password)) {
                return null;
            }
            if (!password.matches(Regex.PASSWORD_REGEX)) {
                System.out.println("Mật khẩu không đúng định dạng vui lòng nhập lại.");
                continue;
            }
            break;
        }
        //Nhập họ tên
        String fullname;
        while (true) {
            System.out.println("Mời bạn nhập họ tên: ");
            fullname = new Scanner(System.in).nextLine();
            if (!fullname.matches(".*\\d.*") && !fullname.isEmpty()) {
                break;
            } else {
                System.out.println("Tên không hợp lệ, Vui lòng nhập lại.");
            }
        }
        //Nhập tuổi
        int age = -1;
        while (true) {
            try {
                System.out.println("Mời bạn nhập tuổi: ");
                age = new Scanner(System.in).nextInt();
                if (age < 0 || age > 120) {
                    System.out.println("Tuổi không hợp lệ. Vui lòng nhập lại.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.print("Số tuổi là số nguyên từ 1 đến 120, vui lòng nhập lại: ");
            }
        }
        //Nhập ngôn ngữ mẹ đẻ
        String motherTounge;
        System.out.print("Nhập ngôn ngữ mẹ đẻ của bạn: ");
        motherTounge = new Scanner(System.in).nextLine();

        User user = new User(email, password, fullname, age, motherTounge);
        users.add(user);
        return user;
    }

    //Tìm kiếm người dùng theo tên
    public void searchUserByName() {
        System.out.println("Mời bạn nhập tên của User: ");
        String name = new Scanner(System.in).nextLine();
        List<User> users1 = new ArrayList<>();

        for (User user : users) {
            if (user.getFullname() != null && user.getFullname().toLowerCase().contains(name.toLowerCase())) {
                users1.add(user);
            }
        }
        showUsers(users1);
    }

    //Hiển thị thông tin người dùng
    private void showUser(User user) {
        printHeader();
        showUserDetail(user);
    }

    private void showUsers(List<User> users1) {
        printHeader();
        for (User user : users1) {
            showUserDetail(user);
        }
    }

    private void printHeader() {
        System.out.printf("%-5s%-30s%-30s%-20s%-20s%-10s%-10s%n", "Id", "Fullname", "Email", "Age", "Role", "Mothertounge");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }

    private void showUserDetail(User user) {
        System.out.printf("%-5s%-30s%-30s%-20s%-20s%-10s%-10s%n",
                user.getId(),
                user.getFullname(),
                user.getEmail(),
                user.getAge(),
                user.getRole(),
                user.getMothertounge());
    }

    public User getLoggedInUser() {
        for (User userTemp : users) {
            if (userTemp.getId() == Main.LOGGED_IN_USER.getId()) {
                return userTemp;
            }
        }
        return null;
    }

    public void lockUserById() {
    }
}
