package entity;

import entity.Card;

import java.util.Date;
import java.util.List;

public class Test {
    private static int AUTO_ID = 100;

    private int id;
    private String name; //Tên bài test
    private String testStatus;
    private Date createdDate;
    private List<Card> card; //Danh sách thẻ cần test
    private int passScoreThreshold; // Ngưỡng điểm đạt

    public Test(){
        this.id = AUTO_ID;
        AUTO_ID++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<Card> getCard() {
        return card;
    }

    public void setCard(List<Card> card) {
        this.card = card;
    }

    public int getPassScoreThreshold() {
        return passScoreThreshold;
    }

    public void setPassScoreThreshold(int passScoreThreshold) {
        this.passScoreThreshold = passScoreThreshold;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", testStatus='" + testStatus + '\'' +
                ", createdDate=" + createdDate +
                ", card=" + card +
                ", passScoreThreshold=" + passScoreThreshold +
                '}';
    }
}
