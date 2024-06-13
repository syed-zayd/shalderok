public class RockShooter extends Weapon {
    
    public RockShooter(Entity owner, double x, double y){
        super(owner, x, y, 1.0, null);
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new Rock(x, y, angle, calculateDamage()));
            cooldownTimer = 200;
        }
    }

    @Override
    public void interact() {
        
    }

}
