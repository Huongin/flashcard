package constant;

public enum CardType {
    Noun("Danh từ"),
    Adji("Tính từ đuôi i"),
    AdjNa("Tính từ đuôi na"),
    Verb("Động từ"),
    Other("Loại từ khác");

    public String value;

    public String getValue() {
        return value;
    }

    CardType(String value) {
        this.value = value;
    }
}
