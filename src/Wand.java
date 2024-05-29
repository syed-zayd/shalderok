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

    @Override
    public void shoot(){
        projectiles.add(new Projectile(x, y, angle, 20, 20, Projectile.LINEAR));
        System.out.println(angle);
    }

    @Override
    public void paint(Graphics2D g2d){
        g2d.drawImage(sprite, drawX(), drawY(), null);
        for(Projectile projectile: projectiles){
            projectile.paint(g2d);
        }
    }

    @Override
    public void update(){
        for(Projectile projectile: projectiles){
            projectile.update();
        }
    }

}
