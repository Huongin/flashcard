package entity;

import java.util.Date;
import java.util.List;

public class TestDetail {
    private User user;
    private Test test;
    private Date testDate; //Ngày làm test
    private List<Card> correctCard;
    private List<Card> incorrectCard; //Danh sách các thẻ trả lời sai
    private int Score; //Điểm số

    public TestDetail(User user, Test test, Date date, List<Card> correctCards, List<Card> incorrectCards, int score) {
        this.user = user;
        this.test = test;
        this.testDate = testDate;
        this.correctCard = correctCards;
        this.incorrectCard = incorrectCard;
        Score = score;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public List<Card> getCorrectCard() {
        return correctCard;
    }

    public void setCorrectCard(List<Card> corectCard) {
        this.correctCard = corectCard;
    }

    public List<Card> getIncorrectCard() {
        return incorrectCard;
    }

    public void setIncorrectCard(List<Card> incorrectCard) {
        this.incorrectCard = incorrectCard;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    @Override
    public String toString() {
        return "TestDetail{" +
                "user=" + user +
                ", test=" + test +
                ", testDate=" + testDate +
                ", correctCard=" + correctCard +
                ", incorrectCard=" + incorrectCard +
                ", Score=" + Score +
                '}';
    }
}
