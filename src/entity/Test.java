package entity;

import constant.Status;

import java.util.Date;
import java.util.List;

public class Test {

    private int id;
    private String name; //Tên bài test
    private Status testStatus;// Trạng thái bài test
    private Date createdDate; //Ngày tạo bài test
    private List<Card> card; //Danh sách thẻ cần test
    private int passScoreThreshold; // Ngưỡng điểm đạt

    public Test(int i) {
        this.id = id;
    }

    public Test(int id, String name, Status testStatus, Date createdDate, List<Card> card, int passScoreThreshold) {
        this.id = id;
        this.name = name;
        this.testStatus = testStatus;
        this.createdDate = createdDate;
        this.card = card;
        this.passScoreThreshold = passScoreThreshold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(Status testStatus) {
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
                ", testStatus=" + testStatus.getValue() +
                ", createdDate=" + createdDate +
                ", card=" + card +
                ", passScoreThreshold=" + passScoreThreshold +
                '}';
    }
}
