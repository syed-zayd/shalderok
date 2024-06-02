import java.util.ArrayList;
import java.util.HashMap;

import java.awt.image.BufferedImage;

public class Sprite {
    
    private HashMap<String, ArrayList<BufferedImage>> sprites;

    public Sprite(){
        sprites = new HashMap<String, ArrayList<BufferedImage>>();
    }

    public void rotate(double angle){
        
        for(String key: sprites.keySet()){
            for(int i = 0; i < sprites.get(key).size(); i++){
                sprites.get(key).set(i, Util.rotateImage(sprites.get(key).get(i), angle));
            }
        }

    }

    public void addState(String state, ArrayList<BufferedImage> spriteList){
        sprites.put(state, spriteList);
    }

    public int getNumFrames(String state){
        return sprites.get(state).size();
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

    public int getWidth(){
        return sprites.get("idle").get(0).getWidth();
    }

    public int getHeight(){
        return sprites.get("idle").get(0).getHeight();
    }

}
