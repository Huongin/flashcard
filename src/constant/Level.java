package constant;

public enum Level {
    N1("Trình độ JLPT N1"),
    N2("Trình độ JLPT N2"),
    N3("Trình độ JLPT N3"),
    N4("Trình độ JLPT N4"),
    N5("Trình độ JLPT N5");

    public String value;

    public String getValue() {
        return value;
    }

    Level(String value) {
        this.value = value;
    }
}
