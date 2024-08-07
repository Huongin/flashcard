package entity;

import java.util.List;

public class Study {
    private User user;
    private Deck deck;
    private List<Card> studiedCards; // Danh sách các thẻ đã học
    private List<Card> incomingCards; // Danh sách các thẻ chưa học (sắp học)


    public Study(User user, Deck deck, List<Card> studiedCards, List<Card> incomingCards) {
        this.user = user;
        this.deck = deck;
        this.studiedCards = studiedCards;
        this.incomingCards = incomingCards;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public List<Card> getStudiedCards() {
        return studiedCards;
    }

    public void setStudiedCards(List<Card> studiedCards) {
        this.studiedCards = studiedCards;
    }

    public List<Card> getIncomingCards() {
        return incomingCards;
    }

    public void setIncomingCards(List<Card> incomingCards) {
        this.incomingCards = incomingCards;
    }

    @Override
    public String toString() {
        return "Study{" +
                "user=" + user +
                ", deck=" + deck +
                ", studiedCards=" + studiedCards +
                ", incommingCards=" + incomingCards +
                '}';
    }
}
