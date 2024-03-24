import java.util.Scanner;

class Main {

    private static int SIZE_c = 8;
    private static int SIZE_r = 9;
    private static int BOMB_N = 10;

    public static void main(String[] args) {
        clearConsole();
        int r, c;
        Scanner scanner = new Scanner(System.in);
        Minesweeper m = new Minesweeper(SIZE_c,SIZE_r, BOMB_N);
        m.draw();
        Input();
        while (scanner.hasNextInt()) {
            clearConsole();
            r = scanner.nextInt();
            c = scanner.nextInt();
            System.out.printf("C: %d, R: %d %n", c, r);

            m.setStatusAt(r, c, Status.USER);

            System.out.printf("C: %d, R: %d %n", m.getSize_c(), m.getSize_r());
            m.draw();
            Input();
        }
    }

    public static void Input() {
        System.out.print("Enter Input (row column): ");
    }

    public static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
