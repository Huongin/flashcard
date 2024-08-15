package service;


import constant.CardType;
import entity.Card;
import entity.Deck;
import main.Main;
import util.FileUtil;
import util.InputUtil;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class CardService {

    private final FileUtil<Card> fileUtil = new FileUtil<>();
    private static final String CARD_DATA_FILE = "cards.json";
    private static int AUTO_ID;
    private List<Card> cards = new ArrayList<>();
    private final UserService userService;
    private final DeckService deckService;

    public CardService(UserService userService, DeckService deckService) {
        this.userService = userService;
        this.deckService = deckService;
    }

    public void setCards() {
        List<Card> cardList = fileUtil.readDataFromFile(CARD_DATA_FILE, Card[].class);
        cards = cardList != null ? cardList : new ArrayList<>();
    }

    public void findCurrentAutoId() {
        int maxId = -1;
        for (Card card : cards) {
            if (card.getId() > maxId) {
                maxId = card.getId();
            }
        }
        AUTO_ID = maxId + 1;
    }

    //Tạo thẻ học mới
    public void createCard() {
        //Chọn bộ thẻ cho thẻ học
        List<Deck> userDecks = deckService.getUserCreatedDecks(Main.LOGGED_IN_USER);
        if (userDecks.isEmpty()) {
            System.out.println("Bạn không có bộ thẻ nào trong danh sách. Vui lòng tạo bộ thẻ trước khi tạo thẻ học");
            return;
        }

        System.out.println("Danh sách các bộ thẻ của bạn là : ");
        for (Deck deck : userDecks) {
            System.out.println("ID: " + deck.getId() + ", Tên chủ đề: " + deck.getTopic() + ", Cấp độ: " + deck.getLevel());
        }

        System.out.println("Hãy chọn chủ đề cho thẻ học của bạn bằng cách nhập ID của bộ thẻ: ");
        Deck selectedDeck;
        while (true) {
            int deckId;
            try {
                deckId = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn nhập không phải số nguyên.Vui lòng nhập lại: ");
                continue;
            }
            selectedDeck = deckService.findDeckById(deckId);
            if (selectedDeck == null) {
                System.out.println("Thông tin không chính xác vui lòng nhập lại");
                continue;
            }
            break;
        }
        //Tạo thẻ học
        Card card = new Card(AUTO_ID++);

        System.out.println("Mời bạn nhập từ vựng: "); //Nhập từ vựng
        card.setWord(new Scanner(System.in).nextLine());

        System.out.println("Mời bạn nhập phiên âm của từ: ");// Nhập phiên âm
        card.setPhonetic(new Scanner(System.in).nextLine());

        System.out.println("Mời bạn nhập nghĩa của từ: ");// Nhập nghĩa từ vựng
        card.setMeaning(new Scanner(System.in).nextLine());

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
        card.setDeck(selectedDeck);//Gán bộ thẻ cho thẻ học

        cards.add(card); // Thêm thẻ vào danh sách thẻ
        showCard(card);
        saveCardData();
    }

    public List<Card> getCardsByDeck(Deck deck) {
        List<Card> cardsInDeck = new ArrayList<>();
        for (Card card : cards) {
            if (card.getDeck() != null && card.getDeck().equals(deck)) {
                cardsInDeck.add(card);
            }
        }
        return cardsInDeck;
    }

    private void saveCardData() {
        fileUtil.writeDataToFile(cards, CARD_DATA_FILE);
    }

    //Cập nhật thông tin thẻ hoc
    public void updateCardInfo() {
        Card card = null;
        System.out.println("Mời bạn nhập ID của thẻ học: ");
        while (true) {
            int cardId;
            try {
                cardId = new Scanner(System.in).nextInt();
                card = findCardById(cardId);
                if (card == null) {
                    System.out.println("Thông tin không chính xác, vui lòng nhập lại: ");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại");
            }
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
                Deck deck;
                deckService.showCardDeckList();
                while (true) {
                    System.out.println("Mời bạn nhập ID của chủ đề bộ thẻ muốn cập nhật: ");
                    int id;
                    try {
                        id = new Scanner(System.in).nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Giá trị vừa nhập không phải là một số nguyên. Vui lòng nhập lại: ");
                        continue;
                    }
                    deck = deckService.findDeckById(id);
                    if (deck == null) {
                        System.out.println("Id vừa nhập không tồn tại trong hệ thống, vui lòng nhập lại: ");
                        continue;
                    }
                    break;
                }
                card.setDeck(deck); //Cập nhật bộ thẻ cho thẻ học
                break;
            case 7:
                return;
        }
        System.out.println("Bạn đã cập nhật thành công thông tin về thẻ học");
        showCard(card);
        saveCardData();
    }

    //Tìm thẻ học bằng ID
    private Card findCardById(int cardId) {
        for (Card card : cards) {
            if (card.getId() == cardId) {
                return card;
            }
        }
        return null;
    }

    // Tìm thẻ học theo từ tựng
    public void findCardByWord() {
        System.out.println("Mời bạn nhập từ vựng cần tìm: ");
        String word = new Scanner(System.in).nextLine();
        List<Card> cards1 = new ArrayList<>();
        for (Card card : cards) {
            if (card.getWord().toLowerCase().contains(word.toLowerCase())) {
                cards1.add(card);
            }
        }
        showCards(cards1);
    }

    //Tìm từ vựng theo chủ đề bộ thẻ
    public void findCardByNameTopic() {
        System.out.println("Mời bạn nhập chủ đề muốn tìm: ");
        String topic = new Scanner(System.in).nextLine();
        List<Card> cards1 = new ArrayList<>();
        for (Card card : cards) {
            if (card.getDeck().getTopic().toLowerCase().contains(topic.toLowerCase())) {
                cards1.add(card);
            }
        }
        showCards(cards1);
    }

    public void showCard(Card card) {
        showCardDetail(card);
    }

    public void showCards(List<Card> cards1) {
        for (Card card : cards1) {
            showCardDetail(card);
        }
    }

    public void showCardDetail(Card card) {
        System.out.println("ID: " + card.getId());
        System.out.println("Từ vựng: " + card.getWord());
        System.out.println("Phiên âm: " + card.getPhonetic());
        System.out.println("Nghĩa của từ: " + card.getMeaning());
        System.out.println("Loại từ: " + card.getCardType());
        System.out.println("Trạng thái: " + card.getState());
        System.out.println("Bộ thẻ: " + card.getDeck().getTopic());
        System.out.println("Ví dụ: " + card.getExample());
        System.out.println("Người tạo: " + card.getCreator().getFullname());
        System.out.println("------------------------------------------");
    }

    // Xóa thẻ học bằng ID
    public void deleteCardById(int cardId) {
        Card card = findCardById(cardId);
        cards.remove(card);
        showCard(card);
        saveCardData();//Lưu file
    }

    // TODO - chua xu ly
    public List<Card> getAdminCards() {
        return List.of();
    }
}
