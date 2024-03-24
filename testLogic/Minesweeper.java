import java.util.Random;

/**
 * minesweeper
 */
public class Minesweeper {
    private Status data[][];
    private int size_c; 
    private int size_r; 
    private int bomb_n;
    private RoundStatus roundStatus;

    public Minesweeper(int size_c, int size_r, int bomb_n){ 
        this.size_c = size_c;
        this.size_r = size_r;
        this.bomb_n = bomb_n;
        this.data = new Status[size_c][size_r];  
        this.roundStatus = RoundStatus.INGAME;
        initEmptyBoard(); 
    }

    private void initEmptyBoard() {
        for (int c = 0; c < this.size_c; c++){
            for (int r = 0; r < this.size_r; r++){
                data[c][r] = Status.EMPTY;
            }
        }
        setRandomBomb();
    }

    private void setRandomBomb(){
        Random random = new Random();
        int bomb_temp = bomb_n;
        while(bomb_temp > 0){
            int c = random.nextInt(size_c);
            int r = random.nextInt(size_r);
            if (data[c][r] == Status.BOMB)
                continue;

            data[c][r] = Status.BOMB; 
            --bomb_temp;
        }   
    } 
    
    public int getSize_c() {
        return size_c;
    }

    public int getSize_r() {
        return size_r;
    }

    public Status getStatus(int c, int r) {
        return data[c][r];
    }
 
    public void setStatusAt(int c, int r, Status status) { 

        if (this.roundStatus != RoundStatus.INGAME)
            return;

        if (status == Status.FLAG){
            //Ignore if Cell already opened
            if (getStatus(c, r) != Status.EMPTY && getStatus(c, r) != Status.BOMB && getStatus(c, r) != Status.FLAG && getStatus(c, r) != Status.BOMB_UNDER_FLAG)
                return; 

            if (getStatus(c, r) == Status.FLAG) {
                data[c][r] = Status.EMPTY;
            } else if (getStatus(c, r) == Status.EMPTY){
                data[c][r] = Status.FLAG;
            } else if (getStatus(c, r) == Status.BOMB){
                data[c][r] = Status.BOMB_UNDER_FLAG;
            } else { 
                data[c][r] = Status.BOMB;
            }
            System.out.println(getStatus(c, r));
            return;
        }

        if (getStatus(c, r) == Status.BOMB) {
            System.out.println("LOSE!!!!, You got bomb");
            this.roundStatus = RoundStatus.LOSE;
            return;
        } 

        if (getStatus(c, r) != Status.EMPTY){
            System.out.println("Invalid!!!, please select again");
            return;
        } 
        data[c][r] = Status.USER;   
        scanBomb(c,r); 
    }

    public RoundStatus getRoundStatus() {
        if (this.roundStatus == RoundStatus.LOSE || this.roundStatus == RoundStatus.VIEWBOARD) return this.roundStatus;

        int empty = 0;
        for (int r = 0; r < this.size_r; r++) {
            for (int c = 0; c < this.size_r; c++) { 
                if (data[c][r] == Status.EMPTY || data[c][r] == Status.FLAG){
                    ++empty;
                }
            }
        } 
        if (empty <= 0) {
            return RoundStatus.WIN;
        } else return RoundStatus.INGAME;
    }

    public void setRoundStatus(RoundStatus roundStatus){
        this.roundStatus = roundStatus;
    }

    private int getCountBombAround(int c, int r){
        int bomb_n = 0;
        for (int temp_c = c - 1; temp_c <= c + 1; temp_c++) {
            for (int temp_r = r - 1; temp_r <= r + 1; temp_r++) { 
                //Check valid if pointer is out of range or skip if pointer equal to point C and R
                if (temp_c < 0 || temp_c > size_c - 1 || temp_r < 0 || temp_r > size_r - 1 || temp_r == r && temp_c == c) continue;
                 
                if (data[temp_c][temp_r] == Status.BOMB){
                    ++bomb_n;
                }
            }
        }
        return bomb_n;
    }

    public void scanBomb(int c, int r){   
        for (int temp_c = c - 1; temp_c <= c + 1; temp_c++) {
            for (int temp_r = r - 1; temp_r <= r + 1; temp_r++) { 
                //Check valid if pointer is out of range or skip if pointer equal to point C and R
                if (temp_c < 0 || temp_c > size_c - 1 || temp_r < 0 || temp_r > size_r - 1 || temp_r == r && temp_c == c) continue;
                  
                Status cellStatus = data[temp_c][temp_r];

                // Skip flagged cells
                if (cellStatus == Status.FLAG || cellStatus == Status.BOMB_UNDER_FLAG) continue;

                if (cellStatus == Status.EMPTY ){

                    if (cellStatus != Status.BOMB){
                        int bomb_n = getCountBombAround(temp_c, temp_r);
 
                        data[temp_c][temp_r] = Status.set(bomb_n);

                        if (bomb_n == 0) scanBomb(temp_c, temp_r);
                    } 
                }
            }
        }
    } 

    public void restart(){
        initEmptyBoard(); 
        this.roundStatus = RoundStatus.INGAME; 
    }

    void draw() {
        System.out.print("   ");
        for (int i = 0; i < size_c; i++) {
            System.out.print(i + " ");
        }
        System.out.printf(" Row %n"); // Change "Row" to "Column"
    
        for (int r = 0; r < this.size_r; r++) { // Switch c with r
            System.out.print(r + "  ");
            for (int c = 0; c < this.size_c; c++) { // Switch r with c
                System.out.print(data[c][r] + " "); // Switch c with r
            }
            System.out.println();
        }
        System.out.println("\nColumn"); // Change "Column" to "Row"
    } 
}