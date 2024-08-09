package constant;

public enum State {
    ACTIVE("Cho phép"),
    INACTIVE("Chưa được phép");

    public String value;

    public String getValue() {
        return value;
    }

    State(String value) {
        this.value = value;
    }
}
