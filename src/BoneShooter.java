public class BoneShooter extends Weapon {
    
    public BoneShooter(Entity owner, double x, double y){
        super(owner, x, y, null);
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new Bone(x, y, angle, owner.damage));
            cooldownTimer = 100;
        }
    }

    @Override
    public void interact() {
        
    }

}
