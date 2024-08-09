package service;

import entity.Card;
import entity.Deck;
import entity.Study;
import entity.User;
import util.FileUtil;

import java.util.ArrayList;
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
    public void setStudy(){
        List<Study> studyList = fileUtil.readDataFromFile(STUDY_DATA_FILE,Study[].class);
        studies = studyList != null ? studyList : new ArrayList<>();
    }
    private void saveStudyData() {
        fileUtil.writeDataToFile(studies,STUDY_DATA_FILE);
    }

    // Tìm đối tượng study tương ứng với User và Deck nhập vào
    private Study getStudyByUserAndDeck(User user, Deck deck){
        for (Study study : studies){
            if(study.getUser().equals(user) && study.getDeck().equals(deck)){
                return study;
            }
        }
        return null;
    }


    public void studyWithPublicCards() {
        User user = userService.getLoggedInUser();

        // Hiển thị các bộ thẻ trong danh sách chung
        System.out.println("Danh sách bộ thẻ chung");
        List<Deck> publicDecks = deckService.getAdminCreatedDecks();
        if (publicDecks.isEmpty()){
            System.out.println("Không có bộ thẻ nào do admin tạo để sử dụng");
            return;
        }
        //Hiển thị danh sách bộ thẻ
        System.out.println("Chọn bộ thẻ bạn muốn: ");
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
        //Tạo mới Study nếu chưa có
        Study userStudy = getStudyByUserAndDeck(user,selected);
        if (userStudy == null){
            userStudy = new Study(user,selected,new ArrayList<>(),new ArrayList<>(cards));
            studies.add(userStudy);
        }
        System.out.println("Số lượng từ trong bộ thẻ: " + cards.size());

        Scanner inputEnter = new Scanner(System.in);
        List<Card> cardsRemove = new ArrayList<>(); //Danh sách card sau khi học xong sẽ xóa khỏi danh sách chưa học
        for (Card card : userStudy.getIncomingCards()){
            cardService.showCard(card);
            System.out.println("Nhấn Enter để chuyển qua từ tiếp theo....");
            inputEnter.nextLine();

            userStudy.getStudiedCards().add(card); //Những thẻ đã học sẽ được chuyển vào danh sách đã học
            cardsRemove.add(card); //những thẻ học rồi cho danh sách riêng tí xóa
        }
        userStudy.getIncomingCards().removeAll(cardsRemove); //xóa danh sách những thẻ đã học trong những
        System.out.println("Bạn đã hoàn thành bài học với bộ thẻ này");

        //Lưu lại
        saveStudyData();
    }

    public void studyWithPersonalCards() {
        User user = userService.getLoggedInUser();

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

        //Tạo mới Study nếu chưa có
        Study userStudy = getStudyByUserAndDeck(user,selected);
        if (userStudy == null){
            userStudy = new Study(user,selected,new ArrayList<>(),new ArrayList<>(cards));
            studies.add(userStudy);
        }

        System.out.println("Số lượng từ trong bộ thẻ: " + cards.size());

        Scanner inputEnter = new Scanner(System.in);
        List<Card> cardsRemove = new ArrayList<>(); //Danh sách cards sau khi học xong sẽ xóa khỏi danh sách chưa học
        for (Card card : userStudy.getIncomingCards()){
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
        User user = userService.getLoggedInUser();

        List<Card> studiedCards = new ArrayList<>();

        for (Study study : studies){
            if (study.getUser().equals(user)){
                studiedCards.addAll(study.getStudiedCards());
            }
        }
        if (studiedCards.isEmpty()) {
            System.out.println("Bạn chưa có thẻ nào được học.");
        }else {
            System.out.println("Danh sách các thẻ đã học: ");
            for (Card card : studiedCards){
                System.out.println(card);
            }
        }
    }

    public void IncomingCards() {

        User user = userService.getLoggedInUser();

        List<Card> incomingCards = new ArrayList<>();

        for (Study study : studies) {
            if (study.getUser().equals(user)) {
                incomingCards.addAll(study.getIncomingCards());
            }
        }
        if (incomingCards.isEmpty()) {
            System.out.println("Bạn chưa có thẻ nào được học.");
        } else {
            System.out.println("Danh sách các thẻ đã học: ");
            for (Card card : incomingCards) {
                System.out.println(card);
            }
        }
    }
}
