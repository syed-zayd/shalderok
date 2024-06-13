public class LeafShooter extends Weapon {
    
    public LeafShooter(Entity owner, double x, double y){
        super(owner, x, y, 1.0, null);
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new Leaf(x, y, angle, calculateDamage()));
            cooldownTimer = 75;
        }
    }

    @Override
    public void interact() {
        
    }

}