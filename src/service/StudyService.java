package service;

import Main.Main;
import constant.UserRole;
import entity.Card;
import entity.Deck;
import entity.Study;
import entity.User;
import util.FileUtil;
import util.InputUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class StudyService {

    private final FileUtil<Card> fileUtil = new FileUtil<>();
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
    public void setStudy(){
        List<Study> studyList = fileUtil.readDataFromFile(STUDY_DATA_FILE,Study.class);
        studies = studyList != null ? studies : new ArrayList<>();
    }

    public void studyWithPublicCards() {
        User user = userService.getLoggedInUser();
        if(user == null){
            System.out.println("Vui lòng đăng nhập lại.");
            return;
        }
        // Hiển thị các bộ thẻ trong danh sách công khai
        System.out.println("Danh sách bộ thẻ công khai");
        List<Deck> publicDecks = deckService.getAdminCreatedDecks();
        for (Deck deck: publicDecks){
            System.out.println("ID: " + deck.getId() + ", Chủ đề: " + deck.getTopic() + ", Cấp độ: " + deck.getLevel());
        }
        //Chọn và nhập ID  bộ thẻ muốn học
        System.out.println("Nhập ID bộ thẻ muốn học");
        int deckId = new Scanner(System.in).nextInt();
        Deck selected = null;
        for (Deck deck: publicDecks){
            if(deck.getId() == deckId){
                selected = deck;
                break;
            }
        }
        if(selected == null){
            System.out.println("Không tìm thấy bộ thẻ với ID vừa nhập.");
            return;
        }
        //Hiển thị các thẻ học trong bộ thẻ đã chọn
        List<Card> cards = cardService.getCardsByDeck(selected);
        if (cards.isEmpty()){
            System.out.println("Bộ thẻ này không có thẻ học nào.");
            return;
        }
        System.out.println("Số lượng từ trong bộ thẻ: "+cards.size());

        Scanner inputEnter = new Scanner(System.in);
        for (Card card : cards){
            cardService.showCard(card);
            System.out.println("Nhấn Enter để chuyển qua từ tiếp theo....");
            inputEnter.nextLine();
        }
        System.out.println("Bạn đã hoàn thành bài học với bộ thẻ này");
    }

    public void studyWithPersonalCards() {
        User user = userService.getLoggedInUser();
        if(user == null){
            System.out.println("Vui lòng đăng nhập lại.");
            return;
        }
        // Hiển thị các bộ thẻ trong danh sách cá nhân
        System.out.println("Danh sách bộ thẻ cá nhân");
        List<Deck> userDecks = deckService.getUserCreatedDecks(user);
        for (Deck deck: userDecks){
            System.out.println("ID: " + deck.getId() + ", Chủ đề: " + deck.getTopic() + ", Cấp độ: " + deck.getLevel());
        }
        //Chọn và nhập ID  bộ thẻ muốn học
        System.out.println("Nhập ID bộ thẻ muốn học");
        int deckId = new Scanner(System.in).nextInt();
        Deck selected = null;
        for (Deck deck: userDecks){
            if(deck.getId() == deckId){
                selected = deck;
                break;
            }
        }
        if(selected == null){
            System.out.println("Không tìm thấy bộ thẻ với ID vừa nhập.");
            return;
        }
        //Hiển thị các thẻ học trong bộ thẻ đã chọn
        List<Card> cards = cardService.getCardsByDeck(selected);
        if (cards.isEmpty()){
            System.out.println("Bộ thẻ này không có thẻ học nào.");
            return;
        }
        System.out.println("Số lượng từ trong bộ thẻ: "+cards.size());

        Scanner inputEnter = new Scanner(System.in);
        for (Card card : cards){
            cardService.showCard(card);
            System.out.println("Nhấn Enter để chuyển qua từ tiếp theo....");
            inputEnter.nextLine();
        }
        System.out.println("Bạn đã hoàn thành bài học với bộ thẻ này");

    }

    public void studiedCards() {
        User user = userService.getLoggedInUser();
        if(user == null) {
            System.out.println("Vui lòng đăng nhập lại.");
            return;
        }
    }

    public void IncomingCards() {
    }
}
