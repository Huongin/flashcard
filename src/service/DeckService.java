package service;

import Main.Main;
import constant.Level;
import constant.UserRole;
import entity.Deck;
import entity.Study;
import entity.User;
import util.FileUtil;
import util.InputUtil;


import java.time.LocalDate;
import java.util.*;

public class DeckService {

    private static final FileUtil<Deck> fileUtil = new FileUtil<>();

    private static final String DECK_DATA_FILE = "deck.json";

    private static int AUTO_ID;

    private static List<Deck> decks;
    private final UserService userService;

    public DeckService(UserService userService) {
        this.userService =userService;
    }

    public void setDecks(){
      List<Deck> deckList = fileUtil.readDataFromFile(DECK_DATA_FILE, Deck[].class);
      decks = deckList != null ? deckList : new ArrayList<>();
   }
    public void findCurrentAutoId(){
        int maxId = -1;
        for (Deck deck : decks){
            if (deck.getId() > maxId){
                maxId = deck.getId();
            }
        }
        AUTO_ID = maxId +1;
    }

    public void createDeck() {
        User user = userService.getLoggedInUser();
        if (user == null){
            System.out.println("Bạn cần đăng nhập trước khi tạo bộ thẻ");
            return;
        }
        Deck deck = new Deck(AUTO_ID++);
        System.out.println("Mời bạn nhập chủ đề cho bộ thẻ: ");
        deck.setTopic(new Scanner(System.in).nextLine());
        System.out.println("Mời bạn nhập cấp độ cho chủ đề của bộ thẻ: ");
        System.out.println("1.Trình độ JLPT N1");
        System.out.println("2.Trình độ JLPT N2");
        System.out.println("3.Trình độ JLPT N3");
        System.out.println("4.Trình độ JLPT N4");
        System.out.println("5.Trình độ JLPT N5");
        int choice = InputUtil.chooseOption("Xin mời chọn chức năng" ,
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
        System.out.print("Thời gian tạo thẻ: ");
        deck.setCreatedDate(LocalDate.now());
        System.out.println("Nhập miêu tả nội dung hoặc nguồn tài liệu của bộ thẻ: ");
        deck.setDescription(new Scanner(System.in).nextLine());

        deck.setCreator(user);//Gán thông tin người tạo deck
        decks.add(deck);
        saveDeckData(); //Lưu dữ liệu Data
    }

    public void saveDeckData() {
        fileUtil.writeDataToFile(decks,DECK_DATA_FILE);
    }

    public static Deck findDeckById(int id) {
        for (Deck deck: decks){
            if(deck.getId() == id){
                return deck;
            }
        }
        return null;
    }

    public void updateDeckById() {
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
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
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
                    showDeck(deck);
                    saveDeckData();//Lưu dữ liệu Data
                    return;
            }
        }
    }

    public void deleteDeckById(int id) {
        Deck deck = findDeckById(id);
        decks.remove(deck);
        saveDeckData();//Lưu file
        showCardDeckList();
    }

    public static void showCardDeckList() {
        printHeader();
        for (Deck deck : decks){
            showDeckDetail(deck);
        }
    }

    public static void printHeader() {
        System.out.printf("%-5s%-30s%n", "Id", "Topic", "Level", "CreatDate", "Description");
        System.out.println("----------------------------------------");
    }

    public void showDeck(Deck deck) {
        printHeader();
        showDeckDetail(deck);
    }

    public static void showDeckDetail(Deck deck) {
        System.out.printf("%-5s%-30s%n", deck.getId(), deck.getTopic(), deck.getLevel(), deck.getDescription(), deck.getDescription());
    }

    //Danh sách bộ thẻ do admin tạo
    public List<Deck> getAdminCreatedDecks(){
        List<Deck> publicDecks = new ArrayList<>();
        for (Deck deck: decks){
            if (deck.getCreator().getRole().equals(UserRole.ADMIN)){
                publicDecks.add(deck);
            }
        }
        return publicDecks;
    }

    // Danh sách bộ thẻ do user đăng nhập tạo
    public List<Deck> getUserCreatedDecks(User user){
        List<Deck> userDecks = new ArrayList<>();
        for (Deck deck: decks){
            if (deck.getCreator().equals(user)) {
                userDecks.add(deck);
            }
        }
        return userDecks;
    }
}
