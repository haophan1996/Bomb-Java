package Object;

public class GameSetting {
    private static int bomb;
    private static int size_c;
    private static int size_r;
    private static int width;
    private static int height;

    public static int get_size_c(){
        return size_c;
    }

    public static int get_size_r(){
        return size_r;
    }

    public static int get_bomb(){
        return bomb;
    }

    public static int get_width (){
        return width;
    }

    public static int get_height (){
        return height;
    }

    public static void setClassic(){
        size_c = size_r = 8;
        bomb = 10;
        width = 350;//437; +320 + 30
        height = 410;//495; 320 + 90
    }

    public static void setEasy(){
        size_c = size_r = 9;
        bomb = 12;
        width = 390; //53
        height = 450; //50
    }

    public static void setMedium(){
        size_c = size_r = 16;
        bomb = 42;
        width = 670;
        height = 730;
    }

    public static void setExpert(){
        size_c = 30;
        size_r = 16;
        bomb = 45;
        width = 1240;
        height = 730;
    }

    public static void setCustom(int s_c, int s_r){
        size_c = s_c;
        size_r = s_r;
        bomb = 40;
    }
}
