public class Wand extends Weapon {
    
    public Wand(Entity owner, double x, double y){
        super(owner, x, y, (int) (Math.random() * 2) + 1, SpriteLoader.getSprite("wand"));
    }
    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new MagicOrb(x, y, angle, calculateDamage()));
            cooldownTimer = 50;
        }
    }

    @Override
    public void interact() {

    }
}