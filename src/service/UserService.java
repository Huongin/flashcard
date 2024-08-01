package service;


import Main.Main;
import constant.Regex;
import constant.UserRole;
import util.InputUtil;
import entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private static final String USER_DATA = "users.dat";
    private static int AUTO_ID;
    private static final List<User> users = new ArrayList<>();
    private final List<Integer> lockedUserIds = new ArrayList<>();
    private static final int MAX_LOGIN_TIMES = 5;

    //Admin mặc định
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "Admin123";

    public UserService() {
        loadUserData();
        findCurrentAutoId();
        createDefaulAdminUser();
    }

    //Tạo Admin mặc định
    private void createDefaulAdminUser() {
        if (users.isEmpty()) {
            createAdmin();
            return;
        }
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(ADMIN_EMAIL)
                    && user.getPassword().equalsIgnoreCase(ADMIN_PASSWORD)) {
                return;
            }
        }
        createAdmin();
    }

    private void createAdmin() {
        User user = new User(ADMIN_EMAIL, ADMIN_PASSWORD, UserRole.ADMIN);
        user.setId(0);
        users.add(user);
        saveUserData();
    }

    //Tìm Id tự động
    private void findCurrentAutoId() {
        int maxId = -1;
        for (User user : users) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        AUTO_ID = maxId + 1;
    }


    private void loadUserData() {
        try (FileInputStream fs = new FileInputStream(USER_DATA);
             ObjectInputStream ois = new ObjectInputStream(fs)) {
             List<User> loadedUsers = (List<User>)ois.readObject();
            if (loadedUsers != null) {
                users.addAll(loadedUsers);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Không thể đọc dữ liệu từ file hoặc file không tồn tại.");
        }
    }

    //Lưu dữ liệu người dùng
    private void saveUserData() {
        try {
            FileOutputStream fos = new FileOutputStream(USER_DATA);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(users);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu dữ liệu người dùng", e);
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
        saveUserData();
    }

    // Đăng ký người dùng mới
    public User register() {
        String email;
        while (true) {
            System.out.println("Nhập email (nhập 'exit' để thoát): ");
            email = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(email)) {
                return null;
            }

            User existedUser = findUserByEmail(email);
            if (existedUser != null) {
                System.out.println("Email đã tồn tại trong hệ thống, vui lòng nhập lại: ");
                continue;
            }
            break;
        }
        
        System.out.println("Nhập mật khẩu (nhập 'exit' để thoát): ");
        String password = new Scanner(System.in).nextLine();
        if (InputUtil.exitInput(password)) {
            return null;
        }
        
        User user = creatUserInfo();
        user.setRole(UserRole.USER);
        users.add(user);
        showUser(user);
        saveUserData();

        return user;
    }

    private User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    private User creatUserInfo() {
        String email;
        String password;
        //Nhập email
        while (true) {
            System.out.println("Mời bạn nhập email: ");
            email = new Scanner(System.in).nextLine();
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
            System.out.println("Mời bạn nhập mật khẩu (8 đến 12 ký tự cả chữ thường, chữ hoa và số)");
            password = new Scanner(System.in).nextLine();
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
        while (true) {
            System.out.print("Nhập ngôn ngữ mẹ đẻ của bạn: ");
            motherTounge = new Scanner(System.in).nextLine();
            if (motherTounge.isEmpty()) {
                System.out.println("ngôn ngữ mẹ đẻ không được bỏ trống. Vui lòng nhập lại.");
                continue;
            }
            break;
        }
        return new User(AUTO_ID++, email, password, fullname, age, motherTounge);
    }

    public User login() {
        int loginCount = 1;
        User user;
        do {
            System.out.println("Nhập email (nhập 'exit' để thoát): ");
            String email = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(email)) {
                return null;
            }

            System.out.println("Nhập mật khẩu (nhập 'exit' để thoát): ");
            String password = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(password)) {
                return null;
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
        } while (true);
        return user;
    }

    private User findUserByEmailAndPassword(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static User findUserById(int idUser) {
        for (User user : users) {
            if (user.getId() == idUser) {
                return user;
            }
        }
        return null;
    }

    public void updateUserInformation(int idUserUpdate) {
        User user = findUserById(idUserUpdate);
        System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa: ");
        System.out.println("1. Email");
        System.out.println("2. Password");
        System.out.println("3. Tên");
        System.out.println("4. Tuổi");
        System.out.println("5. Ngôn ngữ mẹ đẻ");
        System.out.println("6. Thoát");
        int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                "Chức năng là số dương từ 1 đến 6, Vui lòng nhập lại: ", 1, 6);
        switch (choice) {
            case 1:
                String newEmail;
                while (true) {
                    System.out.println("Mời bạn nhập email: ");
                    newEmail = new Scanner(System.in).nextLine();
                    if (!newEmail.matches(Regex.EMAIL_REGEX)) {
                        System.out.println("Email không đúng định dạng vui lòng nhập lại");
                        continue;
                    }
                    boolean coTrungEmailKhong = false;
                    for (User user1 : users) {
                        if (newEmail.equalsIgnoreCase(user1.getEmail())) {
                            System.out.println("Email đã tồn tại vui lòng nhập lại.");
                            coTrungEmailKhong = true;
                            break;
                        }
                    }
                    if (coTrungEmailKhong == false) {
                        break;
                    }
                }
                user.setEmail(newEmail);
                break;
            case 2:
                String newPassword;
                while (true) {
                    System.out.println("Mời bạn nhập mật khẩu (8 đến 12 ký tự cả chữ thường, chữ hoa và số)");
                    newPassword = new Scanner(System.in).nextLine();
                    if (!newPassword.matches(Regex.PASSWORD_REGEX)) {
                        System.out.println("Mật khẩu không đúng định dạng vui lòng nhập lại.");
                        continue;
                    }
                    break;
                }
                user.setPassword(newPassword);
                break;
            case 3:
                System.out.println("Mời bạn nhập tên mới: ");
                String newName = new Scanner(System.in).nextLine();
                user.setFullname(newName);
                break;
            case 4:
                int newAge;
                while (true) {
                    try {
                        System.out.println("Mời bạn nhập tuổi: ");
                        newAge = new Scanner(System.in).nextInt();
                        if (newAge < 0 || newAge > 120) {
                            System.out.println("Tuổi không hợp lệ. Vui lòng nhập lại.");
                            continue;
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.print("Số tuổi là số nguyên từ 1 đến 120, vui lòng nhập lại: ");
                    }
                }
                user.setAge(newAge);
                break;
            case 5:
                String newMotherTounge;
                System.out.print("Nhập lại ngôn ngữ mẹ đẻ của bạn: ");
                newMotherTounge = new Scanner(System.in).nextLine();
                user.setMothertounge(newMotherTounge);
                break;
            case 6:
                return;
        }
        showUser(user);
        saveUserData();
        System.out.println("Thông tin người dùng đã được cập nhật");
    }
    public void lockUserById(int idUserLock) {
        for (int i = 0; i < users.size() ; i++) {
            if (users.get(i).getId() == idUserLock){
                lockedUserIds.add(idUserLock);
                System.out.println("User có ID " + idUserLock + " đã được khóa.");
                saveUserData();
                return;
            }
        }
        System.out.println("id vừa nhập đã bị khóa!");
    }

    public boolean isUserLocked(int idUser){
        return lockedUserIds.contains(idUser);
    }

    public User getLoggedInUser() {
        for (User userTemp : users) {
            if (userTemp.getId() == Main.LOGGED_IN_USER.getId()) {
                return userTemp;
            }
        }
        return null;
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
}
