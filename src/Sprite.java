import java.util.ArrayList;
import java.util.HashMap;

import java.awt.image.BufferedImage;

public class Sprite {
    
    private HashMap<String, ArrayList<BufferedImage>> sprites;

    public Sprite(){
        sprites = new HashMap<String, ArrayList<BufferedImage>>();
    }

    public void addState(String state, ArrayList<BufferedImage> spriteList){
        sprites.put(state, spriteList);
    }

    public BufferedImage getSprite(String state, int index){
        return sprites.get(state).get(index);
    }

    public void printSprite(){
        for(String key: sprites.keySet()){
            for(BufferedImage sprite: sprites.get(key)){
                System.out.println(sprite.getWidth());
            }
        }
    }

}
