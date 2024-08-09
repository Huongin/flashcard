package service;


import constant.CardType;
import constant.UserRole;
import entity.Card;
import entity.Deck;
import entity.User;
import util.FileUtil;
import util.InputUtil;

import java.util.*;


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

    public void createCard(User user) {
        //Chọn bộ thẻ cho thẻ học
        System.out.println("Hãy chọn chủ đề cho thẻ học của bạn: ");
        List<Deck> userDecks = deckService.getUserCreatedDecks(user);
        if (userDecks.isEmpty()) {
            System.out.println("Bạn không có bộ thẻ nào trong danh sách.");
            return;
        } else {
            System.out.println("Danh sách các bộ thẻ của bạn là : ");
            for (Deck deck : userDecks){
                System.out.println("ID: " + deck.getId() + ", Tên chủ đề: " + deck.getTopic() + ",Cấp độ: " + deck.getLevel());
            }
        }
        Deck selectedDeck;
        while (true){
            System.out.println("Nhập ID bộ thẻ muốn thêm thẻ học vào: ");
            int deckId;
            try {
                deckId = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn nhập không phải số nguyên.Vui lòng nhập lại: ");
                continue;
            }
            selectedDeck = DeckService.findDeckById(deckId);
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
        card.setCreator(user); //Gán người tạo thẻ
        card.setDeck(selectedDeck);//Gán bộ thẻ cho thẻ học

        cards.add(card); // Thêm thẻ vào danh sách bộ thẻ
        showCard(card);
        saveCardData();
    }

    public List<Card> getCardsByDeck(Deck deck) {
        List<Card> cardsInDeck = new ArrayList<>();
        for (Card card : cards) {
            if (card.getDeck().equals(deck)) {
                cardsInDeck.add(card);
            }
        }
        return cardsInDeck;
    }

    private void saveCardData() {
        fileUtil.writeDataToFile(cards, CARD_DATA_FILE);
    }

    private Card findCardById(int cardId) {
        for (Card card : cards) {
            if (card.getId() == cardId) {
                return card;
            }
        }
        return null;
    }

    public void updateCardInfo() {
        User user = userService.getLoggedInUser();
        if (user == null) {
            System.out.println("Vui lòng đăng nhập lại trước khi tạo thẻ.");
        }
        while (true) {
            System.out.println("Mời bạn nhập ID của thẻ học: ");
            int cardId;
            while (true) {
                try {
                    cardId = new Scanner(System.in).nextInt();
                    break; // Thoát khỏi vòng lặp nếu gúa trị là số nguyên hợp lệ
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại");
                }
            }
            Card card = findCardById(cardId);
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
                        deck = DeckService.findDeckById(id);
                        if (deck == null) {
                            System.out.println("Id vừa nhập không tồn tại trong hệ thống, vui lòng nhập lại: ");
                            continue;
                        }
                        break;
                    }
                    card.setDeck(deck); //Cập nhật bộ thẻ cho thẻ học
                case 7:
                    return;
            }
            System.out.println("Bạn đã cập nhật thành công thông tin về thẻ học");
            showCard(card);
            saveCardData();
            break;
        }
    }

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
        printHeader();
        showCardDetail(card);
    }

    public void showCards(List<Card> cards1) {
        printHeader();
        for (Card card : cards1) {
            showCardDetail(card);
        }
    }

    public void showCardDetail(Card card) {
        System.out.printf("%-5s%-20s%-20s%-30s%-20s%-20s%-60s%-20s%-10s%n", card.getId(), card.getWord(), card.getPhonetic(), card.getMeaning(), card.getCardType(), card.getState(), card.getExample(), card.getCreator(), card.getDeck());
    }

    public void printHeader() {
        System.out.printf("%-5s%-20s%-20s%-30s%-20s%-20s%-60s%-20s%-10s%n", "id", "Word", "Phonetic", "Meaning", "CardType", "State", "Example", "Creator", "Deck");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void deleteCardById(int cardId) {
        Card card = findCardById(cardId);
        cards.remove(card);
        showCard(card);
        saveCardData();//Lưu file
    }

    public List<Card> getAdminCards() {
        return List.of();
    }
}
