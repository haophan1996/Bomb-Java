
public enum RoundStatus {
    WIN("W"),
    LOSE("L"),
    VIEWBOARD("V"),
    INGAME("I");

    private final String description; 
     
    RoundStatus(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }
}
