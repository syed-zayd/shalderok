

public class Wand extends Weapon {
    
    public Wand(double x, double y){
        super(x, y, SpriteLoader.getSprite("wand"));
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new MagicOrb(x, y, 5, angle));
            cooldownTimer = 50;
        }
    }

    @Override
    public void interact() {

    }
}