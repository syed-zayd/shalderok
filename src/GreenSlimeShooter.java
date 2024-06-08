
public class GreenSlimeShooter extends Weapon {
    
    public GreenSlimeShooter(Entity owner, double x, double y){
        super(owner, x, y, null);
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new GreenSlimeBall(x, y, angle, owner.damage));
            cooldownTimer = 150;
        }
    }

    @Override
    public void interact() {
        
    }

}
