public class WebShooter extends Weapon {
    
    public WebShooter(Entity owner, double x, double y){
        super(owner, x, y, 1.0, null);
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new Web(x, y, angle, calculateDamage()));
            cooldownTimer = 500;
        }
    }

    @Override
    public void interact() {
        
    }

}
