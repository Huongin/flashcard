package service;

import constant.Level;
import entity.Deck;
import util.FileUtil;
import util.InputUtil;


import java.time.LocalDate;
import java.util.*;

public class DeckService {

    private static final FileUtil<Deck> fileUtil = new FileUtil<>();

    private static final String DECK_DATA_FILE = "deck.json";

    private static int AUTO_ID;

    private static List<Deck> decks;


    public void setDecks(){
       List<Deck> deckList = fileUtil.readDataFromFile(DECK_DATA_FILE, Deck[].class);
       decks = deckList != null ? deckList : new ArrayList<>();
   }
    public static void createDeck() {
        Deck deck = new Deck(AUTO_ID++);
        System.out.println("Mời bạn nhập chủ đề cho bộ thẻ: ");
        deck.setTopic(new Scanner(System.in).nextLine());
        System.out.println("Mời bạn nhập cấp độ cho chủ đề của bộ thẻ: ");
        System.out.println("1.Trình độ JLPT N1");
        System.out.println("2.Trình độ JLPT N2");
        System.out.println("3.Trình độ JLPT N3");
        System.out.println("4.Trình độ JLPT N4");
        System.out.println("5.Trình độ JLPT N5");
        int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
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
        System.out.println("Ngày tạo bộ thẻ: ");
        deck.setCreatedDate(LocalDate.now());
        System.out.println("Nhập miêu tả nội dung hoặc nguồn tài liệu của bộ thẻ: ");
        deck.setDescription(new Scanner(System.in).nextLine());
        saveDeckData(); //Lưu dữ liệu Data
    }

    public static void saveDeckData() {
       fileUtil.writeDataToFile(decks,DECK_DATA_FILE);
    }

    private static Deck findDeckById(int id) {
        for (Deck deck: decks){
            if(deck.getId() == id){
                return deck;
            }
        }
        return null;
    }

    public static void updateDeckById() {
        while (true){
            System.out.println("Mời bạn nhập ID của chủ đề bộ thẻ muốn cập nhật: ");
            int id;
            try{
                id = new Scanner(System.in).nextInt();
            }catch (InputMismatchException e){
                System.out.println("Giá trị vừa nhập không phải là một số nguyên. Vui lòng nhập lại: ");
                continue;
            }
            Deck deck = findDeckById(id);
            if(deck == null){
               System.out.println("Id vừa nhập không tồn tại trong hệ thống, vui lòng nhập lại: ");
               continue;
            }
            System.out.println("Mời bạn nhập chủ đề mới cho bộ thẻ: ");
            deck.setTopic(new Scanner(System.in).nextLine());
            System.out.println("Mời bạn chọn cấp độ mới cho bộ thẻ: ");
            System.out.println("1.Trình độ JLPT N1");
            System.out.println("2.Trình độ JLPT N2");
            System.out.println("3.Trình độ JLPT N3");
            System.out.println("4.Trình độ JLPT N4");
            System.out.println("5.Trình độ JLPT N5");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
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
            System.out.println("Ngày tạo bộ thẻ: ");
            deck.setCreatedDate(LocalDate.now());
            System.out.println("Nhập miêu tả nội dung hoặc nguồn tài liệu của bộ thẻ: ");
            deck.setDescription(new Scanner(System.in).nextLine());
            showDeck(deck);
            saveDeckData();
            break;//Lưu dữ liệu Data
        }
    }

    public static void deleteDeckById(int id) {
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

    private static void printHeader() {
        System.out.printf("%-5s%-30s%n", "Id", "Topic", "Level", "CreatDate", "Description");
        System.out.println("----------------------------------------");
    }

    private static void showDeck(Deck deck) {
        printHeader();
        showDeckDetail(deck);
    }

    private static void showDeckDetail(Deck deck) {
        System.out.printf("%-5s%-30s%n", deck.getId(), deck.getTopic(), deck.getLevel(), deck.getDescription(), deck.getDescription());
    }

}
