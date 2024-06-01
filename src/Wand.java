import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import java.awt.*;

public class Wand extends Weapon {
    
    public Wand(double x, double y){
        super(x, y, SpriteLoader.getSprite("wand"));
    }

    public void shoot(){
        queuedProjectiles.add(new MagicOrb(x, y, 5, angle));
    }

}
