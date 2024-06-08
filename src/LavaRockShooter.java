public class LavaRockShooter extends Weapon {
    
    public LavaRockShooter(Entity owner, double x, double y){
        super(owner, x, y, null);
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new LavaRock(x, y, angle, owner.damage));
            cooldownTimer = 100;
        }
    }

    @Override
    public void interact() {
        
    }

}
