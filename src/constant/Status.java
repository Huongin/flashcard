package constant;

public enum Status {
    ACTIVE("Cho phép"),
    INACTIVE("Chưa được phép");

    public String value;

    public String getValue() {
        return value;
    }

    Status(String value) {
        this.value = value;
    }
}
