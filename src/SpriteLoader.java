import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SpriteLoader {
    
    public static HashMap<String, Sprite> sprites;

    public static void loadSprites(){

        sprites = new HashMap<String, Sprite>();
        
        try {

            File spritesFolder = new File("sprites");
            File[] spritesDirs = spritesFolder.listFiles();

            for(File spritesDir: spritesDirs){

                System.out.println("Loading " + spritesDir.getName() + "...");

                if(!spritesDir.isDirectory()){
                    System.out.println(spritesDir.getName() + " is not a directory...");
                    continue;
                }

                File[] objects = spritesDir.listFiles();

                for(File obj: objects){
                    System.out.println("\tLoading " + obj.getName() + "...");

                    Sprite s = new Sprite();
                    File[] states = obj.listFiles();
                    
                    for(File state: states){

                        System.out.println("\t\tLoading " + state.getName() + "...");
    
                        ArrayList<BufferedImage> spriteList = new ArrayList<BufferedImage>();
    
                        File[] sprites = state.listFiles();
    
                        for(File sprite: sprites){
                            spriteList.add(ImageIO.read(sprite));
                        }
    
                        s.addState(state.getName(), spriteList);
    
                    }

                    sprites.put(obj.getName(), s);
    
                }

            }

        } catch(IOException e){
            System.out.println("Error: The path of the image could not be found.");
        } catch(NullPointerException e){
            System.out.println("Error: File type is not compatible  with expected file format.");
        }
    }

    public static Sprite getSprite(String spriteName){
        return sprites.get(spriteName);
    }

}
