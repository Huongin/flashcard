package entity;


import constant.CardType;
import constant.State;

public class Card {
    private static int AUTO_ID = 1;

    private int id;
    private String word;
    private String phonetic;
    private String meaning;
    private CardType cardType;
    private State state;
    private String example;
    private User creator; //Người tạo thẻ học
    private Deck deck; // Bộ thẻ mà thẻ học thuộc về

    public Card(){
        this.id = AUTO_ID;
        AUTO_ID++;
    }


    public Card(int id, String word, String phonetic, String meaning,
                CardType cardType, State state, String example, User creator, Deck deck) {
        this.id = id;
        this.word = word;
        this.phonetic = phonetic;
        this.meaning = meaning;
        this.cardType = cardType;
        this.state = state;
        this.example = example;
        this.creator = creator;
        this.deck = deck;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", meaning='" + meaning + '\'' +
                ", cardType=" + cardType.getValue()+
                ", state=" + state.getValue()+
                ", example='" + example + '\'' +
                ", creator=" + creator +
                ", deck=" + deck +
                '}';
    }
}
