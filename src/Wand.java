import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.*;

public class Wand extends Weapon {

    private BufferedImage sprite;
    
    public Wand(double x, double y){
        super(x, y, 25, 50);
        try {
            sprite = ImageIO.read(new File("sprites/wand.png"));
        } catch(IOException e){
            
        }
    }

    public void shoot(){

    }

    public void paint(Graphics2D g2d){
        Util.rotateImage(sprite, angle);
        g2d.drawImage(sprite, drawX(), drawY(), null);
    }

    public void update(){

    }

}
