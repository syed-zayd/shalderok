

public class Wand extends Weapon {
    
    public Wand(double x, double y){
        super(x, y, SpriteLoader.getSprite("wand"));
    }

    public void shoot(){
        queuedProjectiles.add(new MagicOrb(x, y, 5, angle));
    }

    @Override
    public void interact() {

    }
}