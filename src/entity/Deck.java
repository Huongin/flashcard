package entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import constant.Level;



public class Deck {
    private int id;
    private String topic;
    private Level level; //Mức độ đánh giá theo kỳ thi JLPT
    private List<Card> cards; //Danh sách các thẻ học
    private Date createdDate; //Ngày tạo bộ thẻ
    private String description; //Mô tả nội dung, nguồn tài liệu
    private User creator; // Người tạo ra bộ thẻ
    private List<User> assignedUser;


    public Deck(int id) {
        this.id = id;
    }

    public Deck( String topic, Level level, List<Card> cards, Date createdDate, String description, User creator) {
        this.topic = topic;
        this.level = level;
        this.cards = cards;
        this.createdDate = createdDate;
        this.description = description;
        this.creator = creator;
    }

    public Deck(List<User> assignedUser) {
        this.assignedUser = assignedUser;
    }

    public List<User> getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(List<User> assignedUser) {
        this.assignedUser = assignedUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }


    @Override
    public String toString() {
        return "Deck{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", level=" + level.getValue()+
                ", cards=" + cards +
                ", createdDate=" + createdDate +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                '}';
    }

}
