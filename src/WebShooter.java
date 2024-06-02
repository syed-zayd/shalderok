import java.awt.Graphics2D;

public class WebShooter extends Weapon {
    
    public WebShooter(double x, double y){
        super(x, y, null);
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
