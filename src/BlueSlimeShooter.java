public class BlueSlimeShooter extends Weapon {
    
    public BlueSlimeShooter(Entity owner, double x, double y){
        super(owner, x, y, null);
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new GreenSlimeBall(x, y, angle, owner.damage));
            cooldownTimer = 100;
        }
    }

    @Override
    public void interact() {
        
    }

}
