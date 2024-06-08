public class WebShooter extends Weapon {
    
    public WebShooter(Entity owner, double x, double y){
        super(owner, x, y, null);
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new Web(x, y, angle, owner.damage));
            cooldownTimer = 300;
        }
    }

    @Override
    public void interact() {
        
    }

}
