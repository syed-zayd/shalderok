
public class WebShooter extends Weapon {
    
    public WebShooter(Entity owner, double x, double y){
        super(owner, x, y, SpriteLoader.getSprite("wand"));
    }

    public void shoot(){
        if(canShoot()){
            System.out.println("attacked");
            queuedProjectiles.add(new Web(x, y, 5, angle, owner.damage));
            cooldownTimer = 50;
        }
    }

    @Override
    public void interact() {
        
    }

}
