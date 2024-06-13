public class LavaRockShooter extends Weapon {
    
    public LavaRockShooter(Entity owner, double x, double y){
        super(owner, x, y, 1.0, null);
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new LavaRock(x, y, angle, calculateDamage()));
            cooldownTimer = 100;
        }
    }

    @Override
    public void interact() {
        
    }

}
