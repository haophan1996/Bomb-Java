

public enum MPOS {
    LEFT_CLICK("L"),
    RIGHT_CLICK("R");

    private final String description;
 
    MPOS(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }
}
