package service;

import entity.Card;
import entity.Deck;
import entity.Study;
import entity.User;
import main.Main;
import util.FileUtil;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class StudyService {

    private final FileUtil<Study> fileUtil = new FileUtil<>();
    private static final String STUDY_DATA_FILE = "study.json";
    private List<Study> studies;

    private final UserService userService;
    private final DeckService deckService;
    private final CardService cardService;

    public StudyService(UserService userService, DeckService deckService, CardService cardService) {
        this.userService = userService;
        this.deckService = deckService;
        this.cardService = cardService;
    }

    public void setStudy() {
        List<Study> studyList = fileUtil.readDataFromFile(STUDY_DATA_FILE, Study[].class);
        studies = studyList != null ? studyList : new ArrayList<>();
    }

    private void saveStudyData() {
        fileUtil.writeDataToFile(studies, STUDY_DATA_FILE);
    }

    // Tìm đối tượng study tương ứng với User và Deck nhập vào
    private Study getStudyByUserAndDeck(User user, Deck deck) {
        for (Study study : studies) {
            if (study.getUser().equals(user) && study.getDeck().equals(deck)) {
                return study;
            }
        }
        return null;
    }

    //Chức năng học với thẻ học được gán
    public void studyWithAssignedDecks() {
        //Hiển thị các bộ thẻ người dùng được gán
        System.out.println("Danh sách bộ thẻ được gán cho bạn là: ");
        List<Deck> assignedDecks = deckService.getAssignedDecksForUser(Main.LOGGED_IN_USER);//Danh sách bộ thẻ được gán cho người d
        if (assignedDecks.isEmpty()) {
            System.out.println("Bạn chưa được gán bộ thẻ nào để học");
            return;
        }
        //Hiển thị danh sách bộ thẻ
        System.out.println("Chọn bộ thẻ bạn muốn học: ");
        for (Deck deck : assignedDecks) {
            System.out.println("ID: " + deck.getId() + ", Chủ đề: " + deck.getTopic() + ", Cấp độ: " + deck.getLevel() + ", Người tạo: " + deck.getCreator());
        }

        //Chọn và nhập ID bộ thẻ muốn học
        int deckId;
        Deck selected = null;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Nhập ID bộ thẻ muốn học");
                deckId = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                scanner.nextLine();
                continue;
            }
            for (Deck deck : assignedDecks) {
                if (deck.getId() == deckId) {
                    selected = deck;
                    break;
                }
            }
            if (selected == null) {
                System.out.println("Không tìm thấy bộ thẻ với ID vừa nhập.");
                continue;
            }
            break;
        }
        //Hiển thị các thẻ học trong bộ thẻ đã chọn
        List<Card> cards = cardService.getCardsByDeck(selected);
        if (cards.isEmpty()) {
            System.out.println("Bộ thẻ này không có thẻ học nào.");
            return;
        }
        //Tạo mới Study nếu chưa có
        Study userStudy = getStudyByUserAndDeck(Main.LOGGED_IN_USER, selected);
        if (userStudy == null) {
            userStudy = new Study(Main.LOGGED_IN_USER, selected, new ArrayList<>(), new ArrayList<>(cards));
            studies.add(userStudy);
        }

        System.out.print("Số lượng từ trong bộ thẻ: " + cards.size());


        Scanner inputEnter = new Scanner(System.in);
        List<Card> cardsRemove = new ArrayList<>(); //Danh sách cards sau khi học xong sẽ xóa khỏi danh sách chưa học
        for (Card card : userStudy.getIncomingCards()) {
            cardService.showCard(card);
            System.out.println("Nhấn Enter để chuyển qua từ tiếp theo....");
            inputEnter.nextLine();

            userStudy.getStudiedCards().add(card); //Những thẻ đã học sẽ được chuyển vào đây
            cardsRemove.add(card); //những thẻ đã học được lưu vào danh sách này để xóa khỏi danh sách chưa học
        }
        userStudy.getIncomingCards().removeAll(cardsRemove); //xóa danh sách những thẻ đã học trong danh sách thẻ chưa học
        System.out.println("Bạn đã hoàn thành bài học với bộ thẻ này");

        //Lưu lại
        saveStudyData();
    }

    //Chức năng học với thẻ học cá nhân
    public void studyWithPersonalCards() {
        // Hiển thị các bộ thẻ trong danh sách cá nhân
        System.out.println("Danh sách bộ thẻ cá nhân");
        List<Deck> userDecks = deckService.getUserCreatedDecks(Main.LOGGED_IN_USER);
        for (Deck deck : userDecks) {
            System.out.println("ID: " + deck.getId() + ", Chủ đề: " + deck.getTopic() + ", Cấp độ: " + deck.getLevel());
        }
        //Chọn và nhập ID  bộ thẻ muốn học
        int deckId;
        Deck selected = null;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Nhập ID bộ thẻ muốn học");
                deckId = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                scanner.nextLine();
                continue;
            }
            for (Deck deck : userDecks) {
                if (deck.getId() == deckId) {
                    selected = deck;
                    break;
                }
            }
            if (selected == null) {
                System.out.println("Không tìm thấy bộ thẻ với ID vừa nhập.");
                continue;
            }
            break;
        }
        //Hiển thị các thẻ học trong bộ thẻ đã chọn
        List<Card> cards = cardService.getCardsByDeck(selected);
        if (cards.isEmpty()) {
            System.out.println("Bộ thẻ này không có thẻ học nào.");
            return;
        }
        System.out.println("Bộ thẻ này có các thẻ học là: ");
        for (Card card : cards) {
            cardService.showCardDetail(card);
        }

        //Tạo mới Study nếu chưa có
        Study userStudy = getStudyByUserAndDeck(Main.LOGGED_IN_USER, selected);
        if (userStudy == null) {
            userStudy = new Study(Main.LOGGED_IN_USER, selected, new ArrayList<>(), new ArrayList<>(cards));
            studies.add(userStudy);
        }

        System.out.print("Số lượng từ trong bộ thẻ: " + cards.size());
        Scanner inputEnter = new Scanner(System.in);
        List<Card> cardsRemove = new ArrayList<>(); //Danh sách cards sau khi học xong sẽ xóa khỏi danh sách chưa học
        for (Card card : userStudy.getIncomingCards()) {
            cardService.showCard(card);
            System.out.println("Nhấn Enter để chuyển qua từ tiếp theo....");
            inputEnter.nextLine();

            userStudy.getStudiedCards().add(card); //Những thẻ đã học sẽ được chuyển vào đây
            cardsRemove.add(card); //những thẻ đã học được lưu vào danh sách này để xóa khỏi danh sách chưa học
        }
        userStudy.getIncomingCards().removeAll(cardsRemove); //xóa danh sách những thẻ đã học trong danh sách thẻ chưa học
        System.out.println("Bạn đã hoàn thành bài học với bộ thẻ này");
        //Lưu lại
        saveStudyData();
    }


        // Lấy danh sách các thẻ đã học
    public void studiedCards() {
        List<Card> studiedCards = new ArrayList<>();

        for (Study study : studies) {
            if (study.getUser().equals(Main.LOGGED_IN_USER)) {
                studiedCards.addAll(study.getStudiedCards());
            }
        }
        if (studiedCards.isEmpty()) {
            System.out.println("Bạn chưa có thẻ nào được học.");
        } else {
            System.out.println("Danh sách các thẻ đã học: ");
            for (Card card : studiedCards) {
                System.out.println("ID: " + card.getId());
                System.out.println("Từ vựng: " + card.getWord());
                System.out.println("Phiên âm: " + card.getPhonetic());
                System.out.println("Nghĩa của từ: " + card.getMeaning());
                System.out.println("Loại từ: " + card.getCardType());
                System.out.println("Bộ thẻ: " + card.getDeck().getTopic());
                System.out.println("Ví dụ: " + card.getExample());
                System.out.println("------------------------------------------");
            }
        }
    }

    public void IncomingCards() {
        List<Card> incomingCards = new ArrayList<>();

        for (Study study : studies) {
            if (study.getUser().equals(Main.LOGGED_IN_USER)) {
                incomingCards.addAll(study.getIncomingCards());
            }
        }
        if (incomingCards.isEmpty()) {
            System.out.println("Bạn chưa có thẻ nào được học.");
        } else {
            System.out.println("Danh sách các thẻ đã học: ");
            for (Card card : incomingCards) {
                System.out.println("ID: " + card.getId());
                System.out.println("Từ vựng: " + card.getWord());
                System.out.println("Phiên âm: " + card.getPhonetic());
                System.out.println("Nghĩa của từ: " + card.getMeaning());
                System.out.println("Loại từ: " + card.getCardType());
                System.out.println("Bộ thẻ: " + card.getDeck().getTopic());
                System.out.println("Ví dụ: " + card.getExample());
                System.out.println("------------------------------------------");
            }
        }
    }

    // Xóa lịch sử học theo deckId
    public void deleteByDeckId(int deleteId) {
        List<Study> studiesToRemove = new ArrayList<>();
        for (Study study : studies){
            if (study.getDeck().getId() == deleteId){
                studiesToRemove.add(study);
            }
        }
        studies.removeAll(studiesToRemove);
        saveStudyData();
    }
}
