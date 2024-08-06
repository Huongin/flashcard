package service;

import Main.Main;
import constant.UserRole;
import entity.Card;
import entity.Deck;
import entity.Study;
import entity.User;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;


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

    public void studyCardList() {


    }

    public void personalCardList() {
    }

    public void studiedCards() {
    }

    public void IncomingCards() {
    }
}
