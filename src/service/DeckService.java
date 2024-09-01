package service;

import constant.Level;
import constant.UserRole;
import entity.Deck;
import entity.User;
import main.Main;
import util.FileUtil;
import util.InputUtil;

import java.util.*;

public class DeckService {

    private static final FileUtil<Deck> fileUtil = new FileUtil<>();
    private static final String DECK_DATA_FILE = "deck.json";
    private static int AUTO_ID;
    private static List<Deck> decks;
    private final UserService userService;

    public DeckService(UserService userService) {
        this.userService = userService;
    }

    public void setDecks() {
        List<Deck> deckList = fileUtil.readDataFromFile(DECK_DATA_FILE, Deck[].class);
        decks = deckList != null ? deckList : new ArrayList<>();
    }

    public void saveDeckData() {
        fileUtil.writeDataToFile(decks, DECK_DATA_FILE);
    }

    public void findCurrentAutoId() {
        int maxId = -1;
        for (Deck deck : decks) {
            if (deck.getId() > maxId) {
                maxId = deck.getId();
            }
        }
        AUTO_ID = maxId + 1;
    }

    public void createDeck() {
        Deck deck = new Deck(AUTO_ID++);
        System.out.println("Mời bạn nhập chủ đề cho bộ thẻ: ");
        deck.setTopic(new Scanner(System.in).nextLine());
        System.out.println("Mời bạn nhập cấp độ cho chủ đề của bộ thẻ: ");
        System.out.println("1.Trình độ JLPT N1");
        System.out.println("2.Trình độ JLPT N2");
        System.out.println("3.Trình độ JLPT N3");
        System.out.println("4.Trình độ JLPT N4");
        System.out.println("5.Trình độ JLPT N5");
        int choice = InputUtil.chooseOption("Xin mời chọn chức năng ",
                "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 5);
        switch (choice) {
            case 1:
                deck.setLevel(Level.N1);
                break;
            case 2:
                deck.setLevel(Level.N2);
                break;
            case 3:
                deck.setLevel(Level.N3);
                break;
            case 4:
                deck.setLevel(Level.N4);
                break;
            case 5:
                deck.setLevel(Level.N5);
                break;
        }
        System.out.println("Nhập miêu tả nội dung hoặc nguồn tài liệu của bộ thẻ: ");
        deck.setDescription(new Scanner(System.in).nextLine());

        deck.setCreatedDate(new Date());
        deck.setCreator(Main.LOGGED_IN_USER);//Gán thông tin người tạo deck
        decks.add(deck);
        saveDeckData(); //Lưu dữ liệu Data
    }

    public Deck findDeckById(int id) {
        for (Deck deck : decks) {
            if (deck.getId() == id) {
                return deck;
            }
        }
        return null;
    }

    public void updateDeckById() {
        List<Deck> userDecks = getUserCreatedDecks(Main.LOGGED_IN_USER);
        if (userDecks.isEmpty()) {
            System.out.println("Bạn không có bộ thẻ nào trong danh sách.");
            return;
        }
        System.out.println("Danh sách bộ thẻ của bạn là: ");
        showCardDeckList();

        while (true) {
            System.out.println("Mời bạn nhập ID của chủ đề bộ thẻ muốn cập nhật: ");
            int id;
            try {
                id = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị vừa nhập không phải là một số nguyên. Vui lòng nhập lại: ");
                continue;
            }
            Deck deck = findDeckById(id);
            if (deck == null) {
                System.out.println("Id vừa nhập không tồn tại trong hệ thống, vui lòng nhập lại: ");
                continue;
            }
            System.out.println("Mời bạn nhập thông tin muốn chỉnh sửa: ");
            System.out.println("1.Chủ đề bộ thẻ học");
            System.out.println("2.Cấp độ của bộ thẻ");
            System.out.println("3.Nội dung miêu tả về bộ thẻ");
            System.out.println("4.Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng ",
                    "Chức năng là số dương từ 1 đến 4, Vui lòng nhập lại: ", 1, 4);
            switch (choice) {
                case 1:
                    System.out.println("Mời bạn nhập chủ đề mới cho bộ thẻ: ");
                    deck.setTopic(new Scanner(System.in).nextLine());
                    break;
                case 2:
                    System.out.println("Mời bạn chọn cấp độ mới cho bộ thẻ: ");
                    System.out.println("1.Trình độ JLPT N1");
                    System.out.println("2.Trình độ JLPT N2");
                    System.out.println("3.Trình độ JLPT N3");
                    System.out.println("4.Trình độ JLPT N4");
                    System.out.println("5.Trình độ JLPT N5");
                    int levelChoice = InputUtil.chooseOption("Xin mời chọn chức năng",
                            "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 5);
                    switch (levelChoice) {
                        case 1:
                            deck.setLevel(Level.N1);
                            break;
                        case 2:
                            deck.setLevel(Level.N2);
                            break;
                        case 3:
                            deck.setLevel(Level.N3);
                            break;
                        case 4:
                            deck.setLevel(Level.N4);
                            break;
                        case 5:
                            deck.setLevel(Level.N5);
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Nhập miêu tả nội dung hoặc nguồn tài liệu của bộ thẻ: ");
                    deck.setDescription(new Scanner(System.in).nextLine());
                    break;
                case 4:
                    return;
            }
            showDeck(deck);
            saveDeckData();//Lưu dữ liệu Data
            break;
        }
    }

    public void deleteDeckById(int deleteId) {
        while (true) {
            Deck deck = findDeckById(deleteId);
            if (deck == null) {
                System.out.println("Không tìm thấy bộ thẻ nào với ID vừa nhập. vui lòng nhập lại ID: ");
                deleteId = new Scanner(System.in).nextInt();
                continue;
            }
            decks.remove(deck);
            saveDeckData();// Lưu file
            System.out.println("Bạn đã xóa chủ đề thành công. Các chủ đề hiện tại còn lại trong bộ thẻ của bạn là: ");
            showCardDeckList();
            break;
        }
    }

    public void showCardDeckList() {
        for (Deck deck : decks) {
            showDeckDetail(deck);
        }
    }


    public void showDeck(Deck deck) {
        showDeckDetail(deck);
    }

    public static void showDeckDetail(Deck deck) {
        System.out.println("Id: " + deck.getId());
        System.out.println("Topic: " + deck.getTopic());
        System.out.println("Level: " + deck.getLevel());
        System.out.println("Created Date: " + deck.getCreatedDate());
        System.out.println("Description: " + deck.getDescription());
        System.out.println("------------------------------------------");
    }


    // Danh sách bộ thẻ do user đăng nhập tạo
    public List<Deck> getUserCreatedDecks(User user) {
        List<Deck> userDecks = new ArrayList<>();
        for (Deck deck : decks) {
            User creator = deck.getCreator();
            if (creator != null && creator.getId() == user.getId()) {
                userDecks.add(deck);
            }
        }
        return userDecks;
    }

    //Chức năng gán người dùng vào bộ thẻ của admin

    public void assignUserToDeck() {
        // Danh sách người dùng được gán vào bộ thẻ
        List<User> assignedUsers = new ArrayList<>();//Danh sách người dùng được chọn gán vào bộ thẻ
        // Tìm deck cần gán
        System.out.println("Nhập ID bộ thẻ cần gán cho người dùng");
        int deckId = new Scanner(System.in).nextInt();
        Deck deck = findDeckById(deckId);
        if (deck == null) {
            System.out.println("không tìm thấy bộ thẻ với ID này");
            return;
        }
        // Nhập số lượng người dùng muốn gán vào bộ thẻ
        int userNumber = 0;
        while (true) {
            System.out.println("Nhập số người dùng muốn gán vào bộ thẻ");
            try {
                userNumber = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Cần nhập một nguyên dương, vui lòng nhập lại: ");
            }
            if (userNumber > 0) {
                break;
            } else {
                System.out.println("Số lượng người dùng phải lớn hơn 0. Vui lòng thử lại.");
            }
        }

        //Nhập ID người dùng cần gán vào bộ thẻ
        for (int i = 0; i < userNumber; i++) {
            while (true) {
                System.out.println("Nhập ID người dùng thứ " + (i + 1) + " cần gán vào bộ thẻ: ");
                try {
                    int userId = new Scanner(System.in).nextInt();
                    User user = userService.findUserById(userId);
                    if (user == null) {
                        System.out.println("Người dùng với ID" + userId + "Không tồn tại, vui lòng nhập lại.");
                    } else {
                        assignedUsers.add(user);
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị nhập vào không phải là số nguyên. Vui lòng thử lại.");
                }
            }
        }
        deck.setAssignedUser(assignedUsers);
        System.out.println("Đã gán người dùng vào bộ thẻ thành công");
        System.out.println("Danh sách người dùng đã được gán vào bộ thẻ");
        System.out.printf("%-10s %-20s%n", "ID", "Tên người dùng");
        System.out.println("------------------------------------------");
        for (User user : assignedUsers){
            System.out.printf("%-10s %-20s%n",user.getId(), user.getFullname());
        }
        System.out.println();
    }

    //Lấy danh sách bộ thẻ người dùng được gán
    public List<Deck> getAssignedDecksForUser(User user) {
        List<Deck> assignedDecks = new ArrayList<>();
        for (Deck deck : decks) {
            if (deck.getAssignedUser() != null && deck.getAssignedUser().contains(user)) {
                assignedDecks.add(deck);
            }
        }
        return assignedDecks;
    }

    public List<Deck> getAdminDecks(){
        List<Deck> adminDecks = new ArrayList<>();
        for (Deck deck : decks){
            if (deck.getCreator().getRole().equals(UserRole.ADMIN)){
                adminDecks.add(deck);
            }
        }
        return adminDecks;
    }
}
