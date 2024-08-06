package entity;

import java.util.List;
import entity.User;
import entity.Card;

public class Study {
    private User user;
    private Card card;
    private List<Card> studiedCards;
    private List<Card> incommingCards;


    public Study(User user, Card card, List<Card> studiedCards, List<Card> incommingCards) {
        this.user = user;
        this.card = card;
        this.studiedCards = studiedCards;
        this.incommingCards = incommingCards;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Card> getStudiedCards() {
        return studiedCards;
    }

    public void setStudiedCards(List<Card> studiedCards) {
        this.studiedCards = studiedCards;
    }

    public List<Card> getIncommingCards() {
        return incommingCards;
    }

    public void setIncommingCards(List<Card> incommingCards) {
        this.incommingCards = incommingCards;
    }

    @Override
    public String toString() {
        return "Study{" +
                "user=" + user +
                ", card=" + card +
                ", studiedCards=" + studiedCards +
                ", incommingCards=" + incommingCards +
                '}';
    }
}
