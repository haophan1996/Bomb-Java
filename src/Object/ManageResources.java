package Object;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.image.Image;

public class ManageResources {
    private static final Map<String, Image> loaded_img = new HashMap<>(); 
    private static final Map<String, Clip> loaded_sound = new HashMap<>();   
    private static final String IMAGE_PATH_FORMAT = "/resources/GAME_UI/%s.png";
    private static final String SOUND_PATH_FORMAT = "/resources/GAME_SND/%s.wav";

    public static void load_resourcs(){
        System.out.print("Loading resources....");

        String data_resources_img[] = {
            "FLAG_IMG", "BLANK_IMG", "BOMB_IMG", "CURSOR_IMG", "BLANK_IMG", "HAND_IMG",
            "1", "2", "3", "4", "5", "6", "7", "8" };

        String data_resources_sound[] = {"LEFT_CLICK_SND", "LEFT_CLICK_SND_2", "LEFT_CLICK_SND_3", "RIGHT_CLICK_SND", "BOMB__EXPL_SND"};

        String path = "";
        for(int i = 0; i < data_resources_img.length; i++){
            if (loaded_img.containsKey(data_resources_img[i]) == false) {
                try {
                        path = String.format(IMAGE_PATH_FORMAT, data_resources_img[i]); 
                        Image img = new Image(ManageResources.class.getResourceAsStream(path));
                        loaded_img.put(data_resources_img[i], img);
                } catch (Exception e){
                    System.out.printf("File not found: \"%s\" %nCheck \"ManageResources.java\" for detail %nExit now", path);
                    System.exit(0);
                }
            }
        }  

        for(int i = 0; i < data_resources_sound.length; i++){
            if (loaded_sound.containsKey(data_resources_sound[i]) == false){ 
                path = String.format(SOUND_PATH_FORMAT, data_resources_sound[i]); 
                try(InputStream resources_sound = ManageResources.class.getResourceAsStream(path)) { 
                    if (resources_sound != null){ 
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resources_sound);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        loaded_sound.put(data_resources_sound[i], clip);
                    } else { 
                        System.out.printf("File not found: \"%s\" %nCheck \"ManageResources.java\" for detail %nExit now", path);
                        System.exit(0);
                    }
                }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
            }
        }


        System.out.println("Done!!!\n");
    }

    public static Image getImage(String name_img){
        if (loaded_img.containsKey(name_img)){
            return loaded_img.get(name_img);
        } else {
            System.out.printf("File not found: \"%s\" %nPlease add image into \"ManageResources.java\" to load before use %nExit now", name_img);
            System.exit(0); 
        } 
        return loaded_img.get(name_img);
    }

    public static Clip getSound(String name_snd){
        if (loaded_sound.containsKey(name_snd)){
            if (name_snd.equals("LEFT_CLICK_SND")){
                if (loaded_sound.get("LEFT_CLICK_SND").isRunning() == false) return loaded_sound.get("LEFT_CLICK_SND");
                else if (loaded_sound.get("LEFT_CLICK_SND_2").isRunning() == false) return loaded_sound.get("LEFT_CLICK_SND_2");
                else return loaded_sound.get("LEFT_CLICK_SND_3");
            }
            return loaded_sound.get(name_snd);
        } else {
            System.out.printf("File not found: \"%s\" %nPlease add SOUND into \"ManageResources.java\" to load before use %nExit now", name_snd);
            System.exit(0); 
        } 
        return loaded_sound.get(name_snd);
    }
}
