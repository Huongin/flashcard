package constant;

public enum State {
    Active("Cho phép học"),
    INACTIVE("Chưa được phép học");

    public String value;

    public String getValue() {
        return value;
    }

    State(String value) {
        this.value = value;
    }
}
