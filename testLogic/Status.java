 
public enum Status {
    FLAG("F"),
    BOMB("B"),
    BOMB_EXP("E"), //SHOW all BOM WHEN LOSE
    BOMB_UNDER_FLAG("BF"),
    EMPTY("X"),
    CHECKED(" "),
    USER("U"), 
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8");


    private final String description;

    public static Status set(int num) {
        switch (num) {
            case 1: return Status.ONE;
            case 2: return Status.TWO;
            case 3: return Status.THREE;
            case 4: return Status.FOUR;
            case 5: return Status.FIVE;
            case 6: return Status.SIX;
            case 7: return Status.SEVEN;
            case 8: return Status.EIGHT; 
            default: return Status.CHECKED;
        }
    }

    Status(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }
} 