public class FireballShooter extends Weapon {
    
    public FireballShooter(Entity owner, double x, double y){
        super(owner, x, y, null);
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new Fireball(x, y, angle, owner.damage));
            cooldownTimer = 50;
        }
    }

    @Override
    public void interact() {
        
    }

}
