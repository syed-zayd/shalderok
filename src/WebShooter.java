
public class WebShooter extends Weapon {
    
    public WebShooter(Entity owner, double x, double y){
        super(owner, x, y, null);
    }

    public void shoot(){
        if(canShoot()){
            queuedProjectiles.add(new Web(x, y, 5, angle));
            cooldownTimer = 50;
        }
    }

    @Override
    public void interact() {
        
    }

}
