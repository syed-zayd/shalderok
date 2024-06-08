public class LeafShooter extends Weapon {
    
    public LeafShooter(Entity owner, double x, double y){
        super(owner, x, y, null);
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new Leaf(x, y, angle, owner.damage));
            cooldownTimer = 75;
        }
    }

    @Override
    public void interact() {
        
    }

}