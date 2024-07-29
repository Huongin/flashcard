package src;

import src.constant.StudyStatus;

public class CardStudy {
    private User user;
    private Card card;
    private StudyStatus status;

    public CardStudy(User user, Card card, StudyStatus status) {
        this.user = user;
        this.card = card;
        this.status = status;
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

    public StudyStatus getStatus() {
        return status;
    }

    public void setStatus(StudyStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CardStudy{" +
                "user=" + user +
                ", card=" + card +
                ", status=" + status.getValue()+
                '}';
    }
}
