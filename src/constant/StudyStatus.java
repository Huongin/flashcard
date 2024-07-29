package src.constant;

public enum StudyStatus {
    learned("Đã thuộc"),
    notlearned("Chưa thuộc");

    public String value;

    public String getValue() {
        return value;
    }

    StudyStatus(String value) {
        this.value = value;
    }
}
