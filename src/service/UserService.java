package service;


import constant.Status;
import main.Main;
import constant.Regex;
import constant.UserRole;

import util.FileUtil;
import util.InputUtil;
import entity.User;

import java.util.*;

public class UserService {

    private final FileUtil<User> fileUtil = new FileUtil<>();
    private static final String USER_FILE = "users.json";
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "Admin123";
    private static int AUTO_ID;
    private List<User> users;

    private static final int MAX_LOGIN_TIMES = 5;


    public void setUsers(){
        List<User> userList = fileUtil.readDataFromFile(USER_FILE, User[].class);
        users = userList != null ? userList : new ArrayList<>();
    }

    //Lưu dữ liệu vào File
    private void saveUserData(){
        fileUtil.writeDataToFile(users,USER_FILE);
    }

    public void findCurrentAutoId(){
        int maxId = -1;
        for (User user : users){
            if (user.getId() > maxId){
                maxId = user.getId();
            }
        }
        AUTO_ID = maxId + 1;
    }

    public User login() {
        int loginCount = 1;
        User user = null;
        do {
            System.out.println("Mời bạn nhập Email (nhập 'exit' để thoát): ");
            String email = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(email)) {
                return null;
            }
            if (!email.matches(Regex.EMAIL_REGEX)) {
                System.out.println("Email không đúng định dạng vui lòng nhập lại");
                continue;
            }
            System.out.println("Mời bạn nhập mật khẩu (nhập 'exit' để thoát): ");
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
                if(user.getStatus() == Status.INACTIVE){
                    System.out.println("Tài khoản này đã bị khóa");
                    user = null;
                }else {
                    break;
                }
            }
            loginCount++;
            if (loginCount == MAX_LOGIN_TIMES) {
                System.out.println("Bạn đã vượt quá số lần đăng nhập tối đa (5 lần), vui lòng thử lại sau");
                break;
            }
            System.out.println("Thông tin đăng nhập không chính xác.Đăng nhập thất bại, vui lòng thử lại.");
        } while (true) ;
        return user;
    }

    //Tìm kiếm người dùng bằng mail
    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail() != null &&
                    user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    //Tìm kiếm người dùng bằng mail và mật khẩu
    public User findUserByEmailAndPassword(String email, String password) {
        for (User user : users) {
            if (user.getEmail() != null && user.getPassword() != null && user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    //Tìm kiếm người dùng bằng ID
    public User findUserById(int idUser) {
        for (User user : users) {
            if (user.getId() == idUser) {
                return user;
            }
        }
        return null;
    }

    //Tạo mới tài khoản cho Admin
    public void createUserForAdmin() {
        User user = creatUserInfo();
        System.out.println("Chọn quyền của user:");
        System.out.println("1.User");
        System.out.println("2.Admin");
        int choice;
        while (true) {
            choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 đến 2, Vui lòng nhập lại: ", 1, 2);
            if (choice == 1 || choice == 2) {
                break;
            }
            System.out.println("Vui lòng chọn lại chức năng hợp lệ");
        }
        user.setRole(choice == 1 ? UserRole.USER : UserRole.ADMIN);
        users.add(user);
        showUser(user);
        saveUserData();
    }

    //Hàm tạo user admin
    public  void createDefaultAdminUser(){
        if (users == null || users.isEmpty()) {
            createAdmin();
            return;
        }
        for (User user : users){
            if(user.getEmail() != null && user.getPassword() != null &&
            user.getEmail().equalsIgnoreCase(ADMIN_EMAIL)
                    && user.getPassword().equalsIgnoreCase(ADMIN_PASSWORD)){
                return;
            }
        }
        createAdmin();
    }

    private void createAdmin() {
        User user = new User(ADMIN_EMAIL,ADMIN_PASSWORD,UserRole.ADMIN);
        user.setId(0);
        users.add(user);
        saveUserData();
    }

    // Đăng ký người dùng mới
    public User register() {
        User user = creatUserInfo();
        user.setRole(UserRole.USER);
        users.add(user);
        saveUserData();
        return user;
    }

    // Khai báo thông tin người dùng
    public User creatUserInfo() {
        String email;
        String password;
        //Nhập email
        while (true) {
            System.out.println("Mời bạn nhập email (nhập 'exit' để thoát): ");
            email = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(email)) {
                return null;
            }
            if (!email.matches(Regex.EMAIL_REGEX)) {
                System.out.println("Email không đúng định dạng vui lòng nhập lại");
                continue;
            }
            User findUser = findUserByEmail(email);
            if (findUser != null) {
                System.out.println("Email đã tồn tại trong hệ thống, vui lòng nhập lại: ");
                continue;
            }
            break;
        }

        //Nhập mật khẩu
        while (true) {
            System.out.println("Vui lòng nhập mật khẩu (8 -> 12 ký tự cả chữ thường, chữ hoa, số (nhập 'exit' để thoát): ");
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
        System.out.println("Mời bạn nhập ngôn ngữ mẹ đẻ: ");
        motherTounge = new Scanner(System.in).nextLine();

        User user = new User(AUTO_ID++, email, password, fullname, age, motherTounge);
        users.add(user);
        saveUserData();
        return user;
    }

    //Tìm kiếm người dùng theo tên
    public void searchUserByName() {
        System.out.println("Mời bạn nhập tên của User: ");
        String name = new Scanner(System.in).nextLine();
        for (User user : users) {
            if (user.getFullname() != null && user.getFullname().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(user);
            }
            showUser(user);
        }
    }

    //Hiển thị thông tin người dùng
    private void showUser(User user) {
        printHeader();
        showUserDetail(user);
    }

    public void showUsers(List<User> users1) {
        printHeader();
        for (User user : users1) {
            showUserDetail(user);
        }
    }

    public void printHeader() {
        System.out.printf("%-5s%-30s%-30s%-20s%-10s%-10s%n", "Id", "Fullname", "Email", "Age", "Role", "Mothertounge");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }

    public void showUserDetail(User user) {
        System.out.printf("%-5s%-30s%-30s%-20s%-10s%-10s%n",
                user.getId(),
                user.getFullname(),
                user.getEmail(),
                user.getAge(),
                user.getRole(),
                user.getMothertounge());
    }

    //Câp nhật, sửa đổi thông tin
    public void updateUserInformation(int idUserUpdate) {
        User user = findUserById(idUserUpdate);
        System.out.println("Mời bạn nhập thông tin muốn chỉnh sửa: ");
        System.out.println("1.Email");
        System.out.println("2.Password");
        System.out.println("3.Tên");
        System.out.println("4.Tuổi");
        System.out.println("5.Ngôn ngữ mẹ đẻ");
        System.out.println("6.Thoát");
        int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                "Chức năng là số dương từ 1 đến 6, Vui lòng nhập lại: ", 1, 6);
        switch (choice){
            case 1:
                String newEmail;
                while (true){
                    System.out.println("Mời bạn nhập email mới: ");
                    newEmail = new Scanner(System.in).nextLine();
                    if (!newEmail.matches(Regex.EMAIL_REGEX)) {
                        System.out.println("Email không đúng định dạng vui lòng nhập lại");
                        continue;
                    }
                    boolean coTrungEmailKhong = false;
                    for (User user1 : users) {
                        if (newEmail.equalsIgnoreCase(user1.getEmail()) && user1.getId() != user.getId()) {
                            System.out.println("Email đã tồn tại vui lòng nhập lại");
                            coTrungEmailKhong = true;
                            break;
                        }
                    }
                    if (!coTrungEmailKhong) {
                        break;
                    }
                }
                user.setEmail(newEmail);
                break;
            case 2:
                String newPassword;
                while (true) {
                    System.out.println("Mời bạn nhập password mới (8 -> 12 ký tự cả chữ thường, chữ hoa và cả số)");
                    newPassword = new Scanner(System.in).nextLine();
                    if (!newPassword.matches(Regex.PASSWORD_REGEX)) {
                        System.out.println("Password không đúng định dạng vui lòng nhập lại ");
                        continue;
                    }
                    break;
                }
                user.setPassword(newPassword);
                break;
            case 3:
                String newName;
                while (true) {
                    System.out.println("Mời bạn nhập họ tên mới: ");
                    newName = new Scanner(System.in).nextLine();
                    if (!newName.matches(".*\\d.*") && !newName.isEmpty()) {
                        break;
                    } else {
                        System.out.println("Tên không hợp lệ, Vui lòng nhập lại.");
                    }
                }
                user.setFullname(newName);
                break;
            case 4:
                int newAge = - 1;
                while (true) {
                    try {
                        System.out.println("Mời bạn nhập tuổi mới: ");
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
                System.out.print("Nhập ngôn ngữ mẹ đẻ của bạn: ");
                newMotherTounge = new Scanner(System.in).nextLine();
                user.setMothertounge(newMotherTounge);
                break;
            case 6:
                return;
        }
        showUser(user);
        saveUserData();
    }

    public void updateUserInformationByAdmin() {
        System.out.println("Mời bạn nhập email tài khoản cần cập nhật thông tin: ");
        String email = new Scanner(System.in).nextLine();
        User user = findUserByEmail(email);
        System.out.println("Mời bạn nhập thông tin muốn chỉnh sửa: ");
        System.out.println("1.Email");
        System.out.println("2.Password");
        System.out.println("3.Tên");
        System.out.println("4.Tuổi");
        System.out.println("5.Ngôn ngữ mẹ đẻ");
        System.out.println("6.Thoát");
        int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                "Chức năng là số dương từ 1 đến 6, Vui lòng nhập lại: ", 1, 6);
        switch (choice){
            case 1:
                String newEmail;
                while (true){
                    System.out.println("Mời bạn nhập email mới: ");
                    newEmail = new Scanner(System.in).nextLine();
                    if (!newEmail.matches(Regex.EMAIL_REGEX)) {
                        System.out.println("Email không đúng định dạng vui lòng nhập lại");
                        continue;
                    }
                    boolean coTrungEmailKhong = false;
                    for (User user1 : users) {
                        if (newEmail.equalsIgnoreCase(user1.getEmail()) && user1.getId() != user.getId()) {
                            System.out.println("Email đã tồn tại vui lòng nhập lại");
                            coTrungEmailKhong = true;
                            break;
                        }
                    }
                    if (!coTrungEmailKhong) {
                        break;
                    }
                }
                user.setEmail(newEmail);
                break;
            case 2:
                String newPassword;
                while (true) {
                    System.out.println("Mời bạn nhập password mới (8 -> 12 ký tự cả chữ thường, chữ hoa và cả số)");
                    newPassword = new Scanner(System.in).nextLine();
                    if (!newPassword.matches(Regex.PASSWORD_REGEX)) {
                        System.out.println("Password không đúng định dạng vui lòng nhập lại ");
                        continue;
                    }
                    break;
                }
                user.setPassword(newPassword);
                break;
            case 3:
                String newName;
                while (true) {
                    System.out.println("Mời bạn nhập họ tên mới: ");
                    newName = new Scanner(System.in).nextLine();
                    if (!newName.matches(".*\\d.*") && !newName.isEmpty()) {
                        break;
                    } else {
                        System.out.println("Tên không hợp lệ, Vui lòng nhập lại.");
                    }
                }
                user.setFullname(newName);
                break;
            case 4:
                int newAge = - 1;
                while (true) {
                    try {
                        System.out.println("Mời bạn nhập tuổi mới: ");
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
                System.out.print("Nhập ngôn ngữ mẹ đẻ của bạn: ");
                newMotherTounge = new Scanner(System.in).nextLine();
                user.setMothertounge(newMotherTounge);
                break;
            case 6:
                return;
        }
        showUser(user);
        saveUserData();
    }

    public User getLoggedInUser() {
        for (User userTemp : users) {
            if (userTemp.getId() == Main.LOGGED_IN_USER.getId()) {
                return userTemp;
            }
        }
        return null;
    }

    public void lockUserById(int idUserLock) {
        for (User user : users){
            if (user.getId() == idUserLock){
                user.setStatus(Status.INACTIVE);
                System.out.println("User có ID trên đã được khóa");
                printHeader();
                showUserDetail(user);
                saveUserData();
                break;
            }
        }
    }

    public void unlockedUserById(int idUserLock){
        for (User user: users){
            if (user.getId() == idUserLock){
                user.setStatus(Status.ACTIVE);
                System.out.println("User có ID trên đã được mở khóa");
                printHeader();
                showUserDetail(user);
                saveUserData();
                break;
            }
        }
    }
}
