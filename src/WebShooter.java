
public class WebShooter extends Weapon {
    
    public WebShooter(Entity owner, double x, double y){
        super(owner, x, y, null);
    }

    public void shoot(){
        if(canShoot()){
            System.out.println("attacked");
            queuedProjectiles.add(new Web(x, y, 3, angle, owner.damage));
            cooldownTimer = 200;
        }
    }

    @Override
    public void interact() {
        
    }

}
