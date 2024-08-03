package service;


import Main.Main;
import constant.CardType;
import constant.State;
import entity.Card;
import entity.Deck;
import util.FileUtil;
import util.InputUtil;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class CardService {

    private final FileUtil<Card> fileUtil = new FileUtil<>();
    private static final String CARD_DATA_FILE = "cards.json";
    private static int AUTO_ID;
    private List<Card> cards;
    private DeckService deckService;

    public CardService(DeckService deckService) {
        this.deckService = deckService;
    }

    public void setCards(){
        List<Card> cardList = fileUtil.readDataFromFile(CARD_DATA_FILE, Card[].class);
        cards = cardList != null ? cardList : new ArrayList<>();
    }
    public void findCurrentAutoId(){
        int maxId = -1;
        for (Card card : cards) {
            if (card.getId() > maxId) {
                maxId = card.getId();
            }
        }
        AUTO_ID = maxId + 1;
    }

    public void createCard() {
        if (Main.LOGGED_IN_USER == null) {
            System.out.println("Bạn cần đăng nhập trước khi tạo bộ thẻ");
            return;
        }
        Card card = new Card();
        card.setId(AUTO_ID++);

        System.out.println("Mời bạn nhập từ vựng: "); //Nhập từ vựng
        card.setWord(new Scanner(System.in).nextLine());

        System.out.println("Mời bạn nhập phiên âm của từ: ");// Nhập phiên âm
        card.setPhonetic(new Scanner(System.in).nextLine());

        System.out.println("Mời bạn chọn loại từ vựng");// Nhập loại từ
        System.out.println("1. Danh từ");
        System.out.println("2. Tính từ i");
        System.out.println("3. Tính từ na");
        System.out.println("4. Động từ");
        int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                "Chức năng là số dương từ 1 đến 4, Vui lòng nhập lại: ", 1, 5);
        switch (choice) {
            case 1:
                card.setCardType(CardType.Noun);
                break;
            case 2:
                card.setCardType(CardType.Adji);
                break;
            case 3:
                card.setCardType(CardType.AdjNa);
                break;
            case 4:
                card.setCardType(CardType.Verb);
                break;
        }

        System.out.println("Nhập ví dụ về cách dùng từ"); //Nhập ví dụ
        card.setExample(new Scanner(System.in).nextLine());

        card.setCreator(Main.LOGGED_IN_USER); //Gán người tạo thẻ

        //Chọn bộ thẻ cho thẻ học
        deckService.setDecks();
        DeckService.showCardDeckList();

        while (true) {
            System.out.println("Mời bạn nhập ID của chủ đề bộ thẻ muốn cập nhật: ");
            int id;
            try {
                id = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị vừa nhập không phải là một số nguyên. Vui lòng nhập lại: ");
                continue;
            }
            Deck deck = deckService.findDeckById(id);
            if (deck == null) {
                System.out.println("Id vừa nhập không tồn tại trong hệ thống, vui lòng nhập lại: ");
                continue;
            }
            card.setDeck(deck);
        }
        cards.add(card);
        showCard(card);
        saveCardData();
    }

    private void saveCardData() {
        fileUtil.writeDataToFile(cards,CARD_DATA_FILE);
    }
    private Card findCardById(int idCard) {
        for (Card card: cards) {
        }
    }

    public void updateCardInfo() {
        while (true) {
            System.out.println("Mời bạn nhập ID của thẻ học: ");
            int idCard;
            while (true) {
                try {
                    idCard= new Scanner(System.in).nextInt();
                    break; // Thoát khỏi vòng lặp nếu gúa trị là số nguyên hợp lệ
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại");
                }
            }
            Card card = findCardById(idCard);
            if (card == null) {
                System.out.println("Thông tin không chính xác, vui lòng nhập lại: ");
                continue;
            }
            System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa: ");
            System.out.println("1. Từ vựng");
            System.out.println("2. Phiên âm");
            System.out.println("3. Nghĩa của từ");
            System.out.println("4. Loại từ vung");
            System.out.println("5. Ví dụ về cách dùng từ");
            System.out.println("6. Chủ đề bộ thẻ");
            System.out.println("7. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 đến 7, Vui lòng nhập lại: ", 1, 7);
            switch (choice) {
                case 1:
                    System.out.println("Mời bạn nhập từ mới");
                    String newWord = new Scanner(System.in).nextLine();
                    card.setWord(newWord);
                    break;
                case 2:
                    System.out.println("Mời bạn nhâp phiên âm mới của từ");
                    String newPhonetic = new Scanner(System.in).nextLine();
                    card.setPhonetic(newPhonetic);
                    break;
                case 3:
                    System.out.println("Mời bạn nhập nghĩa mới của từ");
                    String newMeaning = new Scanner(System.in).nextLine();
                    card.setMeaning(newMeaning);
                    break;
                case 4:
                    System.out.println("Mời bạn nhập thể loại mới của từ");
                    System.out.println("1. Danh từ");
                    System.out.println("2. Tính từ i");
                    System.out.println("3. Tính từ na");
                    System.out.println("4. Động từ");
                    int newStyle = InputUtil.chooseOption("Xin mời chọn chức năng",
                            "Chức năng là số dương từ 1 đến 4, Vui lòng nhập lại: ", 1, 5);
                    switch (newStyle) {
                        case 1:
                            card.setCardType(CardType.Noun);
                            break;
                        case 2:
                            card.setCardType(CardType.Adji);
                            break;
                        case 3:
                            card.setCardType(CardType.AdjNa);
                            break;
                        case 4:
                            card.setCardType(CardType.Verb);
                            break;
                    }
                    break;
                case 5:
                    System.out.println("Mời bạn nhập lại ví dụ");
                    String newExample = new Scanner(System.in).nextLine();
                    card.setExample(newExample);
                    break;
                case 6:
                    System.out.println("Mời bạn nhập lại bộ thẻ của từ");
                    deckService.setDecks();
                    DeckService.showCardDeckList();

                    while (true) {
                        System.out.println("Mời bạn nhập ID của chủ đề bộ thẻ muốn cập nhật: ");
                        int id;
                        try {
                            id = new Scanner(System.in).nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị vừa nhập không phải là một số nguyên. Vui lòng nhập lại: ");
                            continue;
                        }
                        Deck deck = deckService.findDeckById(id);
                        if (deck == null) {
                            System.out.println("Id vừa nhập không tồn tại trong hệ thống, vui lòng nhập lại: ");
                            continue;
                        }
                        card.setDeck(deck);
                    }
                    break;
                case 7:
                    return;
            }
            System.out.println("Bạn đã cập nhật thành công thông tin về thẻ học");
            showCard(card);
            saveCardData();
            break;
        }
    }



    public void searchCardByWord() {
    }

    public void searchCardByTopic() {
    }

    public void listOfFlashcards() {
    }

    private void showCard(Card card) {
        printHeader();
        showCardDetail(card);
    }

    private void showCardDetail(Card card) {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n", card.getId(), card.getWord(), card.getPhonetic(), card.getMeaning(), card.getCardType(), card.getState(),card.getExample(), card.getCreator(), card.getDeck());
    }

    private void printHeader() {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n", "id", "Word", "Phonetic", "Meaning", "CardType", "State", "Example", "Creator", "Deck");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
    }
}
